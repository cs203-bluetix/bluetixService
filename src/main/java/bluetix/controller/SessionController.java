package bluetix.controller;

import bluetix.exception.DataNotFoundException;
import bluetix.model.Session;
import bluetix.repository.SessionRepo;
import bluetix.serializable.SessionId;
import bluetix.service.SectionService;
import bluetix.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionService.findAll();
    }

//    @GetMapping("/{eventId}/{sessionId}")
//    public Session getSessionById(@PathVariable Long eventId, @PathVariable Long sessionId) {
//    	SessionId id = new SessionId();
//    	id.setEventId(eventId);
//    	id.setSessionId(sessionId);
//    	
//        return sessionService.findById(id);
//    }
    

//    Get existing sessions by venue id
    @GetMapping("/findByVenueId/{venue_id}")
    public List<Session> getSessionsByVenue(@PathVariable Long venue_id) {
        List<Session> sessions = sessionService.findByVenueId(venue_id);
        
        return sessions;
    }
    
//    Get existing sessions by event id
	  @GetMapping("/byEventId/{event_id}")
	  public List<Session> getSessionsByEventName(@PathVariable Long event_id) {
	      List<Session> sessions = sessionService.findByEventId(event_id);
	      
	      return sessions;
	  }

    @PostMapping
    public Session createSession(@RequestBody Session session) {
//        int eventId = session.getEvent().getEventId();
//        Long maxSessionId = sessionRepository.findMaxSessionIdByEventId(eventId);
//        Long nextSessionId = maxSessionId != null ? maxSessionId + 1 : 1;
//        session.setSessionId(nextSessionId);
        return sessionService.save(session);
    }

//
//    @PutMapping("/{eventId}/{sessionId}")
//    public Session updateSession(@PathVariable Long eventId, @PathVariable Long sessionId, @RequestBody Session sessionDetails) {
//    	SessionId id = new SessionId();
//    	id.setEventId(eventId);
//    	id.setSessionId(sessionId);
//    	Session session = sessionService.findById(id);
//
//        return sessionService.save(session);
//    }
//
//    @DeleteMapping("/{eventId}/{sessionId}")
//    public void deleteSession(@PathVariable Long eventId, @PathVariable Long sessionId) {
//    	SessionId id = new SessionId();
//    	id.setEventId(eventId);
//    	id.setSessionId(sessionId);
//    	
//    	sessionService.deleteById(id);
//    }
}