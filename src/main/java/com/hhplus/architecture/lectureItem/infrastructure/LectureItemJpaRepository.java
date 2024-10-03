package com.hhplus.architecture.lectureItem.infrastructure;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LectureItemJpaRepository extends JpaRepository<LectureItemEntity, Long> {

    // List: availableCnt > 0 AND availableDate > today
    List<LectureItemEntity> findByAvailableCntGreaterThanAndAvailableDateAfter(Integer availableCnt, LocalDate availableDate);

    boolean existsByLectureIdAndAvailableDate(Long lectureId, Date availableDate);

    Optional<LectureItemEntity> findByLectureId(Long lectureId);

}
