package com.flumine.securityjwtauth20.auth;

import com.flumine.securityjwtauth20.models.DeviceModel;
import com.flumine.securityjwtauth20.models.UserModel;
import com.flumine.securityjwtauth20.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
        response.sendRedirect("http://localhost/");
    }



  @GetMapping("/refresh_token")
  public ResponseEntity<AuthenticationResponse> refresh_token(HttpServletRequest request) {

    return ResponseEntity.ok(service.create_tokens(service.get_user_from_request(request)));
  }

    @PostMapping("/add_device")
    public ResponseEntity<?> add_device(HttpServletRequest request, @RequestBody DeviceModel device) {

        UserModel user = service.get_user_from_request(request);
        device.setUserid(user.getId());
        service.add_device(device);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/get_device")
    public ResponseEntity<?> get_device(HttpServletRequest request) {

        UserModel user = service.get_user_from_request(request);
        List<DeviceModel> device = service.get_device(user.getId());


        return ResponseEntity.status(200).body(device);
    }

    @GetMapping("/add_type_device")
    public ResponseEntity<?> add_type_device(HttpServletRequest request, @RequestParam("device") Long device_id,
                                             @RequestParam("type") String type) {

//        UserModel user = service.get_user_from_request(request);
       service.add_type_device(device_id, type);


        return ResponseEntity.status(200).build();
    }

}

