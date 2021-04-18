package com.agh.hr.persistence.repository;


import com.agh.hr.persistence.model.FooUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FooUserRepository extends JpaRepository<FooUser, Long> {

    Optional<FooUser> findByUsername(String username);

}
