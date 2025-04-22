package lk.ijse.backend.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Component
public class AuthDTO {
    private String email;
    private String token;
    private String role;
}