package main.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.UserRequest;
import main.dto.UserResponse;
import main.mappers.UserRequestMapper;
import main.mappers.UserResponseMapper;
import main.repository.UserRepository;
import main.repository.entities.User;
import main.repository.entities.UserRole;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {

    private final UserRepository userRepository;

    private final UserResponseMapper mapper;

    private final UserRequestMapper requestMapper;


    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer id) {
        Optional<User> searchedUserById = userRepository.findById(id);

        if (searchedUserById.isPresent()) {

            log.info("User with id : " + id + " was found in the database");

            return mapper.map(searchedUserById.get());

        } else {
            log.error("User was not found in the database with id : " + id);
            throw new UnsupportedOperationException(" * User was not found in the database with id : " + id);
        }
    }

    public List<UserResponse> findByName(String name) {

        List<User> searchedByName = userRepository.findByName(name);

        if (searchedByName.isEmpty()) {

            log.error("User(s) was not found in the database with name : " + name);
            throw new UnsupportedOperationException(" * User(s) was not found in the database with name : " + name);

        } else {
            log.info("User with name : " + name + " was found in the database");
            return searchedByName.stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());
        }
    }

    public UserResponse save(UserRequest userRequest) {
        User mappedUser = requestMapper.map(userRequest);
        userRepository.save(mappedUser);

        log.info("User was Successfully saved in the database ..");

        return mapper.map(mappedUser);
    }


    public void delete(Integer id) {

        Optional<User> searchedUserById = userRepository.findById(id);

        if (searchedUserById.isPresent()) {

            log.info("User with id : " + id + " was found in the database..");
            userRepository.deleteById(id);
            log.info("User with id : " + id + " was Successfully deleted..");

        } else {
            log.error("User was not found in the database with id : " + id);
            throw new NotFoundException(" * User was not found in the database with id : " + id);
        }
    }

    public UserResponse update(Integer id, UserRequest userRequest) {

        Optional<User> userFromDataBase = userRepository.findById(id);

        if (userFromDataBase.isPresent()) {

            log.info("User was found in the database");

            User userToBeUpdated = userFromDataBase.get();

            setAllParameters(userRequest, userToBeUpdated);

            User savedUser = userRepository.save(userToBeUpdated);

            log.info("Successful Update! .. User was mapped to the database");

            return mapper.map(savedUser);

        } else

            log.error("User was not found in the database with id : " + id);
        throw new UnsupportedOperationException(" * User was not found in the database with id : " + id);

    }


    public UserResponse updateUserPartially(UserResponse request) {

        Integer id = request.getId();

        User userFromDataBase = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id : " + id));

        setParametersPartially(request, userFromDataBase);

        User saved = userRepository.save(userFromDataBase);

        return mapper.map(saved);
    }


    public List<UserResponse> findBlockedUsers() {

        List<User> allUsers = userRepository.findAll();

        List<User> blockedUsers = allUsers.stream()
                .filter(User::getUserBlocked).toList();

        return blockedUsers.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }

    private void setAllParameters(UserRequest userRequest, User userToBeUpdated) {
        userToBeUpdated.setName(userRequest.getName());
        userToBeUpdated.setAge(userRequest.getAge());
        userToBeUpdated.setPassword(userRequest.getPassword());
        userToBeUpdated.setCity(userRequest.getCity());
        userToBeUpdated.setPhoneNumber(userRequest.getPhoneNumber());
        userToBeUpdated.setAddress(userRequest.getAddress());
        userToBeUpdated.setEmail(userRequest.getEmail());
        userToBeUpdated.setHasDog(userRequest.getHasDog());
        userToBeUpdated.setUserBlocked(userRequest.isUserBlocked());
        userToBeUpdated.setUserRole(userRequest.getUserRole());
    }

    private void setParametersPartially(UserResponse request, User userFromDataBase) {
        userFromDataBase.setName(request.getName() == null ? userFromDataBase.getName() : request.getName());

        userFromDataBase.setAge(request.getAge() == null ? userFromDataBase.getAge() : request.getAge());

        userFromDataBase.setPassword(request.getPassword() == null ?
                userFromDataBase.getPassword() : request.getPassword());

        userFromDataBase.setCity(request.getCity() == null ? userFromDataBase.getCity() : request.getCity());

        userFromDataBase.setPhoneNumber(request.getPhoneNumber() == null ?
                userFromDataBase.getPhoneNumber() : request.getPhoneNumber());

        userFromDataBase.setAddress(request.getAddress() == null ?
                userFromDataBase.getAddress() : request.getAddress());

        userFromDataBase.setEmail(request.getEmail() == null ? userFromDataBase.getEmail() : request.getEmail());

        // If I don't give a parameter in the JSON ,that means == null -> do something , else -> do another thing
        userFromDataBase.setHasDog(request.getHasDog() == null ? userFromDataBase.getHasDog() : request.getHasDog());

        userFromDataBase.setUserBlocked(request.getUserBlocked() == null ?
                userFromDataBase.getUserBlocked() : request.getUserBlocked());

        userFromDataBase.setUserRole(UserRole.valueOf(request.getUserRole() == null ?
                String.valueOf(userFromDataBase.getUserRole()) : String.valueOf(request.getUserRole())));
    }

}
