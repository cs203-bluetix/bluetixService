package bluetix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bluetix.dto.TicketFormDTO;
import bluetix.model.Event;
import bluetix.model.User;
import bluetix.model.Venue;
import bluetix.repository.EventRepo;
import bluetix.service.CreatorService;
import bluetix.service.SessionService;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/creators")
public class CreatorController {

    @Autowired
    private CreatorService creatorService;

    @Transactional
    @RequestMapping(value = "/createEventSessionAndTicket", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<String> createEventSessionAndTicket(
            @RequestPart("ticketFormDTO") TicketFormDTO ticketFormDTO,
            @RequestPart("file") MultipartFile file
        ) {    	System.out.println("Creating Event, Session, Ticket...");
        try {
            creatorService.createEventSessionAndTicket(ticketFormDTO);
            System.out.println("Event, Session, Ticket Successfully Created!");
            return ResponseEntity.ok("Event and Ticket created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Failed to create Event and Ticket: " + e.getMessage());
        }
    }
    
    @GetMapping
    void test() {
        System.out.println("successfully called");
    }

}
