package com.agh.hr.persistence.service.application;

import com.agh.hr.model.error.InvalidRequestException;
import com.agh.hr.model.payload.BonusApplicationPayload;
import com.agh.hr.model.payload.DelegationApplicationPayload;
import com.agh.hr.model.payload.LeaveApplicationPayload;
import com.agh.hr.persistence.dto.*;
import com.agh.hr.persistence.model.BonusApplication;
import com.agh.hr.persistence.model.DelegationApplication;
import com.agh.hr.persistence.model.LeaveApplication;
import com.agh.hr.persistence.model.Status;
import com.agh.hr.persistence.repository.BonusApplicationRepository;
import com.agh.hr.persistence.repository.DelegationApplicationRepository;
import com.agh.hr.persistence.repository.LeaveApplicationRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService implements IApplicationService {

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final DelegationApplicationRepository delegationApplicationRepository;

    private final BonusApplicationRepository bonusApplicationRepository;

    private final Converters converters;

    public ApplicationService(Converters converters,
                              LeaveApplicationRepository leaveApplicationRepository,
                              DelegationApplicationRepository delegationApplicationRepository,
                              BonusApplicationRepository bonusApplicationRepository) {

        this.converters = converters;
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.delegationApplicationRepository = delegationApplicationRepository;
        this.bonusApplicationRepository = bonusApplicationRepository;
    }

    @Override
    public List<LeaveApplicationDTO> getLeaveApplications(UserDTO user) {
        return leaveApplicationRepository.findByUserId(user.getId())
                .stream().map(converters::leaveApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BonusApplicationDTO> getBonusApplications(UserDTO user) {
        return bonusApplicationRepository.findByUserId(user.getId())
                .stream().map(converters::bonusApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DelegationApplicationDTO> getDelegationApplications(UserDTO user) {
        return delegationApplicationRepository.findByUserId(user.getId())
                .stream().map(converters::delegationApplicationToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeaveApplicationDTO> getLeaveApplicationById(Long id) {
        return leaveApplicationRepository.findById(id)
                .map(converters::leaveApplicationToDTO);
    }

    @Override
    public Optional<BonusApplicationDTO> getBonusApplicationById(Long id) {
        return bonusApplicationRepository.findById(id)
                .map(converters::bonusApplicationToDTO);
    }

    @Override
    public Optional<DelegationApplicationDTO> getDelegationApplicationById(Long id) {
        return delegationApplicationRepository.findById(id)
                .map(converters::delegationApplicationToDTO);
    }

    @Override
    public DelegationApplicationDTO createDelegationApplication(UserDTO user, DelegationApplicationPayload payload) {
        val validated = validatePayload(user, payload);
        val saved = this.delegationApplicationRepository.save(validated);

        return this.converters.delegationApplicationToDTO(saved);
    }

    @Override
    public LeaveApplicationDTO createLeaveApplication(UserDTO user, LeaveApplicationPayload payload) {
        val validated = validatePayload(user, payload);
        val saved = this.leaveApplicationRepository.save(validated);

        return this.converters.leaveApplicationToDTO(saved);
    }

    @Override
    public BonusApplicationDTO createBonusApplication(UserDTO user, BonusApplicationPayload payload) {
        val validated = validatePayload(user, payload);
        val saved = this.bonusApplicationRepository.save(validated);

        return this.converters.bonusApplicationToDTO(saved);
    }


    // --------------------------------------------------------------------
    // All methods below should validate payload received from the client!
    // --------------------------------------------------------------------

    // It'd probably be even clearer if these methods below were part of a different class.
    // Preferred way of returning an error message is via throwing InvalidRequestException
    // It'll be converted to a BadRequest, see error controller.

    private DelegationApplication validatePayload(UserDTO userDTO, DelegationApplicationPayload payload)
            throws InvalidRequestException {

        val user = converters.DTOToUser(userDTO);

        return DelegationApplication.builder()
                .user(user)
                .content(payload.getContent())
                .place(payload.getPlace())
                .status(Status.PENDING)
                .startDate(payload.getStartDate())
                .endDate(payload.getEndDate())
                .destination(payload.getDestination())
                .build();
    }

    private BonusApplication validatePayload(UserDTO userDTO, BonusApplicationPayload payload) {
        val user = converters.DTOToUser(userDTO);

        return BonusApplication.builder()
                .user(user)
                .content(payload.getContent())
                .place(payload.getPlace())
                .status(Status.PENDING)
                .money(payload.getMoney())
                .build();
    }

    private LeaveApplication validatePayload(UserDTO userDTO, LeaveApplicationPayload payload) {
        val user = converters.DTOToUser(userDTO);

        return LeaveApplication.builder()
                .user(user)
                .content(payload.getContent())
                .place(payload.getPlace())
                .status(Status.PENDING)
                .startDate(payload.getStartDate())
                .endDate(payload.getEndDate())
                .paid(payload.isPaid())
                .build();
    }

}
