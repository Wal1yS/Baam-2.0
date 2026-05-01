package com.example.baam2.service;

import com.example.baam2.config.CacheConfig;
import com.example.baam2.exception.CustomException;
import com.example.baam2.repository.SessionRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QRTokenService {
    private final CacheManager cacheManager;
    private final SimpMessagingTemplate messageTemplate;
    private final SessionRepository sessionRepository;

    public QRTokenService(CacheManager cacheManager, SimpMessagingTemplate messageTemplate, SessionRepository sessionRepository){
        this.cacheManager = cacheManager;
        this.messageTemplate = messageTemplate;
        this.sessionRepository = sessionRepository;
    }

    public String convertAndSend(Long sessionId){
        String token = UUID.randomUUID().toString();
        Cache cache = cacheManager.getCache(CacheConfig.QR_TOKEN_CACHE);
        if (cache != null) cache.put(token, sessionId);

        messageTemplate.convertAndSend("/topic/session/"+sessionId+"/qr", token);

        return token;
    }

    public Long validateTokenAndGetSesionId(String token){
        Cache cache = cacheManager.getCache(CacheConfig.QR_TOKEN_CACHE);

        if (cache != null) {
            Long sessionId = cache.get(token, Long.class);
            if (sessionId != null) return sessionId;
        }

        throw new CustomException("TOKEN_NOT_ACCEPTED", "Token is not up to date or is not valid");
    }

    @Scheduled(fixedRate = 2000)
    public void updateAllActiveTokens(){
        List<Long> activeIds = sessionRepository.findAllActiveIds();

        activeIds.forEach(id -> convertAndSend(id));
    }
}
