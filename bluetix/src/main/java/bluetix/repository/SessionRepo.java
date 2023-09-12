package bluetix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bluetix.model.Session;

public interface SessionRepo extends JpaRepository<Session, Long>{
	  @Query(value = "SELECT * FROM SESSION WHERE EVENT_ID IN (SELECT EVENT_ID FROM EVENT WHERE VENUE_ID = ?) AND END_TIME > CURDATE();", nativeQuery = true)
	  List<Session> findByVenueName(String venueName);
}
