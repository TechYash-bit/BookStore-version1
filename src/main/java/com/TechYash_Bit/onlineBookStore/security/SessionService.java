package com.TechYash_Bit.onlineBookStore.security;

import com.TechYash_Bit.onlineBookStore.Entities.SessionEntity;
import com.TechYash_Bit.onlineBookStore.Entities.UserEntity;
import com.TechYash_Bit.onlineBookStore.Repositories.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepo sessionRepo;
    private final int SESSION_LIMIT=2;
    public void genrateNewSession(UserEntity userEntity,String refreshToken){
        List<SessionEntity> userSession=sessionRepo.findByUser(userEntity);

        if(userSession.size()==SESSION_LIMIT){
            userSession.sort(Comparator.comparing(SessionEntity::getCreatedAt));
            SessionEntity lastUsedSession=userSession.getFirst();
            sessionRepo.delete(lastUsedSession);
        }

        SessionEntity newSession=SessionEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .build();
        sessionRepo.save(newSession);
    }

    public void isvalidate(String refreshToken){
        SessionEntity session=sessionRepo.findByRefreshToken(refreshToken).orElseThrow(()->new SessionAuthenticationException("session not found"));
        session.setCreatedAt(LocalDateTime.now());
        sessionRepo.save(session);
    }
}
