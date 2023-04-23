package com.flumine.securityjwtauth20.services;

import com.flumine.securityjwtauth20.auth.AuthenticationResponse;
import com.flumine.securityjwtauth20.auth.RegisterRequest;
import com.flumine.securityjwtauth20.exceptions.ResourceAlreadyExistException;
import com.flumine.securityjwtauth20.exceptions.ResourceNotFoundException;
import com.flumine.securityjwtauth20.models.ERole;
import com.flumine.securityjwtauth20.models.RoleModel;
import com.flumine.securityjwtauth20.models.UserModel;
import com.flumine.securityjwtauth20.repositories.RoleRepository;
import com.flumine.securityjwtauth20.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public void existByUsername (String username)
    {
        if (userRepository.existsByUsernameIgnoreCase(username))
            throw new ResourceAlreadyExistException(String.format("User with username '%s' already exist", username));
    }

    public void existByEmail (String email)
    {
        if (userRepository.existsByEmailIgnoreCase(email))
            throw new ResourceAlreadyExistException(String.format("User with email '%s' already exist", email));
    }

    public UserModel checkExistUser(String username, String email)
    {
        UserModel user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        if (user != null && user.getEnabled()) throw new ResourceAlreadyExistException("Username already exist");
        user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        if (user != null && user.getEnabled()) throw new ResourceAlreadyExistException("Username already exist");
        if (user == null) user = new UserModel();
        return user;
    }

    public UserModel loadByUsernameOrEmail(String user) {
        return userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(user, user).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserModel loadByUsername(String user) {
        return userRepository.findByUsernameIgnoreCase(user).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
