package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.Event;

public interface EventRepo extends JpaRepository<Event, Long>{

}
