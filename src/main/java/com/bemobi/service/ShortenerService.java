package com.bemobi.service;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ShortenerService {

    public String shortenerUrl(String url) {
        //BIBLIOTECA DO GOOGLE
        //https://google.github.io/guava/releases/19.0/api/docs/com/google/common/hash/Hashing.html
        String hashedUrl = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();

        //return String.valueOf(System.nanoTime());
        return hashedUrl;
    }

}
