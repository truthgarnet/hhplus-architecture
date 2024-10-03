package com.hhplus.architecture.registration.application;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.registration.infrastructure.RegistrationEntity;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface RegistrationRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void save(RegistrationEntity registrationEntity);

    Long countByLecture(LectureEntity lecture);

    boolean existsByUserAndLecture(UserEntity user, LectureEntity lecture);

    List<RegistrationEntity> findByUser(UserEntity user);
}
