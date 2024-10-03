package com.hhplus.architecture.registration.infrastructure;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.registration.application.RegistrationRepository;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public RegistrationRepositoryImpl(RegistrationJpaRepository registrationJpaRepository) {
        this.registrationJpaRepository = registrationJpaRepository;
    }

    @Override
    public void save(RegistrationEntity registrationEntity) {
        // @TODO: Repository 단계에서 try-catch문을 작성해도 되는 지?
        try {
            registrationJpaRepository.save(registrationEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long countByLecture(LectureEntity lecture) {
        return registrationJpaRepository.countByLecture(lecture);
    }

    @Override
    public boolean existsByUserAndLecture(UserEntity user, LectureEntity lecture) {
        return registrationJpaRepository.existsByUserAndLecture(user, lecture);
    }

    @Override
    public List<RegistrationEntity> findByUser(UserEntity user) {
        return registrationJpaRepository.findByUser(user);
    }

    @Override
    @Transactional
    public RegistrationEntity findWithPessimisticLock(Long userId, Long lectureId) {
        // 비관적 락을 사용하여 LectureItemEntity를 조회
        List<RegistrationEntity> results = entityManager.createQuery("SELECT r FROM RegistrationEntity r WHERE r.user.id = :userId AND r.lecture.id = :lectureId", RegistrationEntity.class)
                .setParameter("userId", userId)
                .setParameter("lectureId", lectureId)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
