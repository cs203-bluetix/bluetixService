package bluetix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.User;


public interface UserRepo extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
