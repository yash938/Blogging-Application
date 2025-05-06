package com.BlogApp.service;


import com.BlogApp.dto.RefreshTokenDto;
import com.BlogApp.dto.UserDto;

public interface RefreshTokenService {


    public RefreshTokenDto createRefreshToken(String username);

    public RefreshTokenDto findByToken(String token);

    public RefreshTokenDto verifyToken(RefreshTokenDto refreshTokenDto);

    UserDto getUser(RefreshTokenDto tokenDto);

}
