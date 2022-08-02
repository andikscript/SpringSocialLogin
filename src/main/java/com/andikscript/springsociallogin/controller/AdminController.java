package com.andikscript.springsociallogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/admin")
public class AdminController {

    @GetMapping(value = "/access")
    public String adminAccess() {
        return "admin access";
    }
}