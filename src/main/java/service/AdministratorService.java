package service;

import dto.AdministratorDto;
import entity.Administrator;
import exception.*;
import mapper.AdministratorMapper;
import repository.AdministratorRepo;
import utils.ApplicationUtils;
import validator.AdministratorValidator;

import javax.persistence.NoResultException;

public class AdministratorService {

    private final AdministratorRepo administratorRepoRepo;

    public AdministratorService(){
        this.administratorRepoRepo = new AdministratorRepo();
    }

    public AdministratorDto getAdministratorByUsernameAndPassword(String username, String password) throws NoResultException {
        Administrator administrator = administratorRepoRepo.findByUsernameAndPassword(username, password);
        return AdministratorMapper.entityToDto(administrator);
    }

    public AdministratorDto getAdministratorByUsername(String username) throws NoResultException {
        Administrator administrator = administratorRepoRepo.findByUsername(username);
        return AdministratorMapper.entityToDto(administrator);
    }


    public void addAdministrator(AdministratorDto administratorDtoDto) throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException, InvalidPhoneNumberException, InvalidUsernameException, InvalidPasswordException {
        if (!AdministratorValidator.isFirstNameValid(administratorDtoDto.getFirstName())){
            throw new InvalidFirstNameException(CustomExceptionMessages.INVALID_FIRST_NAME_MESSAGE);
        }

        if (!AdministratorValidator.isLastNameValid(administratorDtoDto.getLastName())) {
            throw new InvalidLastNameException(CustomExceptionMessages.INVALID_LAST_NAME_MESSAGE);
        }

        if (!AdministratorValidator.isEmailValid(administratorDtoDto.getEmailAddress())) {
            throw new InvalidEmailException(CustomExceptionMessages.INVALID_EMAIL_MESSAGE);
        }

        if (!AdministratorValidator.isPhoneNumberValid(administratorDtoDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException(CustomExceptionMessages.INVALID_PHONE_MESSAGE);
        }

        if (!AdministratorValidator.isUsernameValid(administratorDtoDto.getUsername())) {
            throw new InvalidUsernameException(CustomExceptionMessages.INVALID_USERNAME_MESSAGE);
        }

        if (!AdministratorValidator.isPasswordValid(administratorDtoDto.getPassword())) {
            throw new InvalidPasswordException(CustomExceptionMessages.INVALID_PASSWORD_MESSAGE);
        }

        Administrator administrator = AdministratorMapper.dtoToEntity(administratorDtoDto);
        administratorRepoRepo.insertNewAdministrator(administrator);
    }

    public void updateAdministrator (AdministratorDto administratorDtoDto) throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException, InvalidPhoneNumberException, InvalidPasswordException {
        if (!AdministratorValidator.isFirstNameValid(administratorDtoDto.getFirstName())){
            throw new InvalidFirstNameException(CustomExceptionMessages.INVALID_FIRST_NAME_MESSAGE);
        }

        if (!AdministratorValidator.isLastNameValid(administratorDtoDto.getLastName())) {
            throw new InvalidLastNameException(CustomExceptionMessages.INVALID_LAST_NAME_MESSAGE);
        }

        if (!AdministratorValidator.isEmailValid(administratorDtoDto.getEmailAddress())) {
            throw new InvalidEmailException(CustomExceptionMessages.INVALID_EMAIL_MESSAGE);
        }

        if (!AdministratorValidator.isPhoneNumberValid(administratorDtoDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException(CustomExceptionMessages.INVALID_PHONE_MESSAGE);
        }

        if (!AdministratorValidator.isPasswordValid(administratorDtoDto.getPassword())) {
            throw new InvalidPasswordException(CustomExceptionMessages.INVALID_PASSWORD_MESSAGE);
        }

        administratorRepoRepo.updateAdministrator(AdministratorMapper.dtoToEntity(administratorDtoDto));
    }
}
