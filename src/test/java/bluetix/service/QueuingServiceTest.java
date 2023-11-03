package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import bluetix.model.User;
import bluetix.repository.UserRepo;
import bluetix.service.QueuingService;

@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
class QueuingServiceTest {

    @MockBean
    private UserRepo userRepo;

    private QueuingService queuingService;

    @BeforeEach
    void setUp() {
        queuingService = new QueuingService(5, userRepo);
    }

    @Test
    void testEnqueue() {
        // Arrange
        User user = createUser(1);
        when(userRepo.save(any())).thenReturn(user);

        // Act
        queuingService.enqueue(user);

        // Assert
        assertTrue(queuingService.inQueueOrService(user));
    }

    @Test
    void testDequeue() {
        // Arrange
        User user = createUser(1);
        queuingService.enqueue(user);

        // Act
        User dequeuedUser = queuingService.dequeue();

        // Assert
        assertNotNull(dequeuedUser);
        assertFalse(queuingService.inQueueOrService(dequeuedUser));
    }

    @Test
    void testMoveToService() {
        // Arrange
        User user = createUser(1);
        queuingService.enqueue(user);

        // Act
        queuingService.moveToService();

        // Assert
        assertFalse(queuingService.getQueue().contains(user));
        assertTrue(queuingService.inService(user));
    }

    @Test
    void testDrainQueue() {
        // Arrange
        List<User> users = createUserList(5);
        for (User user : users) {
            queuingService.enqueue(user);
        }

        // Act
        queuingService.drainQueue();

        // Assert
        assertTrue(queuingService.getService().size() == 5);
        assertTrue(queuingService.getQueue().isEmpty());
    }

    @Test
    void testKickIdleUsers() {
        // Arrange
        User user = createUser(2);

        // Move the current time ahead by 6 minutes
        long currentTime = System.currentTimeMillis();
        long sixMinutesEarlier = currentTime - TimeUnit.MINUTES.toMillis(6);

        // Set the service time for the user in service to be 6 minutes ago
        queuingService.getService().put(user, sixMinutesEarlier);

        // Act
        queuingService.kickIdleUsers();

        // Assert
        assertFalse(queuingService.inService(user));
        assertTrue(queuingService.getQueue().isEmpty());
    }

    @Test
    void testKickFromQueue() {
        // Arrange
        List<User> users = createUserList(6);
        for (User user : users) {
            queuingService.enqueue(user);
            try{
            TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e){
                throw new RuntimeException();
            }
        }

        queuingService.drainQueue();
        queuingService.removeFromService(users.get(0));
        queuingService.removeFromService(users.get(1));
        queuingService.removeFromService(users.get(2));
        queuingService.removeFromService(users.get(3));
        queuingService.removeFromService(users.get(4));

        // Act
        queuingService.kickFromQueue();

        // Assert
        assertTrue(queuingService.getQueue().isEmpty());
        verify(userRepo, times(2)).save(users.get(5));
        assertEquals(1, users.get(5).getFailedPurchases());
    }

    @Test
    void testRemoveFromService() {
        // Arrange
        User user = createUser(1);
        queuingService.enqueue(user);
        queuingService.moveToService();

        // Act
        queuingService.removeFromService(user);

        // Assert
        assertFalse(queuingService.inService(user));
        assertEquals(0, user.getFailedPurchases());
    }

    @Test
    void testIsEmpty() {
        // Assert
        assertTrue(queuingService.isEmpty());
    }

    private User createUser(int id) {
        User user = new User();
        user.setId(id);
        user.setFirstName("User" + id);
        user.setLastName("Last" + id);
        return user;
    }

    private User createUser(int id, Long timeStamp, Integer failedPurchases) {
        User user = new User();
        user.setId(id);
        user.setFirstName("User" + id);
        user.setLastName("Last" + id);
        user.setTimeStamp(timeStamp);
        user.setFailedPurchases(failedPurchases);
        return user;
    }

    private List<User> createUserList(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            users.add(createUser(i, System.currentTimeMillis(), 0));
        }
        return users;
    }
}
