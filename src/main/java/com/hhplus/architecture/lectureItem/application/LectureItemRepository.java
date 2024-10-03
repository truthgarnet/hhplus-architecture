package com.hhplus.architecture.lectureItem.application;

import com.hhplus.architecture.lectureItem.infrastructure.LectureItemEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LectureItemRepository {

    // 수강 인원이 0 이상이며, 입력받은 날짜이후인것
    List<LectureItemEntity> findAvailbleRegistration(LocalDate now);

    boolean existsByLectureIdAndAvailbelDate(Long lectureId, Date now);

}
