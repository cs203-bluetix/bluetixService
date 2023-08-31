package bluetix.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Creator extends User {
}