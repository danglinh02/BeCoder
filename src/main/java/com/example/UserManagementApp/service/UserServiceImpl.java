package com.example.UserManagementApp.service;

import com.example.UserManagementApp.entity.Role;
import com.example.UserManagementApp.entity.UserDtls;
import com.example.UserManagementApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public UserDtls createUser(UserDtls userDtls) {
        userDtls.setPassword(bCryptPasswordEncoder.encode(userDtls.getPassword()));
        List<Role>role=new ArrayList<>();
        role.add(new Role("ROLE_USER"));
        userDtls.setRoles(role);
        return this.userRepository.save(userDtls);
    }
    @Override
    @Transactional
    public UserDtls saveUser(UserDtls userDtls)
    {
        return this.userRepository.save(userDtls);
    }

    @Override
    public UserDtls getByEmailAndPhoneNumber(String email, String phoneNumber) {
        return this.userRepository.findByEmailAndPhoneNumber(email,phoneNumber);
    }

    @Override
    public UserDtls getById(int id) {
        return this.userRepository.getById(id);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDtls getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDtls user=this.userRepository.findByEmail(email);
        if (user==null)
        {
            throw new UsernameNotFoundException("Invalid email "+email);
        }
        else {
            return new User(user.getEmail(), user.getPassword(), getRoles(user.getRoles()));
        }
    }
    private Collection<? extends GrantedAuthority>getRoles(Collection<Role>roles)
    {
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
