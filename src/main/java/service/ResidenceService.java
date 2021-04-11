package service;

import dto.ResidenceDto;
import dto.UserDto;
import entity.Residence;
import exception.*;
import mapper.ResidenceMapper;
import mapper.UserMapper;
import repository.ResidenceRepo;
import utils.ApplicationUtils;
import validator.ResidenceValidator;

import javax.persistence.NoResultException;

public class ResidenceService {

    private ResidenceRepo residenceRepo;

    public ResidenceService(){
        this.residenceRepo = new ResidenceRepo();
    }

    public void addResidence(ResidenceDto residenceDto) throws InvalidCityException, InvalidStreetException, InvalidNumberException {
        if (!ResidenceValidator.isCityValid(residenceDto.getCity())){
            throw new InvalidCityException(CustomExceptionMessages.INVALID_CITY_MESSAGE);
        }

        if (!ResidenceValidator.isStreetValid(residenceDto.getStreet())) {
            throw new InvalidStreetException(CustomExceptionMessages.INVALID_STREET_MESSAGE);
        }

        if (!ResidenceValidator.isNumberValid(residenceDto.getNumber())) {
            throw new InvalidNumberException(CustomExceptionMessages.INVALID_NUMBER_MESSAGE);
        }

        residenceRepo.insertNewResidence(ResidenceMapper.dtoToEntity(residenceDto));
    }

    public ResidenceDto getResidenceById(String id) throws NoResultException {
        return ResidenceMapper.entityToDto(residenceRepo.findById(id));
    }


    public void removeResidence(String id){
        residenceRepo.removeResidence(id);
    }

    public ResidenceDto getResidenceByCityStreetNumberUser(String city, String street, String number, UserDto user) {
        return ResidenceMapper.entityToDto(residenceRepo.findByCityStreetNumberUser(city, street, number, UserMapper.dtoToEntity(user)));
    }
}
