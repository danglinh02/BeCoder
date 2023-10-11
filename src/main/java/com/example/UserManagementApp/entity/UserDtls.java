package com.example.UserManagementApp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_dtls",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserDtls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "email",length = 255,nullable = false)
    private String email;

    @Column(name = "password",length = 255,nullable = false)
    private String password;
    @Column(name = "full_name",length = 255,nullable = false)
    private String fullName;
    @Column(name = "address",length = 255,nullable = false)
    private String address;
    @Column(name = "qualification",length = 255)
    private String qualification;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private List<Role>roles;

    public UserDtls() {
    }

    public UserDtls(String email, String password, String fullName, String address, String qualification) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.qualification = qualification;
    }

    public UserDtls(String email, String password, String fullName, String address, String qualification, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.qualification = qualification;
        this.roles = roles;
    }

    public UserDtls(String email, String password, String fullName, String address, String qualification, String phoneNumber, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserDtls{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", qualification='" + qualification + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                '}';
    }
}
