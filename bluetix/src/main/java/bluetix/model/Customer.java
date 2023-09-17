package bluetix.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Customer extends User {
	private String crypto_wallet;

	public Customer(String crypto_wallet) {
		this.crypto_wallet = crypto_wallet;
	}

}