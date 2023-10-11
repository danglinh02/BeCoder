package com.example.UserManagementApp.config;

import com.example.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    private UserService userService;
    private CustomSuccessHandler customSuccessHandler;

    @Autowired
    @Lazy
    public SecurityConfiguration(UserService userService,CustomSuccessHandler customSuccessHandler) {

        this.userService = userService;
        this.customSuccessHandler=customSuccessHandler;

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(bCryptPasswordEncoder());
        return auth;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                congigurer->congigurer
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/teacher/**").hasAuthority("ROLE_TEACHER")

                        .anyRequest().permitAll()
        ).formLogin(
                login->login
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser").successHandler(customSuccessHandler)

        ).logout(
                logout->logout
                        .permitAll()
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        return http.build();
    }

}
