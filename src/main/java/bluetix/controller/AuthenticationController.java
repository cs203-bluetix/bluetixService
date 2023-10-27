package bluetix.controller;

import java.time.Duration;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bluetix.dao.request.SigninRequest;
import bluetix.dao.request.SignUpRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.repository.UserRepo;
import bluetix.service.AuthenticationService;
import bluetix.service.JwtService;
import bluetix.service.JwtServiceImpl;
import bluetix.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepo userRepo;

    @PostMapping("/signup/customer")
    public ResponseEntity<JwtAuthenticationResponse> signupCustomer(@RequestBody SignUpRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signupCustomer(request);
        ResponseCookie resCookie = ResponseCookie.from("jwt", responseBody.getToken()).httpOnly(false)
        .maxAge(Duration.ofHours(10))
        .path("/")
        .httpOnly(true)
        .secure(false)
        .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", resCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseBody);
    }

    @PostMapping("/signup/creator")
    public ResponseEntity<JwtAuthenticationResponse> signupCreator(@RequestBody SignUpRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signupCreator(request);
        ResponseCookie resCookie = ResponseCookie.from("jwt", responseBody.getToken()).httpOnly(false)
        .maxAge(Duration.ofHours(10))
        .path("/")
        .httpOnly(true)
        .secure(false)
        .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", resCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseBody);
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity signin(@RequestBody SigninRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody;
        try {
            responseBody = authenticationService.signin(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        ResponseCookie resCookie = ResponseCookie.from("jwt", responseBody.getToken()).httpOnly(false)
        .maxAge(Duration.ofHours(10))
        .path("/")
        .httpOnly(true)
        .secure(false)
        .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", resCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseBody);
    }

    @PostMapping("/validateJwt")
    @ResponseBody
    public ResponseEntity<JwtAuthenticationResponse> validateJwt(HttpServletRequest request,
            HttpServletResponse response) {
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
      
        String userEmail = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userEmail)) {
            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var user = userRepo.findByEmail(userEmail)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
                return ResponseEntity.ok(
                        JwtAuthenticationResponse.builder().email(userEmail).role(user.getDecriminatorValue()).build());
            }
        }
        return ResponseEntity.badRequest().body(JwtAuthenticationResponse.builder().build());
    }
}
