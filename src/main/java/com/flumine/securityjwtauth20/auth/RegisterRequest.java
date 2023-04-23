package com.flumine.securityjwtauth20.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
//    @NotBlank(message = "username should not be empty")
//    @Pattern(regexp = "/^[a-z0-9_-]{3,16}$/", message = "username can contain letters, numbers and underscores, length [3, 16]")
    private String username;
//    @Email
//    @NotBlank
    private String email;
//    @NotBlank
//    @Pattern(regexp = "/^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$/", message = "password should not be simple")
    private String password;
}
