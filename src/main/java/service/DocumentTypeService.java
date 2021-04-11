package service;

import controller.AdministratorDocumentPageController;
import dto.DocumentTypeDto;
import dto.RequestDto;
import entity.DocumentType;
import exception.CustomExceptionMessages;
import exception.InvalidDocumentNameException;
import exception.InvalidEmissionDateException;
import exception.InvalidTypeException;
import javafx.collections.FXCollections;
import mapper.DocumentTypeMapper;
import repository.DocumentTypeRepo;
import utils.ApplicationUtils;
import validator.DocumentTypeValidator;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class DocumentTypeService {

    private final DocumentTypeRepo documentTypeRepo;

    public DocumentTypeService() {
        this.documentTypeRepo = new DocumentTypeRepo();
    }

    public DocumentTypeDto getDocumentTypeById(String id) throws NoResultException {
        DocumentType documentType = documentTypeRepo.findById(id);
        return DocumentTypeMapper.entityToDto(documentType);
    }

    public DocumentTypeDto getDocumentTypeByName(String name) throws NoResultException {
        DocumentType documentType = documentTypeRepo.findByName(name);
        return DocumentTypeMapper.entityToDto(documentType);
    }

    public void addDocumentType(DocumentTypeDto documentTypeDto) throws InvalidTypeException, InvalidEmissionDateException, InvalidDocumentNameException {
        if (!DocumentTypeValidator.isDocumentTypeValid(documentTypeDto.getName())){
            throw new InvalidTypeException(CustomExceptionMessages.INVALID_TYPE_MESSAGE);
        }

        if (!DocumentTypeValidator.isNameValid(documentTypeDto.getName())){
            throw new InvalidDocumentNameException(CustomExceptionMessages.INVALID_DOCUMENT_NAME_MESSAGE);
        }

        documentTypeRepo.insertNewDocument(DocumentTypeMapper.dtoToEntity(documentTypeDto));
    }

    public void removeDocumentTypeByName(String type){
        documentTypeRepo.removeDocumentType(documentTypeRepo.findByName(type).getId());
    }

    public void removeDocumentTypeById(String id){
        documentTypeRepo.removeDocumentType(id);
    }

    public List<String> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepo.findAllDocumentTypes();
        List<String> documentTypesList = new ArrayList<>();

        for(DocumentType doc: documentTypes){
            documentTypesList.add(doc.getName());
        }

        return documentTypesList;
    }

    public List<DocumentTypeDto> getAllDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepo.findAllDocumentTypes();
        List<DocumentTypeDto> documentTypesList = new ArrayList<>();

        for(DocumentType doc: documentTypes){
            documentTypesList.add(DocumentTypeMapper.entityToDto(doc));
        }

        return documentTypesList;
    }

    public void populateList() {
        AdministratorDocumentPageController.data.clear();
        AdministratorDocumentPageController.data = FXCollections.observableArrayList(this.getAllDocumentTypes());
    }

    public boolean deleteApprovedDocuments(DocumentTypeDto documentTypeDto) {
        RequestService requestService = new RequestService();
        List<RequestDto> requestDtoList = requestService.getRequestsByDocumentType(DocumentTypeMapper.dtoToEntity(documentTypeDto));

        boolean allApproved = true;

        for (RequestDto r : requestDtoList) {
            if (!r.isApproved()) {
                allApproved = false;
                break;
            }
        }

        if (allApproved) {
            for (RequestDto r : requestDtoList) {
                requestService.deleteRequest(r.getId());
            }

            this.removeDocumentTypeById(documentTypeDto.getId());
            AdministratorDocumentPageController.data.remove(documentTypeDto);
        } else {
            ApplicationUtils.displayMessage(CustomExceptionMessages.INVALID_DOCUMENT_DELETE_MESSAGE, "Document Delete", "Assets/no.jpg");
        }

        return allApproved;
    }

}
