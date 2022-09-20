package main.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.controllers.dto.UserRequest;
import main.controllers.dto.UserResponse;
import main.mappers.UserRequestMapper;
import main.mappers.UserResponseMapper;
import main.repository.UserRepository;
import main.repository.entities.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    public void delete(Integer id) throws UnsupportedOperationException {

        Optional<User> searchedUserById = userRepository.findById(id);

        if (searchedUserById.isPresent()) {

            log.info("User with id : " + id + " was found in the database..");
            userRepository.deleteById(id);
            log.info("User with id : " + id + " was Successfully deleted..");

        } else
            log.error("User was not found in the database with id : " + id);
        throw new UnsupportedOperationException(" * User was not found in the database with id : " + id);

    }

    public UserResponse update(Integer id, UserRequest userRequest) {

        Optional<User> userFromDataBase = userRepository.findById(id);

        if (userFromDataBase.isPresent()) {

            log.info("User was found in the database");

            User userToBeUpdated = userFromDataBase.get();
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
            userToBeUpdated.setDogs(userRequest.getDogs());
//            userToBeUpdated.setWalker(userRequest.getWalker());
//            userToBeUpdated.setOrder(userRequest.getOrder());

            User savedUser = userRepository.save(userToBeUpdated);
            log.info("Successful Update! .. User was mapped to the database");
            return mapper.map(savedUser);
        } else
            log.error("User was not found in the database with id : " + id);
        throw new UnsupportedOperationException(" * User was not found in the database with id : " + id);
    }

//        @Transactional
    public UserResponse updatePassword(Integer id, String password) {
        User userFromDataBase = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id : " + id));

        userFromDataBase.setPassword(password);

        User saved = userRepository.save(userFromDataBase);

        return mapper.map(saved);
    }

    public UserResponse updatePhoneNumber(Integer id, String phoneNumber) {

        User userToBeUpdated = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id : " + id));

        userToBeUpdated.setPhoneNumber(phoneNumber);
        User saved = userRepository.save(userToBeUpdated);

        return mapper.map(saved);
    }

    public UserResponse updateAddress(Integer id, String address) {

        User userToBeUpdated = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id : " + id));

        userToBeUpdated.setAddress(address);
        User user = userRepository.save(userToBeUpdated);

        return mapper.map(user);
    }

    public UserResponse updateEmail(Integer id, String email) {
        User userToBeUpdated = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id :  " + id));

        userToBeUpdated.setEmail(email);
        User user = userRepository.save(userToBeUpdated);

        return mapper.map(user);
    }

    public UserResponse updateUserStatus(Integer id, String blocked) {
        User userToBeUpdated = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * User was not found in the database with id : " + id));

        userToBeUpdated.setUserBlocked(Boolean.parseBoolean(blocked));
        User user = userRepository.save(userToBeUpdated);

        return mapper.map(user);
    }
}
