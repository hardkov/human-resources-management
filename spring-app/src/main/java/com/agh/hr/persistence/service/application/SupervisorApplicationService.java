package com.agh.hr.persistence.service.application;

import com.agh.hr.persistence.dto.*;
import com.agh.hr.persistence.repository.BonusApplicationRepository;
import com.agh.hr.persistence.repository.DelegationApplicationRepository;
import com.agh.hr.persistence.repository.LeaveApplicationRepository;
import com.agh.hr.persistence.service.subordinate.ISubordinateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SupervisorApplicationService implements ISupervisorApplicationService {

    private final ISubordinateService permissionService;

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final DelegationApplicationRepository delegationApplicationRepository;

    private final BonusApplicationRepository bonusApplicationRepository;

    private final Converters converters;

    public SupervisorApplicationService(Converters converters,
                                        ISubordinateService permissionService,
                                        LeaveApplicationRepository leaveApplicationRepository,
                                        DelegationApplicationRepository delegationApplicationRepository,
                                        BonusApplicationRepository bonusApplicationRepository) {
        this.converters = converters;
        this.permissionService = permissionService;
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.delegationApplicationRepository = delegationApplicationRepository;
        this.bonusApplicationRepository = bonusApplicationRepository;
    }

    @Override
    public List<LeaveApplicationDTO> getLeaveApplications(UserDTO supervisor) {
        return subordinatesIds(supervisor)
                .map(leaveApplicationRepository::findByUserId)
                .flatMap(List::stream)
                .map(converters::leaveApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BonusApplicationDTO> getBonusApplications(UserDTO supervisor) {
        return subordinatesIds(supervisor)
                .map(bonusApplicationRepository::findByUserId)
                .flatMap(List::stream)
                .map(converters::bonusApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DelegationApplicationDTO> getDelegationApplications(UserDTO supervisor) {
        return subordinatesIds(supervisor)
                .map(delegationApplicationRepository::findByUserId)
                .flatMap(List::stream)
                .map(converters::delegationApplicationToDTO)
                .collect(Collectors.toList());
    }

    private Stream<Long> subordinatesIds(UserDTO supervisor) {
        return this.permissionService.getSubordinates(supervisor)
                .stream()
                .map(UserDTO::getId);
    }

}
