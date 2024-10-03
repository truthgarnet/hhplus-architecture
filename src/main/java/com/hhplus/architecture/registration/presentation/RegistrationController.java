package com.hhplus.architecture.registration.presentation;

import com.hhplus.architecture.common.response.CommonResponse;
import com.hhplus.architecture.lecture.application.Lecture;
import com.hhplus.architecture.registration.application.Registration;
import com.hhplus.architecture.registration.application.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping(value = "/enroll")
    ResponseEntity<CommonResponse<Object>> enroll(@RequestBody Registration registration) {
        registrationService.createRegistration(registration);

        CommonResponse<Object> response = CommonResponse.builder()
                .msg("수강 신청 성공")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
        date:false일 경우 오늘날자로 검색
     */
    @GetMapping(value = "/lectures")
    ResponseEntity<CommonResponse<Object>> getLectures() {
        List<Lecture> lectureList = registrationService.getAvailbleRegistration();

        CommonResponse<Object> response = CommonResponse.builder()
                .msg("날짜별 수강 신청 가능한 강의 조회 성공")
                .data(lectureList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{userId}")
    ResponseEntity<CommonResponse<Object>> getUserLectures(@PathVariable("userId") Long userId) {
        List<Lecture> data = registrationService.getUserLectures(userId);

        CommonResponse<Object> response = CommonResponse.builder()
                .msg("특강 신청 완료 목록 조회 성공")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
