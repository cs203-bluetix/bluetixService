package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bluetix.model.Event;
import bluetix.repository.EventRepo;

@RestController
public class EventController {

	@Autowired
	private EventRepo eventRepo;
	
	@PostMapping("/createUser")
	Event createUser(@RequestBody Event newEvent) {
		return eventRepo.save(newEvent);
	}
	
	@GetMapping("/getAllUsers")
	List<Event> getAllEvents(){
		return eventRepo.findAll();
	}
}
