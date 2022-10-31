package main.services;

import main.dto.DogRequest;
import main.dto.DogResponse;
import main.entities.Dog;
import main.mappers.DogRequestMapper;
import main.mappers.DogResponseMapper;
import main.repository.DogsRepository;
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

@ExtendWith(MockitoExtension.class)
class DogsServiceTest {

    @Mock
    private DogsRepository dogsRepository;

    @Mock
    private DogResponseMapper dogResponseMapper;

    @Mock
    private DogRequestMapper dogRequestMapper;

    @InjectMocks
    private DogsService dogsService;

    //dummy data
    private static int ID = 5;

    private static final Dog DOG_RECORD = new Dog(ID, "Sun", 6, "Labrador",
            true, "89-784-6532");

    private final List<Dog> DOGS = List.of(DOG_RECORD,
            new Dog(7, "Joe", 2, "Bigle", true, "14-541-6587"));


    private DogRequest dogRequestRecord = new DogRequest(ID, "Black", 4, "Haski",
            true, "987-854-6522");

    @Test
    void findAll() {
        Mockito.when(dogsRepository.findAll()).thenReturn(DOGS);

        List<DogResponse> expected = DOGS.stream().map(dog -> dogResponseMapper.map(dog)).toList();

        Assertions.assertEquals(expected, dogsService.findAll());
    }

    //since the method return an optional , we make 2 test methods
    // 1. for the case when it actually returns a record
    @ParameterizedTest
    @ValueSource(ints = {1})
    void findById(Integer ID) {
        Mockito.when(dogsRepository.findById(ID)).thenReturn(Optional.of(DOG_RECORD));

        Optional<Dog> dogRecord = Optional.of(DOG_RECORD);

        Assertions.assertEquals(dogRecord, dogsRepository.findById(ID));

    }

    //2. when throws an exception
    @ParameterizedTest
    @ValueSource(ints = {1})
    void findByIdException(Integer ID) throws NotFoundException {
        Mockito.when(dogsRepository.findById(ID)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> dogsService.findById(ID));

    }

