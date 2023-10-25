package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import bluetix.dto.EventDTO;
import bluetix.dto.SessionDTO;
import bluetix.dto.TicketDTO;
import bluetix.dto.TicketFormDTO;
import bluetix.model.Event;
import bluetix.model.Session;
import bluetix.model.Ticket;
import bluetix.service.CreatorService;
import bluetix.service.EventService;
import bluetix.service.SessionService;
import bluetix.service.TicketService;

@SpringBootTest
class CreatorServiceTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private TicketService ticketService;

    private CreatorService creatorService;

    @BeforeEach
    void setUp() {
        // Inject the mock dependencies into the CreatorService instance
        creatorService = new CreatorService(eventService, sessionService, ticketService);
    }

    @Test
    void createEventSessionAndTicket_Success() throws Exception {
        // Arrange
        TicketFormDTO ticketFormDTO = mockTicketFormDTO();
        Event createdEvent = new Event(); // Create a mock or real Event instance as needed
        List<Session> createdSessions = Arrays.asList(new Session(), new Session()); // Mock or real Session instances
        List<Ticket> createdTickets = Arrays.asList(new Ticket(), new Ticket()); // Mock or real Ticket instances

        // Mock service method calls
        when(eventService.createEventWithVenueId(any(EventDTO.class), anyLong(), anyLong())).thenReturn(createdEvent);
        when(sessionService.createList(any(Event.class), anyList())).thenReturn(createdSessions);
        when(ticketService.create(anyList(), anyList(), anyLong())).thenReturn(createdTickets);

        // Act
        List<Ticket> result = creatorService.createEventSessionAndTicket(ticketFormDTO);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Adjust the expected size based on the logic in your service

        // Verify that service methods were called with the expected parameters
        verify(eventService).createEventWithVenueId(
                eq(ticketFormDTO.getEventDTO()),
                anyLong(),
                anyLong());

        verify(sessionService).createList(createdEvent, ticketFormDTO.getSessionDTOList());
        verify(ticketService).create(createdSessions, ticketFormDTO.getTicketDTOList(),
                ticketFormDTO.getVenue_id());
    }

    // Add more test cases for edge cases, exceptions, etc.

    private TicketFormDTO mockTicketFormDTO() {
        // Using Mockito to mock TicketFormDTO
        TicketFormDTO mockDto = mock(TicketFormDTO.class);
        when(mockDto.getEventDTO()).thenReturn(mock(EventDTO.class));
        when(mockDto.getSessionDTOList()).thenReturn(Arrays.asList(mock(SessionDTO.class), mock(SessionDTO.class)));
        when(mockDto.getTicketDTOList()).thenReturn(Arrays.asList(mock(TicketDTO.class), mock(TicketDTO.class)));
        when(mockDto.getVenue_id()).thenReturn(1L);
        return mockDto;
    }
}
