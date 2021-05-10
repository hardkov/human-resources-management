package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.LeaveRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Optional<Leave> saveLeave(Leave leave,boolean isNew) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(isNew&&!(Auth.getAdd(userAuth)))
            return Optional.empty();
        else if(!isNew &&!(Auth.getWriteIds(userAuth).contains(leave.getUser().getId())))
            return Optional.empty();
        try {
            return Optional.of(leaveRepository.save(leave));
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<Leave> getById(Long id) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<Leave> getAllLeaves() {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findAll(Auth.getReadIds(userAuth));
    }

    public ResponseEntity<Void> deleteLeave(Long leaveId) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<Leave> leave=leaveRepository.findById(leaveId);
        if(!leave.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(leave.get().getUser().getId())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        leaveRepository.deleteById(leaveId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<Leave> getByStartDateEquals(LocalDateTime startDate) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByStartDateEquals(startDate,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByStartDateBefore(LocalDateTime before) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByStartDateBefore(before,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByStartDateAfter(LocalDateTime after) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByStartDateAfter(after,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateEquals(LocalDateTime endDate) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByEndDateEquals(endDate,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateBefore(LocalDateTime before) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByEndDateBefore(before,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByEndDateAfter(LocalDateTime after) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByEndDateAfter(after,Auth.getReadIds(userAuth));
    }

    public List<Leave> getByPaidEquals(boolean paid) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return leaveRepository.findByPaidEquals(paid,Auth.getReadIds(userAuth));
    }
    
}
