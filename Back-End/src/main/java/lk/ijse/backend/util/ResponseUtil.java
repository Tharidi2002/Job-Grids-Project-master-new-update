package lk.ijse.backend.util;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseUtil {

    private int code;
    private String message;
    private Object data;
}
