package com.userManagement.userManagement.controller;

import com.userManagement.userManagement.common.LoginResponse;
import com.userManagement.userManagement.dto.UserDetailsDTO;
import com.userManagement.userManagement.dto.UserLoginRequestDTO;
import com.userManagement.userManagement.dto.UserRegisterDTO;
import com.userManagement.userManagement.service.KeycloakService;
import com.userManagement.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private KeycloakService keycloakService;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;


    @PostMapping("/customer/register")
    public ResponseEntity<?> registerCustomer(@RequestBody UserRegisterDTO userRegisterDTO) {
        if(userService.getUserByEmail(userRegisterDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist");
        }

        String result = userService.registerUser(userRegisterDTO, "customer");

        try{
            keycloakService.createKeycloakUser(userRegisterDTO, "customer");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Keycloak registration failed: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/receptionist/register")
    public ResponseEntity<?> registerReceptionist(@RequestBody UserRegisterDTO userRegisterDTO) {
        if(userService.getUserByEmail(userRegisterDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist");
        }

        String result = userService.registerUser(userRegisterDTO, "receptionist");

        try{
            keycloakService.createKeycloakUser(userRegisterDTO, "receptionist");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Keycloak registration failed: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/receptionist/login")
    public LoginResponse loginReceptionist(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return userService.loginUser(userLoginRequestDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "Authorized";
    }

    @PostMapping("/customer/keycloak-login")
    public ResponseEntity<?> loginViaKeycloak(@RequestBody UserLoginRequestDTO loginDTO) {
        return keycloakService.Login(loginDTO);
    }

    @GetMapping("/getAllCustomers")
    @PreAuthorize("hasRole('receptionist') or hasRole('admin')")
    public List<UserDetailsDTO> getAllCustomers() {
        return userService.getAllCustomers();
    }

}