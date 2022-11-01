package main.services;

import main.dto.UserRequest;
import main.dto.UserResponse;
import main.entities.User;
import main.entities.UserRole;
import main.mappers.UserRequestMapper;
import main.mappers.UserResponseMapper;
import main.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserResponseMapper userResponseMapper;
    @Mock
    private UserRequestMapper userRequestMapper;

    @InjectMocks
    private UserService userService;

    private static final int ID = 1;
    private static final int ID2 = 2;


    private static final User FIRST_USER = new User(ID, "Alina Lacatus", 25, "Alinfata1",
            "Bucharest", "0726969875", "Mircea voda 23", "alina3@gmail.com", true,
            false, UserRole.ROLE_USER);

    private static final User SECOND_USER = new User(ID2, "Marius Mihai", 26, "mer23!",
            "Bucharest", "0727529875", "Berceni 209", "marius2@gmail.com", true,
            false, UserRole.ROLE_USER);

    private static final List<User> USERS = List.of(FIRST_USER, SECOND_USER);

    private UserRequest userRequestRecord = new UserRequest(ID2, "Marian Popovici", 44, "pop23!",
            "Bucharest", "072666675", "Drumul taberei 89", "popovici33@gmail.com", true,
            false, UserRole.ROLE_USER);

    private UserResponse userResponseRecord = new UserResponse(ID2, "Jan Mihai", 24, "jan$!",
            "Bucharest", "0726633220", "Strada Ilioara 2", "janjjj@gmail.com", true,
            false, UserRole.ROLE_USER);


    @Test
    void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(USERS);
        Assertions.assertEquals(2, userService.findAll().size());

        List<UserResponse> expected = USERS.stream().map(user -> userResponseMapper.map(user)).toList();
        Assertions.assertEquals(expected, userService.findAll());

    }

    //since the method return an optional , we make 2 test methods
    // 1. for the case when it actually returns a record
    @ParameterizedTest
    @ValueSource(ints = {1})
    void findById() {
        UserResponse expected = userResponseMapper.map(FIRST_USER);
//        //when
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(FIRST_USER));
//        //then
        Assertions.assertEquals(expected, userService.findById(ID));
