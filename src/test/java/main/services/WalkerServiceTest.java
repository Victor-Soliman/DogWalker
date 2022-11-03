package main.services;

import main.dto.WalkerRequest;
import main.dto.WalkerResponse;
import main.entities.Walker;
import main.mappers.WalkerRequestMapper;
import main.mappers.WalkerResponseMapper;
import main.repository.WalkerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalkerServiceTest {

    private static final int ID = 5;
    private static final int ID2 = 6;
    private static final Walker WALKER_RECORD = new Walker(ID, "James Martin", 19, "jie28@",
            "Bucharest", "0784751236", "Camil Resu 78", "martin11@gmail.com",
            1, true);
    private static final Walker WALKER_RECORD_2 = new Walker(ID2, "Alin Munteanu", 45, "mmihfh4567",
            "Bucharest", "0784333362", "Pantelimon 45", "alin91@gmail.com",
            6, true);

    private final List<Walker> WALKERS = List.of(WALKER_RECORD, WALKER_RECORD_2);

    private final WalkerRequest WALKER_REQUEST = new WalkerRequest(ID, "Alin Munteanu", 45, "mmihfh4567",
            "Bucharest", "0784333362", "Pantelimon 45", "alin91@gmail.com",
            6, true);

    private final WalkerResponse WALKER_RESPONSE = new WalkerResponse(ID, "Alin Munteanu", 45, "mmihfh4567",
            "Bucharest", "0784333362", "Pantelimon 45", "alin91@gmail.com",
            6, true);

    @Mock
    private WalkerRepository walkerRepository;

    @Mock
    private WalkerResponseMapper responseMapper;

    @Mock
    private WalkerRequestMapper requestMapper;

    @InjectMocks
    private WalkerService walkerService;


    @Test
    void findAll() {

        Mockito.when(walkerRepository.findAll()).thenReturn(WALKERS);

        List<WalkerResponse> expected = WALKERS.stream().map(walker -> responseMapper.map(walker)).toList();

        Assertions.assertEquals(expected, walkerService.findAll());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void findById(Integer ID) {
        Mockito.when(walkerRepository.findById(ID)).thenReturn(Optional.of(WALKER_RECORD));

        WalkerResponse expected = responseMapper.map(WALKER_RECORD);

        Assertions.assertEquals(expected, walkerService.findById(ID));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void findByIdException(Integer ID) {
        Mockito.when(walkerRepository.findById(ID)).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> walkerService.findById(ID));
    }

    @ParameterizedTest
    @ValueSource(strings = "Alin Munteanu")
    void findByName(String name) {
        Mockito.when(walkerRepository.findByName(name)).thenReturn(WALKERS);

        List<WalkerResponse> expected = walkerRepository.findByName(name).stream()
                .map(walker -> responseMapper.map(walker)).toList();

        Assertions.assertEquals(expected, walkerService.findByName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Michel Hitman"})
    void findByNameException(String notHereName) {
        Mockito.when(walkerRepository.findByName(notHereName)).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> walkerService.findByName(notHereName));
    }

    @Test
    void save() {
        Mockito.when(requestMapper.map(WALKER_REQUEST)).thenReturn(WALKER_RECORD);
        Mockito.when(walkerRepository.save(WALKER_RECORD)).thenReturn(WALKER_RECORD);

        WalkerResponse expected = responseMapper.map(WALKER_RECORD);

        Assertions.assertEquals(expected, walkerService.save(WALKER_REQUEST));
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    void delete(Integer id) {
        Mockito.when(walkerRepository.findById(id)).thenReturn(Optional.of(WALKER_RECORD));

        Integer walker_recordId = WALKER_RECORD.getId();

        Assertions.assertEquals(WALKER_RECORD, walkerRepository.findById(walker_recordId).get());

        Mockito.verifyNoMoreInteractions(walkerRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    void deleteException(Integer id) {
        Mockito.when(walkerRepository.findById(id)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> walkerService.delete(id));

        Mockito.verifyNoMoreInteractions(walkerRepository);
    }

    @Test
    void setAllParameters() {
        settingParameters(WALKER_REQUEST, WALKER_RECORD);

        Assertions.assertEquals(WALKER_RECORD.getName(), WALKER_REQUEST.getName());
        Assertions.assertEquals(WALKER_REQUEST.getAge(), WALKER_RECORD.getAge());
        Assertions.assertEquals(WALKER_REQUEST.getPassword(), WALKER_RECORD.getPassword());
        Assertions.assertEquals(WALKER_REQUEST.getCity(), WALKER_RECORD.getCity());
        Assertions.assertEquals(WALKER_REQUEST.getPhoneNumber(), WALKER_RECORD.getPhoneNumber());
        Assertions.assertEquals(WALKER_REQUEST.getAddress(), WALKER_RECORD.getAddress());
        Assertions.assertEquals(WALKER_REQUEST.getEmail(), WALKER_RECORD.getEmail());
        Assertions.assertEquals(WALKER_REQUEST.getYearsOfExperience(), WALKER_RECORD.getYearsOfExperience());
        Assertions.assertEquals(WALKER_REQUEST.getIsAvailable(), WALKER_RECORD.getIsAvailable());
    }


    @Test
    void update() {
        Optional<Walker> walkerRecord = Optional.of(WALKER_RECORD);
        Walker walker = walkerRecord.get();

        Mockito.when(walkerRepository.findById(ID)).thenReturn(walkerRecord);

        settingParameters(WALKER_REQUEST, WALKER_RECORD);

        Walker saved = walkerRepository.save(walker);

        WalkerResponse walkerResponse = responseMapper.map(saved);


        Assertions.assertEquals(walkerRecord, walkerRepository.findById(ID));
        Assertions.assertEquals(saved, walkerRepository.save(walker));
        Assertions.assertEquals(walkerResponse, responseMapper.map(walker));
    }

    @Test
    void updateException() {
        Mockito.when(walkerRepository.findById(ID)).thenThrow(UnsupportedOperationException.class);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> walkerService.update(ID, WALKER_REQUEST));
    }

    @Test
    void setParametersPartially() {
        setPartialParameters(WALKER_REQUEST, WALKER_RECORD);

        Assertions.assertEquals(WALKER_RECORD.getName(), WALKER_REQUEST.getName());
        Assertions.assertEquals(WALKER_RECORD.getAge(), WALKER_REQUEST.getAge());
        Assertions.assertEquals(WALKER_RECORD.getPassword(), WALKER_REQUEST.getPassword());
        Assertions.assertEquals(WALKER_RECORD.getCity(), WALKER_REQUEST.getCity());
        Assertions.assertEquals(WALKER_RECORD.getPhoneNumber(), WALKER_REQUEST.getPhoneNumber());
        Assertions.assertEquals(WALKER_RECORD.getAddress(), WALKER_REQUEST.getAddress());
        Assertions.assertEquals(WALKER_RECORD.getEmail(), WALKER_REQUEST.getEmail());
        Assertions.assertEquals(WALKER_RECORD.getYearsOfExperience(), WALKER_REQUEST.getYearsOfExperience());
        Assertions.assertEquals(WALKER_RECORD.getIsAvailable(), WALKER_REQUEST.getIsAvailable());

    }


    @Test
    void updateWalkerPartially() {
        Mockito.when(walkerRepository.findById(ID)).thenReturn(Optional.of(WALKER_RECORD));
        Walker walker = Optional.of(WALKER_RECORD).get();

        setPartialParameters(WALKER_REQUEST, walker);

        Walker saved = walkerRepository.save(walker);

        Mockito.when(saved).thenReturn(walker);

        WalkerResponse response = responseMapper.map(saved);

        Assertions.assertEquals(response, walkerService.updateWalkerPartially(WALKER_REQUEST));


    }

    @Test
    void updateWalkerPartiallyException() {
        Mockito.when(walkerRepository.findById(ID)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> walkerService.updateWalkerPartially(WALKER_REQUEST));
    }

    @Test
    void checkWalkerAvailability() {
        Mockito.when(walkerRepository.findAll()).thenReturn(WALKERS);

        List<Walker> availableWalkers = WALKERS.stream()
                .filter(Walker::getIsAvailable)
                .toList();

        List<WalkerResponse> walkerResponseList = availableWalkers.stream()
                .map(responseMapper::map).toList();

        Assertions.assertEquals(walkerResponseList,walkerService.checkWalkerAvailability());
    }

    @Test
    void findTopWalkersByYearsOfExperienceGreaterThan() {

        Mockito.when(walkerRepository.findByYearsOfExperienceGreaterThan(ID)).thenReturn(WALKERS);

        List<WalkerResponse> walkerResponses = WALKERS.stream().map(walker -> responseMapper.map(walker)).toList();

        Assertions.assertEquals(walkerResponses,walkerService.findTopWalkersByYearsOfExperienceGreaterThan(ID));
    }

    //helper methods
    private void settingParameters(WalkerRequest walkerRequest, Walker walker) {
        walker.setName(walkerRequest.getName());
        walker.setAge(walkerRequest.getAge());
        walker.setPassword(walkerRequest.getPassword());
        walker.setCity(walkerRequest.getCity());
        walker.setPhoneNumber(walkerRequest.getPhoneNumber());
        walker.setAddress(walkerRequest.getAddress());
        walker.setEmail(walkerRequest.getEmail());
        walker.setYearsOfExperience(walkerRequest.getYearsOfExperience());
        walker.setIsAvailable(walkerRequest.getIsAvailable());

    }

    private void setPartialParameters(WalkerRequest walker_request, Walker walkerRecord) {

        walkerRecord.setName(walker_request.getName() == null ? walkerRecord.getName() : walker_request.getName());

        walkerRecord.setAge(walker_request.getAge() == null ? walkerRecord.getAge() : walker_request.getAge());

        walkerRecord.setPassword(walker_request.getPassword() == null ?
                walkerRecord.getPassword() : walker_request.getPassword());

        walkerRecord.setCity(walker_request.getCity() == null ? walkerRecord.getCity() : walker_request.getCity());

        walkerRecord.setPhoneNumber(walker_request.getPhoneNumber() == null ?
                walkerRecord.getPhoneNumber() : walker_request.getPhoneNumber());

        walkerRecord.setAddress(walker_request.getAddress() == null ?
                walkerRecord.getAddress() : walker_request.getAddress());

        walkerRecord.setEmail(walker_request.getEmail() == null ? walkerRecord.getEmail() : walker_request.getEmail());

        walkerRecord.setYearsOfExperience(walker_request.getYearsOfExperience() == null ?
                walkerRecord.getYearsOfExperience() : walker_request.getYearsOfExperience());

        walkerRecord.setIsAvailable(walker_request.getIsAvailable() == null ?
                walkerRecord.getIsAvailable() : walker_request.getIsAvailable());
    }
}