package bluetix.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bluetix.model.Ticket;
import bluetix.serializable.TicketId;
import bluetix.service.SessionService;
import bluetix.service.TicketService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ticket")
public class TicketController {
	@Autowired
    private TicketService ticketService;

//    @PostMapping
//    Ticket createTicketCategory(@RequestParam Ticket) {
//        return ticketService.save(newTicket);
//    }
    
//	@PostMapping
//	@Transactional
//	public ResponseEntity<String> createTickets(
//	    @RequestParam("category") String category,
//        @RequestParam("price") int price,
//	    @RequestParam("event_id") String event_id,
//        @RequestParam("venue_id") String venue_id) {
//    	
//	    try {
//	        if (ticketService.create(category.charAt(0), (double) price, Long.parseLong(venue_id), Long.parseLong(venue_id))) return ResponseEntity.ok("Tickets created successfully");
//	        return ResponseEntity.ok("No tickets inserted");
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	            .body("Failed to create tickets: " + e.getMessage());
//	    }
//	}
	

    @GetMapping
    List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{event_id}/{venue_id}/{section_id}")
    Ticket getTicketById(@PathVariable Long event_id, @PathVariable Long venue_id, @PathVariable String section_id) {
        return ticketService.findById(event_id, venue_id, section_id);
    }

    
    @GetMapping("/{session_id}")
    List<Ticket> getTicketBySessionId(@PathVariable Long session_id) {
        return ticketService.findBySessionId(session_id);
    }

    @GetMapping("/{event_id}/{session_id}")
    List<Ticket> getTicketByEventSessionId(@PathVariable Long event_id, @PathVariable Long venue_id) {
        return ticketService.findByEventSessionId(event_id, venue_id);
    }
    
    @GetMapping("/getUnique/{event_id}/{venue_id}")
    List<Long> getUniqueTicketByEventVenueId(@PathVariable Long event_id, @PathVariable Long venue_id) {
        return ticketService.findUniqueByEventVenueId(event_id, venue_id);
    }

//    @DeleteMapping("/{event_id}/{session_id}/{venue_id}/{section_id}")
//    void deleteTicket(@PathVariable Long event_id, @PathVariable Long session_id, @PathVariable Long venue_id, @PathVariable String section_id) {
//        TicketId id = new TicketId(event_id, session_id, venue_id, section_id);
//        ticketService.deleteById(id);
//    }
}