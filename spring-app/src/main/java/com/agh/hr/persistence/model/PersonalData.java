package com.agh.hr.persistence.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalData {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private String address;

    @Column(columnDefinition = "DATE")
    private LocalDate birthdate;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] thumbnail;

}
