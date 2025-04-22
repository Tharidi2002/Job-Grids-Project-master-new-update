package lk.ijse.backend.repository;

import lk.ijse.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Company findByUserEmail(String email);
    boolean existsByUserEmail(String email);
}
