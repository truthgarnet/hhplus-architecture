package com.hhplus.architecture.registration.application;

import com.hhplus.architecture.lecture.application.Lecture;
import com.hhplus.architecture.lecture.application.LectureRepository;
import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.lectureItem.application.LectureItemRepository;
import com.hhplus.architecture.lectureItem.infrastructure.LectureItemEntity;
import com.hhplus.architecture.registration.infrastructure.RegistrationEntity;
import com.hhplus.architecture.user.application.UserRepository;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LectureItemRepository lectureItemRepository;
    @Autowired
    private RegistrationRepository registrationRepository;

    // 수강 신청하기
    @Transactional
    public void createRegistration(Registration registration) {
        // 0. user, lecture 정보 가져오기
        Long userId = registration.getUserId();
        Long lectureId = registration.getLectureId();
        
        // 0-1. 검증(user, lecture)
        RegistrationEntity registrationEntity = new RegistrationEntity(userId, lectureId);

        UserEntity user = userRepository.findById(userId);

        // 1-1. 존재하는 강의인지 체크
        LectureEntity lecture = lectureRepository.findById(lectureId);
        
        // 1-2. 수강 가능한 강의인지 체크 (날짜 기준)
        Date now = new Date();
        boolean checkAvailable = lectureItemRepository.existsByLectureIdAndAvailbelDate(lecture.getId(), now);
        if (!checkAvailable) {
            throw new RuntimeException("수강 신청할 수 없는 강의입니다.");
        }

        // 1-3. 수강 신청한 사람이 30명 이상인지 체크
        LectureItemEntity  lectureItem = lectureItemRepository.findWithPessimisticLock(lecture.getId());
        int availableCnt = lectureItem.getAvailableCnt();
        if (availableCnt <= 0) {
            throw new RuntimeException("수강 신청 가능한 인원이 초과되었습니다.");
        }

        registrationEntity = RegistrationEntity.toEntity(user, lecture);

        // 2-1. 저장
        registrationRepository.save(registrationEntity);

        // 2-2. available cnt --;
        LectureItemEntity lectureEntity = new LectureItemEntity(lectureItem.getId(), lectureItem.getAvailableDate(), availableCnt-1,lecture);
        lectureItemRepository.save(lectureEntity);
    }

    // 닐찌별 수강 가능한 특강 목록
    public List<Lecture> getAvailbleRegistration() {
        // 1. 검색한 날짜 기준
        LocalDate localDate = LocalDate.now();
        
        // 2. 수강 신청할 수 있는 인원이 0명 이상일 경우 검색
        List<LectureItemEntity> lectureItemList = lectureItemRepository.findAvailbleRegistration(localDate);
        List<LectureEntity> lecture = lectureItemList.stream().map(m -> m.getLecture()).collect(Collectors.toList());

        return lecture.stream().map(m -> new Lecture(m.getId(), m.getLectureName(), m.getLecturer())).collect(Collectors.toList());
    }

    // 신청한 강의 정보 가져오기
    public List<Lecture> getUserLectures(Long userId) {
        // 0-1. 사용자 정보 가져오기
        UserEntity user = new UserEntity(userId);

        // 0-2. 사용자가 신청한 강의 정보 가져오기
        // 서비스 단계에서 RegistrationEntity -> LectureEntity 로 옮기는 것이 맞는지?
        List<LectureEntity> lectures = registrationRepository.findByUser(user)
                .stream().map(RegistrationEntity::getLecture).collect(Collectors.toList());

        List<Lecture> lecture = lectures.stream().map(m -> new Lecture(m.getId(), m.getLectureName(), m.getLecturer())).collect(Collectors.toList());

        return lecture;
    }
}
