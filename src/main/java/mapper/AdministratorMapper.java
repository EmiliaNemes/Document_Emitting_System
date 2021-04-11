package mapper;

import dto.AdministratorDto;
import entity.Administrator;

public class AdministratorMapper {

    public static AdministratorDto entityToDto(Administrator administrator){
        AdministratorDto adminDto = new AdministratorDto();
        adminDto.setId(administrator.getId());
        adminDto.setFirstName(administrator.getFirstName());
        adminDto.setLastName(administrator.getLastName());
        adminDto.setEmailAddress(administrator.getEmailAddress());
        adminDto.setPhoneNumber(administrator.getPhoneNumber());
        adminDto.setUsername(administrator.getUsername());
        adminDto.setPassword(administrator.getPassword());
        return adminDto;
    }

    public static Administrator dtoToEntity(AdministratorDto administratorDto){
        Administrator admin = new Administrator();
        admin.setId(administratorDto.getId());
        admin.setFirstName(administratorDto.getFirstName());
        admin.setLastName(administratorDto.getLastName());
        admin.setEmailAddress(administratorDto.getEmailAddress());
        admin.setPhoneNumber(administratorDto.getPhoneNumber());
        admin.setUsername(administratorDto.getUsername());
        admin.setPassword(administratorDto.getPassword());
        return admin;
    }
}
