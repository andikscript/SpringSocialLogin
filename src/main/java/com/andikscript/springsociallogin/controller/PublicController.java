package com.andikscript.springsociallogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/public")
public class PublicController {

    @GetMapping(value = "/access")
    public String publicAccess() {
        return "public access";
    }
}