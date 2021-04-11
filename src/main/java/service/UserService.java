package service;

import dto.RequestDto;
import dto.ResidenceDto;
import dto.UserDto;
import dto.UsersTableDto;
import entity.*;
import exception.*;
import mapper.*;
import repository.RequestRepo;
import repository.ResidenceRepo;
import repository.UserRepo;
import validator.UserValidator;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepo userRepo;

    public UserService(){
        this.userRepo= new UserRepo();
    }

    public void addUser(UserDto userDto) throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException, InvalidPhoneNumberException, InvalidUsernameException, InvalidPasswordException {
        if (!UserValidator.isFirstNameValid(userDto.getFirstName())){
            throw new InvalidFirstNameException(CustomExceptionMessages.INVALID_FIRST_NAME_MESSAGE);
        }

        if (!UserValidator.isLastNameValid(userDto.getLastName())) {
            throw new InvalidLastNameException(CustomExceptionMessages.INVALID_LAST_NAME_MESSAGE);
        }

        if (!UserValidator.isEmailValid(userDto.getEmailAddress())) {
            throw new InvalidEmailException(CustomExceptionMessages.INVALID_EMAIL_MESSAGE);
        }

        if (!UserValidator.isPhoneNumberValid(userDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException(CustomExceptionMessages.INVALID_PHONE_MESSAGE);
        }

        if (!UserValidator.isUsernameValid(userDto.getUsername())) {
            throw new InvalidUsernameException(CustomExceptionMessages.INVALID_USERNAME_MESSAGE);
        }

        if (!UserValidator.isPasswordValid(userDto.getPassword())) {
            throw new InvalidPasswordException(CustomExceptionMessages.INVALID_PASSWORD_MESSAGE);
        }

        userRepo.insertNewUser(UserMapper.dtoToEntity(userDto));
    }

    public UserDto getUserByUsernameAndPassword(String username, String password) throws NoResultException {
        User user = userRepo.findByUsernameAndPassword(username, password);
        return UserMapper.entityToDto(user);
    }

    public UserDto getUserByUsername(String username) throws NoResultException {
        User user = userRepo.findByUsername(username);
        return UserMapper.entityToDto(user);
    }

    public void updateUser (UserDto userDto) throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException, InvalidPhoneNumberException, InvalidPasswordException {
        if (!UserValidator.isFirstNameValid(userDto.getFirstName())){
            throw new InvalidFirstNameException(CustomExceptionMessages.INVALID_FIRST_NAME_MESSAGE);
        }

        if (!UserValidator.isLastNameValid(userDto.getLastName())) {
            throw new InvalidLastNameException(CustomExceptionMessages.INVALID_LAST_NAME_MESSAGE);
        }

        if (!UserValidator.isEmailValid(userDto.getEmailAddress())) {
            throw new InvalidEmailException(CustomExceptionMessages.INVALID_EMAIL_MESSAGE);
        }

        if (!UserValidator.isPhoneNumberValid(userDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException(CustomExceptionMessages.INVALID_PHONE_MESSAGE);
        }

        if (!UserValidator.isPasswordValid(userDto.getPassword())) {
            throw new InvalidPasswordException(CustomExceptionMessages.INVALID_PASSWORD_MESSAGE);
        }

        userRepo.updateUser(UserMapper.dtoToEntity(userDto));
    }

    public void removeUser(UserDto userDto){
        RequestRepo requestRepo = new RequestRepo();
        ResidenceRepo residenceRepo = new ResidenceRepo();

        //find user's requests remove them
        for(RequestDto requestDto: userDto.getRequests()){
            requestRepo.removeRequest(requestDto.getId());
        }

        //find user's residences remove them
        for(ResidenceDto residenceDto: userDto.getResidences()){
            residenceRepo.removeResidence(residenceDto.getId());
        }

        // finally remove the user
        userRepo.removeUser(userDto.getId());
    }

    public List<RequestDto> getUsersRequestsForResidenceForDocumentType(RequestDto requestDto){
        RequestRepo requestRepo = new RequestRepo();
        List<Request> requests = requestRepo.getRequestsByUserAndResidenceAndDocumentType(requestDto.getUser(), ResidenceMapper.dtoToEntity(requestDto.getResidenceDto()), DocumentTypeMapper.dtoToEntity(requestDto.getDocumentType()));
        if(requests.isEmpty()){
            return new ArrayList<>();
        }
        List<RequestDto> requestDtos = new ArrayList<>();

        for (Request request: requests) {
            requestDtos.add(RequestMapper.entityToDto(request));
        }
        return requestDtos;
    }

    public List<RequestDto> getUsersExistingRequests(RequestDto requestDto){
        RequestRepo requestRepo = new RequestRepo();
        List<Request> requests = requestRepo.getRequestsByUserAndResidenceAndDocumentType(requestDto.getUser(), ResidenceMapper.dtoToEntity(requestDto.getResidenceDto()), DocumentTypeMapper.dtoToEntity(requestDto.getDocumentType()));
        if(requests.isEmpty()){
            throw new IllegalArgumentException(CustomExceptionMessages.NO_REQUESTS_FOR_USER_MESSAGE);
        }
        List<RequestDto> requestDtos = new ArrayList<>();

        for (Request request: requests) {
            requestDtos.add(RequestMapper.entityToDto(request));
        }
        return requestDtos;
    }

    public List<RequestDto> getUsersRequests(User user){
        RequestRepo requestRepo = new RequestRepo();
        List<Request> requests = requestRepo.getRequestsByUser(user);
        if(requests.isEmpty()){
            return new ArrayList<>();
        }
        List<RequestDto> requestDtos = new ArrayList<>();

        for (Request request: requests) {
            requestDtos.add(RequestMapper.entityToDto(request));
        }
        return requestDtos;
    }

    public List<ResidenceDto> getUsersResidencies(User user){
        ResidenceRepo residenceRepo = new ResidenceRepo();
        List<Residence> residences = residenceRepo.findByUser(user);
        if(residences.isEmpty()){
            //throw new IllegalArgumentException(CustomExceptionMessages.NO_RESIDENCES_FOR_USER_MESSAGE);
            return new ArrayList<>();
        }
        List<ResidenceDto> residenceDtos = new ArrayList<>();

        for (Residence r: residences) {
                ResidenceDto residenceDto= ResidenceMapper.entityToDto(r);
            residenceDtos.add(residenceDto);
        }
        return residenceDtos;
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.findAllUsers();
        if(users.isEmpty()){
            throw new IllegalArgumentException(CustomExceptionMessages.NO_USERS_MESSAGE);
        }
        List<UserDto> userDtos = new ArrayList<>();

        for (User user: users) {
            UserDto userDto=UserMapper.entityToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }


    public void addPersonalResidence(UserDto userDto, ResidenceDto residenceDto ) throws InvalidNumberException, InvalidCityException, InvalidStreetException {
        ResidenceService residenceService = new ResidenceService();
        residenceService.addResidence(residenceDto);
    }

    public void removePersonalResidence(UserDto userDto, ResidenceDto residenceDto ){
        ResidenceService residenceService = new ResidenceService();
        residenceService.removeResidence(residenceDto.getId());
    }

    public UserDto getUserByName(String name) {
        String[] parts = name.split(" ");
        User user = userRepo.findByFirstNameAndLastName(parts[0], parts[1]);
        return UserMapper.entityToDto(user);
    }

    public List<UsersTableDto> createUserTableDtoFromUserDto(List<UserDto> userDtoList){
        List<UsersTableDto> usersTableDtoList = new ArrayList<>();

        for(UserDto u: userDtoList) {
            UsersTableDto usersTableDto = UserMapper.dtoToUsersTableDto(u);
            usersTableDtoList.add(usersTableDto);
        }

        return usersTableDtoList;
    }
}
