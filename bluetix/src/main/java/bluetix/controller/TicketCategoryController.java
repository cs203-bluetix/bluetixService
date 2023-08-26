package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bluetix.model.TicketCategory;
import bluetix.repository.TicketCategoryRepo;

@RestController
public class TicketCategoryController {

	@Autowired
	private TicketCategoryRepo ticketRepo;
	
	@PostMapping("/createTicketCategory")
	TicketCategory createUser(@RequestBody TicketCategory newTicketCategory) {
		return ticketRepo.save(newTicketCategory);
	}
	
	@GetMapping("/getAllTicketCategory")
	List<TicketCategory> getAllEvents(){
		return ticketRepo.findAll();
	}
}
