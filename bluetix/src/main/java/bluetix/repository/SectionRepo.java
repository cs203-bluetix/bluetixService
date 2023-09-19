package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Section;
import bluetix.model.Session;
import bluetix.serializable.SectionId;

public interface SectionRepo extends JpaRepository<Section, SectionId> {
	 
	@Query(value = "SELECT * FROM section WHERE venue_id = ?;", nativeQuery = true)
	List<Section> findByVenueId(Long venueId);
	
	@Query(value = "SELECT DISTINCT category FROM section WHERE venue_id = ?;", nativeQuery = true)
	List<Character> findCategoriesByVenueId(Long venueId);
}
