package mapper;

import dto.RequestDto;
import dto.RequestTableDto;
import entity.Request;
import service.RequestService;

public class RequestMapper {

    public static RequestDto entityToDto(Request request){
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setUser(request.getUser());
        requestDto.setDocumentType(DocumentTypeMapper.entityToDto(request.getDocumentType()));
        requestDto.setContent(request.getContent());
        requestDto.setRequestDate(request.getRequestDate());
        requestDto.setResidenceDto(ResidenceMapper.entityToDto(request.getResidence()));
        requestDto.setApproved(request.isApproved());
        requestDto.setApprovalDate(request.getApprovalDate());
        return requestDto;
    }

    public static Request dtoToEntity(RequestDto requestDto){
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setUser(requestDto.getUser());
        request.setDocumentType(DocumentTypeMapper.dtoToEntity(requestDto.getDocumentType()));
        request.setContent(requestDto.getContent());
        request.setRequestDate(requestDto.getRequestDate());
        request.setResidence(ResidenceMapper.dtoToEntity(requestDto.getResidenceDto()));
        request.setApproved(requestDto.isApproved());
        request.setApprovalDate(requestDto.getApprovalDate());
        return request;
    }

    public static RequestTableDto dtoToTableDto(RequestDto requestDto){
        RequestTableDto requestTableDto = new RequestTableDto();
        /*
        String approved = "No";
        if(requestDto.isApproved()){
            approved = "Yes";
        }

         */

        String approved = requestDto.isApproved()? "Yes" : "No";

        requestTableDto.setId(requestDto.getId());
        requestTableDto.setUser(requestDto.getUser().getFirstName() + " " + requestDto.getUser().getLastName());
        requestTableDto.setDocumentType(requestDto.getDocumentType().getName());
        requestTableDto.setDocumentContent(requestDto.getContent());
        requestTableDto.setRequestDate(requestDto.getRequestDate());
        requestTableDto.setResidence(requestDto.getResidenceDto().toString());
        requestTableDto.setApproved(approved);
        requestTableDto.setApprovalDate(requestDto.getApprovalDate());
        return requestTableDto;
    }

    public static RequestDto tableDtoToDto(RequestTableDto requestTableDto) {
        RequestService requestService = new RequestService();
        return requestService.getRequestById(requestTableDto.getId());
    }
}
