package com.hhplus.architecture.lecture.infrastructure;

import com.hhplus.architecture.lecture.application.LectureRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    public LectureRepositoryImpl(LectureJpaRepository lectureJpaRepository) {
        this.lectureJpaRepository = lectureJpaRepository;
    }

    @Override
    public LectureEntity findById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("찾으려는 강좌가 없습니다."));
    }
}
