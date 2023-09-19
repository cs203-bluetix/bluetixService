package bluetix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bluetix.model.Creator;
import bluetix.model.Event;
import bluetix.model.User;
import bluetix.model.Venue;
import bluetix.repository.EventRepo;
import bluetix.repository.UserRepo;
import bluetix.repository.VenueRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import bluetix.exception.DataNotFoundException;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/events")
public class EventController {
	
	@Value("${event.image.path}")
	private String eventImagePath;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;
    
    @Autowired
    private VenueRepo venueRepo;

//    @PostMapping
//    Event createEvent(@RequestBody Event newEvent) {
//    	String eventName = newEvent.getName();
//        return eventRepo.save(newEvent);
//    }
	
	@PostMapping
	@Transactional
	public ResponseEntity<String> createEventAndUploadImage(
	    @RequestParam("name") String name,
        @RequestParam("description") String description,
	    @RequestParam("venue_id") String venue_id,
        @RequestParam("type") String type,
	    @RequestParam("faq") String faq,
        @RequestParam("ticket_pricing") String ticket_pricing,
	    @RequestParam("admission_policy") String admission_policy,
        @RequestParam("image_url") String image_url,
	    @RequestParam("file") MultipartFile file) {
    	
	    try {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please upload a file.");
	        }

	        String uploadDir = eventImagePath + File.separator + image_url;

	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            System.out.println("Making dir " + uploadDir + "....");
	            directory.mkdirs();
	        }

	        File destFile = new File(uploadDir + File.separator + file.getOriginalFilename());

	        if (destFile.exists()) {
	            file.transferTo(destFile);
	            return ResponseEntity.ok("File overwritten successfully.");
	        }

	        file.transferTo(destFile);
	        
	        Long tempUserId = Long.parseLong("1");
	        User user = userRepo.findById(tempUserId)
	                .orElseThrow(EntityNotFoundException::new);
	        if(!(user instanceof Creator)) {
	        	throw new Exception("User is not a Creator");
	        }
	        Creator creator = (Creator) user;
	        Venue venue = venueRepo.findById(Long.parseLong(venue_id))
	                .orElseThrow(EntityNotFoundException::new);
	        
	        Event newEvent = new Event(venue, creator, name, description, faq, type, ticket_pricing, admission_policy, image_url);

	        // Save the new event
	        eventRepo.save(newEvent);

	        return ResponseEntity.ok("Event and file uploaded successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload event or file.");
	    }
	}


    @GetMapping
    List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @GetMapping("/{id}")
    Event getEventById(@PathVariable Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));
    }

    @PutMapping("/{id}")
    Event updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // Update the properties of the event with the values from updatedEvent
        // Example: event.setTitle(updatedEvent.getTitle());

        return eventRepo.save(event);
    }

    @DeleteMapping("/{id}")
    void deleteEvent(@PathVariable Long id) {
        if (eventRepo.existsById(id)) {
            eventRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("Event not found");
        }
    }
}