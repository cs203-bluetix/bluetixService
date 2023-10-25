package bluetix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.dto.EventDTO;
import bluetix.model.Creator;
import bluetix.model.Event;
import bluetix.model.User;
import bluetix.model.Venue;
import bluetix.repository.EventRepo;
import bluetix.repository.UserRepo;
import bluetix.repository.VenueRepo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
    

    private EventRepo eventRepo;


    private VenueRepo venueRepo;
	

    private UserRepo userRepo;

    @Autowired
	public EventService(EventRepo eventRepo, VenueRepo venueRepo, UserRepo userRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
        this.userRepo = userRepo;
    }

    //Basic Event CRUD
    public Event getEventById(Long eventId) {
        return eventRepo.findById(eventId).orElse(null);
    }

    public Event createEventWithVenueId(EventDTO eventDTO, Long venue_id, Long user_id) throws Exception {
    	Venue venue = venueRepo.findById(venue_id).orElse(null);
        Creator creator = (Creator) userRepo.findById(user_id)
                .orElseThrow(EntityNotFoundException::new);
//        if(user.getDecriminatorValue().equals("CUSTOMER")) {
//        	System.out.println("Suss..Imposter!");
//        	throw new Exception("CUSTOMER is not a Creator");
//        }
        // Creator creator = (Creator) user;
        Event newEvent = new Event(venue, creator, eventDTO.getName(), eventDTO.getDescription(), eventDTO.getFaq(), eventDTO.getType(), eventDTO.getTicket_pricing(), eventDTO.getAdmission_policy(), eventDTO.getImage_url()+".jpg");
        return eventRepo.save(newEvent);
    }

    public Event updateEvent(Event event) {
        return eventRepo.save(event);
    }

    public void deleteEventById(Long eventId) {
        eventRepo.deleteById(eventId);
    }
    
    //Creator's Event Settings
    public List<Event> findByCreatorId(Long creatorId) {
        return eventRepo.findByCreatorId(creatorId);
    }
    
}
