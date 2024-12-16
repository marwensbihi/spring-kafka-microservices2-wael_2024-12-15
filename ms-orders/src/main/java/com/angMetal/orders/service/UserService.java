package com.angMetal.orders.service;


import com.angMetal.orders.payloads.request.CreateUserDTO;
import com.angMetal.orders.payloads.request.UpdatePasswordDTO;
import com.angMetal.orders.payloads.request.UpdateUserDTO;
import com.angMetal.orders.payloads.request.UserDTO;
import com.angMetal.orders.repositories.UserRepository;
import com.angMetal.orders.enums.ERole;
import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.payloads.response.Response;
import com.angMetal.orders.repositories.RoleRepository;
import com.angMetal.orders.security.jwt.JwtUtils;
import com.angMetal.orders.utils.ResponseUtils;
import com.angMetal.orders.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserUtils userUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


/*
    public ResponseEntity<Response> getAll() {
        try {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

            List<User> users = userRepository.findAll().stream()
                    .filter(user -> !user.getRoles().contains(adminRole))
                    .collect(Collectors.toList());

            // Set passwords to null for security purposes
            users.forEach(user -> user.setPassword(user.getPassword())); // Do not expose passwords

            HashMap<String, List<User>> data = new HashMap<>();
            data.put("users", users);

            return ResponseUtils.handleResponse("All users retrieved successfully", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

*/

    public ResponseEntity<Response> getAll() {
        try {
            // Find the admin role
           // Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

            // Filter users with admin role
            List<User> users = userRepository.findAll();

            // Map users to DTOs including roles
            List<UserDTO> userDTOs = users.stream()
                    .map(user -> new UserDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getAddress(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getRoles().stream()
                                    .map(Role::getName) // Extract role names
                                    .map(Enum::name)    // Convert enum to String
                                    .collect(Collectors.toSet()) // Collect as a Set
                    ))
                    .collect(Collectors.toList());

            // Prepare response data
            Map<String, List<UserDTO>> data = Map.of("users", userDTOs);

            return ResponseUtils.handleResponse("All users retrieved successfully", HttpStatus.OK, data);
        } catch (Exception e) {
            // Handle exceptions
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> getById(Long userId) {
        try {
            User user = userRepository.findById(userId).map(u -> {
                u.setPassword(null);
                return u;
            }).orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist."));

            HashMap<String, User> data = new HashMap<>();
            data.put("user", user);

            return ResponseUtils.handleResponse("User: '" + userId + "' retrieved successfully", HttpStatus.OK, data);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> deleteById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            user.getRoles().removeAll(user.getRoles());

            userRepository.deleteById(userId);

            return ResponseUtils.handleResponse("User: '" + userId + "' deleted successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Transactional
    public ResponseEntity<Response> update(Long userId, UpdateUserDTO updateUserDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            // Update basic fields
            String username = updateUserDTO.getUsername();
            String email = updateUserDTO.getEmail();
            String firstName = updateUserDTO.getFirstName();
            String lastName = updateUserDTO.getLastName();
            String address = updateUserDTO.getAddress();
            String phoneNumber = updateUserDTO.getPhoneNumber();

            if (username != null && !username.equals(user.getUsername()) && userUtils.isUsernameValid(username)) {
                user.setUsername(username);
            }

            if (email != null && !email.equals(user.getEmail()) && userUtils.isEmailValid(email)) {
                user.setEmail(email);
            }

            if (firstName != null && !firstName.isEmpty()) {
                user.setFirstName(firstName);
            }

            if (lastName != null && !lastName.isEmpty()) {
                user.setLastName(lastName);
            }

            if (address != null && !address.isEmpty()) {
                user.setAddress(address);
            }

            if (phoneNumber != null && !phoneNumber.isEmpty() && userUtils.isPhoneNumberValid(phoneNumber)) {
                user.setPhoneNumber(phoneNumber);
            }

            // Update roles if provided
            if (updateUserDTO.getRoles() != null && !updateUserDTO.getRoles().isEmpty()) {
                Set<Role> newRoles = updateUserDTO.getRoles().stream()
                        .map(roleName -> {
                            Role role = roleRepository.findByName(ERole.valueOf(roleName));
                            if (role == null) {
                                throw new NoSuchElementException("Role '" + roleName + "' does not exist");
                            }
                            return role;
                        })
                        .collect(Collectors.toSet());

                user.setRoles(newRoles);
            }

            userRepository.save(user);

            // Generate JWT token for updated user
            String token = jwtUtils.generateJwtTokenFromUsername(username);

            HashMap<String, String> data = new HashMap<>();
            data.put("accessToken", token);

            return ResponseUtils.handleResponse("User updated successfully", HttpStatus.OK, data);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Response> updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            String oldPassword = updatePasswordDTO.getOldPassword();
            String newPassword = updatePasswordDTO.getNewPassword();

            if (oldPassword == null || oldPassword.isEmpty()) {
                throw new IllegalStateException("Old password cannot be null/empty");
            }

            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalStateException("New password cannot be null/empty");
            }

            if (!encoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalStateException("Passwords do not match");
            }

            if (userUtils.isPasswordValid(newPassword)) {
                user.setPassword(encoder.encode(newPassword));
                userRepository.save(user);
            }

            return ResponseUtils.handleResponse("Password updated successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Response> updatePasswordByAdmin(Long userId, String newPassword) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalStateException("New password cannot be null/empty");
            }

            if (userUtils.isPasswordValid(newPassword)) {
                user.setPassword(encoder.encode(newPassword));
                userRepository.save(user);
            }

            return ResponseUtils.handleResponse("Password updated successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> assignRole(Long userId, String roleToAdd) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            ERole eRole;

            switch (roleToAdd) {
                case "ADMIN":
                    eRole = ERole.ROLE_ADMIN;
                    break;
                case "EMPLOYEE":
                    eRole = ERole.ROLE_EMPLOYEE;
                    break;
                case "COMPUTABLE":
                    eRole = ERole.ROLE_COMPUTABLE;
                    break;
                default:
                    throw new NoSuchElementException("Permission: '" + roleToAdd.toUpperCase() + "' does not exist");
            }

            Role role = roleRepository.findByName(eRole);

            user.getRoles().add(role);
            userRepository.save(user);

            return ResponseUtils.handleResponse("Role: '" + roleToAdd.toUpperCase() + "' assigned successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> revokeRole(Long userId, String roleToRevoke) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            ERole eRole;

            switch (roleToRevoke) {
                case "ADMIN":
                    eRole = ERole.ROLE_ADMIN;
                    break;
                case "COMPTABLE":
                    eRole = ERole.ROLE_COMPUTABLE;
                    break;
                default:
                    throw new NoSuchElementException("Role: '" + roleToRevoke.toUpperCase() + "' does not exist");
            }

            Role role = roleRepository.findByName(eRole);

            user.getRoles().remove(role);
            userRepository.save(user);

            return ResponseUtils.handleResponse("Role: '" + roleToRevoke.toUpperCase() + "' revoked successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Response> createUser(CreateUserDTO createUserDTO) {
        try {
            if (userUtils.isUsernameValid(createUserDTO.getUsername()) && userUtils.isEmailValid(createUserDTO.getEmail())) {
                if (userRepository.existsByUsername(createUserDTO.getUsername())) {
                    throw new IllegalStateException("Username is already taken.");
                }
                if (userRepository.existsByEmail(createUserDTO.getEmail())) {
                    throw new IllegalStateException("Email is already taken.");
                }
                User user = new User(
                        createUserDTO.getUsername(),
                        createUserDTO.getEmail(),
                        encoder.encode(createUserDTO.getPassword())
                );

                user.setUsername(createUserDTO.getUsername());
                user.setEmail(createUserDTO.getEmail());
                user.setPassword(encoder.encode(createUserDTO.getPassword()));
                user.setFirstName(createUserDTO.getFirstName());
                user.setLastName(createUserDTO.getLastName());
                user.setAddress(createUserDTO.getAddress());
                user.setPhoneNumber(createUserDTO.getPhoneNumber());

                Set<String> strRoles = createUserDTO.getRoles();
                Set<Role> roles = new HashSet<>();

                if (strRoles == null) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_COMPUTABLE);
                    roles.add(userRole);
                } else {
                    strRoles.forEach(role -> {
                        switch (role) {
                            case "ADMIN":
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                                roles.add(adminRole);
                                break;
                            case "USER":
                            default:
                                Role userRole = roleRepository.findByName(ERole.ROLE_COMPUTABLE);
                                roles.add(userRole);
                                break;
                        }
                    });
                }

                user.setRoles(roles);
                userRepository.save(user);

                return ResponseUtils.handleResponse("User created successfully", HttpStatus.CREATED, null);
            } else {
                throw new IllegalStateException("Invalid username or email.");
            }
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }


    @Transactional
    public ResponseEntity<Response> updateUser(Long userId,UpdateUserDTO updateUserDTO) {
        try {
            // Check if the user exists

            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new IllegalStateException("User not found.");
            }

            User user = optionalUser.get();

            // Validate username and email
            if (!user.getUsername().equals(updateUserDTO.getUsername()) && userRepository.existsByUsername(updateUserDTO.getUsername())) {
                throw new IllegalStateException("Username is already taken.");
            }

            if (!user.getEmail().equals(updateUserDTO.getEmail()) && userRepository.existsByEmail(updateUserDTO.getEmail())) {
                throw new IllegalStateException("Email is already taken.");
            }

            // Update user fields
            user.setUsername(updateUserDTO.getUsername());
            user.setEmail(updateUserDTO.getEmail());
            user.setFirstName(updateUserDTO.getFirstName());
            user.setLastName(updateUserDTO.getLastName());
            user.setAddress(updateUserDTO.getAddress());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());

            // Handle roles
            Set<String> strRoles = updateUserDTO.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role computableRole = roleRepository.findByName(ERole.ROLE_COMPUTABLE);
                roles.add(computableRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ADMIN":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                            roles.add(adminRole);
                            break;
                        case "EMPLOYEE":
                            Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE);
                            roles.add(employeeRole);
                            break;
                        case "COMPTABLE":
                        default:
                            Role computableRole = roleRepository.findByName(ERole.ROLE_COMPUTABLE);
                            roles.add(computableRole);
                            break;
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseUtils.handleResponse("User updated successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

}