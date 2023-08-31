package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.INTEGER)
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique=true)
    private int id;

    @Column(name="email", unique=true)
    @NotBlank
    private String email;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="crypto_wallet")
	public String crypto_wallet;
//
//    @Column(name="password")
//    @NotBlank
//    private String password;

//    @Column(name="role")
//    @NotBlank
//    private int role;
    
	
}
