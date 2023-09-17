package bluetix.serializable;

import bluetix.model.Event;
import bluetix.model.Section;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TicketId {

    @Column(name = "event_id")
    private Long eventId;
    
    @Column(name = "venue_id")
    private Long venueId;
    
    @Column(name = "section_id")
    private String sectionId;

//  private Event event;
//  private Section section;
//    private Long eventId;
//    private Long venueId;
//    private String sectionId;
	

}
