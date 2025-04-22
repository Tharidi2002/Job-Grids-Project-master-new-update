package lk.ijse.backend.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyDataDTO {
    private String companyName;
    private String location;
    private MultipartFile logo;
    private String email;
    private String contact;
    private String facebookUrl;
    private String websiteUrl;
    private int companyCategoryId;
    private String userEmail;
}