    @Test
    void findByName() {

        String dogName = "leo ";

        List<Dog> dogsWithNameLeo = dogsRepository.findByName(dogName);
        dogsWithNameLeo.add(DOG_RECORD);

        Mockito.when(dogsRepository.findByName(dogName)).thenReturn(dogsWithNameLeo);

        List<DogResponse> expected = dogsWithNameLeo.stream().map(dog -> dogResponseMapper.map(dog)).toList();
        Assertions.assertEquals(expected, dogsService.findByName(dogName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Leo"})
    void findByNameException() {

        final String nonexistentDogName = "Leo";
        Mockito.when(dogsRepository.findByName(nonexistentDogName)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> dogsService.findByName(nonexistentDogName));

    }

    @Test
    void save() {

        Dog dog = dogRequestMapper.map(dogRequestRecord);
        DogResponse responseDog = dogResponseMapper.map(dog);

        Mockito.when(dogRequestMapper.map(dogRequestRecord)).thenReturn(dog);

        Mockito.when(dogsRepository.save(dog)).thenReturn(dog);

        Assertions.assertEquals(responseDog, dogsService.save(dogRequestRecord));
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    void delete() {

        Optional<Dog> dogRecord = Optional.of(DOG_RECORD);

        Mockito.when(dogsRepository.findById(ID)).thenReturn(dogRecord);

        Assertions.assertEquals(dogRecord, dogsRepository.findById(ID));

        Mockito.verifyNoMoreInteractions(dogsRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    void deleteException() {
        Mockito.when(dogsRepository.findById(ID)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> dogsRepository.findById(ID));
    }

    @Test
    void setAllParameters() {

        settingParameters(DOG_RECORD, dogRequestRecord);

        Assertions.assertEquals(DOG_RECORD.getName(), dogRequestRecord.getName());
        Assertions.assertEquals(DOG_RECORD.getAge(), dogRequestRecord.getAge());
        Assertions.assertEquals(DOG_RECORD.getGenealogy(), dogRequestRecord.getGenealogy());
        Assertions.assertEquals(DOG_RECORD.getHasMicrochip(), dogRequestRecord.getHasMicrochip());
        Assertions.assertEquals(DOG_RECORD.getChipNumber(), dogRequestRecord.getChipNumber());

    }


    @Test
    void update() {
        Optional<Dog> dogRecord = Optional.of(DOG_RECORD);
        Dog dog = dogRecord.get();

        settingParameters(dog, dogRequestRecord);
        Dog savedDog = dogsRepository.save(dog);
        DogResponse dogResponse = dogResponseMapper.map(savedDog);

        Mockito.when(dogsRepository.findById(ID)).thenReturn(dogRecord);
        Mockito.when(dogsRepository.save(dog)).thenReturn(savedDog);
        Mockito.when(dogResponseMapper.map(savedDog)).thenReturn(dogResponse);

        Assertions.assertEquals(dogResponse, dogsService.update(ID, dogRequestRecord));

        Mockito.verify(dogsRepository).findById(ID);

    }

    @Test
    void updateException() {

        Mockito.when(dogsRepository.findById(ID)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> dogsService.update(ID, dogRequestRecord));

    }

    @Test
    void setPartialParameters() {
        setParametersPartially(dogRequestRecord, DOG_RECORD);

        Assertions.assertEquals(DOG_RECORD.getName(), dogRequestRecord.getName() == null ?
                DOG_RECORD.getName() : dogRequestRecord.getName());
        Assertions.assertEquals(DOG_RECORD.getAge(), dogRequestRecord.getAge() == null ?
                DOG_RECORD.getAge() : dogRequestRecord.getAge());
        Assertions.assertEquals(DOG_RECORD.getHasMicrochip(), dogRequestRecord.getHasMicrochip() == null ?
                DOG_RECORD.getHasMicrochip() : dogRequestRecord.getHasMicrochip());
        Assertions.assertEquals(DOG_RECORD.getChipNumber(), dogRequestRecord.getChipNumber() == null ?
                DOG_RECORD.getChipNumber() : dogRequestRecord.getChipNumber());

    }

    @Test
    void updateDogPartially() {

        Optional<Dog> dogRecord = Optional.of(DOG_RECORD);
        Dog dog = dogRecord.get();
        Mockito.when(dogsRepository.findById(ID)).thenReturn(dogRecord);

        setParametersPartially(dogRequestRecord, dog);

        Dog saved = dogsRepository.save(dog);

        DogResponse dogResponse = dogResponseMapper.map(dog);

        Assertions.assertEquals(dogRecord, dogsRepository.findById(ID));
        Assertions.assertEquals(saved, dogsRepository.save(dog));
        Assertions.assertEquals(dogResponse, dogResponseMapper.map(dog));

    }

    @Test
    void updateDogPartiallyException() {

        Mockito.when(dogsRepository.findById(ID)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dogsService.updateDogPartially(dogRequestRecord));
    }


    private void settingParameters(Dog dog, DogRequest dogRequest) {
        dog.setName(dogRequest.getName());
        dog.setAge(dogRequest.getAge());
        dog.setGenealogy(dogRequest.getGenealogy());
        dog.setHasMicrochip(dogRequest.getHasMicrochip());
        dog.setChipNumber(dogRequest.getChipNumber());
    }

    private void setParametersPartially(DogRequest request, Dog dogFromDataBase) {
        dogFromDataBase.setName(request.getName() == null ? dogFromDataBase.getName() : request.getName());

        dogFromDataBase.setAge(request.getAge() == null ? dogFromDataBase.getAge() : request.getAge());

        dogFromDataBase.setHasMicrochip(request.getHasMicrochip() == null ?
                dogFromDataBase.getHasMicrochip() : request.getHasMicrochip());

        dogFromDataBase.setChipNumber(request.getChipNumber() == null ?
                dogFromDataBase.getChipNumber() : request.getChipNumber());
    }
}