package bluetix.model;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class TicketCategory {

    @Id
    @Column(name="cat_id", unique=true)
    private int catId;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="event_id", insertable=false, updatable=false)
    private Event event;

    @Column(name="price")
    @NotBlank
    private double price;

    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "seats", joinColumns = @JoinColumn(name = "seats_id"))
    @Column(name = "seats", nullable = false)
    private List<Integer> seats = new ArrayList<>();

}
