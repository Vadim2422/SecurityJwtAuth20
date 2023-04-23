package com.flumine.securityjwtauth20.auth;

import com.flumine.securityjwtauth20.models.ERole;
import com.flumine.securityjwtauth20.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("Its work");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) throws MessagingException {
        service.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }



    @GetMapping("/confirm_email/{token}")
    public void confirm_email (
            @PathVariable String token, HttpServletResponse response) throws IOException {
        try {
            service.confirm_email(token);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        response.sendRedirect("");
    }



  @GetMapping("/refresh_token")
  public ResponseEntity<AuthenticationResponse> refresh_token(HttpServletRequest request) {

    return ResponseEntity.ok(service.create_tokens(service.get_user_from_request(request)));
  }
}

