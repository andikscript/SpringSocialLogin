package com.andikscript.springsociallogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/testsecured/")
public class TestSecuredController {

    @GetMapping(value = "/user")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_ROOT"})
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("user secured");
    }

    @GetMapping(value = "/admin")
    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    public ResponseEntity<?> getAdmin() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("secured admin");
    }

    @GetMapping(value = "/root")
    @Secured("ROLE_ROOT")
    public ResponseEntity<?> getRoot() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("secured root");
    }
}
