package lk.ijse.backend.dto;

import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDate joinDate;

}
