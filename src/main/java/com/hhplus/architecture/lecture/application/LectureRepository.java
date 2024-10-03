package com.hhplus.architecture.lecture.application;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;

public interface LectureRepository {
    LectureEntity findById(Long lectureId);

}
