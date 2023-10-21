package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bluetix.dto.SessionDTO;
import bluetix.model.Event;
import bluetix.model.Session;
import bluetix.model.User;
import bluetix.repository.SessionRepo;
import bluetix.serializable.SessionId;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class SessionService {
	private final SessionRepo sessionRepo;

	@Autowired
	private final QueuingService<User> queue;

	@Autowired
	public SessionService(SessionRepo sessionRepository) {
		this.sessionRepo = sessionRepository;
		this.queue = new QueuingService<>();
	}

	public List<Session> findAll() {
		return sessionRepo.findAll();
	}

	public Session save(Session session) {
		// Long eventId = session.getEvent().getEventId();
		// Long largest_session_id = sessionRepo.getLargestSessionByEventId(eventId);
		// if(largest_session_id == null) {
		// largest_session_id = 1L;
		// }
		// SessionId sessionId = new SessionId();
		// sessionId.setEventId(eventId);
		// sessionId.setSessionId(largest_session_id);
		return sessionRepo.save(session);
	}

	@Transactional
	public void setTransAddr(Long eventId, Long sessionId, String transAddr) {
		try {
			sessionRepo.setTransAddr(eventId, sessionId, transAddr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Session> createList(Event event, List<SessionDTO> sessionDTO) {
		List<Session> list = new ArrayList<>();
		for (SessionDTO item : sessionDTO) {

			Time startTime = new Time(item.getStart_time().getTime());
			Time endTime = new Time(item.getEnd_time().getTime());
			Session s = new Session(item.getDate(), startTime, endTime, item.getTransaction_addr(), event);
			try {
				list.add(sessionRepo.save(s));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
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

	public Session findById(Long eventId, Long sessionId) {
		return sessionRepo.findById(eventId, sessionId);
		// TODO Auto-generated method stub
	}

	// public Session updateSession(SessionId id, Session updatedSession) {
	// return sessionRepo.save(existingSession);
	// }

}
