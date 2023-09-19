package bluetix.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
	private String crypto_wallet;

	public Customer(String crypto_wallet) {
		this.crypto_wallet = crypto_wallet;
	}

	@Builder
	public Customer(String firstName, String lastName, String email, String password, String crypto_wallet) {
		super(firstName, lastName, email, password);
		this.crypto_wallet = crypto_wallet;
	}

	
}