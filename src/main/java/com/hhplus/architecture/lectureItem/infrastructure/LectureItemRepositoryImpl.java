package com.hhplus.architecture.lectureItem.infrastructure;

import com.hhplus.architecture.lecture.application.Lecture;
import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.lectureItem.application.LectureItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LectureItemRepositoryImpl implements LectureItemRepository {

    public LectureItemJpaRepository lectureItemJpaRepository;

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
}
