package bluetix.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import bluetix.dao.request.SignUpRequest;
import bluetix.dao.request.SignInRequest;
import bluetix.dao.response.JwtAuthenticationResponse;
import bluetix.repository.UserRepo;
import bluetix.service.JwtServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest(properties = {
                "spring.config.location=classpath:application-test.properties" }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {

        @LocalServerPort
        private int port;
        private final String baseURL = "http://localhost:";

        @Autowired
        private TestRestTemplate testRestTemplate;

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private JwtServiceImpl jwtService;

        @AfterEach
        void tearDown() {
                userRepo.deleteAll();
        }

        @Test
        public void signUpCustomer_Valid_Success() throws Exception {
                URI uri = new URI(baseURL + port + "/api/auth/signup/customer");
                SignUpRequest request = SignUpRequest.builder().firstName("yes").lastName("yes").email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                request,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());
        }

        @Test
        public void signUpCreator_Valid_Success() throws Exception {
                URI uri = new URI(baseURL + port + "/api/auth/signup/creator");
                SignUpRequest request = SignUpRequest.builder().firstName("yes").lastName("yes").email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                request,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());
        }

        @Test
        public void signUpCreatorCallTwice_Valid_Failure() throws Exception {
                URI uri = new URI(baseURL + port + "/api/auth/signup/creator");
                SignUpRequest request = SignUpRequest.builder().firstName("yes").lastName("yes").email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                request,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());
                result = testRestTemplate.postForEntity(uri,
                                request,
                                JwtAuthenticationResponse.class);

                assertEquals(403, result.getStatusCode().value());
        }

        @Test
        public void signIn_InvalidCredentials_Failure() throws Exception {
                URI uri = new URI(baseURL + port + "/api/auth/signin");
                SignInRequest signInRequest = SignInRequest.builder().email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                signInRequest,
                                JwtAuthenticationResponse.class);

                assertEquals(401, result.getStatusCode().value());
        }

        @Test
        public void signIn_Valid_Success() throws Exception {

                URI uri = new URI(baseURL + port + "/api/auth/signup/creator");
                SignUpRequest signUpRequest = SignUpRequest.builder().firstName("yes").lastName("yes")
                                .email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                signUpRequest,
                                JwtAuthenticationResponse.class);

                uri = new URI(baseURL + port + "/api/auth/signin");
                SignInRequest signInRequest = SignInRequest.builder().email("yes@yes.yes")
                                .password("yes").build();

                result = testRestTemplate.postForEntity(uri,
                                signInRequest,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());
        }

        @Test
        public void signInTwice_Valid_Success() throws Exception {

                URI uri = new URI(baseURL + port + "/api/auth/signup/creator");
                SignUpRequest signUpRequest = SignUpRequest.builder().firstName("yes").lastName("yes")
                                .email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                signUpRequest,
                                JwtAuthenticationResponse.class);

                uri = new URI(baseURL + port + "/api/auth/signin");
                SignInRequest signInRequest = SignInRequest.builder().email("yes@yes.yes")
                                .password("yes").build();

                result = testRestTemplate.postForEntity(uri,
                                signInRequest,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());

                String jwt = "jwt=" + createExpiredToken("yes@yes.yes");

                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", jwt);

                result = testRestTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(signInRequest, headers),
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());
        }

        @Test
        public void validateJwt_Valid_Success() throws Exception {
                URI uri = new URI(baseURL + port + "/api/auth/signup/creator");
                SignUpRequest request = SignUpRequest.builder().firstName("yes").lastName("yes").email("yes@yes.yes")
                                .password("yes").build();

                ResponseEntity<JwtAuthenticationResponse> result = testRestTemplate.postForEntity(uri,
                                request,
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());

                HttpHeaders headers = result.getHeaders();

                List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
                String jwt = "jwt=" + extractCookieValue(cookies, "jwt");

                headers = new HttpHeaders();
                headers.add("Cookie", jwt);

                uri = new URI(baseURL + port + "/api/auth/validateJwt");

                result = testRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
                                JwtAuthenticationResponse.class);

                assertEquals(200, result.getStatusCode().value());

        }

        private String extractCookieValue(List<String> cookies, String cookieName) {
                for (String cookie : cookies) {
                        if (cookie.contains(cookieName)) {
                                // Extract the value from the cookie string
                                return cookie.split("=")[1].split(";")[0];
                        }
                }
                return null; // Cookie not found
        }

        private String createToken(String username) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("role", Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER")));

                return jwtService.generateToken(claims, new User(username, "", Collections.emptyList()));
        }

        private String createExpiredToken(String username) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("role", Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER")));

                // Set an expiration time in the past to create an expired token
                return Jwts.builder()
                                .setClaims(claims)
                                .setSubject(username)
                                .setIssuedAt(new Date(System.currentTimeMillis() - 1000))
                                .setExpiration(new Date(System.currentTimeMillis() - 500))
                                .signWith(jwtService.getSigningKey(), SignatureAlgorithm.HS256)
                                .compact();
        }
}
