package com.example.UserManagementApp.service;

import com.example.UserManagementApp.entity.UserDtls;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDtls createUser(UserDtls userDtls);
    public boolean checkEmail(String email);
    public UserDtls getByEmail(String email);
    public UserDtls saveUser(UserDtls userDtls);
    public UserDtls getByEmailAndPhoneNumber(String email,String phoneNumber);
    public UserDtls getById(int id);

}
