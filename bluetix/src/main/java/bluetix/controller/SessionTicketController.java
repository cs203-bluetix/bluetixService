package bluetix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import bluetix.model.SessionTicket;
import bluetix.repository.SessionTicketRepo;
import bluetix.serializable.SessionTicketId;
import bluetix.service.SessionService;
import bluetix.service.SessionTicketService;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sessiontickets")
public class SessionTicketController {
	
    private final SessionTicketService sessionTicketService;

    @Autowired
    public SessionTicketController(SessionTicketService sessionTicketService) {
        this.sessionTicketService = sessionTicketService;
    }

    @GetMapping("/")
    public List<SessionTicket> getAllSessionTickets() {
        return sessionTicketService.findAll();
    }

    // can be more proper by passing in request body but lazy
    @GetMapping("/{event_id}/{session_id}/{venue_id}/{section_id}")
    public SessionTicket getSessionTicketById(@PathVariable Long event_id, @PathVariable Long session_id, @PathVariable Long venue_id, @PathVariable String section_id) {
    	SessionTicketId id = new SessionTicketId();
    	id.setEventId(event_id);
    	id.setSessionId(session_id);
    	id.setVenueId(venue_id);
    	id.setSectionId(section_id);
    	
        return sessionTicketService.findById(id);
    }
    
    @GetMapping("/findByEvent/{event_id}/{session_id}")
    public List<SessionTicket> getSessionTicketBySessionId(@PathVariable Long event_id, @PathVariable Long session_id) {
        return sessionTicketService.findBySessionId(event_id, session_id);
    }
    
    @GetMapping("/findByEvent/{event_id}")
    public List<SessionTicket> getSessionTicketByEventId(@PathVariable Long event_id) {
        return sessionTicketService.findByEventId(event_id);
    }

    @GetMapping("/findByEvent/{venue_id}/{section_id}")
    public List<SessionTicket> getSessionTicketBySectionId(@PathVariable Long venue_id, @PathVariable String section_id) {
        return sessionTicketService.findBySectionId(venue_id, section_id);
    }
    
    @GetMapping("/findByVenue/{event_id}")
    public List<SessionTicket> getSessionTicketByVenueId(@PathVariable Long event_id) {
        return sessionTicketService.findByVenueId(event_id);
    }

    @PostMapping("/")
    public SessionTicket createSessionTicket(@RequestBody SessionTicket sessionTicket) {
        return sessionTicketService.save(sessionTicket);
    }

//    @PutMapping("/{id}")
//    public SessionTicket updateSessionTicket(@PathVariable Long id, @RequestBody SessionTicket sessionTicket) {
//        sessionTicket.setSessionTicketId(id);
//        return sessionTicketService.save(sessionTicket);
//    }

    @DeleteMapping("/{event_id}/{session_id}/{venue_id}/{section_id}")
    public void deleteSessionTicket(@PathVariable Long event_id, @PathVariable Long session_id, @PathVariable Long venue_id, @PathVariable String section_id) {
    	SessionTicketId id = new SessionTicketId();
    	id.setEventId(event_id);
    	id.setSessionId(session_id);
    	id.setVenueId(venue_id);
    	id.setSectionId(section_id);
    	
        sessionTicketService.deleteById(id);
    }
}
