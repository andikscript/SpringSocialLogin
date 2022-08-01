package com.andikscript.springsociallogin.controller;

import com.andikscript.springsociallogin.exception.AlreadyUser;
import com.andikscript.springsociallogin.exception.FailedValueBody;
import com.andikscript.springsociallogin.exception.RefreshTokenExpired;
import com.andikscript.springsociallogin.message.ResponseMessage;
import com.andikscript.springsociallogin.model.User;
import com.andikscript.springsociallogin.payload.RefreshTokenRequest;
import com.andikscript.springsociallogin.payload.UserPassRequest;
import com.andikscript.springsociallogin.security.jwt.JwtUtils;
import com.andikscript.springsociallogin.security.refresh.RefreshTokenService;
import com.andikscript.springsociallogin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService,
                          JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws AlreadyUser {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Successfully create user"));
    }

    @PostMapping(value = "/signin", consumes = "application/json")
    public ResponseEntity<?> authUser(@Valid @RequestBody UserPassRequest userPassRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.authUser(userPassRequest));
    }

    @PostMapping(value = "/refreshtoken", consumes = "application/json")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
            throws RefreshTokenExpired {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.refreshToken(refreshTokenRequest));
    }

    @PostMapping(value = "/signout")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ROOT')")
    public ResponseEntity<?> signout(@RequestHeader(value = "Authorization") String token) throws FailedValueBody {
        if (token == null) {
            throw new FailedValueBody();
        }

        String user = jwtUtils.getUsernameFromJwtToken(token.substring(7));
        refreshTokenService.deleteByUser(userService.getUserByUsername(user).get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Successfully signout"));
    }
}
