package com.bsa.bsagiphy.service;

import com.bsa.bsagiphy.entity.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GifOperationService {

    @Autowired
    private HttpGifsApiClient httpGifsApiClient;

    public List<Gif> getAll() {
        return null;
    }

    public Gif get(String query) {
        return httpGifsApiClient.getGif(query);
    }
}
