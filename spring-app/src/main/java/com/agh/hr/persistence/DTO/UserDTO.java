package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static jdk.nashorn.internal.objects.NativeDebug.map;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    protected Long id;
    protected PersonalDataDTO personalData;
    protected String position;
}
