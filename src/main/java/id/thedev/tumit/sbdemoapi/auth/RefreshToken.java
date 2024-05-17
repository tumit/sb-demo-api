package id.thedev.tumit.sbdemoapi.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private int id;

    private String token;
    private Instant expiryData;
}
