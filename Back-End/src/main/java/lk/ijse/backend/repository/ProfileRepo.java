package lk.ijse.backend.repository;

import jakarta.validation.constraints.Email;
import lk.ijse.backend.entity.Profile;
import lk.ijse.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Profile findByUserEmail(String email);
    Profile findByUser(User user);

    boolean existsByUserEmail(@Email(message = "Invalid Format") String email);
}
