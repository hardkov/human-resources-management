package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Optional<Leave> saveLeave(Leave leave, User userAuth,boolean isNew) {
        if(isNew&&!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(leave.getUser().getId())))
            return Optional.empty();
        else if(!isNew &&!(Auth.getWriteIds(userAuth).contains(leave.getUser().getId())))
            return Optional.empty();
        try {
            return Optional.of(leaveRepository.save(leave));
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<Leave> getById(Long id,User userAuth) {
        return leaveRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<Leave> getAllLeaves(User userAuth) {
        return leaveRepository.findAll(Auth.getReadIds(userAuth));
    }

    public ResponseEntity<Void> deleteLeave(Long leaveId,User userAuth) {
        Optional<Leave> leave=leaveRepository.findById(leaveId);
        if(!leave.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(leave.get().getUser().getId())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        leaveRepository.deleteById(leaveId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<Leave> getByStartDateEquals(LocalDateTime startDate,User userAuth) {
        return leaveRepository.findByStartDateEquals(startDate,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByStartDateBefore(LocalDateTime before,User userAuth) {
        return leaveRepository.findByStartDateBefore(before,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByStartDateAfter(LocalDateTime after,User userAuth) {
        return leaveRepository.findByStartDateAfter(after,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateEquals(LocalDateTime endDate,User userAuth) {
        return leaveRepository.findByEndDateEquals(endDate,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateBefore(LocalDateTime before,User userAuth) {
        return leaveRepository.findByEndDateBefore(before,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateAfter(LocalDateTime after,User userAuth) {
        return leaveRepository.findByEndDateAfter(after,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByPaidEquals(boolean paid,User userAuth) {
        return leaveRepository.findByPaidEquals(paid,Auth.getReadIds(userAuth));
    }
    
}
