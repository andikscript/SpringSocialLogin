package com.andikscript.springsociallogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/root")
public class RootController {

    @GetMapping(value = "/access")
    public String rootAccess() {
        return "root access";
    }
}