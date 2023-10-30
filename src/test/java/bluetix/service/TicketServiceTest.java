package bluetix.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import bluetix.dto.TicketDTO;
import bluetix.model.Section;
import bluetix.model.Session;
import bluetix.model.Ticket;
import bluetix.repository.SectionRepo;
import bluetix.repository.SessionRepo;
import bluetix.repository.TicketRepo;
import bluetix.serializable.TicketId;

@SpringBootTest(properties = {"spring.config.location=classpath:application-test.properties"})
class TicketServiceTest {

    @Mock
    private TicketRepo ticketRepo;

    @Mock
    private SessionRepo sessionRepo;

    @Mock
    private SectionRepo sectionRepo;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testFindAll() {
        // Arrange
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepo.findAll()).thenReturn(tickets);

        // Act
        List<Ticket> result = ticketService.findAll();

        // Assert
        assertEquals(tickets.size(), result.size());
        // Add more specific assertions based on your requirements
    }

    @Test
    void testCreate() {
        // Arrange
        List<Session> sessions = Arrays.asList(new Session());
        List<TicketDTO> ticketDTOs = Arrays.asList(new TicketDTO());
        List<Section> sections = Arrays.asList(new Section());
        
        // Mock the behavior of sectionRepo.findByVenueId method
        when(sectionRepo.findByVenueId(anyLong())).thenReturn(sections);
        
        // Mock the behavior of ticketRepo.save method
        when(ticketRepo.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<Ticket> result = ticketService.create(sessions, ticketDTOs, 1L);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your requirements
    }


}

