package bluetix.dto;

import java.util.List;

import bluetix.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventDTO {
	private String name;
	private String description;
	private String faq;
	private String type;
	private String ticket_pricing;
	private String admission_policy;
	private String image_url;
}
