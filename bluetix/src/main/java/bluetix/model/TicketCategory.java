package bluetix.model;

import java.util.*;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class TicketCategory {
	@EmbeddedId
    @Column(name="catId")
    @NotBlank
    private int catId;

    @Embedded 
    @NotBlank
    private Event event;

    @Column(name="price")
    @NotBlank
    private double price;

    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "seats", joinColumns = @JoinColumn(name = "seats_id"))
    @Column(name = "seats", nullable = false)
    private List<Integer> seats = new ArrayList<>();

}
