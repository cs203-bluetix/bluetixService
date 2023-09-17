package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.model.Ticket;
import bluetix.repository.TicketRepo;
import bluetix.serializable.TicketId;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
//not used
@Service
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }
    
    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    public Ticket save(Ticket session) {
        return ticketRepo.save(session);
    }

//	public Ticket findById(TicketId ticketId) {
//    	return ticketRepo.findById(ticketId.getEventId(), ticketId.getVenueId(), ticketId.getSectionId());
//	}
    
	public Ticket findById(Long event_id, Long venue_id, String section_id) {
	return ticketRepo.findById(event_id, venue_id, section_id);
}
	
    // Retrieve sessions for a specific event
    public List<Ticket> findByEventId(Long eventId) {
    	return ticketRepo.findByEventId(eventId);
    }
    // Retrieve sessions for a specific event
    public List<Ticket> findByVenueId(Long venueId) {
    	return ticketRepo.findByVenueId(venueId);
    }

	public void deleteById(TicketId id) {
		ticketRepo.deleteById(id);
	}

//	public Ticket updateTicket(TicketId id, Ticket updatedTicket) {
//        Optional<Ticket> optionalTicket = ticketRepo.findById(id);
//
//        if (optionalTicket.isPresent()) {
//            Ticket existingTicket = optionalTicket.get();
//            existingTicket.setPrice(updatedTicket.getPrice());
//            Ticket savedTicket = ticketRepo.save(existingTicket);
//
//            return savedTicket;
//        } else {
//            throw new EntityNotFoundException("Ticket not found with id: " + id.toString());
//        }
//	}

}
