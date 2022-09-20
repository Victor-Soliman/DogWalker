package main.repository;

import main.controllers.dto.DogResponse;
import main.repository.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogsRepository extends JpaRepository<Dog, Integer> {
    @Override
    Optional<Dog> findById(Integer integer);

   List<Dog> findByName(String name);


}
