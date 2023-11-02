package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
        jwtService.jwtSigningKey = "9kqlMjb9YMXzI3IzYYCBxo5zcXtbkLH6twDC4MQzodyySJ2QnLerfu1wMnKobuMv"; // Set a valid secret key for testing
    }

    @Test
    void extractUserName_ValidToken_ReturnsUserName() {
        String token = createToken("user123");
        String userName = jwtService.extractUserName(token);

        assertEquals("user123", userName);
    }

    @Test
    void generateToken_ValidUserDetails_ReturnsToken() {
        UserDetails userDetails = new User("user123", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
    }

    @Test
    void isTokenValid_ValidTokenAndUserDetails_ReturnsTrue() {
        UserDetails userDetails = new User("user123", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = createToken("user123");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_ExpiredToken_ReturnsFalse() {
        UserDetails userDetails = new User("user123", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String expiredToken = createExpiredToken("user123");

        boolean isValid = true;
        try {
            jwtService.isTokenValid(expiredToken, userDetails);
        } catch (Exception e) {
            isValid = false;
        }
        

        assertFalse(isValid);
    }

    // Helper methods for creating tokens

    private String createToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        return jwtService.generateToken(claims, new User(username, "", Collections.emptyList()));
    }

    private String createExpiredToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

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
