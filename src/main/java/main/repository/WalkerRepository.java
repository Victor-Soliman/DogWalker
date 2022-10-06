package main.repository;

import main.entities.Walker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalkerRepository extends JpaRepository<Walker, Integer> {

    @Override
    Optional<Walker> findById(Integer integer);

    List<Walker> findByName(String name);

    Walker findByEmail(String email);

    List<Walker> findByYearsOfExperienceGreaterThan(Integer yearsOfExperience);
}
