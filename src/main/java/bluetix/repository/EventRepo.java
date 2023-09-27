package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Event;
import bluetix.model.Ticket;

public interface EventRepo extends JpaRepository<Event, Long>{

	@Query(value = "SELECT * FROM event WHERE user_id = ?1", nativeQuery = true)
	List<Event> findByCreatorId(Long creatorId);
}
