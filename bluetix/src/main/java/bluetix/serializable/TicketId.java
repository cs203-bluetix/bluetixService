package bluetix.serializable;

import bluetix.model.Event;
import bluetix.model.Section;
import bluetix.model.Session;
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
    
    private Session session;
    
    private Section section;

}
