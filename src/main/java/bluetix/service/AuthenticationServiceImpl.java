package bluetix.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bluetix.dao.request.SignUpRequest;
import bluetix.dao.request.SigninRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.model.Creator;
import bluetix.model.Customer;
import bluetix.model.User;
import bluetix.repository.UserRepo;
import bluetix.repository.UserTypeRepo;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final UserTypeRepo<Customer> customerRepo;
    private final UserTypeRepo<Creator> creatorRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signupCustomer(SignUpRequest request) {
        var user = Customer.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        customerRepo.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).email(request.getEmail()).role("CUSTOMER").build();
    }

    @Override
    public JwtAuthenticationResponse signupCreator(SignUpRequest request) {
        var user = Creator.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        creatorRepo.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).email(request.getEmail()).role("CREATOR").build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        try {
            authenticate(request.getEmail(), request.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).email(request.getEmail()).role(user.getDecriminatorValue()).build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}