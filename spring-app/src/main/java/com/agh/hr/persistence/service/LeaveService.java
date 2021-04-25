package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveService {


    private final LeaveRepository leaveRepository;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository){
        this.leaveRepository=leaveRepository;
    }

    public Optional<Leave> saveLeave(Leave leave) {
        try {
            return Optional.of(leaveRepository.save(leave));
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<Leave> getById(Long id) {
        return leaveRepository.findById(id);
    }

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public void deleteLeave(Long leaveId) {
            leaveRepository.deleteById(leaveId);
    }

    public List<Leave> getByStartDateEquals(LocalDateTime startDate) {
        return leaveRepository.findByStartDateEquals(startDate);
    }

    public List<Leave> getByStartDateBefore(LocalDateTime before) {
        return leaveRepository.findByStartDateBefore(before);
    }

    public List<Leave> getByStartDateAfter(LocalDateTime after) {
        return leaveRepository.findByStartDateAfter(after);
    }

    public List<Leave> getByEndDateEquals(LocalDateTime endDate) {
        return leaveRepository.findByEndDateEquals(endDate);
    }

    public List<Leave> getByEndDateBefore(LocalDateTime before) {
        return leaveRepository.findByEndDateBefore(before);
    }

    public List<Leave> getByEndDateAfter(LocalDateTime after) {
        return leaveRepository.findByEndDateAfter(after);
    }

    public List<Leave> getByPaidEquals(boolean paid) {
        return leaveRepository.findByPaidEquals(paid);
    }
    
}
