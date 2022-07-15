package dev.megashopper.common.repository;


import dev.megashopper.common.entities.Password;
import dev.megashopper.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    @Query("from User u where u.username = :username")
    List<User> findUsersByUsername(String username);

    List<User> findAll();


    @Query("select u from User u where u.username = ?1")
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);


    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    Optional<User> findUserByEmailAndPassword(String email, Password password);


    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> findUserByUsernameAndPassword(String username, Password password);


}

