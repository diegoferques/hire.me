package com.bemobi.controller;

import com.bemobi.entity.Url;
import com.bemobi.model.UrlResponse;
import com.bemobi.repository.UrlRepository;
import com.bemobi.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.*;
import java.util.List;

@RestController
public class UrlController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UrlRepository repository;

    @Autowired
    private UrlService service;

    public UrlController() throws UnknownHostException {
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseBody
    String home() {
        return "Acesso de forma incorreta!";
    }

    @RequestMapping(path = "/create", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<UrlResponse> shorten(@RequestParam String url, @RequestParam(required = false) String alias) {
        Long timeStart = System.currentTimeMillis();

        try {
            URI u = new URI(url);

            if(StringUtils.isEmpty(u.getScheme()))
            {
                url = "http://" + url;
            }

            UrlResponse urlResponse = service.shortenerUrl(url, alias);
            urlResponse.setTimeTaken(String.valueOf(System.currentTimeMillis() - timeStart) + "ms");

            if(StringUtils.isEmpty(urlResponse.getErrorCode())) {
                log.info("URL {} encurtada para {}.", url, urlResponse.getUrl());
                return ResponseEntity.ok(urlResponse);
            } else {
                log.error("ERROR CODE {}.", urlResponse.getErrorCode());
                return ResponseEntity.status(409).body(urlResponse);
            }

        }
        catch(Exception ex) {
            UrlResponse urlResponse = new UrlResponse();
            urlResponse.setErrorCode("500");
            urlResponse.setDescription("Internal Server Error");
            log.error("Erro salvando url.", ex);

            return ResponseEntity.status(500).body(urlResponse);
        }
    }

    @RequestMapping(path = "/u/{alias}", method = RequestMethod.GET)
    @ResponseBody
    public UrlResponse retrieveUrl(@PathVariable String alias, HttpServletResponse servletResponse) throws IOException {

        try {
            UrlResponse urlResponse = service.retrieveUrl(alias);

            if(urlResponse.getErrorCode() != null) {
                log.error("Erro ao obter a url encurtada. Erro = " +  urlResponse.getErrorCode());

                return  urlResponse;
            } else {
                servletResponse.sendRedirect(urlResponse.getOriginalUrl());
                log.info("Redirecionando para {}", urlResponse.getOriginalUrl());
                return null;
            }
        } catch (Exception e) {
            UrlResponse urlResponse = new UrlResponse();
            urlResponse.setErrorCode("500");
            urlResponse.setDescription("Internal Server Error");

            log.error("Internal Server Error {}", e);
            return urlResponse;
        }
    }

    @RequestMapping(path = "/mostUses", method = RequestMethod.GET)
    @ResponseBody
    public List<Url> mostUses() {
        return service.getMostUses();
    }

}
