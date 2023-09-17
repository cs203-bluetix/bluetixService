package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.model.Session;
import bluetix.model.SessionTicket;
import bluetix.repository.SessionRepo;
import bluetix.repository.SessionTicketRepo;
import bluetix.serializable.SessionId;
import bluetix.serializable.SessionTicketId;

import java.util.List;
//not used
@Service
public class SessionTicketService {
    private final SessionTicketRepo sessionTicketRepo;

    @Autowired
    public SessionTicketService(SessionTicketRepo sessionTicketRepo) {
        this.sessionTicketRepo = sessionTicketRepo;
    }
    
    public List<SessionTicket> findAll() {
        return sessionTicketRepo.findAll();
    }

    public SessionTicket save(SessionTicket sessionTicket) {
        return sessionTicketRepo.save(sessionTicket);
    }

	public SessionTicket findById(SessionTicketId id) {
    	return sessionTicketRepo.findById(id.getEventId(), id.getSessionId(), id.getVenueId(), id.getSectionId());
	}

    // Retrieve sessions for a specific session
    public List<SessionTicket> findBySessionId(Long eventId, Long sessionId) {
    	return sessionTicketRepo.findBySessionId(eventId, sessionId);
    }
    
    // Retrieve sessions for a specific event
    public List<SessionTicket> findByEventId(Long eventId) {
    	return sessionTicketRepo.findByEventId(eventId);
    }

    // Retrieve sessions for a specific session
    public List<SessionTicket> findBySectionId(Long eventId, String sectionId) {
    	return sessionTicketRepo.findBySectionId(eventId, sectionId);
    }
    
    // Retrieve sessions for a specific event
    public List<SessionTicket> findByVenueId(Long venueId) {
    	return sessionTicketRepo.findByVenueId(venueId);
    }

	public void deleteById(SessionTicketId id) {
		sessionTicketRepo.deleteById(id.getEventId(), id.getSessionId(), id.getVenueId(), id.getSectionId());
	}

//	public Session updateSession(SessionId id, Session updatedSession) {
//		return sessionTicketRepo.save(existingSession);
//	}

}
