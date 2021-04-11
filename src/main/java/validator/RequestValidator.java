package validator;

import dto.RequestDto;
import service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class RequestValidator {

    public static boolean isApprovalDateValid(LocalDate date){
        if(date.isAfter(LocalDate.now())){ // date is after current date
            return false;
        }
        return true;
    }

    public static boolean isRequestMakingValid(RequestDto requestDto){
        UserService userService = new UserService();
        List<RequestDto> requestDtoList = userService.getUsersRequestsForResidenceForDocumentType(requestDto);

        int contor = 0;
        for(RequestDto r: requestDtoList){
            Period period = Period.between(r.getRequestDate(), LocalDate.now());
            if(period.getYears() < 1){
                contor++;
            }
        }

        if(contor >= 3){
            return false;
        } else {
            return true;
        }
    }
}
