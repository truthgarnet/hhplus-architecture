package com.hhplus.architecture;

import com.hhplus.architecture.lecture.application.Lecture;
import com.hhplus.architecture.lecture.infrastructure.LectureEntity;
import com.hhplus.architecture.lecture.application.LectureRepository;
import com.hhplus.architecture.lectureItem.application.LectureItemRepository;
import com.hhplus.architecture.lectureItem.infrastructure.LectureItemEntity;
import com.hhplus.architecture.registration.application.RegistrationService;
import com.hhplus.architecture.registration.application.Registration;
import com.hhplus.architecture.registration.infrastructure.RegistrationEntity;
import com.hhplus.architecture.registration.application.RegistrationRepository;
import com.hhplus.architecture.user.infrastructure.UserEntity;
import com.hhplus.architecture.user.application.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureItemRepository lectureItemRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;


    @Test
    @DisplayName("수강 신청 성공 테스트")
    void success_createRegistration() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;
        Date now = new Date();

        Registration registration = new Registration(userId, lectureId);
        UserEntity user = new UserEntity(userId);
        LectureEntity lecture = new LectureEntity(lectureId, "강의 제목", "강사진");

        // when
        when(userRepository.findById(userId)).thenReturn(user);
        when(lectureRepository.findById(lectureId)).thenReturn(lecture);
        // @TODO: 고쳐야 하는 부분
        doReturn(true).when(lectureItemRepository).existsByLectureIdAndAvailbelDate(eq(lectureId), any(Date.class));

        registrationService.createRegistration(registration);

        // then
        verify(registrationRepository, times(1)).save(any(RegistrationEntity.class));
    }

    @Test
    @DisplayName("수강 신청 실패 테스트 - 강의 신청 불가능")
    void fail_createRegistrationWhenNotAvailable() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;
        Date now = new Date();

        Registration registration = new Registration(userId, lectureId);
        UserEntity user = new UserEntity(userId);
        LectureEntity lecture = new LectureEntity(lectureId, "강의 제목", "강사진");

        // when
        when(userRepository.findById(userId)).thenReturn(user);
        when(lectureRepository.findById(lectureId)).thenReturn(lecture);
        // @TODO: 고쳐야 하는 부분
        Mockito.lenient().when(lectureItemRepository.existsByLectureIdAndAvailbelDate(lectureId, now)).thenReturn(false);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registrationService.createRegistration(registration);
        });

        // then
        assertEquals("수강 신청할 수 없는 강의입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("수강 가능한 특강 목록 조회 테스트")
    void success_getAvailableRegistration() {
        // given
        LectureEntity lecture1 = new LectureEntity(1L, "강의1", "강사진1");
        LectureEntity lecture2 = new LectureEntity(2L, "강의2", "강사진2");

        LectureItemEntity lectureItem1 = new LectureItemEntity(1L, new Date(), 5, lecture1);
        LectureItemEntity lectureItem2 = new LectureItemEntity(2L, new Date(), 10, lecture2);

        // when
        when(lectureItemRepository.findAvailbleRegistration(any(LocalDate.class)))
                .thenReturn(Arrays.asList(lectureItem1, lectureItem2));

        List<Lecture> availableLectures = registrationService.getAvailbleRegistration();

        // then
        assertEquals(2, availableLectures.size());
        assertEquals("강의1", availableLectures.get(0).getLectureName());
        assertEquals("강의2", availableLectures.get(1).getLectureName());
    }

    @Test
    @DisplayName("사용자가 신청한 강의 목록 조회 테스트")
    void getUserLectures_success() {
        // given
        Long userId = 1L;
        UserEntity user = new UserEntity(userId);

        LectureEntity lecture1 = new LectureEntity(1L, "강의1", "강사진1");
        LectureEntity lecture2 = new LectureEntity(2L, "강의2", "강사진2");

        // 생성자를 통해 LectureEntity 설정
        RegistrationEntity registration1 = new RegistrationEntity(user, lecture1);
        RegistrationEntity registration2 = new RegistrationEntity(user, lecture2);

        // when @TODO: 고쳐야 하는 부분
        doReturn(Arrays.asList(registration1, registration2)).when(registrationRepository).findByUser(any(UserEntity.class));

        List<Lecture> userLectures = registrationService.getUserLectures(userId);

        // then
        assertEquals(2, userLectures.size());
        assertEquals("강의1", userLectures.get(0).getLectureName());
        assertEquals("강의2", userLectures.get(1).getLectureName());

    }
}
