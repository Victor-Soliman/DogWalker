package main.repository;

import main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer integer) throws UnsupportedOperationException;

    List<User> findByName(String name) throws UnsupportedOperationException;

    User findByEmail(String email);


}
