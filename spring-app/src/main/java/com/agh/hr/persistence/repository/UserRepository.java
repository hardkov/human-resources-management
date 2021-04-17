package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findByFirstname(String firstname);
    Optional<List<User>> findByLastname(String lastname);
    Optional<List<User>> findByFirstnameAndLastname(String firstname,String lastname);


}
