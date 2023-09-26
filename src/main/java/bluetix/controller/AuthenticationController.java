package bluetix.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*")
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
        Cookie token = new Cookie("jwt", responseBody.getToken());
        token.setHttpOnly(true);
        // token.setSecure(true);
        response.addCookie(token);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/signup/creator")
    public ResponseEntity<JwtAuthenticationResponse> signupCreator(@RequestBody SignUpRequest request,
            HttpServletResponse response) {
        JwtAuthenticationResponse responseBody = authenticationService.signupCreator(request);
        Cookie token = new Cookie("jwt", responseBody.getToken());
        token.setHttpOnly(true);
        // token.setSecure(true);
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
        // token.setSecure(true);
        response.addCookie(token);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/validateJwt")
    @ResponseBody
    public ResponseEntity<JwtAuthenticationResponse> validateJwt(HttpServletRequest request,
            HttpServletResponse response) {
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        final String authHeader = request.getHeader("Authorization");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        if (jwt == null) {
            jwt = authHeader.substring(7);
        }
        String userEmail = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userEmail)) {
            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
                return ResponseEntity.ok(JwtAuthenticationResponse.builder().email(userEmail).role(user.getDecriminatorValue()).build());
            }
        }
        return ResponseEntity.badRequest().body(JwtAuthenticationResponse.builder().build());
    }
}