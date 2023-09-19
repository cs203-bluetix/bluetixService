package bluetix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bluetix.dao.request.SigninRequest;
import bluetix.dao.request.SignUpRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup/customer")
    public ResponseEntity<JwtAuthenticationResponse> signupCustomer(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signupCustomer(request));
    }

    @PostMapping("/signup/creator")
    public ResponseEntity<JwtAuthenticationResponse> signupCreator(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signupCreator(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}