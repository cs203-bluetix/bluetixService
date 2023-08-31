package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.Session;

public interface SessionRepo extends JpaRepository<Session, Long>{

}
