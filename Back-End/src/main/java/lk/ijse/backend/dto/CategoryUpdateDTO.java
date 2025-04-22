package lk.ijse.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryUpdateDTO {
    @Size(min = 2, max = 20, message = "Name must be between 2 and 50 characters")
    private String name;

}
