package bluetix.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import bluetix.exception.DataNotFoundException;
import bluetix.model.Location;
import bluetix.repository.LocationRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepo locationRepo;

    @GetMapping("/get/{id}")
    Location getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationRepo.findById(id);

        if (location.isPresent()) {
            return location.get();
        } else {
            throw new DataNotFoundException("Location not found");
        }
    }

    @PostMapping("/createLocation")
    Location createLocation(@RequestBody Location newLocation) {
        return locationRepo.save(newLocation);
    }

    @GetMapping
    List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    @PutMapping("/updateLocation/{id}")
    Location updateLocation(@PathVariable Long id, @RequestBody Location updatedLocation) {
        Location location = locationRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Location not found"));

        // Update the properties of the location with the values from updatedLocation
        // Example: location.setName(updatedLocation.getName());
        // Dont really need

        return locationRepo.save(location);
    }

    @DeleteMapping("/deleteLocation/{id}")
    void deleteLocation(@PathVariable Long id) {
        if (locationRepo.existsById(id)) {
            locationRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("Location not found");
        }
    }
}