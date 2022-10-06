package main.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.WalkerRequest;
import main.dto.WalkerResponse;
import main.mappers.WalkerRequestMapper;
import main.mappers.WalkerResponseMapper;
import main.repository.WalkerRepository;
import main.entities.Walker;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

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

        Walker walkerFromDataBaseByID = walkerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("* The Id : " + id + " was not found in the database"));

        return mapper.map(walkerFromDataBaseByID);
    }


    public List<WalkerResponse> findByName(String name) {
        List<Walker> walkersFromDataBase = walkerRepository.findByName(name);

        if (walkersFromDataBase.isEmpty()) {
            log.error("Walker was not found in the database with name : " + name);

            throw new NotFoundException("The Walker name : " + name + " was not found in the database");
        }

        return walkerRepository.findByName(name).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public WalkerResponse save(WalkerRequest walkerRequest) {
        Walker mappedWalker = requestMapper.map(walkerRequest);
        walkerRepository.save(mappedWalker);

        log.info("Walker was Successfully saved in the database ..");

        return mapper.map(mappedWalker);
    }

    public void delete(Integer id) {

        Optional<Walker> walkerFromDataBaseByID = walkerRepository.findById(id);

        if (walkerFromDataBaseByID.isPresent()) {
            log.info("Walker with id : " + id + " was found in the database..");

            walkerRepository.deleteById(id);

            log.info("Walker with id : " + id + " was Successfully deleted..");

        } else {
            log.error("Walker was not found in the database with id : " + id);

            throw new NotFoundException("* The Id : " + id + " was not found in the database");
        }
    }

    public WalkerResponse update(Integer id, WalkerRequest walkerRequest) {
        Optional<Walker> walkerById = walkerRepository.findById(id);
        if (walkerById.isPresent()) {

            log.info("Walker was found in the database");

            Walker walkerFromDataBase = walkerById.get();

            setAllParameters(walkerRequest, walkerFromDataBase);

            Walker saved = walkerRepository.save(walkerFromDataBase);

            log.info("Successful Update! .. Walker was mapped to the database");

            return mapper.map(saved);
        } else
            log.error("Walker was not found in the database with id : " + id);

        throw new UnsupportedOperationException("Invalid Id : " + id);
    }



    public WalkerResponse updateWalkerPartially(WalkerRequest request) {
        Integer id = request.getId();

        Walker walkerFromDataBase = walkerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(" * Walker was not found in the database with id : " + id));

        setParametersPartially(request, walkerFromDataBase);

        walkerRepository.save(walkerFromDataBase);

        return mapper.map(walkerFromDataBase);

    }

    // functionality
    public List<WalkerResponse> checkWalkerAvailability() {
        List<Walker> allWalkers = walkerRepository.findAll();

        List<Walker> availableWalkers = allWalkers.stream()
                .filter(Walker::getIsAvailable)
                .toList();

        return availableWalkers.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    // functionality
    public List<WalkerResponse> findTopWalkersByYearsOfExperienceGreaterThan(Integer years) {
        List<Walker> topByYearsOfExperienceGreaterThan = walkerRepository.findByYearsOfExperienceGreaterThan(years);

        return topByYearsOfExperienceGreaterThan.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    //helper
    private void setAllParameters(WalkerRequest walkerRequest, Walker oldWalker) {
        oldWalker.setName(walkerRequest.getName());
        oldWalker.setAge(walkerRequest.getAge());
        oldWalker.setPassword(walkerRequest.getPassword());
        oldWalker.setCity(walkerRequest.getCity());
        oldWalker.setPhoneNumber(walkerRequest.getPhoneNumber());
        oldWalker.setAddress(walkerRequest.getAddress());
        oldWalker.setEmail(walkerRequest.getEmail());
        oldWalker.setYearsOfExperience(walkerRequest.getYearsOfExperience());
        oldWalker.setIsAvailable(walkerRequest.getIsAvailable());
    }

    //helper
    private void setParametersPartially(WalkerRequest request, Walker walkerFromDataBase) {
        walkerFromDataBase.setName(request.getName() == null ? walkerFromDataBase.getName() : request.getName());

        walkerFromDataBase.setAge(request.getAge() == null ? walkerFromDataBase.getAge() : request.getAge());

        walkerFromDataBase.setPassword(request.getPassword() == null ?
                walkerFromDataBase.getPassword() : request.getPassword());

        walkerFromDataBase.setCity(request.getCity() == null ? walkerFromDataBase.getCity() : request.getCity());

        walkerFromDataBase.setPhoneNumber(request.getPhoneNumber() == null ?
                walkerFromDataBase.getPhoneNumber() : request.getPhoneNumber());

        walkerFromDataBase.setAddress(request.getAddress() == null ?
                walkerFromDataBase.getAddress() : request.getAddress());

        walkerFromDataBase.setEmail(request.getEmail() == null ? walkerFromDataBase.getEmail() : request.getEmail());

        walkerFromDataBase.setYearsOfExperience(request.getYearsOfExperience() == null ?
                walkerFromDataBase.getYearsOfExperience() : request.getYearsOfExperience());

        walkerFromDataBase.setIsAvailable(request.getIsAvailable() == null ?
                walkerFromDataBase.getIsAvailable() : request.getIsAvailable());
    }


}

