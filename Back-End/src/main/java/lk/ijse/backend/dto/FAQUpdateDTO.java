package lk.ijse.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FAQUpdateDTO {
    @NotBlank(message = "Question is required")
    @Size(min = 10, max = 255, message = "Question must be between 10-255 characters")
    private String question;

    @NotBlank(message = "Answer is required")
    @Size(min = 10, max = 1000, message = "Answer must be between 10-1000 characters")
    private String answer;
}
