package bluetix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bluetix.model.TicketCategory;

public interface TicketCategoryRepo extends JpaRepository<TicketCategory, Long>{

}
