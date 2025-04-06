package com.BlogApp.serviceImplementation;

import com.BlogApp.dto.UserDto;
import com.BlogApp.entity.User;
import com.BlogApp.repository.UserRepo;
import com.BlogApp.service.UserService;
import com.BlogApp.utils.Helper;
import com.BlogApp.utils.PaegableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User creatingUser = modelMapper.map(userDto, User.class);
        User createUser = userRepo.save(creatingUser);
        UserDto saveUser = modelMapper.map(createUser, UserDto.class);
        return saveUser;
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("userId is not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updateUsser = userRepo.save(user);
        return modelMapper.map(updateUsser,UserDto.class);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User id is not found"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public PaegableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> allUsers = userRepo.findAll(pageRequest);
        PaegableResponse<UserDto> paegable = Helper.getPaegable(allUsers, UserDto.class);
        return paegable;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User id is not found"));
        userRepo.delete(user);
        System.out.println("user is deleted");
    }
}
