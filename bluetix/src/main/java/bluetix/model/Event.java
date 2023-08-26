package bluetix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true)
    private int id;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="description")
    @NotBlank
    private String password;

//    public User() {
//    	
//    }
    
//    public User(String email, String password, String name) {
//    	this.email = email;
//    	this.password = password;
//    	this.name = name;
//    }
}
