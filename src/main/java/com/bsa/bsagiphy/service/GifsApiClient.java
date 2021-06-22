package com.bsa.bsagiphy.service;

import com.bsa.bsagiphy.entity.Gif;

public interface GifsApiClient {
    Gif getGif(String query);
}
