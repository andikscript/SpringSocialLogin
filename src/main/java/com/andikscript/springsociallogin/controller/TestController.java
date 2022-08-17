package com.andikscript.springsociallogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test/")
public class TestController {

    @GetMapping(value = "/public")
    public ResponseEntity<?> getPublic() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("public");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ROOT')")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("user");
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ROOT')")
    public ResponseEntity<?> getAdmin() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("admin");
    }

    @GetMapping(value = "/root")
    @PreAuthorize("hasRole('ROOT')")
    public ResponseEntity<?> getRoot() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("root");
    }
}
