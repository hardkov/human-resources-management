package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByPersonalData_Firstname(String firstname);
    List<User> findByPersonalData_Lastname(String lastname);
    List<User> findByPersonalData_FirstnameAndPersonalData_Lastname(String firstname,String lastname);


}
