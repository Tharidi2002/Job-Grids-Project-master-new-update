package lk.ijse.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO {
    private String roleId;
    @NotBlank(message = "Role name is required")
    private String roleName;
    private String privileges;


}
