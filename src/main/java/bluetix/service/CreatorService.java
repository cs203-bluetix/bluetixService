package bluetix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.dto.TicketFormDTO;
import bluetix.model.Event;
import bluetix.model.Section;
import bluetix.model.Session;
import bluetix.model.Ticket;
import bluetix.repository.EventRepo;
import bluetix.repository.SectionRepo;

@Service
public class CreatorService {
	

    private EventService eventService;

    private SessionService sessionService;
    
    private TicketService ticketService;

	
    @Autowired
    public CreatorService(EventService eventService, SessionService sessionService, TicketService ticketService) {
		this.eventService = eventService;
		this.sessionService = sessionService;
		this.ticketService = ticketService;
	}



	public List<Ticket> createEventSessionAndTicket(TicketFormDTO ticketFormDTO) throws Exception{
        	try {
        		//need to insert actual userId here
		    	Long tempUserId = (long) 1;
		        Event event;
				event = eventService.createEventWithVenueId(ticketFormDTO.getEventDTO(), ticketFormDTO.getVenue_id(), tempUserId);
		        List<Session> sessions = sessionService.createList(event, ticketFormDTO.getSessionDTOList());
		        return ticketService.create(sessions, ticketFormDTO.getTicketDTOList(), ticketFormDTO.getVenue_id());
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	return null;
    }
    
}
