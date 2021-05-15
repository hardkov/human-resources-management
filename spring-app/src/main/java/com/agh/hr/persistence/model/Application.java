package com.agh.hr.persistence.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private String content;

    @ManyToOne
    @JoinColumn
    private PersonalData personalData;

    private String place;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Status status;
}
