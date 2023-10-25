package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import bluetix.dto.SessionDTO;
import bluetix.model.Event;
import bluetix.model.Session;
import bluetix.repository.SessionRepo;

@SpringBootTest
class SessionServiceTest {

    @Mock
    private SessionRepo sessionRepo;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void testFindAll() {
        // Arrange
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        when(sessionRepo.findAll()).thenReturn(sessions);

        // Act
        List<Session> result = sessionService.findAll();

        // Assert
        assertEquals(sessions.size(), result.size());
        // Add more specific assertions based on your requirements
    }

    @Test
    void testSave() {
        // Arrange
        Session session = new Session();
        when(sessionRepo.save(session)).thenReturn(session);

        // Act
        Session result = sessionService.save(session);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your requirements
    }

    @Test
    void testSetTransAddr() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        String transAddr = "some_address";

        // Act
        sessionService.setTransAddr(eventId, sessionId, transAddr);

        // Assert
        // Verify that the sessionRepo.setTransAddr method is called with the correct parameters
        verify(sessionRepo).setTransAddr(eventId, sessionId, transAddr);
    }

    @Test
    void testCreateList() {
        // Arrange
        Event event = new Event();
        List<SessionDTO> sessionDTOList = Arrays.asList(
                new SessionDTO(new Date(0), new Date(2), new Date(1), "address1"),
                new SessionDTO(new Date(0), new Date(2), new Date(1), "address2")
        );

        // Mock the behavior of sessionRepo.save method
        when(sessionRepo.save(any(Session.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<Session> result = sessionService.createList(event, sessionDTOList);

        // Assert
        assertEquals(sessionDTOList.size(), result.size());
        // Add more specific assertions based on your requirements
    }

    // Add more test cases for other methods as needed
}

