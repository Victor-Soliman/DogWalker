package main.services;

import lombok.RequiredArgsConstructor;
import main.controllers.dto.UserResponse;
import main.controllers.dto.WalkerRequest;
import main.controllers.dto.WalkerResponse;
import main.mappers.WalkerRequestMapper;
import main.mappers.WalkerResponseMapper;
import main.repository.WalkerRepository;
import main.repository.entities.User;
import main.repository.entities.Walker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class WalkerService {

    private final WalkerRepository walkerRepository;
    private final WalkerResponseMapper mapper;

    private final WalkerRequestMapper requestMapper;

    public List<WalkerResponse> findAll() {
        return walkerRepository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public WalkerResponse findById(Integer id) {
        return mapper.map(walkerRepository.findById(id).get());
    }

    public List<WalkerResponse> findByName(String name) {
        return walkerRepository.findByName(name).stream()
                .map(walker -> mapper.map(walker))
                .collect(Collectors.toList());
    }

    public WalkerResponse save(WalkerRequest walkerRequest) {
        Walker mappedWalker = requestMapper.map(walkerRequest);
        walkerRepository.save(mappedWalker);
        return mapper.map(mappedWalker);
    }

    public void delete(Integer id) {
        walkerRepository.deleteById(id);
    }

    public WalkerResponse update(Integer id,WalkerRequest walkerRequest) {
        Optional<Walker> walkerById = walkerRepository.findById(id);
        if (walkerById.isPresent()) {
            Walker oldWalker = walkerById.get();
            oldWalker.setName(walkerRequest.getName());
            oldWalker.setAge(walkerRequest.getAge());
            oldWalker.setPassword(walkerRequest.getPassword());
            oldWalker.setCity(walkerRequest.getCity());
            oldWalker.setPhoneNumber(walkerRequest.getPhoneNumber());
            oldWalker.setAddress(walkerRequest.getAddress());
            oldWalker.setEmail(walkerRequest.getEmail());
            oldWalker.setYearsOfExperience(walkerRequest.getYearsOfExperience());
            oldWalker.setIsAvailable(walkerRequest.getIsAvailable());

            Walker saved = walkerRepository.save(oldWalker);

            return mapper.map(saved);
        } else

            throw new UnsupportedOperationException("Invalid Id : " + id);

    }

    public WalkerResponse updatePassword(Integer id, String password) {
        Walker walkerFromDataBase = walkerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Walker does not exist with id : " + id));

        walkerFromDataBase.setPassword(password);

        Walker saved = walkerRepository.save(walkerFromDataBase);

        return mapper.map(saved);
    }

    public WalkerResponse updatePhoneNumber(Integer id, String phoneNumber) {

        Walker walkerFromDataBase = walkerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Walker found with id : " + id));

        walkerFromDataBase.setPhoneNumber(phoneNumber);
        Walker saved = walkerRepository.save(walkerFromDataBase);

        return mapper.map(saved);
    }

    public WalkerResponse updateAddress(Integer id, String address) {

        Walker walkerFromDataBase = walkerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Walker found with id : " + id));

        walkerFromDataBase.setAddress(address);
        Walker walker = walkerRepository.save(walkerFromDataBase);

        return mapper.map(walker);
    }

    public WalkerResponse updateEmail(Integer id, String email) {
        Walker walkerFromDataBase = walkerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Walker found with id : " + id));

        walkerFromDataBase.setEmail(email);
        Walker walker = walkerRepository.save(walkerFromDataBase);

        return mapper.map(walker);
    }

}

