package bluetix.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bluetix.model.Event;
import bluetix.model.Ticket;
import bluetix.serializable.SessionId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SessionDTO {
	private Date date;
	private Date end_time;
	private Date start_time;
	private String transaction_addr;
}
