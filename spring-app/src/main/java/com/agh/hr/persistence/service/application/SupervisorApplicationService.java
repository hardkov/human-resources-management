package com.agh.hr.persistence.service.application;

import com.agh.hr.model.error.InvalidRequestException;
import com.agh.hr.model.error.NotAuthorizedException;
import com.agh.hr.model.payload.UpdateApplicationStatusPayload;
import com.agh.hr.persistence.dto.*;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.DelegationApplication;
import com.agh.hr.persistence.model.LeaveApplication;
import com.agh.hr.persistence.model.Status;
import com.agh.hr.persistence.repository.*;
import com.agh.hr.persistence.service.access.IAccessService;
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

    private final ApplicationConverters applicationConverters;
    private final IAccessService accessService;

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final DelegationApplicationRepository delegationApplicationRepository;
    private final BonusApplicationRepository bonusApplicationRepository;

    private final BonusRepository bonusRepository;
    private final LeaveRepository leaveRepository;
    private final DelegationRepository delegationRepository;

    private final Converters converters;

    public SupervisorApplicationService(Converters converters,
                                        ApplicationConverters applicationConverters,
                                        IAccessService accessService,
                                        LeaveApplicationRepository leaveApplicationRepository,
                                        DelegationApplicationRepository delegationApplicationRepository,
                                        BonusApplicationRepository bonusApplicationRepository,
                                        BonusRepository bonusRepository,
                                        LeaveRepository leaveRepository,
                                        DelegationRepository delegationRepository) {
        this.converters = converters;
        this.applicationConverters = applicationConverters;
        this.accessService = accessService;
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.delegationApplicationRepository = delegationApplicationRepository;
        this.bonusApplicationRepository = bonusApplicationRepository;
        this.bonusRepository = bonusRepository;
        this.leaveRepository = leaveRepository;
        this.delegationRepository = delegationRepository;
    }

    @Override
    public List<LeaveApplicationDTO> getLeaveApplications(UserDTO supervisor) {
        return readableUserIds(supervisor)
                .map(leaveApplicationRepository::findByUserId)
                .flatMap(List::stream)
                .map(converters::leaveApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BonusApplicationDTO> getBonusApplications(UserDTO supervisor) {
        return readableUserIds(supervisor)
                .map(bonusApplicationRepository::findByUserId)
                .flatMap(List::stream)
                .map(converters::bonusApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DelegationApplicationDTO> getDelegationApplications(UserDTO supervisor) {
        return readableUserIds(supervisor)
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

        this.delegationRepository.deleteByDelegationApplicationId(application.getId());
        if (saved.getStatus() == Status.ACCEPTED) {
            val delegation = this.applicationConverters.toDelegation(saved);
            this.delegationRepository.save(delegation);
        }

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

        this.leaveRepository.deleteByLeaveApplicationId(application.getId());
        if (saved.getStatus() == Status.ACCEPTED) {
            val leave = this.applicationConverters.toLeave(saved);
            this.leaveRepository.save(leave);
        }

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

        this.bonusRepository.deleteByBonusApplicationId(application.getId());
        if (saved.getStatus() == Status.ACCEPTED) {
            val bonus = this.applicationConverters.toBonus(saved);
            this.bonusRepository.save(bonus);
        }

        return converters.bonusApplicationToDTO(saved);
    }

    private ApplicationDTO validateApplication(Optional<ApplicationDTO> optional, UserDTO supervisor) {
        if (!optional.isPresent()) {
            throw new InvalidRequestException("There is no such application");
        }

        val application = optional.get();
        val employee = application.getUser();
        if (!hasWriteAccess(supervisor, employee)) {
            throw new NotAuthorizedException();
        }

        return optional.get();
    }

    private Stream<Long> readableUserIds(UserDTO supervisor) {
        return this.accessService
                .modifiableUsers(supervisor.getId())
                .stream().map(UserDTO::getId);
    }

    private boolean hasWriteAccess(UserDTO supervisor, UserDTO user) {
        val supervisorId = supervisor.getId();
        val userId = user.getId();

        return this.accessService.canModify(supervisorId, userId);
    }

}
