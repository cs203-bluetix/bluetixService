package bluetix.model;

import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bluetix.serializable.SessionTicketId;
import bluetix.serializable.TicketId;
import bluetix.service.SessionService;
import bluetix.service.TicketService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
//@IdClass(TicketId.class)
@Getter @Setter @NoArgsConstructor
public class Ticket {

    @EmbeddedId
    private TicketId id;
	
//    @ManyToOne
//    @JoinColumn(name = "event_id", insertable=false, updatable=false)
//    private Event event;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable=false, updatable=false)
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", insertable=false, updatable=false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id", insertable=false, updatable=false)
    @JoinColumn(name = "section_id", referencedColumnName = "section_id", insertable=false, updatable=false)
    private Section section;


    @Column(name="price")
    @NotBlank
    private double price;

    @Column(name="num_seats_left")
    @NotBlank
    private int num_seats_left;

    //One Ticket to Many SessionTickets
//	@JsonIgnore
//    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
//    private List<SessionTicket> sessionTickets;
	

}
