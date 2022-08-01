package com.andikscript.springsociallogin.service;

import com.andikscript.springsociallogin.exception.AlreadyUser;
import com.andikscript.springsociallogin.exception.RefreshTokenExpired;
import com.andikscript.springsociallogin.model.User;
import com.andikscript.springsociallogin.payload.JwtResponse;
import com.andikscript.springsociallogin.payload.RefreshTokenRequest;
import com.andikscript.springsociallogin.payload.RefreshTokenResponse;
import com.andikscript.springsociallogin.payload.UserPassRequest;

import java.util.Optional;

public interface UserService {

    void createUser(User user) throws AlreadyUser;

    Optional<User> getUserByUsername(String username);

    JwtResponse authUser(UserPassRequest userPassRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws RefreshTokenExpired;
}
