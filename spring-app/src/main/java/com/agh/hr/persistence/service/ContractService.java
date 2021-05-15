package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.ContractRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RoleService roleService;

    @Autowired
    public ContractService(ContractRepository contractRepository, RoleService roleService){
        this.contractRepository=contractRepository;
        this.roleService = roleService;
    }

    public Optional<Contract> saveContract(Contract contract) {
        if(contract.getUser() == null)
            return Optional.empty();
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(!userAuth.getAuthorities().contains(roleService.adminRole()) && !Auth.getWriteIds(userAuth).contains(contract.getUser().getId()))
                return Optional.empty();
        try {
            return Optional.of(contractRepository.save(contract));
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Contract> getById(Long id) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(userAuth.getAuthorities().contains(roleService.adminRole()))
            return contractRepository.findByIdAdmin(id);
        return contractRepository.findById(id, Auth.getReadIds(userAuth));
    }

    public List<Contract> getAllContracts() {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(userAuth.getAuthorities().contains(roleService.adminRole()))
            return contractRepository.findAllAdmin();
        return contractRepository.findAll(Auth.getReadIds(userAuth));
    }

    public boolean deleteContract(Long contractId) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(userAuth.getAuthorities().contains(roleService.adminRole()) || userAuth.getPermissions().getWrite().contains(contractId)) {
            contractRepository.deleteById(contractId);
            return true;
        }
        else
            return false;
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
