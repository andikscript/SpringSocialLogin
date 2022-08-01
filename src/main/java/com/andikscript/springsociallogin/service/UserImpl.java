package com.andikscript.springsociallogin.service;

import com.andikscript.springsociallogin.exception.AlreadyUser;
import com.andikscript.springsociallogin.exception.RefreshTokenExpired;
import com.andikscript.springsociallogin.model.RefreshToken;
import com.andikscript.springsociallogin.model.User;
import com.andikscript.springsociallogin.payload.JwtResponse;
import com.andikscript.springsociallogin.payload.RefreshTokenRequest;
import com.andikscript.springsociallogin.payload.RefreshTokenResponse;
import com.andikscript.springsociallogin.payload.UserPassRequest;
import com.andikscript.springsociallogin.repository.UserRepository;
import com.andikscript.springsociallogin.security.jwt.JwtUtils;
import com.andikscript.springsociallogin.security.refresh.RefreshTokenService;
import com.andikscript.springsociallogin.security.service.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                    RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void createUser(User user) throws AlreadyUser {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyUser();
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public JwtResponse authUser(UserPassRequest userPassRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassRequest.getUsername(),
                        userPassRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return new JwtResponse(
                        jwt, refreshToken.getToken(), userDetails.getUsername(),
                        userDetails.getPassword(), roles);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws RefreshTokenExpired {
        String request = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(request)
                .map(refreshTokenService::verifyExpired)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getIdUser().getUsername());
                    return new RefreshTokenResponse(token, request);
                })
                .orElseThrow(() -> new RefreshTokenExpired());
    }
}
