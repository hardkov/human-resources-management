package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.LeaveApplication;
import com.agh.hr.persistence.repository.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveApplicationService {


    private final LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    public LeaveApplicationService(LeaveApplicationRepository leaveApplicationRepository){
        this.leaveApplicationRepository=leaveApplicationRepository;
    }

    public boolean saveLeaveApplication(LeaveApplication leaveApplication) {
        try {
            leaveApplicationRepository.save(leaveApplication);
        }catch(Exception e){return false;}
        return true;
    }

    public Optional<LeaveApplication> getById(Long id) {
        return leaveApplicationRepository.findById(id);
    }

    public List<LeaveApplication> getAllLeaveApplications() {
        return leaveApplicationRepository.findAll();
    }

    public void deleteLeaveApplication(Long leaveApplicationId) {
        leaveApplicationRepository.deleteById(leaveApplicationId);
    }

}
