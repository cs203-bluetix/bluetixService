package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique=true)
    private int userId;

    @Column(name="email", unique=true)
    @NotBlank
    private String email;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="password")
    @NotBlank
    private String password;

    @Column(name="role")
    @NotBlank
    private int role;
	
//    public User() {
//    	
//    }
    
//    public User(String email, String password, String name) {
//    	this.email = email;
//    	this.password = password;
//    	this.name = name;
//    }
}
