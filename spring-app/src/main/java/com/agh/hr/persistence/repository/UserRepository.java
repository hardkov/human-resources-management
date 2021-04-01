package com.agh.hr.persistence.repository;


import com.agh.hr.persistence.model.FooUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FooUser, Long> {

//    Spring can also generate implementation based on a method name and signature!
    Optional<FooUser> findByFirstname(String firstname);

//    It's possible to insert jpql query to do a specific thing
//    @Query("<JPQ statement here>")
//    List<User> findByFoo(User user);

}
