package com.userManagement.userManagement.service;

import com.userManagement.userManagement.dto.UserDTO;
import com.userManagement.userManagement.model.UserModel;
import com.userManagement.userManagement.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        List<UserModel> userModelList = userRepo.findAll();
        return modelMapper.map(userModelList, new TypeToken<List<UserDTO>>(){}.getType());
    }

}
