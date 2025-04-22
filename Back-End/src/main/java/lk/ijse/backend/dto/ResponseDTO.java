package lk.ijse.backend.dto;

import lombok.*;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Component
public class ResponseDTO {
    private int code;
    private String message;
    private Object data;
}
