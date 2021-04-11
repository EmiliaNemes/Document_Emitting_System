package validator;

import service.AdministratorService;

import javax.persistence.NoResultException;

public class AdministratorValidator extends AllUserValidator {

    public static boolean isUsernameValid(String username){
        if(username == null || username.equals("")){
            return false;
        } else {
            AdministratorService administratorService = new AdministratorService();

            try{
                administratorService.getAdministratorByUsername(username);
                return false;
            } catch(NoResultException e){
                return true;
            }
        }
    }

}
