package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.entity.UserHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOperationService {
    public List<Cache> getAllPersonalFilesByUserId(String id) {
        // todo
        return null;
    }

    public UserHistory getHistoryByUserId(String id) {
        // todo
        return null;
    }

    public void cleanHistoryByUserId(String id) {
        // todo
    }

    public Gif searchGifByQuery(String id, String query, Boolean force) {
        // todo
        return null;
    }

    public Gif createGif(String id, GenerateGifForUserDto dto) {
        // todo
        return null;
    }

    public void resetUserCacheByQuery(String id, String query) {
        // todo
    }

    public void resetAllUserCache(String id) {
        // todo
    }

    public void cleanAllUserData(String id) {
        // todo
    }
}