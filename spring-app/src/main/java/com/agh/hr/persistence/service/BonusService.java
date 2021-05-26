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
        } catch(Exception e) {
            return false;
        }

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

}
