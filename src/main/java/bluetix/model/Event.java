package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@EqualsAndHashCode(of = {"eventId"})
@Entity
@Getter @Setter @NoArgsConstructor
public class Event {
    @JsonView(Event.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id", unique=true)
    private Long eventId;

    @JsonView(Event.class)
    @Column(name="name")
    @NotBlank
    private String name;

    @Lob
    @Column(name="description", columnDefinition = "TEXT")
    @NotBlank
    private String description;

    @JsonView(Event.class)
    @Lob
    @Column(name="faq", columnDefinition = "TEXT")
    @NotBlank
    private String faq;

    @JsonView(Event.class)
    @Column(name="type")
    @NotBlank
    private String type;

    @JsonView(Event.class)
    @Lob
    @Column(name="ticket_pricing", columnDefinition = "TEXT")
    @NotBlank
    private String ticket_pricing;

    @JsonView(Event.class)
    @Lob
    @Column(name="admission_policy", columnDefinition = "TEXT")
    @NotBlank
    private String admission_policy;

    @JsonView(Event.class)
    @Column(name="image_url")
    @NotBlank
    private String image_url;

    //One Creator can have many Events
    @JsonView(Event.class)
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Creator creator;
    
    //One Location to Many Events
    @JsonView(Event.class)
    @ManyToOne
    @JoinColumn(name = "venue_id", insertable = false, updatable = false)
    private Venue venue;

    //One Event to Many Sessions
    @JsonView(Event.class)
	@JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Session> sessions;
	
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
