package lk.ijse.backend.repository;

import lk.ijse.backend.entity.ChatQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatQuestionRepo extends JpaRepository<ChatQuestion,Long> {

    // Correct method name following Spring Data conventions
    @Query("SELECT DISTINCT q.category FROM ChatQuestion q")
    List<String> findDistinctCategories();

    // Keep your existing methods
    List<ChatQuestion> findByCategoryAndIsActiveTrue(String category);
    List<ChatQuestion> findByQuestionContainingIgnoreCaseAndIsActiveTrue(String query);

    List<ChatQuestion> findByIsActiveTrue();
}
