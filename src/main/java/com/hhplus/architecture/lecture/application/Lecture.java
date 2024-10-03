package com.hhplus.architecture.lecture.application;

import lombok.Getter;

@Getter
public class Lecture {

    private Long id;
    private String lectureName; // 강연 이름
    private String lecturer; // 강연자

    public Lecture(Long id, String lectureName, String lecturer) {
        this.id = id;
        this.lectureName = lectureName;
        this.lecturer = lecturer;
    }

    public Lecture(String lectureName, String lecturer) {
        this.lectureName = lectureName;
        this.lecturer = lecturer;
    }

}
