package com.agh.hr.dto;

import com.agh.hr.persistence.model.FooUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FooUserView {

    private final Long id;

    private final String username;

    public static FooUserView fromUser(FooUser fooUser) {
        return FooUserView.builder()
                .id(fooUser.getId())
                .username(fooUser.getUsername())
                .build();
    }
}
