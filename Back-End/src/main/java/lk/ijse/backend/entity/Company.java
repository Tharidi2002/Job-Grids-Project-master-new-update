package lk.ijse.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CompanyId;
    private String CompanyName;
    private String Location;
    private String Logo;
    private String Website;
    private String Email;
    private String Contact;
    private String FacebookUrl;
    private String WebsiteUrl;

    @ManyToOne
    private CompanyCategories CompanyCategory;

    @OneToOne
    private User user;
}
