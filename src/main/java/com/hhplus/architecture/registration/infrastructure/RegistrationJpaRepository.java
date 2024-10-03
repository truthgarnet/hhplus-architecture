package com.hhplus.architecture.registration.infrastructure;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {
    Long countByLecture(LectureEntity lecture);

    boolean existsByUserAndLecture(UserEntity user, LectureEntity lecture);

    List<RegistrationEntity> findByUser(UserEntity user);

}
