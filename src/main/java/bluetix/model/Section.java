package bluetix.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import bluetix.serializable.SectionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Section {

    @EmbeddedId
    private SectionId id;

    @Column(name = "category")
    private char category;

    @Column(name = "maxSeat")
    private int maxSeat;
    
    //Many Section to One Events
    @JsonView(Section.class)
    @ManyToOne
    @MapsId("venueId")
    @JoinColumn(name = "venue_id")
    private Venue venue;
    
    //One Section to Many Tickets
	@JsonIgnore
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}