package bluetix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.User;


public interface UserTypeRepo<T extends User> extends JpaRepository<T, Long>{
    Optional<T> findByEmail(String email);
}
