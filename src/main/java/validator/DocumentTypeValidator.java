package validator;

import service.DocumentTypeService;

import javax.persistence.NoResultException;

public class DocumentTypeValidator {

    public static boolean isDocumentTypeValid(String id){
        if(id == null || id.equals("")){
            return false;
        } else {
            DocumentTypeService documentTypeService = new DocumentTypeService();

            try{
                documentTypeService.getDocumentTypeById(id);
                return false;
            } catch(NoResultException e){
                return true;
            }
        }
    }

    public static boolean isNameValid(String type){
        return type != null && !type.equals("") && type.matches("[A-Z][a-z]*");
    }
}
