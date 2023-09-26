package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.Venue;

public interface VenueRepo extends JpaRepository<Venue, Long>{

}
