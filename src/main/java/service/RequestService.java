package service;

import dto.*;
import entity.DocumentType;
import entity.Request;
import exception.CustomExceptionMessages;
import exception.InvalidDocumentException;
import exception.InvalidRequestMakingException;
import exception.InvalidUserException;
import mapper.DocumentTypeMapper;
import mapper.RequestMapper;
import mapper.ResidenceMapper;
import mapper.UserMapper;
import repository.RequestRepo;
import utils.ApplicationUtils;
import validator.DocumentTypeValidator;
import validator.RequestValidator;
import validator.UserValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RequestService {

    private RequestRepo requestRepo;

    public RequestService(){
        this.requestRepo = new RequestRepo();
    }

    public void addRequest(RequestDto requestDto) throws InvalidUserException, InvalidDocumentException, InvalidRequestMakingException {
        if (UserValidator.isUserValid(requestDto.getUser().getUsername())){
            throw new InvalidUserException(CustomExceptionMessages.INVALID_USER_MESSAGE);
        }

        if (DocumentTypeValidator.isDocumentTypeValid(requestDto.getDocumentType().getId())){
            throw new InvalidDocumentException(CustomExceptionMessages.INVALID_DOCUMENT_MESSAGE);
        }

        if(!RequestValidator.isRequestMakingValid(requestDto)){
            throw new InvalidRequestMakingException(CustomExceptionMessages.INVALID_REQUEST_MAKING_MESSAGE);
        }
        requestRepo.insertNewRequest(RequestMapper.dtoToEntity(requestDto));
    }

    public void updateRequest(RequestDto requestDto) throws InvalidUserException, InvalidDocumentException {
        if (UserValidator.isUserValid(requestDto.getUser().getUsername())){
            throw new InvalidUserException(CustomExceptionMessages.INVALID_USER_MESSAGE);
        }

        if (DocumentTypeValidator.isDocumentTypeValid(requestDto.getDocumentType().getId())){
            throw new InvalidDocumentException(CustomExceptionMessages.INVALID_DOCUMENT_MESSAGE);
        }

        requestRepo.updateRequest(RequestMapper.dtoToEntity(requestDto));
    }

    public void deleteRequest(String id){
        requestRepo.removeRequest(id);
    }

    public RequestDto getRequestById(String id){
        return RequestMapper.entityToDto(requestRepo.getRequestById(id));
    }

    public List<RequestDto> getRequestsByDocumentType(DocumentType documentType){
        List<RequestDto> requestDtoList = new ArrayList<>();
        List<Request> requests = requestRepo.getRequestsByDocumentType(documentType);
        for(Request r: requests){
            requestDtoList.add(RequestMapper.entityToDto(r));
        }
        return requestDtoList;
    }

    public List<RequestDto> getAllRequests(){
        List<Request> requests = requestRepo.findAllRequests();
        if(requests.isEmpty()){
            throw new IllegalArgumentException(CustomExceptionMessages.NO_REQUESTS_MESSAGE);
        }
        List<RequestDto> requestDtos = new ArrayList<>();

        for (Request request: requests) {
            RequestDto requestDto= RequestMapper.entityToDto(request);
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }

    public void approveRequest(RequestDto requestDto) throws InvalidDocumentException, InvalidUserException {
        requestDto.setApproved(true);
        requestDto.setApprovalDate(LocalDate.now());
        updateRequest(requestDto);
    }

    public RequestDto makeRequest(UserDto user, String selectedType, String content, String selectedResidence) throws InvalidUserException, InvalidDocumentException, InvalidRequestMakingException {

        DocumentTypeService documentTypeService = new DocumentTypeService();
        DocumentTypeDto documentTypeDto = documentTypeService.getDocumentTypeByName(selectedType);

        ResidenceService residenceService = new ResidenceService();
        String[] parts = selectedResidence.split(", |[str.] | [nr.]");
        ResidenceDto residenceDto = residenceService.getResidenceByCityStreetNumberUser(parts[0], parts[2], parts[4], user);

        Request request = new Request(UserMapper.dtoToEntity(user), DocumentTypeMapper.dtoToEntity(documentTypeDto), content, ResidenceMapper.dtoToEntity(residenceDto));
        addRequest(RequestMapper.entityToDto(request));
        return  RequestMapper.entityToDto(request);
    }

    public RequestDto updateRequestByUser(RequestDto requestDto, String content, String selectedResidence) throws InvalidUserException, InvalidDocumentException, InvalidRequestMakingException {

        ResidenceService residenceService = new ResidenceService();
        String[] parts = selectedResidence.split(", |[str.] | [nr.]");
        ResidenceDto residenceDto = residenceService.getResidenceByCityStreetNumberUser(parts[0], parts[2], parts[4], UserMapper.entityToDto(requestDto.getUser()));

        requestDto.setContent(content);
        requestDto.setResidenceDto(residenceDto);

        updateRequest(requestDto);
        return  requestDto;
    }

    public List<RequestDto> getRequestsByApproval(boolean approval) {
        List<RequestDto> requestDtoList = new ArrayList<>();
        List<Request> requests = requestRepo.getRequestsByApproval(approval);
        for(Request r: requests){
            requestDtoList.add(RequestMapper.entityToDto(r));
        }
        return requestDtoList;
    }

    public List<RequestTableDto> createTableDtoListFromDtoList(List<RequestDto> requestDtoList)
    {
        List<RequestTableDto> requestTableDtoList = new ArrayList<>();

        for (RequestDto r : requestDtoList) {
            RequestTableDto requestTableDto = RequestMapper.dtoToTableDto(r);
            requestTableDtoList.add(requestTableDto);
        }

        return requestTableDtoList;
    }

    public List<RequestTableDto> searchRequestByUserAndDocumentType(String userBoxValue, String documentTypeBoxValue) {
        List<RequestTableDto> requestTableDtoList = null;
        if (documentTypeBoxValue == null && userBoxValue == null) {
            ApplicationUtils.displayMessage("There is no type or user selected! Please select a type and/or a user!", "Error", "Assets/no.jpg");
        } else {
            if (documentTypeBoxValue != null && userBoxValue == null) {
                RequestService requestService = new RequestService();
                DocumentTypeService documentTypeService = new DocumentTypeService();
                DocumentType documentType = DocumentTypeMapper.dtoToEntity(documentTypeService.getDocumentTypeByName(documentTypeBoxValue));
                List<RequestDto> requestDtoList = requestService.getRequestsByDocumentType(documentType);

                requestTableDtoList = createTableDtoListFromDtoList(requestDtoList);
            } else {
                if (documentTypeBoxValue == null) {
                    UserService userService = new UserService();
                    UserDto userDto = userService.getUserByName(userBoxValue);
                    List<RequestDto> requestDtoList = userService.getUsersRequests(UserMapper.dtoToEntity(userDto));

                    requestTableDtoList = createTableDtoListFromDtoList(requestDtoList);
                } else {
                    UserService userService = new UserService();
                    UserDto userDto = userService.getUserByName(userBoxValue);
                    List<RequestDto> requestDtoList = userService.getUsersRequests(UserMapper.dtoToEntity(userDto));

                    List<RequestDto> typeRequests = new ArrayList<>();
                    for (RequestDto r : requestDtoList) {
                        if (r.getDocumentType().getName().equals(documentTypeBoxValue)) {
                            typeRequests.add(r);
                        }
                    }
                    requestTableDtoList = createTableDtoListFromDtoList(typeRequests);
                }
            }
        }
        return requestTableDtoList;
    }

    public List<RequestDto> sortRequestDtoAscending(List<RequestDto> requestDtoList){
        List<RequestDto> list = new ArrayList<>(requestDtoList);
        Collections.sort(list, new Comparator<RequestDto>() {
            @Override
            public int compare(RequestDto r1, RequestDto r2) {
                return r1.getDocumentType().getName().compareTo(r2.getDocumentType().getName());
            }
        });
        return list;
    }

    public List<RequestDto> sortRequestDtoDescending(List<RequestDto> requestDtoList){
        List<RequestDto> list = new ArrayList<>(requestDtoList);
        Collections.sort(list, new Comparator<RequestDto>() {
            @Override
            public int compare(RequestDto r1, RequestDto r2) {
                return r2.getDocumentType().getName().compareTo(r1.getDocumentType().getName());
            }
        });
        return list;
    }

    public List<RequestTableDto> sortRequestTableDtoAscending(List<RequestTableDto> requestTableDtoList){
        List<RequestTableDto> list = new ArrayList<>(requestTableDtoList);
        Collections.sort(list, new Comparator<RequestTableDto>() {
            @Override
            public int compare(RequestTableDto r1, RequestTableDto r2) {
                return r1.getUser().compareTo(r2.getUser());
            }
        });
        return list;
    }

    public List<RequestTableDto> sortRequestTableDtoDescending(List<RequestTableDto> requestTableDtoList){
        List<RequestTableDto> list = new ArrayList<>(requestTableDtoList);
        Collections.sort(list, new Comparator<RequestTableDto>() {
            @Override
            public int compare(RequestTableDto r1, RequestTableDto r2) {
                return r2.getUser().compareTo(r1.getUser());
            }
        });
        return list;
    }

    public boolean areAllRequestsApproved(List<RequestDto> requestDtoList, ResidenceDto residenceDto){
        boolean allApproved = true;
        for (RequestDto r : requestDtoList) {
            if (r.getResidenceDto().equals(residenceDto)) {
                if (!r.isApproved()) {
                    allApproved = false;
                    break;
                }
            }
        }
        return allApproved;
    }

    public void eliminateRequests(List<RequestDto> requestDtoList, ResidenceDto residenceDto){
        for (RequestDto r : requestDtoList) {
            if (r.getResidenceDto().equals(residenceDto)) {
                this.deleteRequest(r.getId());
            }
        }
    }
}
