package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.DelegationApplication;
import com.agh.hr.persistence.repository.DelegationApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DelegationApplicationService {


    private final DelegationApplicationRepository delegationApplicationRepository;

    @Autowired
    public DelegationApplicationService(DelegationApplicationRepository delegationApplicationRepository){
        this.delegationApplicationRepository=delegationApplicationRepository;
    }

    public boolean saveDelegationApplication(DelegationApplication delegationApplication) {
        try {
            delegationApplicationRepository.save(delegationApplication);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<DelegationApplication> getById(Long id) {
        return delegationApplicationRepository.findById(id);
    }

    public List<DelegationApplication> getAllDelegationApplications() {
        return delegationApplicationRepository.findAll();
    }

    public void deleteDelegationApplication(Long DelegationApplicationId) {
            delegationApplicationRepository.deleteById(DelegationApplicationId);
    }

    public List<DelegationApplication> getByStartDateEquals(LocalDateTime startDate) {
        return delegationApplicationRepository.findByStartDateEquals(startDate);
    }

    public List<DelegationApplication> getByStartDateBefore(LocalDateTime before) {
        return delegationApplicationRepository.findByStartDateBefore(before);
    }

    public List<DelegationApplication> getByStartDateAfter(LocalDateTime after) {
        return delegationApplicationRepository.findByStartDateAfter(after);
    }

    public List<DelegationApplication> getByEndDateEquals(LocalDateTime endDate) {
        return delegationApplicationRepository.findByEndDateEquals(endDate);
    }

    public List<DelegationApplication> getByEndDateBefore(LocalDateTime before) {
        return delegationApplicationRepository.findByEndDateBefore(before);
    }

    public List<DelegationApplication> getByEndDateAfter(LocalDateTime after) {
        return delegationApplicationRepository.findByEndDateAfter(after);
    }

    public List<DelegationApplication> getByDestinationContaining(String destination) {
        return delegationApplicationRepository.findByDestinationContaining(destination);
    }
    
}
