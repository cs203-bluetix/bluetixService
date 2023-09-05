package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter @Setter @NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id", unique=true)
    private int eventId;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="description")
    @NotBlank
    private String description;

    //One Creator can have many Events
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Creator creator;
    
    //One Location to Many Events
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    //One Event to Many Sessions
	@JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Session> sessions;
	

    //One Event to Many TicketCat
	@JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketCategory> ticketCategory;
  
}
