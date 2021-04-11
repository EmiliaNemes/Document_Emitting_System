package validator;

import service.UserService;

import javax.persistence.NoResultException;

public class UserValidator extends AllUserValidator{

    public static boolean isUserValid(String username){
        UserService userService = new UserService();

        try{
            System.out.println(username);
            userService.getUserByUsername(username);
            return false;
        } catch(NoResultException e){
            return true;
        }
    }
}
