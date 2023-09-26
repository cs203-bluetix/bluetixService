package bluetix.serializable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class SessionTicketId {

//    @Column(name = "event_id")
//    private Long eventId;
//
//    @Column(name = "session_id")
//    private Long sessionId;
//    
//    @Column(name = "venue_id")
//    private Long venueId;
//    
//    @Column(name = "section_id")
//    private String sectionId;
}
