package com.agh.hr.persistence.service;

import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.dto.UserDTO;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveService {


    private final LeaveRepository leaveRepository;
    private final RoleService roleService;
    private final Converters converters;
    private final UserService userService;
    @Autowired
    public LeaveService(LeaveRepository leaveRepository,RoleService roleService, Converters converters,
                        UserService userService){
        this.leaveRepository=leaveRepository;
        this.roleService=roleService;
        this.converters = converters;
        this.userService=userService;
    }

    public Optional<LeaveDTO> updateLeave(LeaveDTO leaveDTO) {
        val userAuth=Auth.getCurrentUser();
        Optional<User> userOpt = userService.getRawById(leaveDTO.getUser().getId());
        if(!userOpt.isPresent())
            return Optional.empty();
        User user=userOpt.get();
        Leave leave = converters.DTOToLeave(leaveDTO);
        leave.setUser(user);
        if(!roleService.isAdmin(userAuth))
            if(!Auth.getWriteIds(userAuth).contains(user.getId()))
                return Optional.empty();
        converters.updateLeaveWithDTO(leaveDTO,leave);
        val result= Optional.of(leaveRepository.save(leave));
        return result.map(converters::leaveToDTO);
    }

    public Optional<LeaveDTO> saveLeave(LeaveDTO leaveDTO,Long userId) {
        Optional<User> userOpt = userService.getRawById(userId);
        if(!userOpt.isPresent())
            return Optional.empty();
        User user = userOpt.get();
        Leave leave = converters.DTOToLeave(leaveDTO);
        leave.setUser(user);
        leave.setId(0L);
        val userAuth=Auth.getCurrentUser();
        if(!roleService.isAdmin(userAuth))
            if(!Auth.getAdd(userAuth))
                return Optional.empty();
            else if(!Auth.getWriteIds(userAuth).contains(userId))
                return Optional.empty();
        try {
            return Optional.of(leaveRepository.save(leave)).map(converters::leaveToDTO);
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<LeaveDTO> getById(Long id) {

        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findById(id,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .map(converters::leaveToDTO);
    }

    public List<LeaveDTO> getByUserId(Long id) {

        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByUserId(id,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getAllLeaves() {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findAll(Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> deleteLeave(Long leaveId) {
        val userAuth=Auth.getCurrentUser();
        Optional<Leave> leave=leaveRepository.findById(leaveId);
        if(!leave.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!roleService.isAdmin(userAuth))
            if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(leave.get().getUser().getId())))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        leaveRepository.deleteById(leaveId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<LeaveDTO> getByStartDateEquals(LocalDateTime startDate) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByStartDateEquals(startDate,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByStartDateBefore(LocalDateTime before) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByStartDateBefore(before,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByStartDateAfter(LocalDateTime after) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByStartDateAfter(after,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByEndDateEquals(LocalDateTime endDate) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByEndDateEquals(endDate,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByEndDateBefore(LocalDateTime before) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByEndDateBefore(before,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByEndDateAfter(LocalDateTime after) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByEndDateAfter(after,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveDTO> getByPaidEquals(boolean paid) {
        val userAuth=Auth.getCurrentUser();
        return leaveRepository.findByPaidEquals(paid,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::leaveToDTO)
                .collect(Collectors.toList());
    }
    
}
