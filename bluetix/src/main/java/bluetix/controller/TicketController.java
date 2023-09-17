package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bluetix.exception.DataNotFoundException;
import bluetix.model.Ticket;
import bluetix.repository.TicketRepo;
import bluetix.serializable.TicketId;
import bluetix.service.TicketService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    Ticket createTicketCategory(@RequestBody Ticket newTicket) {
        return ticketService.save(newTicket);
    }

    @GetMapping
    List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{event_id}/{venue_id}/{section_id}")
    Ticket getTicketById(@PathVariable Long event_id, @PathVariable Long venue_id, @PathVariable String section_id) {
//    	TicketId id = new TicketId();
//    	id.setEventId(event_id);
//    	id.setVenueId(venue_id);
//    	id.setSectionId(section_id);
//        return ticketService.findById(id);
        return ticketService.findById(event_id, venue_id, section_id);
    }

//    @PutMapping("/{event_id}/{venue_id}/{section_id}")
//    Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
//        Ticket ticket = ticketService.findById(id)
//                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));
//
//        // Update the properties of the ticket category with the values from updatedTicketCategory
//        // Example: ticketCategory.setName(updatedTicketCategory.getName());
//        //Dont really need
//
//        return ticketService.save(ticket);
//    }

    @DeleteMapping("/{event_id}/{venue_id}/{section_id}")
    void deleteTicket(@PathVariable Long event_id, @PathVariable Long venue_id, @PathVariable String section_id) {
        TicketId id = new TicketId(event_id, venue_id, section_id);
        ticketService.deleteById(id);
    }
}