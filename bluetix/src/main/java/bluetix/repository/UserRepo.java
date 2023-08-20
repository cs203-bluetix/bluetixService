package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
