package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Session;
import bluetix.model.SessionTicket;
import bluetix.serializable.SessionTicketId;

public interface SessionTicketRepo extends JpaRepository<SessionTicket, SessionTicketId>{

	@Query(value = "SELECT * FROM sessionticket WHERE event_id = ?1 AND session_id = ?2 AND venue_id = ?3 AND section_id = ?4;", nativeQuery = true)
	SessionTicket findById(Long eventId, Long sessionId, Long venueId, String sectonId);

	@Query(value = "SELECT * FROM sessionticket WHERE event_id = ?1 AND session_id = ?2", nativeQuery = true)
	List<SessionTicket> findBySessionId(Long eventId, Long sessionId);

	@Query(value = "SELECT * FROM sessionticket WHERE event_id = ?1", nativeQuery = true)
	List<SessionTicket> findByEventId(Long eventId);

	@Query(value = "SELECT * FROM sessionticket WHERE venue_id = ?1 AND section_id = ?2", nativeQuery = true)
	List<SessionTicket> findBySectionId(Long venueId, String sectionId);

	@Query(value = "SELECT * FROM sessionticket WHERE venue_id = ?1", nativeQuery = true)
	List<SessionTicket> findByVenueId(Long venueId);
	
	@Query(value = "DELETE * FROM sessionticket WHERE event_id = ?1 AND session_id = ?2 AND venue_id = ?3 AND section_id = ?4;", nativeQuery = true)
	List<SessionTicket> deleteById(Long eventId, Long sessionId, Long venueId, String sectonId);
}
