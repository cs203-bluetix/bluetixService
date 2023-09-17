package bluetix.model;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bluetix.serializable.SessionId;
import bluetix.serializable.SessionTicketId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class SessionTicket {

    @EmbeddedId
    private SessionTicketId id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable=false, updatable=false)
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", insertable=false, updatable=false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id", insertable=false, updatable=false)
    @JoinColumn(name = "section_id", referencedColumnName = "section_id", insertable=false, updatable=false)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable=false, updatable=false)
    @JsonIgnoreProperties({"event_id"})
    private Ticket ticket;

    @Column(name = "numSeatsLeft")
    private int numSeatsLeft;
    
    @Column(name = "transactionAddr")
    private String transactionAddr;
}