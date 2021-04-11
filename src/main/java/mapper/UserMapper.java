package mapper;

import dto.ResidenceDto;
import dto.UserDto;
import dto.UsersTableDto;
import entity.Residence;
import entity.User;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        UserService userService = new UserService();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmailAddress(user.getEmailAddress());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setResidences(userService.getUsersResidencies(user));
        userDto.setRequests(userService.getUsersRequests(user));
        return userDto;
    }

    public static User dtoToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UsersTableDto dtoToUsersTableDto(UserDto userDto){
        UsersTableDto usersTableDto = new UsersTableDto();
        usersTableDto.setId(userDto.getId());
        usersTableDto.setName(userDto.getFirstName() + " " + userDto.getLastName());
        usersTableDto.setEmailAddress(userDto.getEmailAddress());
        usersTableDto.setPhoneNumber(userDto.getPhoneNumber());

        List<ResidenceDto> residenceDtoList = userDto.getResidences();
        String residences ="";
        for(ResidenceDto r: residenceDtoList){
            residences = residences.concat(r.toString()).concat("\n");
        }
        usersTableDto.setResidences(residences);

        return usersTableDto;
    }
}
