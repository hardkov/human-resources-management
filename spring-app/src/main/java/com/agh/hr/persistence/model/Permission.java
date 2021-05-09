package com.agh.hr.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @Builder.Default
    private List<Long> write=new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<Long> read=new ArrayList<>();

    private boolean add;

    public Long getId() {
        return id;
    }
    public boolean addToRead(Long toAdd){ return read.add(toAdd);}
    public boolean addToWrite(Long toAdd){return write.add(toAdd);}
    public boolean getAdd(){return add;}
}
