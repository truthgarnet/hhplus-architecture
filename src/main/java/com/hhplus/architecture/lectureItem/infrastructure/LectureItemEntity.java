package com.hhplus.architecture.lectureItem.infrastructure;

import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "lecture_item")
@Getter
public class LectureItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE) // 날짜만 저장
    @Column(name = "available_date")
    private Date availableDate; // 신청 가능한 날짜

    @Column(length = 2)
    private int availableCnt; // 등록 가능한 사람의 수

    @OneToOne
    @JoinColumn(name = "lecture_id")
    private LectureEntity lecture; // 동시성이 발생하는 필드를 분리하기 위함

    public LectureItemEntity(Long id, Date availableDate, int availableCnt, LectureEntity lecture) {
        this.id = id;
        this.availableDate = availableDate;
        this.availableCnt = availableCnt;
        this.lecture = lecture;
    }

    public LectureItemEntity() {}
}
