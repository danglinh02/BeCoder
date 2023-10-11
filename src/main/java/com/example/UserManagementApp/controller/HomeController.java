package com.example.UserManagementApp.controller;

import com.example.UserManagementApp.entity.UserDtls;
import com.example.UserManagementApp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HomeController(UserService userService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/register")
    public String register()
    {
        return "register";
    }
    @ModelAttribute(value = "user")
    public UserDtls createObject()
    {
        return new UserDtls();
    }
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute(value = "user") UserDtls userDtls, Model model)
    {
        boolean f= userService.checkEmail(userDtls.getEmail());
        if (f)
        {
           model.addAttribute("msg","Email id a already Exits");
        }
        else {
            UserDtls user=this.userService.createUser(userDtls);

            if (user!=null)
            {
                model.addAttribute("msg","Register Successfully");
            }
            else {
                model.addAttribute("msg","Something error in server");
            }
        }
        return "register";
    }
    @GetMapping("/loadForgotPassword")
    public String loadForgotPassword()
    {
        return "forgot_password";
    }
    @GetMapping("/loadResetPassword/{id}")
    public String loadResetPassword(@PathVariable(value = "id") int id,Model model)
    {
        model.addAttribute("id",id);
        System.out.println(id);
        return "reset_password";
    }
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email,@RequestParam("phoneNumber") String phoneNumber,Model model)
    {
        UserDtls user=userService.getByEmailAndPhoneNumber(email,phoneNumber);
        if(user!=null)
        {
            return "redirect:/loadResetPassword/" +user.getId();
        }
        else {
            model.addAttribute("msg","invalid email or phone number ");
            return "forgot_password";
        }

    }
    @PostMapping("/changePassword")
    public String resetPassword(@RequestParam("npw")String npw,@RequestParam("id")Integer id,Model model)
    {
        UserDtls user=this.userService.getById(id);
        String encrypt=bCryptPasswordEncoder.encode(npw);
        user.setPassword(encrypt);
       UserDtls userDtls= userService.saveUser(user);
        if(userDtls!=null)
        {
            model.addAttribute("msgSuccess","Password Change Successfully");
        }
        return "forgot_password";
    }
}
