package com.flumine.securityjwtauth20.config;

import com.flumine.securityjwtauth20.exceptions.ResourceNotFoundException;
import com.flumine.securityjwtauth20.models.ERole;
import com.flumine.securityjwtauth20.models.RoleModel;
import com.flumine.securityjwtauth20.models.UserModel;
import com.flumine.securityjwtauth20.repositories.RoleRepository;
import com.flumine.securityjwtauth20.repositories.UserRepository;
import com.flumine.securityjwtauth20.services.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailService;

    @Bean
    public UserDetailsService userDetailsService() {
        return user -> userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(user, user)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public void addRole() {
        roleRepository.save(new RoleModel(ERole.ROLE_USER));
        roleRepository.save(new RoleModel(ERole.ROLE_ADMIN));
        roleRepository.save(new RoleModel(ERole.ROLE_SUDO));
    }

//    @Bean
    public void add_sudo() {
        RoleModel sudo_role = roleRepository.findByRole(ERole.ROLE_SUDO).orElse(new RoleModel());
        UserModel user =
                UserModel.builder().username("vadim2422").email("breev.vadim@yandex.ru").password(passwordEncoder().encode("123")).roles(new HashSet<>() {{add(sudo_role);}}).build();
        userRepository.save(user);
    }

//    @Bean
    public void add_user() {
        RoleModel user_role = roleRepository.findByRole(ERole.ROLE_USER).orElse(new RoleModel());
        UserModel user = UserModel.builder().username("0").email("3").password(passwordEncoder().encode("4")).roles(new HashSet<>() {{add(user_role);}}).build();
        userRepository.save(user);
    }

}

