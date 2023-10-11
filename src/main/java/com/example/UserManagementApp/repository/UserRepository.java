package com.example.UserManagementApp.repository;

import com.example.UserManagementApp.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDtls,Integer> {
    public boolean existsByEmail(String email);
    public UserDtls findByEmail(String email);
    public UserDtls findByEmailAndPhoneNumber(String email,String phoneNumber);
}
