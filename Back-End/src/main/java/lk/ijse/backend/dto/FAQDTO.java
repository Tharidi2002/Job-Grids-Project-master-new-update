package lk.ijse.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FAQDTO {
    private int id;
    private String question;
    private String answer;
    private String createdByEmail;
}
