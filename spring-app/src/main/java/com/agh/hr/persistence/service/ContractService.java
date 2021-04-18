package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContractService {


    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository){
        this.contractRepository=contractRepository;
    }

    public boolean saveContract(Contract application) {
        try {
            contractRepository.save(application);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<Contract> getById(Long id) {
        return contractRepository.findById(id);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public void deleteContract(Long applicationId) {
            contractRepository.deleteById(applicationId);
    }

    public List<Contract> getByStartDateEquals(LocalDateTime date) {
        return contractRepository.findByStartDateEquals(date);
    }

    public List<Contract> getByStartDateBefore(LocalDateTime date) {
        return contractRepository.findByStartDateBefore(date);
    }

    public List<Contract> getByStartDateAfter(LocalDateTime date) {
        return contractRepository.findByStartDateAfter(date);
    }

    public List<Contract> getByEndDateEquals(LocalDateTime date) {
        return contractRepository.findByEndDateEquals(date);
    }

    public List<Contract> getByEndDateBefore(LocalDateTime date) {
        return contractRepository.findByEndDateBefore(date);
    }

    public List<Contract> getByEndDateAfter(LocalDateTime date) {
        return contractRepository.findByEndDateAfter(date);
    }

    public List<Contract> getByContractType(ContractType type) {
        return contractRepository.findByContractType(type);
    }

    public List<Contract> getByBaseSalaryBetween(BigDecimal first, BigDecimal second) {
        return contractRepository.findByBaseSalaryBetween(first,second);
    }

    public List<Contract> getByBaseSalaryLessThan(BigDecimal value) {
        return contractRepository.findByBaseSalaryLessThan(value);
    }

    public List<Contract> getByBaseSalaryGreaterThan(BigDecimal value) {
        return contractRepository.findByBaseSalaryGreaterThan(value);
    }

    
}
