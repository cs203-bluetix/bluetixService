package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.model.Session;
import bluetix.repository.SessionRepo;
import bluetix.serializable.SessionId;

import java.util.List;
//not used
@Service
public class SessionService {
    private final SessionRepo sessionRepo;

    @Autowired
    public SessionService(SessionRepo sessionRepository) {
        this.sessionRepo = sessionRepository;
    }
    
    public List<Session> findAll() {
        return sessionRepo.findAll();
    }

    public Session save(Session session) {
        return sessionRepo.save(session);
    }
    
    // Retrieve sessions for a specific event
    public List<Session> findByEventId(Long eventId) {
    	return sessionRepo.findByEventId(eventId);
    }
    // Retrieve sessions for a specific event
    public List<Session> findByVenueId(Long venueId) {
    	return sessionRepo.findByVenueId(venueId);
    }

	public void deleteById(SessionId id) {
		sessionRepo.deleteById(id);
	}

	public Session findById(SessionId sessionId) {
    	return sessionRepo.findById(sessionId.getEventId(), sessionId.getSessionId());
		// TODO Auto-generated method stub
	}

//	public Session updateSession(SessionId id, Session updatedSession) {
//		return sessionRepo.save(existingSession);
//	}

}
