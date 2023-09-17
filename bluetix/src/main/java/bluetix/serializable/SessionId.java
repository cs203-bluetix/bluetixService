package bluetix.serializable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SessionId implements Serializable {

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "session_id")
    private Long sessionId;
}