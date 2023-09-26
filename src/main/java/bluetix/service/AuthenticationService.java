package bluetix.service;

import bluetix.dao.request.SignUpRequest;
import bluetix.dao.request.SigninRequest;
import bluetix.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signupCustomer(SignUpRequest request);

    JwtAuthenticationResponse signupCreator(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}