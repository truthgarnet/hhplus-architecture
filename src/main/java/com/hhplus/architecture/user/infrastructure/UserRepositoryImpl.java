package com.hhplus.architecture.user.infrastructure;

import com.hhplus.architecture.user.application.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public UserEntity findById(Long id) {
        return userJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾으려는 사용자가 없습니다."));
    }


    public UserEntity findWithPessimisticLock(Long id) {
        return userJpaRepository.findWithPessimisticLock(id);
    }
}
