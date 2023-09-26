package bluetix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import bluetix.exception.DataNotFoundException;
import bluetix.model.Section;
import bluetix.model.Session;
import bluetix.repository.SectionRepo;
import bluetix.serializable.SectionId;
import bluetix.service.SectionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sections")
public class SectionController {

//    private final SectionRepo sectionRepo;

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/")
    public List<Section> getAllSections() {
        return sectionService.findAll();
    }
    
    //get by Venue_id
    @GetMapping("/{venue_id}")
    public List<Section> getSectionByVenue(@PathVariable Long venue_id) {
        return sectionService.getSectionsByVenue(venue_id);
    }
    
    //get numCategories by Venue_id
    @GetMapping("/getCategories/{venue_id}")
    public List<Character> getCategoriesByVenue(@PathVariable Long venue_id) {
        return sectionService.getCategoriesByVenue(venue_id);
    }

    @PostMapping("/")
    public Section createSection(@RequestBody Section section) {
        return sectionService.save(section);
    }
    
//    @PutMapping("/{venue_id}/{section_id}")
//    public Section updateSection(
//            @PathVariable Long venue_id,
//            @PathVariable Long section_id,
//            @RequestBody Section updatedSection) {
//        // Create a SectionId object to represent the composite key
//        SectionId id = new SectionId();
//        id.setVenueId(venue_id);
//        id.setSectionId(section_id);
//
//        // Call the service to update the section by its composite key
//        return sectionService.updateSection(id, updatedSection);
//    }


    @DeleteMapping("/{venue_id}/{section_id}")
    public void deleteSection(@PathVariable Long venue_id, @PathVariable String section_id) {
        SectionId id = new SectionId();
        id.setVenueId(venue_id);
        id.setSectionId(section_id);
        
        sectionService.deleteById(id);
    }
}
