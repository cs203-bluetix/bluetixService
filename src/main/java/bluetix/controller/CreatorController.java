package bluetix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bluetix.dto.TicketFormDTO;
import bluetix.exception.DataNotFoundException;
import bluetix.model.Event;
import bluetix.model.User;
import bluetix.model.Venue;
import bluetix.repository.EventRepo;
import bluetix.service.CreatorService;
import bluetix.service.SessionService;
import bluetix.service.StorageService;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/creators")
public class CreatorController {

    @Autowired
    private CreatorService creatorService;
    
    @Autowired
    private StorageService storageService;
    
    @Autowired
    private EventRepo eventRepo;

    @Transactional
    @RequestMapping(value = "/createEventSessionAndTicket", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<String> createEventSessionAndTicket(
            @RequestPart("ticketFormDTO") TicketFormDTO ticketFormDTO,
            @RequestPart("file") MultipartFile file
        ) {    	System.out.println("Creating Event, Session, Ticket...");
        try {
            creatorService.createEventSessionAndTicket(ticketFormDTO);
            storageService.uploadFile(file, ticketFormDTO.getEventDTO().getImage_url(), "events");
            System.out.println("Event, Session, Ticket Successfully Created!");
            return ResponseEntity.ok("Event and Ticket created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Failed to create Event and Ticket: " + e.toString());
        }
    }
    
    
    @GetMapping("/{creatorId}")
    List<Event> getEventById(@PathVariable Long creatorId) {
    	List<Event> list = eventRepo.findByCreatorId(creatorId);
    	if(list.size() == 0) {
    		throw new DataNotFoundException("Event not found for Creator Id:" + creatorId);
    	}
        return list;
    }
}
