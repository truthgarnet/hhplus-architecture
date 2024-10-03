package com.hhplus.architecture.integration;

import com.hhplus.architecture.registration.application.Registration;
import com.hhplus.architecture.registration.application.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;

    @Test
    @DisplayName("동시 다발적으로 40명이 신청해서 10명이 실패합니다.")
    void test_registrationConcurrent() throws InterruptedException {
        // 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(30);
        AtomicInteger failedOperations = new AtomicInteger(0); // 실패한 작업 수를 세기 위한 변수

        // 40개의 쓰기 작업을 동시 실행( STEP4: 각기 다른 사용자 )
        for (int i = 0; i < 40; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    Registration registration = new Registration((long) finalI, 1L);
                    registrationService.createRegistration(registration);
                } catch (RuntimeException e) {
                    System.out.println("에러:" + e.getMessage());
                    failedOperations.incrementAndGet(); // 예외가 발생하면 실패 카운트 증가
                }
            });
        }
        // 스레드 종료 후 결과 확인
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS); // 모든 스레드가 종료될 때까지 대기
        // 실패 10번
        assertEquals(10, failedOperations.get(), "동시 다발적으로 40명이 신청해서 10명이 실패합니다.");
    }

    @Test
    @DisplayName("한 사용자가 5번 테스트 했을 경우 1번만 성공")
    void test_whenOneUserRegistrationConcurrent() throws InterruptedException {
        // 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(5);
        AtomicInteger failedOperations = new AtomicInteger(0); // 실패한 작업 수를 세기 위한 변수

        // 40개의 쓰기 작업을 동시 실행
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                try {
                    Registration registration = new Registration(1L, 1L);
                    registrationService.createRegistration(registration);
                } catch (RuntimeException e) {
                    System.out.println("에러:" + e.getMessage());
                    failedOperations.incrementAndGet(); // 예외가 발생하면 실패 카운트 증가
                }
            });
        }
        // 스레드 종료 후 결과 확인
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); // 모든 스레드가 종료될 때까지 대기
        // 실패 4번
        assertEquals(4, failedOperations.get(), "동시 다발적으로 1명이 5번 신청하면 4번 실패합니다.");
    }
}
