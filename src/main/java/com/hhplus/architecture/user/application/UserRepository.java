package com.hhplus.architecture.user.application;

import com.hhplus.architecture.user.infrastructure.UserEntity;

public interface UserRepository {
    UserEntity findById(Long userId);

}
