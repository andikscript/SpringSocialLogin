package com.andikscript.springsociallogin.controller;

import com.andikscript.springsociallogin.access.IsAdmin;
import com.andikscript.springsociallogin.access.IsRoot;
import com.andikscript.springsociallogin.access.IsUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/all")
public class AllController {

    @IsRoot
    @GetMapping(value = "/accessroot")
    public String rootAccess() {
        return "root access";
    }

    @IsAdmin
    @GetMapping(value = "/accessadmin")
    public String adminAccess() {
        return "admin access";
    }

    @IsUser
    @GetMapping(value = "/accessuser")
    public String userAccess() {
        return "user access";
    }
}