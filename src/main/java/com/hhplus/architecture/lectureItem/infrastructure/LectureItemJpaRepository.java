package com.hhplus.architecture.lectureItem.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LectureItemJpaRepository extends JpaRepository<LectureItemEntity, Long> {

    // List: availableCnt > 0 AND availableDate > today
    List<LectureItemEntity> findByAvailableCntGreaterThanAndAvailableDateAfter(Integer availableCnt, LocalDate availableDate);

    boolean existsByLectureIdAndAvailableDate(Long lectureId, Date availableDate);

}
