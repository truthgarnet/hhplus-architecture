package com.hhplus.architecture.registration.infrastructure;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "registration")
@Getter
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; // 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private LectureEntity lecture; // 강의

    @Builder
    public RegistrationEntity(UserEntity user, LectureEntity lecture) {
        this.user = user;
        this.lecture = lecture;
    }

    public RegistrationEntity() {}

    public RegistrationEntity(Long userId, Long lectureId) {
        if (userId <= 0 || userId == null) {
            throw new RuntimeException("사용자 Id가 잘못 입력되었습니다.");
        }
        if (lectureId <= 0 || lectureId == null) {
            throw new RuntimeException("강의 Id가 잘못 입력되었습니다.");
        }
    }

    public static RegistrationEntity toEntity(UserEntity user, LectureEntity lecture) {
        return RegistrationEntity.builder()
                .user(user)
                .lecture(lecture)
                .build();
    }

}
