package mapper;

import dto.ResidenceDto;
import entity.Residence;

public class ResidenceMapper {

    public static ResidenceDto entityToDto(Residence residence){
        ResidenceDto residenceDto = new ResidenceDto();
        residenceDto.setId(residence.getId());
        residenceDto.setCity(residence.getCity());
        residenceDto.setStreet(residence.getStreet());
        residenceDto.setNumber(residence.getNumber());
        residenceDto.setUser(residence.getUser());
        return residenceDto;
    }

    public static Residence dtoToEntity(ResidenceDto residenceDto){
        Residence residence = new Residence();
        residence.setId(residenceDto.getId());
        residence.setCity(residenceDto.getCity());
        residence.setStreet(residenceDto.getStreet());
        residence.setNumber(residenceDto.getNumber());
        residence.setUser(residenceDto.getUser());
        return residence;
    }
}
