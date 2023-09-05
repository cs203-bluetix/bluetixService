package bluetix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import bluetix.model.Event;
import bluetix.repository.EventRepo;
import bluetix.exception.DataNotFoundException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepo eventRepo;

    @PostMapping
    Event createEvent(@RequestBody Event newEvent) {
        return eventRepo.save(newEvent);
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