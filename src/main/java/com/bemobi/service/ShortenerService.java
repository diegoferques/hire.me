package com.bemobi.service;

import org.springframework.stereotype.Service;

@Service
public class ShortenerService {

    public String shortenerUrl(String url) {
        return String.valueOf(System.nanoTime());
    }

}
