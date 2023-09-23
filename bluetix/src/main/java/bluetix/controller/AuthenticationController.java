package bluetix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bluetix.dao.request.SigninRequest;
import bluetix.dao.request.SignUpRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup/customer")
    public ResponseEntity<JwtAuthenticationResponse> signupCustomer(@RequestBody SignUpRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signupCustomer(request);
        Cookie token = new Cookie("jwt", responseBody.getToken());
        token.setHttpOnly(true);
        //token.setSecure(true);
        response.addCookie(token);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/signup/creator")
    public ResponseEntity<JwtAuthenticationResponse> signupCreator(@RequestBody SignUpRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signupCreator(request);
        Cookie token = new Cookie("jwt", responseBody.getToken());
        token.setHttpOnly(true);
        //token.setSecure(true);
        response.addCookie(token);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signin(request);
        Cookie token = new Cookie("jwt", responseBody.getToken());
        token.setHttpOnly(true);
        //token.setSecure(true);
        response.addCookie(token);
        return ResponseEntity.ok(responseBody);
    }
}