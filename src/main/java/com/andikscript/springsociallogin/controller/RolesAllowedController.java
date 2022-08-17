package com.andikscript.springsociallogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/roleallowed/")
public class RolesAllowedController {

    @GetMapping(value = "/user")
    @RolesAllowed({"USER", "ADMIN", "ROOT"})
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("user allowed");
    }

    @GetMapping(value = "/admin")
    @RolesAllowed({"ADMIN", "ROOT"})
    public ResponseEntity<?> getAdmin() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("admin allowed");
    }

    @GetMapping(value = "/root")
    @RolesAllowed("ROOT")
    public ResponseEntity<?> getRoot() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("root allowed");
    }
}
