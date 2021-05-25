package com.agh.hr.persistence.service.subordinate;

import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.repository.UserRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubordinateService implements ISubordinateService {

    private final UserRepository userRepository;
    private final Converters converters;

    public SubordinateService(UserRepository userRepository,
                              Converters converters) {
        this.userRepository = userRepository;
        this.converters = converters;
    }

    @Override
    public boolean isSubordinate(Long supervisorId, Long subordinateId) {
        val option = this.userRepository.findUserById(subordinateId);
        if (!option.isPresent()) {
            return false;
        }

        val userSupervisor = option.get().getSupervisor();
        if (userSupervisor == null) {
            return false;
        }

        return supervisorId.equals(userSupervisor.getId());
    }

    @Override
    public List<UserDTO> getSubordinates(Long supervisorId) {
        val subordinates = this.userRepository.findSubordinates(supervisorId);

        return subordinates.stream()
                .map(converters::userToDTO)
                .collect(Collectors.toList());
    }
}
