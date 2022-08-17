package com.andikscript.springsociallogin.controller;

import com.andikscript.springsociallogin.access.IsRoot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/all")
public class AllController {

    @IsRoot
    @GetMapping(value = "/access")
    public String adminAccess() {
        return "all access";
    }
}