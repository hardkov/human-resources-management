package com.agh.hr.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BonusApplication extends Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // todo figure out a nicer way
    private int money;

}
