package bluetix.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="venue_id", unique=true)
    private int venueid;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="description")
    @NotBlank
    private String description;
    
    @Column(name="address")
    @NotBlank
    private String address;
    
    @Column(name="url")
    private String url;
    
    @Column(name="image")
    private String image;
}
