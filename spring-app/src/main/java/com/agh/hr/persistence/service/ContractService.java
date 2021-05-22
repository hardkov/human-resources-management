package com.agh.hr.persistence.service;

import com.agh.hr.persistence.dto.ContractDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.Leave;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractService {


    private final ContractRepository contractRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final Converters converters;
    @Autowired
    public ContractService(ContractRepository contractRepository, RoleService roleService, UserService userService,
                           Converters converters){
        this.contractRepository=contractRepository;
        this.roleService = roleService;
        this.userService=userService;
        this.converters = converters;
    }
    
    public Optional<ContractDTO> updateContract(ContractDTO contractDTO) {
        val userAuth=Auth.getCurrentUser();
        Optional<User> userOpt = userService.getRawById(contractDTO.getUser().getId());
        if(!userOpt.isPresent())
            return Optional.empty();
        User user=userOpt.get();
        Contract contract = converters.DTOToContract(contractDTO);
        contract.setUser(user);
        if(!roleService.isAdmin(userAuth))
            if(!Auth.getWriteIds(userAuth).contains(user.getId()))
                return Optional.empty();
        converters.updateContractWithDTO(contractDTO,contract);
        val result= Optional.of(contractRepository.save(contract));
        return result.map(converters::contractToDTO);
    }
    
    public Optional<ContractDTO> saveContract(ContractDTO contractDTO,Long userId) {
        Optional<User> userOpt = userService.getRawById(userId);
        if(!userOpt.isPresent())
            return Optional.empty();
        User user = userOpt.get();
        Contract contract = converters.DTOToContract(contractDTO);
        contract.setUser(user);
        contract.setId(0L);

        val userAuth=Auth.getCurrentUser();
        if(!roleService.isAdmin(userAuth) && !Auth.getWriteIds(userAuth).contains(userId))
                return Optional.empty();
        try {
            return Optional.of(contractRepository.save(contract)).map(converters::contractToDTO);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ContractDTO> getById(Long id) {
        val userAuth=Auth.getCurrentUser();
        return contractRepository.findById(id, Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .map(converters::contractToDTO);
    }

    public List<ContractDTO> getByUserId(Long id) {

        val userAuth=Auth.getCurrentUser();
        return contractRepository.findByUserId(id,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::contractToDTO)
                .collect(Collectors.toList());
    }

    public List<ContractDTO> getAllContracts() {
        val userAuth=Auth.getCurrentUser();
        return contractRepository.findAll(Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::contractToDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteContract(Long contractId) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth) || userAuth.getPermissions().getWrite().contains(contractId)) {
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
