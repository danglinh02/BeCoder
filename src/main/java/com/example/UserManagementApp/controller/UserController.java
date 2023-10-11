package com.example.UserManagementApp.controller;

import com.example.UserManagementApp.entity.UserDtls;
import com.example.UserManagementApp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String home()
    {
        return "user/home";
    }
    @GetMapping("/changePass")
    public String changePass()
    {
        return "user/change_password";
    }
    @PostMapping("/updatePass")
    public String changePass2(Principal p, @RequestParam("old_pass") String oldPass,@RequestParam("new_pass") String newPass,Model model)
    {
        String email=p.getName();
        UserDtls user=this.userService.getByEmail(email);
        boolean f=bCryptPasswordEncoder.matches(oldPass,user.getPassword());
        if(f)
        {
            user.setPassword(bCryptPasswordEncoder.encode(newPass));
            UserDtls userDtls=userService.saveUser(user);
            if (userDtls!=null)
            {
                model.addAttribute("successMsg","Password change success");
            }
            else {
                model.addAttribute("errorMsg","Something wrong on server");
            }
        }
        else {
            model.addAttribute("errorMsg","old password incorrect");
        }
        return "user/change_password";
    }
}
