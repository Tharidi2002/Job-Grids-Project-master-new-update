package lk.ijse.backend.repository;

import lk.ijse.backend.entity.ChatInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatInteractionRepo extends JpaRepository<ChatInteraction,Long> {
    List<ChatInteraction> findBySessionId(String sessionId);
}
