package com.bemobi.service;

import com.bemobi.entity.Url;
import com.bemobi.model.UrlResponse;
import com.bemobi.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repository;

    @Autowired
    private ShortenerService shortenerService;

    public UrlService() {
    }

    public UrlResponse shortenerUrl(String url, String alias) {
        UrlResponse urlResponse = new UrlResponse();

        String shortnedAlias;
        Boolean checkAlias = false;
        String errorCode = null;
        String description = null;

        if(StringUtils.isEmpty(alias)) {
            shortnedAlias = shortenerService.shortenerUrl(url);
        } else {
            checkAlias = this.checkAlias(alias);
            shortnedAlias = alias;
        }

        if(checkAlias == true) {
            errorCode = "001";
            description = "CUSTOM ALIAS ALREADY EXISTS";
        } else {
            Url urlEntity = new Url();
            urlEntity.setUrl(url);
            urlEntity.setAlias(shortnedAlias);
            repository.save(urlEntity);
        }

        urlResponse.setAlias(shortnedAlias);
        urlResponse.setUrl(url);
        urlResponse.setErrorCode(errorCode);
        urlResponse.setDescription(description);

        return urlResponse;
    }

    public Boolean checkAlias(String alias) {
        Url url = repository.findByAlias(alias);
        Optional<Url> optionalUrl = Optional.ofNullable(url);
        return optionalUrl.isPresent();
    }




}
