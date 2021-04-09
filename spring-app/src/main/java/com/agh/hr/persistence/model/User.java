package com.agh.hr.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;

    private String passwordHash;

    @OneToMany
    private List<Permission> permissions;

    private String position;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Leave> leaves;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bonus> bonuses;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Delegation> delegations;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Application> applications;

}
