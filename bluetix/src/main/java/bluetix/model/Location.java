package bluetix.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id", unique=true)
    private int locationid;

    @Column(name="name")
    @NotBlank
    private String name;

    @Lob
    @Column(name="description")
    @NotBlank
    private String description;
    
    @Lob
    @Column(name="address")
    @NotBlank
    private String address;
    
    @Column(name="layoutUrl")
    @NotBlank
    private String layoutUrl;
}
