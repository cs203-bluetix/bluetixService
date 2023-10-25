package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bluetix.model.User;
import bluetix.repository.UserRepo;

class UserServiceImplTest {

    @Mock
    private UserRepo userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(mockUser));

        // Act
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        // Arrange
        String nonExistingEmail = "nonexisting@example.com";
        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.userDetailsService().loadUserByUsername(nonExistingEmail));
    }
}
