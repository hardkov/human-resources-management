package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.repository.BonusApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BonusApplicationService {


    private final BonusApplicationRepository bonusApplicationRepository;

    @Autowired
    public BonusApplicationService(BonusApplicationRepository bonusApplicationRepository){
        this.bonusApplicationRepository=bonusApplicationRepository;
    }

    public boolean saveBonusApplication(BonusApplication bonusApplication) {
        try {
            bonusApplicationRepository.save(bonusApplication);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<BonusApplication> getById(Long id) {
        return bonusApplicationRepository.findById(id);
    }

    public List<BonusApplication> getAllBonusApplications() {
        return bonusApplicationRepository.findAll();
    }

    public void deleteBonusApplication(Long BonusApplicationId) {
            bonusApplicationRepository.deleteById(BonusApplicationId);
    }

    public List<BonusApplication> getByMoneyBetween(BigDecimal first, BigDecimal second) {
        return bonusApplicationRepository.findByMoneyBetween(first,second);
    }

    public List<BonusApplication> getByMoneyLessThan(BigDecimal value) {
        return bonusApplicationRepository.findByMoneyLessThan(value);
    }

    public List<BonusApplication> getByMoneyGreaterThan(BigDecimal value) {
        return bonusApplicationRepository.findByMoneyGreaterThan(value);
    }
    
}
