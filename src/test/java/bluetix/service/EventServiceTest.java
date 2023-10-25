package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import bluetix.dto.EventDTO;
import bluetix.model.Creator;
import bluetix.model.Event;
import bluetix.model.Venue;
import bluetix.repository.EventRepo;
import bluetix.repository.UserRepo;
import bluetix.repository.VenueRepo;

@SpringBootTest
class EventServiceTest {

    @MockBean
    private EventRepo eventRepo;

    @MockBean
    private VenueRepo venueRepo;

    @MockBean
    private UserRepo userRepo;

    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepo, venueRepo, userRepo);
    }

    @Test
    void testGetEventById() {
        // Arrange
        Long eventId = 1L;
        Event expectedEvent = new Event();
        when(eventRepo.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        // Act
        Event result = eventService.getEventById(eventId);

        // Assert
        assertEquals(expectedEvent, result);
    }

    @Test
    void testCreateEventWithVenueId() throws Exception {
        // Arrange
        EventDTO eventDTO = new EventDTO();
        Long venueId = 1L;
        Long userId = 2L;
        Venue mockVenue = new Venue();
        Creator mockCreator = new Creator();
        Event expectedEvent = new Event();
        when(venueRepo.findById(venueId)).thenReturn(Optional.of(mockVenue));
        when(userRepo.findById(userId)).thenReturn(Optional.of(mockCreator));
        when(eventRepo.save(any(Event.class))).thenReturn(expectedEvent);

        // Act
        Event result = eventService.createEventWithVenueId(eventDTO, venueId, userId);

        // Assert
        assertEquals(expectedEvent, result);
    }

    @Test
    void testUpdateEvent() {
        // Arrange
        Event mockEvent = new Event();
        when(eventRepo.save(any(Event.class))).thenReturn(mockEvent);

        // Act
        Event result = eventService.updateEvent(mockEvent);

        // Assert
        assertEquals(mockEvent, result);
    }

    @Test
    void testDeleteEventById() {
        // Arrange
        Long eventId = 1L;

        // Act
        eventService.deleteEventById(eventId);

        // Assert
        verify(eventRepo).deleteById(eventId);
    }

    @Test
    void testFindByCreatorId() {
        // Arrange
        Long creatorId = 3L;
        List<Event> expectedEvents = new ArrayList<>();
        when(eventRepo.findByCreatorId(creatorId)).thenReturn(expectedEvents);

        // Act
        List<Event> result = eventService.findByCreatorId(creatorId);

        // Assert
        assertEquals(expectedEvents, result);
    }
}
