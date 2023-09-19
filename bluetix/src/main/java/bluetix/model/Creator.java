package bluetix.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("CREATOR")
public class Creator extends User {
	@JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Event> events;

    private String crypto_wallet;

    @Builder
    public Creator(String firstName, String lastName, String email, String password, String crypto_wallet) {
        super(firstName, lastName, email, password);
        this.crypto_wallet = crypto_wallet;
    }

    
}