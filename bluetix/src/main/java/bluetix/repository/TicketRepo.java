package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Ticket;
import bluetix.serializable.TicketId;

public interface TicketRepo extends JpaRepository<Ticket, TicketId>{

	@Query(value = "SELECT * FROM ticket WHERE event_id = ?1", nativeQuery = true)
	List<Ticket> findByEventId(Long eventId);
	
	@Query(value = "SELECT * FROM ticket WHERE venue_id = ?1", nativeQuery = true)
	List<Ticket> findByVenueId(Long venueId);
	
	@Query(value = "SELECT * FROM ticket WHERE event_id = ?1 AND venue_id = ?2 AND section_id = ?3", nativeQuery = true)
	Ticket findById(Long eventId, Long venueId, String sectionId);
	
}