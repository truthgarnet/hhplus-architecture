package com.hhplus.architecture.lecture.infrastructure;

import com.hhplus.architecture.registration.infrastructure.RegistrationEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lecture")
@Getter
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(length = 100)
    private String lectureName; // 특강 이름
    @Column(length = 50)
    private String lecturer; // 강연자

    public LectureEntity() {}

    public LectureEntity(Long id, String lectureName, String lecturer) {
        this.id = id;
        this.lectureName = lectureName;
        this.lecturer = lecturer;
    }

}
