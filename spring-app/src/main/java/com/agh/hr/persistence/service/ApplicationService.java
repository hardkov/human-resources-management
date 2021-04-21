package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Application;
import com.agh.hr.persistence.model.Status;
import com.agh.hr.persistence.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationService {


    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository=applicationRepository;
    }

    public boolean saveApplication(Application application) {
        try {
            applicationRepository.save(application);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<Application> getById(Long id) {
        return applicationRepository.findById(id);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public void deleteApplication(Long applicationId) {
            applicationRepository.deleteById(applicationId);
    }

    public List<Application> getByDateEquals(LocalDateTime date) {
        return applicationRepository.findByDateEquals(date);
    }

    public List<Application> getByDateBefore(LocalDateTime date) {
        return applicationRepository.findByDateBefore(date);
    }

    public List<Application> getByDateAfter(LocalDateTime date) {
        return applicationRepository.findByDateAfter(date);
    }

    public List<Application> getByPlaceContaining(String place) {
        return applicationRepository.findByPlaceContaining(place);
    }

    public List<Application> getByStatusEquals(Status status) {
        return applicationRepository.findByStatusEquals(status);
    }

    public List<Application> getByFullname(String firstname,String lastname) {
        return applicationRepository.findByPersonalData_FirstnameAndPersonalData_Lastname(firstname,lastname);
    }

    
}
