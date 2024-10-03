package com.hhplus.architecture.user.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50)
    private String userName; // 사용자 이름

    public UserEntity() {}

    public UserEntity(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("사용자 Id가 잘못 입력되었습니다.");
        }
        this.id = id;
    }
}