//        //verification
        Mockito.verify(userRepository).findById(ID);
    }

    //2. when throws an exception
    @ParameterizedTest
    @ValueSource(ints = {1})
    void findByIdExceptionCase() throws UnsupportedOperationException {
        Mockito.when(userRepository.findById(ID)).thenThrow(UnsupportedOperationException.class);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> userRepository.findById(ID));

    }


    @ParameterizedTest
    @ValueSource(strings = {"Alin Lacatus"})
    void findByName(String name) {
        name = "Alin Lacatus";

        List<User> usersByName = userRepository.findByName(name);
        usersByName.add(FIRST_USER);

        Mockito.when(userRepository.findByName(name)).thenReturn(usersByName);

        List<UserResponse> expected = usersByName.stream().map(aUser -> userResponseMapper.map(aUser)).toList();
        Assertions.assertEquals(expected, userService.findByName(name));

    }

    @ParameterizedTest
    @ValueSource(strings = {"Leo"})
    void findByNameException() {

        String name = "Marian";

        Mockito.when(userRepository.findByName(name)).thenThrow(UnsupportedOperationException.class);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> userService.findByName(name));
    }

    @Test
    void save() {
        User user = userRequestMapper.map(userRequestRecord);
        User saved = userRepository.save(user);

        UserResponse returnedUser = userResponseMapper.map(saved);

        Mockito.when(userRepository.save(user)).thenReturn(saved);

        Assertions.assertEquals(saved, userRepository.save(user));
        Assertions.assertEquals(returnedUser, userService.save(userRequestRecord));
    }

    @ParameterizedTest
    @ValueSource(ints = {ID})
    void delete() {
        Optional<User> optionalUser = Optional.of(FIRST_USER);

        Mockito.when(userRepository.findById(ID)).thenReturn(optionalUser);

        Assertions.assertEquals(optionalUser, userRepository.findById(ID));

        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void setAllParameters() {
        setAllParametersTest(userRequestRecord, FIRST_USER);

        Assertions.assertEquals(FIRST_USER.getName(), userRequestRecord.getName());
        Assertions.assertEquals(FIRST_USER.getAge(), userRequestRecord.getAge());
        Assertions.assertEquals(FIRST_USER.getPassword(), userRequestRecord.getPassword());
        Assertions.assertEquals(FIRST_USER.getCity(), userRequestRecord.getCity());
        Assertions.assertEquals(FIRST_USER.getPhoneNumber(), userRequestRecord.getPhoneNumber());
        Assertions.assertEquals(FIRST_USER.getAddress(), userRequestRecord.getAddress());
        Assertions.assertEquals(FIRST_USER.getEmail(), userRequestRecord.getEmail());
        Assertions.assertEquals(FIRST_USER.getHasDog(), userRequestRecord.getHasDog());
        Assertions.assertEquals(FIRST_USER.getUserBlocked(), userRequestRecord.getUserBlocked());
        Assertions.assertEquals(FIRST_USER.getUserRole(), userRequestRecord.getUserRole());
    }

    @Test
    void update() {
        setAllParametersTest(userRequestRecord, FIRST_USER);
        User savedUser = userRepository.save(FIRST_USER);
        UserResponse responseUser = userResponseMapper.map(savedUser);

        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(FIRST_USER));

        Mockito.when(userRepository.save(FIRST_USER)).thenReturn(savedUser);

        Assertions.assertEquals(responseUser, userService.update(ID, userRequestRecord));

    }

    @ParameterizedTest
    @ValueSource(ints = {ID})
    void updateException() {
        Mockito.when(userRepository.findById(ID)).thenThrow(UnsupportedOperationException.class);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> userRepository.findById(ID));
    }


    @Test
    void setPartialParameters() {
        setParametersPartiallyTest(userResponseRecord, FIRST_USER);

        Assertions.assertEquals(userResponseRecord.getName(), FIRST_USER.getName());
        Assertions.assertEquals(userResponseRecord.getAge(), FIRST_USER.getAge());
        Assertions.assertEquals(userResponseRecord.getPassword(), FIRST_USER.getPassword());
        Assertions.assertEquals(userResponseRecord.getCity(), FIRST_USER.getCity());
        Assertions.assertEquals(userResponseRecord.getPhoneNumber(), FIRST_USER.getPhoneNumber());
        Assertions.assertEquals(userResponseRecord.getAddress(), FIRST_USER.getAddress());
        Assertions.assertEquals(userResponseRecord.getEmail(), FIRST_USER.getEmail());
        Assertions.assertEquals(userResponseRecord.getHasDog(), FIRST_USER.getHasDog());
        Assertions.assertEquals(userResponseRecord.getUserBlocked(), FIRST_USER.getUserBlocked());
        Assertions.assertEquals(userResponseRecord.getUserRole(), FIRST_USER.getUserRole());

    }

    @Test
    void updateUserPartially() {
        Optional<User> firstUser = Optional.of(FIRST_USER);
        User user = firstUser.get();

        setParametersPartiallyTest(userResponseRecord, FIRST_USER);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = userResponseMapper.map(savedUser);

        Mockito.when(userRepository.findById(ID2)).thenReturn(firstUser);
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        Assertions.assertEquals(firstUser, userRepository.findById(ID2));
        Assertions.assertEquals(savedUser, userRepository.save(user));
        Assertions.assertEquals(userResponse, userService.updateUserPartially(userRequestRecord));
    }

    @Test
    void updateUserPartiallyException() {
        Mockito.when(userRepository.findById(ID2)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.updateUserPartially(userRequestRecord));
    }

    @Test
    void findBlockedUsers() {
        List<User> blockedUsers = USERS.stream().filter(User::getUserBlocked).toList();
        List<UserResponse> responseUsers = blockedUsers.stream()
                .map(userResponseMapper::map).toList();

        Mockito.when(userRepository.findAll()).thenReturn(USERS);

        Assertions.assertEquals(responseUsers, userService.findBlockedUsers());
    }

    private void setAllParametersTest(UserRequest userRequest, User userToBeUpdated) {
        userToBeUpdated.setName(userRequest.getName());
        userToBeUpdated.setAge(userRequest.getAge());
        userToBeUpdated.setPassword(userRequest.getPassword());
        userToBeUpdated.setCity(userRequest.getCity());
        userToBeUpdated.setPhoneNumber(userRequest.getPhoneNumber());
        userToBeUpdated.setAddress(userRequest.getAddress());
        userToBeUpdated.setEmail(userRequest.getEmail());
        userToBeUpdated.setHasDog(userRequest.getHasDog());
        userToBeUpdated.setUserBlocked(userRequest.getUserBlocked());
        userToBeUpdated.setUserRole(userRequest.getUserRole());
    }

    private void setParametersPartiallyTest(UserResponse userRequest, User user) {
        user.setName(userRequest.getName() == null ? user.getName() : userRequest.getName());

        user.setAge(userRequest.getAge() == null ? user.getAge() : userRequest.getAge());

        user.setPassword(userRequest.getPassword() == null ?
                user.getPassword() : userRequest.getPassword());

        user.setCity(userRequest.getCity() == null ? user.getCity() : userRequest.getCity());

        user.setPhoneNumber(userRequest.getPhoneNumber() == null ?
                user.getPhoneNumber() : userRequest.getPhoneNumber());

        user.setAddress(userRequest.getAddress() == null ?
                user.getAddress() : userRequest.getAddress());

        user.setEmail(userRequest.getEmail() == null ? user.getEmail() : userRequest.getEmail());

        // If I don't give a parameter in the JSON ,that means == null -> do something , else -> do another thing
        user.setHasDog(userRequest.getHasDog() == null ? user.getHasDog() : userRequest.getHasDog());

        user.setUserBlocked(userRequest.getUserBlocked() == null ?
                user.getUserBlocked() : userRequest.getUserBlocked());

        user.setUserRole(UserRole.valueOf(userRequest.getUserRole() == null ?
                String.valueOf(user.getUserRole()) : String.valueOf(userRequest.getUserRole())));
    }
}