package com.agh.hr.persistence.repository;
import com.agh.hr.persistence.model.Application;
import com.agh.hr.persistence.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByDateEquals(LocalDateTime date);
    List<Application> findByDateBefore(LocalDateTime date);
    List<Application> findByDateAfter(LocalDateTime date);

    List<Application> findByPlaceContaining(String place);

    List<Application> findByStatusEquals(Status status);

    List<Application> findByPersonalData_FirstnameAndPersonalData_Lastname(String firstname,String lastname);

}
