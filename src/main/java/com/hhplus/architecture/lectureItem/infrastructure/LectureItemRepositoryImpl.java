package com.hhplus.architecture.lectureItem.infrastructure;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.lectureItem.application.LectureItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class LectureItemRepositoryImpl implements LectureItemRepository {

    public LectureItemJpaRepository lectureItemJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public LectureItemRepositoryImpl(LectureItemJpaRepository lectureItemJpaRepository) {
        this.lectureItemJpaRepository = lectureItemJpaRepository;
    }

    @Override
    public List<LectureItemEntity> findAvailbleRegistration(LocalDate now) {
        return lectureItemJpaRepository.findByAvailableCntGreaterThanAndAvailableDateAfter(0, now);
    }

    @Override
    public boolean existsByLectureIdAndAvailbelDate(Long lectureId, Date now) {
        return lectureItemJpaRepository.existsByLectureIdAndAvailableDate(lectureId, now);
    }

    @Override
    public LectureItemEntity findByLectureId(LectureEntity lecture) {
        return lectureItemJpaRepository.findByLectureId(lecture.getId()).orElseThrow(() -> new RuntimeException("존재하지 않은 강의입니다."));
    }

    @Override
    @Transactional
    public LectureItemEntity findWithPessimisticLock(Long lectureItemId) {
        // 비관적 락을 사용하여 LectureItemEntity를 조회
        return entityManager.find(LectureItemEntity.class, lectureItemId, LockModeType.PESSIMISTIC_WRITE);
    }

    @Override
    public void save(LectureItemEntity lectureItem) {
        lectureItemJpaRepository.save(lectureItem);
    }

}
