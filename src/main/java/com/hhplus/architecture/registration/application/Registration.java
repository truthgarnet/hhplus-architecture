package com.hhplus.architecture.registration.application;

import com.hhplus.architecture.registration.infrastructure.RegistrationEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Registration {

    private Long userId;
    private Long lectureId;

    public Registration() {}

    public static Registration toDto(RegistrationEntity registrationEntity) {
        return Registration.builder()
                .userId(registrationEntity.getUser().getId())
                .lectureId(registrationEntity.getLecture().getId())
                .build();
    }

    public Registration(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }
}
