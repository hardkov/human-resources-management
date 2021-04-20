package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LeaveDTO {
    private Long id;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid;
    private byte[] thumbnail;
}
