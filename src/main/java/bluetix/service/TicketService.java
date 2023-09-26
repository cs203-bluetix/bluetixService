package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.dto.TicketDTO;
import bluetix.model.Section;
import bluetix.model.Session;
import bluetix.model.Ticket;
import bluetix.repository.SectionRepo;
import bluetix.repository.SessionRepo;
import bluetix.repository.TicketRepo;
import bluetix.serializable.TicketId;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//not used
@Service
public class TicketService {
	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private SectionRepo sectionRepo;
	
//	@Autowired
//	public TicketService(TicketRepo ticketRepo) {
//		this.ticketRepo = ticketRepo;
//	}

	public List<Ticket> findAll() {
		return ticketRepo.findAll();
	}

//    public Ticket save(Ticket session) {
//        return ticketRepo.save(session);
//    }

	@Transactional
	public List<Ticket> create(List<Session> sessions, List<TicketDTO> tickets, Long venue_id) {
		List<Section> sections = sectionRepo.findByVenueId(venue_id);
        try {
		List<Ticket> ret = new ArrayList<>();
		for(TicketDTO ticket: tickets) {		          
			for(Section section: sections) {         
				if(section.getCategory() == ticket.getCategory()) {      
					for(Session session: sessions) {         
						Ticket newTicket = new Ticket(session, section, ticket.getPrice(), (int) section.getMaxSeat());         
		                    ret.add(ticketRepo.save(newTicket));
		                }
					}
				}
			}
			return ret;
		} catch (Exception e) {
            System.err.println("Error saving Ticket: " + e.getMessage());
        }	
		return null;
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
