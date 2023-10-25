package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;


import bluetix.dao.request.SigninRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.model.Customer;
import bluetix.repository.UserRepo;
import jakarta.servlet.http.Cookie;

import bluetix.dao.request.*;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.model.Creator;
import bluetix.model.Customer;
import bluetix.model.User;
import bluetix.repository.UserRepo;
import bluetix.repository.UserTypeRepo;
import jakarta.servlet.http.Cookie;

@SpringBootTest
class AuthenticationServiceImplTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserTypeRepo<Customer> customerRepo;

    @MockBean
    private UserTypeRepo<Creator> creatorRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(userRepo, customerRepo, creatorRepo, passwordEncoder,
                jwtService, authenticationManager);
    }

    @Test
    void signupCustomer_Success() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("John", "Doe", "john@example.com", "password123");

        // Mock repository behavior
        when(customerRepo.save(any(Customer.class))).thenReturn(new Customer());

        // Mock password encoding
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // Mock JWT token generation
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signupCustomer(signUpRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
    }

    // Add more test cases for signupCreator, signin, and edge cases as needed
    @Test
    void signupCreator_Success() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("Jane", "Doe", "jane@example.com", "password456");

        // Mock repository behavior
        when(creatorRepo.save(any(Creator.class))).thenReturn(new Creator());

        // Mock password encoding
        when(passwordEncoder.encode("password456")).thenReturn("hashedPassword");

        // Mock JWT token generation
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signupCreator(signUpRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        assertEquals("jane@example.com", response.getEmail());
        assertEquals("CREATOR", response.getRole());
    }

    @Test
    void signin_Success() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("john@example.com", "password123");

        // Mock repository behavior
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(new Customer()));

        // Mock password encoding
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // Mock JWT token generation
        when(jwtService.generateToken(any())).thenReturn("mockedToken");

        // Mock authentication manager
        Authentication authentication = new UsernamePasswordAuthenticationToken("john@example.com", "password123");
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        // Act
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        assertEquals("john@example.com", response.getEmail());
        // Assuming a Customer instance is returned
        assertEquals("CUSTOMER", response.getRole());
    }

    @Test
    void signin_InvalidCredentials() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("john@example.com", "invalidPassword");

        // Mock repository behavior
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(new Customer()));

        // Mock password encoding
        when(passwordEncoder.encode("invalidPassword")).thenReturn("hashedInvalidPassword");

        // Mock authentication manager to throw BadCredentialsException
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> authenticationService.signin(signinRequest));
    }

}
