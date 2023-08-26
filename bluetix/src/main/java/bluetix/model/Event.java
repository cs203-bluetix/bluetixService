package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketCategory> ticketCategories;
//    public User() {
//    	
//    }
    
//    public User(String email, String password, String name) {
//    	this.email = email;
//    	this.password = password;
//    	this.name = name;
//    }
}
