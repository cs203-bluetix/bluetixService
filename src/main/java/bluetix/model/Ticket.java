package bluetix.model;

import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bluetix.serializable.SessionId;
import bluetix.serializable.SessionTicketId;
import bluetix.serializable.TicketId;
import bluetix.service.SessionService;
import bluetix.service.TicketService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@IdClass(TicketId.class)
@Getter @Setter @NoArgsConstructor
public class Ticket {
	
	@Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable=false, updatable=false)
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", insertable=false, updatable=false)
    private Session session;

	@Id
    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id", insertable=false, updatable=false)
    @JoinColumn(name = "section_id", referencedColumnName = "section_id", insertable=false, updatable=false)
    private Section section;

    @Column(name="price")
    @NotBlank
    private double price;

//    @Column(name="transaction_addr")
//    @NotBlank
//    private String transaction_addr;

    @Column(name="num_seats_left")
    @NotBlank
    private int num_seats_left;
	
    public Ticket(Session session, Section section, double price, int numSeatsLeft
//    		, String transaction_addr
    		) {
        this.session = session;
        this.section = section;
        this.price = price;
        this.num_seats_left = numSeatsLeft;
//        this.transaction_addr = transaction_addr;
    }

}
