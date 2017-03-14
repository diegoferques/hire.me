package com.bemobi.controller;

import com.bemobi.model.UrlResponse;
import com.bemobi.repository.UrlRepository;
import com.bemobi.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.*;

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
    public UrlResponse shorten(@RequestParam String url, @RequestParam(required = false) String alias) {
        Long timeStart = System.currentTimeMillis();
        UrlResponse urlResponse = service.shortenerUrl(url, alias);
        urlResponse.setTimeTaken(String.valueOf(System.currentTimeMillis() - timeStart) + "ms");

        return urlResponse;
    }

    @RequestMapping(path = "/u/{alias}", method = RequestMethod.GET)
    @ResponseBody
    public UrlResponse retrieveUrl(@PathVariable String alias, HttpServletResponse servletResponse) throws IOException {
        UrlResponse urlResponse = service.retrieveUrl(alias);

        String url;

        if(urlResponse.getErrorCode() != null) {
            log.error("Erro ao obter a url encurtada. Erro = " +  urlResponse.getErrorCode());

            return  urlResponse;
        } else {

            try {
                URI u = new URI(urlResponse.getOriginalUrl());
                servletResponse.sendRedirect(urlResponse.getOriginalUrl());
                log.info("Redirecionando para {}", urlResponse.getOriginalUrl());
            }
            catch(Exception ex) {
                servletResponse.setStatus(400);
                log.error("Malformed url: {}", urlResponse.getOriginalUrl());
            }

            return null;
        }
    }

}
