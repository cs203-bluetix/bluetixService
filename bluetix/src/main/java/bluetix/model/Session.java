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
@IdClass(SessionId.class)
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_id")
	@SequenceGenerator(name = "session_id", sequenceName = "session_sequence", allocationSize = 1)
	@Column(name = "session_id")
	private Long sessionId;

	@Id
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
	private Event event;

    @Column(name = "date")
    private Date date;

    @Column(name = "start_time")
    private Time start_time;
    
    @Column(name = "end_time")
    private Time end_time;
    
    @Column(name = "transaction_addr")
    private String transaction_addr;

    //One Session to Many Tickets
	@JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Ticket> ticket;
	
	public Session(Date date, Time startTime, Time endTime, String transactionAddr, Event event) {
	    this.date = date;
	    this.start_time = startTime;
	    this.end_time = endTime;
	    this.transaction_addr = transactionAddr;
	    this.event = event;
	}
}