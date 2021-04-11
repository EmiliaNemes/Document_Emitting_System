package validator;

import entity.Residence;
import service.ResidenceService;

import javax.persistence.NoResultException;
import java.util.Set;

public class ResidenceValidator {

    public static boolean isCityValid(String city){
        return city != null && !city.equals("") && city.matches("[A-Z][a-z]*\\-?[A-Z]*[a-z]*");
    }

    public static boolean isStreetValid(String street){
        return street != null && !street.equals("") && street.matches("[A-Z][a-z]*");
    }

    public static boolean isNumberValid(int number){
        return number>0;
    }

    public static boolean isResidenceValid(String city, String street, String number){
        if(number.equals("")){
            return false;
        }
        return isCityValid(city) & isStreetValid(street) & isNumberValid(Integer.valueOf(number));
    }

    public static boolean areResidencesValid(Set<Residence> residenceSet){
        for(Residence residence: residenceSet){
            if(residence.getId() == null || residence.getId().equals("")){
                return false;
            }
        }
        ResidenceService residenceService = new ResidenceService();
        try{
            for(Residence residence: residenceSet) {
                residenceService.getResidenceById(residence.getId());
                return false;
            }
        } catch(NoResultException e){
            return true;
        }

        return true;
    }

    public static boolean doesResidenceExist(String id){
        ResidenceService residenceService = new ResidenceService();

        try{
            residenceService.getResidenceById(id);
            return false;
        } catch(NoResultException e){
            return true;
        }
    }
}
