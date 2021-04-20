package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Bonus;
import com.agh.hr.persistence.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BonusService {


    private final BonusRepository bonusRepository;

    @Autowired
    public BonusService(BonusRepository bonusRepository){
        this.bonusRepository=bonusRepository;
    }

    public boolean saveLeave(Bonus bonus) {
        try {
            bonusRepository.save(bonus);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<Bonus> getById(Long id) {
        return bonusRepository.findById(id);
    }

    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAll();
    }

    public void deleteBonus(Long leaveId) {
        bonusRepository.deleteById(leaveId);
    }

    public List<Bonus> getByStartDateEquals(LocalDateTime startDate) {
        return bonusRepository.findByStartDateEquals(startDate);
    }

    public List<Bonus> getByStartDateBefore(LocalDateTime before) {
        return bonusRepository.findByStartDateBefore(before);
    }

    public List<Bonus> getByStartDateAfter(LocalDateTime after) {
        return bonusRepository.findByStartDateAfter(after);
    }

    public List<Bonus> getByEndDateEquals(LocalDateTime endDate) {
        return bonusRepository.findByEndDateEquals(endDate);
    }

    public List<Bonus> getByEndDateBefore(LocalDateTime before) {
        return bonusRepository.findByEndDateBefore(before);
    }

    public List<Bonus> getByEndDateAfter(LocalDateTime after) {
        return bonusRepository.findByEndDateAfter(after);
    }

    public List<Bonus> getByAmountBetween(BigDecimal first, BigDecimal second) {
        return bonusRepository.findByAmountBetween(first,second);
    }

    public List<Bonus> getByAmountLessThan(BigDecimal value) {
        return bonusRepository.findByAmountLessThan(value);
    }

    public List<Bonus> getByAmountGreaterThan(BigDecimal value) {
        return bonusRepository.findByAmountGreaterThan(value);
    }
}
