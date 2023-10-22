package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode(of = {"eventId"})
@Entity
@Getter @Setter @NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id", unique=true)
    private Long eventId;

    @Column(name="name")
    @NotBlank
    private String name;

    @Lob
    @Column(name="description", columnDefinition = "TEXT")
    @NotBlank
    private String description;

    @Lob
    @Column(name="faq", columnDefinition = "TEXT")
    @NotBlank
    private String faq;

    @Column(name="type")
    @NotBlank
    private String type;

    @Lob
    @Column(name="ticket_pricing", columnDefinition = "TEXT")
    @NotBlank
    private String ticket_pricing;
    
    @Lob
    @Column(name="admission_policy", columnDefinition = "TEXT")
    @NotBlank
    private String admission_policy;

    @Column(name="image_url")
    @NotBlank
    private String image_url;

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
	

//    One Event to Many TicketCat
//	@JsonIgnore
//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
//    private List<Ticket> ticket;
	
    public Event(Venue venue, Creator creator, String name, String description, String faq, String type, String ticket_pricing, String admission_policy, String image_url) {
        this.name = name;
        this.description = description;
        this.faq = faq;
        this.type = type;
        this.ticket_pricing = ticket_pricing;
        this.admission_policy = admission_policy;
        this.image_url = image_url;
        this.venue = venue;
        this.creator = creator;
    }
  
}
