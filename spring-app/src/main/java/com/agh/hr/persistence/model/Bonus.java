package com.agh.hr.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bonus {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    // Todo figure out a nicer way
    private int amount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private BonusApplication bonusApplication;


}
