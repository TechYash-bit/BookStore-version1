package com.TechYash_Bit.onlineBookStore.Repositories;

import com.TechYash_Bit.onlineBookStore.Entities.SessionEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<SessionEntity,Long> {
    List<SessionEntity> findByUser(UserEntity userEntity);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
