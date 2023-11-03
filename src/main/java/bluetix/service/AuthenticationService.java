package bluetix.service;

import bluetix.dao.request.SignUpRequest;
import bluetix.dao.request.SignInRequest;
import bluetix.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signupCustomer(SignUpRequest request);

    JwtAuthenticationResponse signupCreator(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}