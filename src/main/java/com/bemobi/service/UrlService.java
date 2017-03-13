package com.bemobi.service;

import com.bemobi.entity.Url;
import com.bemobi.model.UrlResponse;
import com.bemobi.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repository;

    @Autowired
    private ShortenerService shortenerService;

    // TODO: 11/03/2017 Pegar o HTTP ou HTTPS automaticamente
    private String http = "http://";
    // TODO: 11/03/2017 Pegar porta automaticamente, sem ter que setar manualmente
    private int port = 8080;
    // TODO: 11/03/2017 Pegar o domínio corretamente, ao invés do IP
    private String domain = http + InetAddress.getLocalHost().getHostAddress() + ":" + String.valueOf(port);

    // TODO: 11/03/2017 Tratar a Exception corretamente
    public UrlService() throws UnknownHostException {
    }

    public String getDomain() {
        return domain;
    }

    public UrlResponse shortenerUrl(String url, String alias) {
        UrlResponse urlResponse = new UrlResponse();

        String shortenedAlias;
        String shortenedUrl = null;
        Boolean checkAlias = false;
        String errorCode = null;
        String description = null;
        Boolean saveUrl = true;
        int customAlias = 0;

        if(StringUtils.isEmpty(alias)) {
            Optional<Url> optionalUrl = Optional.ofNullable(repository.findByUrlAndCustomAlias(url, 0));

            if(optionalUrl.isPresent()) {
                Url urlRegistered = optionalUrl.get();
                shortenedAlias = urlRegistered.getAlias();
                saveUrl = false;
            } else {
                shortenedAlias = shortenerService.shortenerUrl(url);
            }

            shortenedUrl = domain + "/u/" + shortenedAlias;

        } else {
            checkAlias = this.checkAlias(alias);
            shortenedAlias = alias;
            customAlias = 1;
        }

        if(checkAlias == true) {
            errorCode = "001";
            description = "CUSTOM ALIAS ALREADY EXISTS";

        } else if(saveUrl == true) {
            shortenedUrl = domain + "/u/" + shortenedAlias;

            Url urlEntity = new Url();

            urlEntity.setAlias(shortenedAlias);
            urlEntity.setUrl(url);
            urlEntity.setCustomAlias(customAlias);

            repository.save(urlEntity);
        }

        urlResponse.setOriginalUrl(url);
        urlResponse.setAlias(shortenedAlias);
        urlResponse.setUrl(shortenedUrl);
        urlResponse.setErrorCode(errorCode);
        urlResponse.setDescription(description);

        return urlResponse;
    }

    public UrlResponse retrieveUrl(String alias) {
        Optional<Url> optionalUrl = Optional.ofNullable(repository.findByAlias(alias));
        UrlResponse urlResponse = new UrlResponse();

        String originalUrl = null;
        String shortenedUrl = null;
        String errorCode = null;
        String description = null;

        if(optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            originalUrl = url.getUrl();
        } else {
            errorCode = "002";
            description = "SHORTENED URL NOT FOUND";
        }

        urlResponse.setOriginalUrl(originalUrl);
        urlResponse.setAlias(alias);
        urlResponse.setUrl(shortenedUrl);
        urlResponse.setErrorCode(errorCode);
        urlResponse.setDescription(description);

        return urlResponse;

    }

    public Boolean checkAlias(String alias) {
        Optional<Url> optionalUrl = Optional.ofNullable(repository.findByAlias(alias));
        return optionalUrl.isPresent();
    }


}
