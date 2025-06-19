package com.userManagement.userManagement.service;

import com.userManagement.userManagement.common.ErrorLoginResponse;
import com.userManagement.userManagement.common.LoginResponse;
import com.userManagement.userManagement.common.SuccessLoginResponse;
import com.userManagement.userManagement.dto.LoginResponseDTO;
import com.userManagement.userManagement.dto.UserDetailsDTO;
import com.userManagement.userManagement.dto.UserLoginRequestDTO;
import com.userManagement.userManagement.dto.UserRegisterDTO;
import com.userManagement.userManagement.model.UserModel;
import com.userManagement.userManagement.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDetailsDTO> getAllCustomers() {
        List<UserModel> userModelList = userRepo.findAllCustomers();
        return modelMapper.map(userModelList, new TypeToken<List<UserDetailsDTO>>(){}.getType());
    }

    public String registerUser(UserRegisterDTO userRegisterDTO, String userRole) {
        UserModel userModel = modelMapper.map(userRegisterDTO, UserModel.class);
        userModel.setUserRole(userRole);
        userModel.setPasswordHash(passwordEncoder.encode(userRegisterDTO.getPasswordHash()));

        userRepo.save(userModel);
        return "Register Successfully!";
    }

    public Optional<UserModel> getUserByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }

    public LoginResponse loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        Optional<UserModel> userModelOpt = getUserByEmail(userLoginRequestDTO.getEmail());

        if (userModelOpt.isPresent()) {
            UserModel user = userModelOpt.get();

            if (passwordEncoder.matches(userLoginRequestDTO.getPassword(), user.getPasswordHash())) {
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setEmail(user.getEmail());
                loginResponseDTO.setUserId(user.getUserId());
                return new SuccessLoginResponse(loginResponseDTO);
            } else {
                return new ErrorLoginResponse("Email or password doesn't match");
            }
        } else {
            return new ErrorLoginResponse("User not found");
        }
    }
}