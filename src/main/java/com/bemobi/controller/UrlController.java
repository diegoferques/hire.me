package com.bemobi.controller;

import com.bemobi.model.UrlResponse;
import com.bemobi.repository.UrlRepository;
import com.bemobi.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.UnknownHostException;

@RestController
public class UrlController {

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

    @RequestMapping(path = "/create?url={url}", method = RequestMethod.GET)
    @ResponseBody
    String show(@PathVariable String url) {
        return url;
    }

    @RequestMapping(path = "/create", method = RequestMethod.PUT)
    @ResponseBody
    public UrlResponse create(@RequestParam String url, @RequestParam(required = false) String alias) {
        Long timeStart = System.currentTimeMillis();
        UrlResponse urlResponse = service.shortenerUrl(url, alias);
        urlResponse.setTimeTaken(String.valueOf(System.currentTimeMillis() - timeStart) + "ms");

        return urlResponse;
    }

    @RequestMapping(path = "/check", method = RequestMethod.PUT)
    @ResponseBody
    String check(@RequestParam(required = false) String alias) {
        Boolean checkAlias = service.checkAlias(alias);
        String texto;

        if(checkAlias == true) {
            texto = "Alias Encontrado!";
        } else {
            texto = "Alias NÃO encontrado!";
        }

        return texto;
    }
}
