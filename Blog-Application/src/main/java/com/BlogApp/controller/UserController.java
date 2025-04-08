package com.BlogApp.controller;

import com.BlogApp.dto.AllResponse;
import com.BlogApp.dto.UserDto;
import com.BlogApp.service.UserService;
import com.BlogApp.utils.PaegableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create
    @PostMapping
   public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
   }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto){
        UserDto update = userService.updateUser(id, userDto);
        return ResponseEntity.ok(update);
    }
    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserBYId(@PathVariable int id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    // Get All Users
    @GetMapping
    public ResponseEntity<PaegableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir
    ){
        PaegableResponse<UserDto> allUsers = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allUsers);
    }
    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<AllResponse> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        AllResponse userDeletedSuccessfully = AllResponse.builder().message("User deleted successfully").status(HttpStatus.OK).date(LocalDate.now()).build();
        return ResponseEntity.ok(userDeletedSuccessfully);
    }
    // Add methods for each of the above functionalities here.


}
