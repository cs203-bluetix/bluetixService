package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import bluetix.model.Session;
import bluetix.model.User;
import bluetix.repository.UserRepo;
import bluetix.service.QueueManagementService;
import bluetix.service.SessionService;
import bluetix.service.QueuingService;

@SpringBootTest
class QueueManagementServiceTest {

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserRepo userRepo;

    private QueueManagementService queueManagementService;

    @BeforeEach
    void setUp() {
        queueManagementService = new QueueManagementService(sessionService, userRepo);
    }

    @Test
    void testInitializeQueueForSession() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        Session mockSession = mock(Session.class);
        when(sessionService.findById(eventId, sessionId)).thenReturn(mockSession);

        // Act
        queueManagementService.initializeQueueForSession(eventId, sessionId);

        // Assert
        verify(sessionService).findById(eventId, sessionId);
        assertNotNull(queueManagementService.getSessionToQueueMap().get(mockSession));
    }

    @Test
    void testAddUserToQueue() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        User mockUser = mock(User.class);
        Session mockSession = mock(Session.class);
        QueuingService mockQueue = mock(QueuingService.class);
        when(sessionService.findById(eventId, sessionId)).thenReturn(mockSession);
        queueManagementService.getSessionToQueueMap().put(mockSession, mockQueue);

        // Act
        queueManagementService.addUserToQueue(eventId, sessionId, mockUser);

        // Assert
        verify(sessionService).findById(eventId, sessionId);
        verify(mockQueue).enqueue(mockUser);
    }

    @Test
    void testCheckUserInQueue() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        User mockUser = mock(User.class);
        Session mockSession = mock(Session.class);
        QueuingService mockQueue = mock(QueuingService.class);
        when(sessionService.findById(eventId, sessionId)).thenReturn(mockSession);
        when(mockQueue.inQueueOrService(mockUser)).thenReturn(true);
        queueManagementService.getSessionToQueueMap().put(mockSession, mockQueue);

        // Act
        boolean result = queueManagementService.checkUserInQueue(eventId, sessionId, mockUser);

        // Assert
        verify(sessionService).findById(eventId, sessionId);
        assertTrue(result);
    }

    @Test
    void testCheckUserInService() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        User mockUser = mock(User.class);
        Session mockSession = mock(Session.class);
        QueuingService mockQueue = mock(QueuingService.class);
        when(sessionService.findById(eventId, sessionId)).thenReturn(mockSession);
        when(mockQueue.inService(mockUser)).thenReturn(true);
        queueManagementService.getSessionToQueueMap().put(mockSession, mockQueue);

        // Act
        boolean result = queueManagementService.checkUserInService(eventId, sessionId, mockUser);

        // Assert
        verify(sessionService).findById(eventId, sessionId);
        assertTrue(result);
    }

    @Test
    void testLeaveQueue() {
        // Arrange
        Long eventId = 1L;
        Long sessionId = 2L;
        User mockUser = mock(User.class);
        Session mockSession = mock(Session.class);
        QueuingService mockQueue = mock(QueuingService.class);
        when(sessionService.findById(eventId, sessionId)).thenReturn(mockSession);
        queueManagementService.getSessionToQueueMap().put(mockSession, mockQueue);

        // Act
        queueManagementService.leaveQueue(eventId, sessionId, mockUser);

        // Assert
        verify(sessionService).findById(eventId, sessionId);
        verify(mockQueue).removeFromService(mockUser);
    }
}
