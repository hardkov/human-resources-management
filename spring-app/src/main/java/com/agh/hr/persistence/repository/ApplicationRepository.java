package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Application;
import com.agh.hr.persistence.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<List<Application>> findByDateEquals(LocalDateTime date);
    Optional<List<Application>> findByDateBefore(LocalDateTime date);
    Optional<List<Application>> findByDateAfter(LocalDateTime date);

    Optional<List<Application>> findByPlaceContaining(String place);

    Optional<List<Application>> findByStatusEquals(Status status);

    Optional<List<Application>> findByPersonalData_FirstnameAndLastname(String firstname,String lastname);

}
