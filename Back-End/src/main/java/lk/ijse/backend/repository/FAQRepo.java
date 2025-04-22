package lk.ijse.backend.repository;

import lk.ijse.backend.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepo extends JpaRepository<FAQ, Integer> {
    List<FAQ> findAllByOrderByIdAsc();
}
