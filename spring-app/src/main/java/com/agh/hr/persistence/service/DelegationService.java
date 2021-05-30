package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Delegation;
import com.agh.hr.persistence.repository.DelegationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DelegationService {


    private final DelegationRepository delegationRepository;

    @Autowired
    public DelegationService(DelegationRepository delegationRepository){
        this.delegationRepository=delegationRepository;
    }

    public boolean saveLeave(Delegation delegation) {
        try {
            delegationRepository.save(delegation);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<Delegation> getById(Long id) {
        return delegationRepository.findById(id);
    }

    public List<Delegation> getAllLeaves() {
        return delegationRepository.findAll();
    }

    public void deleteLeave(Long leaveId) {
        delegationRepository.deleteById(leaveId);
    }

}
