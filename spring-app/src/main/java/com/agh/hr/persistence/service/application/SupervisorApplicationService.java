package com.agh.hr.persistence.service.application;

import com.agh.hr.model.error.InvalidRequestException;
import com.agh.hr.model.error.NotAuthorizedException;
import com.agh.hr.model.payload.UpdateApplicationStatusPayload;
import com.agh.hr.persistence.dto.*;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.DelegationApplication;
import com.agh.hr.persistence.model.LeaveApplication;
import com.agh.hr.persistence.repository.BonusApplicationRepository;
import com.agh.hr.persistence.repository.DelegationApplicationRepository;
import com.agh.hr.persistence.repository.LeaveApplicationRepository;
import com.agh.hr.persistence.service.subordinate.ISubordinateService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class SupervisorApplicationService implements ISupervisorApplicationService {

    private final ISubordinateService subordinateService;

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final DelegationApplicationRepository delegationApplicationRepository;

    private final BonusApplicationRepository bonusApplicationRepository;

    private final Converters converters;

    public SupervisorApplicationService(Converters converters,
                                        ISubordinateService subordinateService,
                                        LeaveApplicationRepository leaveApplicationRepository,
                                        DelegationApplicationRepository delegationApplicationRepository,
                                        BonusApplicationRepository bonusApplicationRepository) {
        this.converters = converters;
        this.subordinateService = subordinateService;
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

    @Override
    public DelegationApplicationDTO updateDelegationApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) {
        Optional<ApplicationDTO> option = this.delegationApplicationRepository
                .findById(payload.getApplicationId())
                .map(converters::delegationApplicationToDTO);

        val dto = validateApplication(option, supervisor);
        dto.setStatus(payload.getStatus());

        val application = converters.dtoTOApplication(dto, DelegationApplication.class);
        val saved = delegationApplicationRepository.save(application);

        return converters.delegationApplicationToDTO(saved);
    }

    @Override
    public LeaveApplicationDTO updateLeaveApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) throws NotAuthorizedException {
        Optional<ApplicationDTO> option = this.leaveApplicationRepository
                .findById(payload.getApplicationId())
                .map(converters::leaveApplicationToDTO);

        val dto = validateApplication(option, supervisor);
        dto.setStatus(payload.getStatus());

        val application = converters.dtoTOApplication(dto, LeaveApplication.class);
        val saved = leaveApplicationRepository.save(application);

        return converters.leaveApplicationToDTO(saved);
    }

    @Override
    public BonusApplicationDTO updateBonusApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) throws NotAuthorizedException {
        Optional<ApplicationDTO> option = this.bonusApplicationRepository
                .findById(payload.getApplicationId())
                .map(converters::bonusApplicationToDTO);

        val dto = validateApplication(option, supervisor);
        dto.setStatus(payload.getStatus());

        val application = converters.dtoTOApplication(dto, BonusApplication.class);
        val saved = bonusApplicationRepository.save(application);

        return converters.bonusApplicationToDTO(saved);
    }

    private ApplicationDTO validateApplication(Optional<ApplicationDTO> optional, UserDTO supervisor) {
        if (!optional.isPresent()) {
            throw new InvalidRequestException("There is no such application");
        }

        val application = optional.get();
        val employee = application.getUser();
        if (!isSubordinate(supervisor, employee)) {
            throw new NotAuthorizedException();
        }

        return optional.get();
    }

    private boolean isSubordinate(UserDTO supervisor, UserDTO user) {
        return this.subordinateService.isSubordinate(supervisor.getId(), user.getId());
    }

    private Stream<Long> subordinatesIds(UserDTO supervisor) {
        return this.subordinateService.getSubordinates(supervisor.getId())
                .stream()
                .map(UserDTO::getId);
    }

}
