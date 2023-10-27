package bluetix.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bluetix.model.Event;
import bluetix.model.Ticket;
import bluetix.serializable.SessionId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TicketFormDTO {
	private EventDTO eventDTO;
	private List<SessionDTO> sessionDTOList;
	private List<TicketDTO> ticketDTOList;
	private Long venue_id;
    private MultipartFile file;
    private Long user_id;
}
