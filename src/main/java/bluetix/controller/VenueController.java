package bluetix.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import bluetix.exception.DataNotFoundException;
import bluetix.model.Venue;
import bluetix.repository.VenueRepo;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueRepo venueRepo;

    @GetMapping("/{id}")
    Venue getLocationById(@PathVariable Long id) {
        Optional<Venue> venue = venueRepo.findById(id);

        if (venue.isPresent()) {
            return venue.get();
        } else {
            throw new DataNotFoundException("Venue not found");
        }
    }

    @PostMapping("/createVenue")
    Venue createVenue(@RequestBody Venue newVenue) {
        return venueRepo.save(newVenue);
    }

    @GetMapping
    List<Venue> getAllVenues() {
        return venueRepo.findAll();
    }

    @PutMapping("/updateVenue/{id}")
    Venue updateVenue(@PathVariable Long id, @RequestBody Venue updatedVenue) {
        Venue venue = venueRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Venue not found"));

        // Update the properties of the location with the values from updatedLocation
        // Example: location.setName(updatedLocation.getName());
        // Dont really need

        return venueRepo.save(venue);
    }

    @DeleteMapping("/deleteVenue/{id}")
    void deleteVenue(@PathVariable Long id) {
        if (venueRepo.existsById(id)) {
        	venueRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("Venue not found");
        }
    }
}