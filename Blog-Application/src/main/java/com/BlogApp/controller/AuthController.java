package com.BlogApp.controller;

import com.BlogApp.dto.*;
import com.BlogApp.security.JwtHelper;
import com.BlogApp.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/generateRefreshToken")
    public ResponseEntity<JwtResponse> generateRefreshToken(@RequestBody RefreshTokenBody refreshToken){
        RefreshTokenDto byToken = refreshTokenService.findByToken(refreshToken.getRefreshToken());
        RefreshTokenDto refreshTokenDto = refreshTokenService.verifyToken(byToken);


        UserDto user = refreshTokenService.getUser(refreshTokenDto);
        String s = jwtHelper.generateToken(modelMapper.map(user,User.class));

        JwtResponse build = JwtResponse.builder().token(s).refreshTokenDto(byToken).user(user ).build();
        return ResponseEntity.ok(build);

    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){

        log.info("Username {} , Password {} ",jwtRequest.getEmail(),jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        JwtResponse build = JwtResponse.builder().token(token).refreshTokenDto(refreshToken).user(modelMapper.map(userDetails, UserDto.class)).build();

        return ResponseEntity.ok(build);

    }

    private void doAuthenticate(String email, String password) {
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        }catch (BadCredentialsException ex){

            ex.getMessage();
        }
    }

}
