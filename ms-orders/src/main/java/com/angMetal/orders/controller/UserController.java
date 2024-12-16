package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.payloads.request.CreateUserDTO;
import com.angMetal.orders.payloads.request.UpdatePasswordDTO;
import com.angMetal.orders.payloads.request.UpdateUserDTO;
import com.angMetal.orders.service.UserService;
import com.angMetal.orders.payloads.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    UserService userService;

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"","/"})
    public ResponseEntity<Response> getAll() {
        return userService.getAll();
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "{userId}")
    public ResponseEntity<Response> getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    //@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "{userId}/change-password")
    public ResponseEntity<Response> updatePassword(@PathVariable Long userId, @RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return userService.updatePassword(userId, updatePasswordDTO);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "{userId}/admin-change-password")
    public ResponseEntity<Response> updatePasswordByAdmin(@PathVariable Long userId, @RequestParam(name = "newPassword") String newPassword) {
        return userService.updatePasswordByAdmin(userId, newPassword);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long userId) {
        return userService.deleteById(userId);
    }

    @PutMapping(value = "{userId}/update")
    public ResponseEntity<Response> update(@PathVariable Long userId, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.update(userId, updateUserDTO);
    }

  //  @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "{userId}/roles")
    public ResponseEntity<Response> assignRole(@PathVariable Long userId, @RequestParam(name = "role") String role) {
        return userService.assignRole(userId, role);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "{userId}/roles")
    public ResponseEntity<Response> revokeRole(@PathVariable Long userId, @RequestParam(name = "role") String role) {
        return userService.revokeRole(userId, role);
    }
    @DeleteMapping(value = "{userId}/delete")
    public ResponseEntity<Response> delete(@PathVariable Long userId) {
        return userService.deleteById(userId);
    }
    // Create a new user
    @PostMapping(value = "/create")
    public ResponseEntity<Response> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }
}