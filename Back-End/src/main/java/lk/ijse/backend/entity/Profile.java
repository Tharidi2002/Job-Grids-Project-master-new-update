package lk.ijse.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;

    @OneToOne
    private User user;

    private String image;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;

    @Transient // This means it won't be persisted in the database
    public LocalDate getJoinDate() {
        return this.user != null ? this.user.getJoinDate() : null;
    }
}
