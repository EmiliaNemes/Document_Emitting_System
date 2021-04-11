package mapper;

import dto.DocumentTypeDto;
import entity.DocumentType;

public class DocumentTypeMapper {

    public static DocumentTypeDto entityToDto(DocumentType documentType){
        DocumentTypeDto documentTypeDto = new DocumentTypeDto();
        documentTypeDto.setId(documentType.getId());
        documentTypeDto.setName(documentType.getName());
        return documentTypeDto;
    }

    public static DocumentType dtoToEntity(DocumentTypeDto documentTypeDto){
        DocumentType documentType = new DocumentType();
        documentType.setId(documentTypeDto.getId());
        documentType.setName(documentTypeDto.getName());
        return documentType;
    }
}
