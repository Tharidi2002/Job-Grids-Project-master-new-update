package lk.ijse.backend.dto.formDTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfileDataDTO {
    private String firstName;
    private String lastName;
    private MultipartFile image;
    private String email;
    private String contact;
    private String address;
}
