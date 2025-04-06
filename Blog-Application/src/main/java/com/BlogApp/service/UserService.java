package com.BlogApp.service;

import com.BlogApp.dto.UserDto;
import com.BlogApp.utils.PaegableResponse;

import java.util.List;

public interface UserService {

        UserDto createUser(UserDto userDto);
        UserDto updateUser(int id,UserDto userDto);
        UserDto getUserById(int id);
        PaegableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
        void deleteUser(int id);
}
