package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.Location;

public interface LocationRepo extends JpaRepository<Location, Long>{

}
