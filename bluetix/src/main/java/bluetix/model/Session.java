package bluetix.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bluetix.serializable.SessionId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Session {

    @EmbeddedId
    private SessionId id;

    @Column(name = "date")
    private Date date;

    @Column(name = "start_time")
    private Time start_time;
    
    @Column(name = "end_time")
    private Time end_time;

    //Many sessions to One Events
    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    //One Session to Many Tickets
	@JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<SessionTicket> sessionTicket;
	
}