package bluetix.serializable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SectionId implements Serializable {

    @Column(name = "venue_id")
    private Long venueId;

    @Column(name = "section_id")
    private String sectionId;
}