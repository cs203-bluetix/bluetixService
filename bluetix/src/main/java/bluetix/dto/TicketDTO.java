package bluetix.dto;

import java.util.List;

import bluetix.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TicketDTO {
	private char category;
	private double price;
}
