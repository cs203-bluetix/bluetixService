package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Session;
import bluetix.serializable.SessionId;

public interface SessionRepo extends JpaRepository<Session, SessionId>{
	  @Query(value = "SELECT * FROM session WHERE event_id IN (SELECT event_id FROM event WHERE venue_id = ?) AND end_time > CURDATE();", nativeQuery = true)
	  List<Session> findByVenueId(Long venue_id);
	  

		@Query(value = "SELECT * FROM session WHERE event_id = ?;", nativeQuery = true)
		List<Session> findByEventId(Long eventId);

		
		@Query(value = "SELECT * FROM session WHERE event_id = ?1 AND session_id = ?2;", nativeQuery = true)
		Session findById(Long eventId, Long sessionId);
}
