package bluetix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.model.Section;
import bluetix.repository.SectionRepo;
import bluetix.serializable.SectionId;

import java.util.List;
//not used
@Service
public class SectionService {
    private final SectionRepo sectionRepo;

    @Autowired
    public SectionService(SectionRepo sectionRepository) {
        this.sectionRepo = sectionRepository;
    }
    
    public List<Section> findAll() {
        return sectionRepo.findAll();
    }

    public Section save(Section section) {
        return sectionRepo.save(section);
    }
    
    // Retrieve sections for a specific venue
    public List<Section> getSectionsByVenue(Long venueId) {
    	return sectionRepo.findByVenueId(venueId);
    }
    
    public List<Character> getCategoriesByVenue(Long venueId) {
    	return sectionRepo.findCategoriesByVenueId(venueId);
    }

	public void deleteById(SectionId id) {
		sectionRepo.deleteById(id);
	}

//	public Section updateSection(SectionId id, Section updatedSection) {
//		return sectionRepo.save(existingSection);
//	}

}
