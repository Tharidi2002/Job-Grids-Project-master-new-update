package lk.ijse.backend.controller;

import lk.ijse.backend.dto.ChatRequestDTO;
import lk.ijse.backend.dto.ChatResponseDTO;
import lk.ijse.backend.entity.ChatQuestion;
import lk.ijse.backend.service.imple.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatServiceImpl chatServiceImpl;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(chatServiceImpl.getAllCategories());
    }

    @PostMapping("/query")
    public ResponseEntity<ChatResponseDTO> handleQuery(
            @RequestBody ChatRequestDTO request,
            @RequestHeader(value = "X-Session-ID", required = false) String sessionId) {
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
        }
        return ResponseEntity.ok(chatServiceImpl.handleUserQuery(request.getQuestion(), sessionId));
    }

    @GetMapping("/admin/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChatQuestion>> getAllQuestions() {
        return ResponseEntity.ok(chatServiceImpl.getAllActiveQuestions());
    }

    @PostMapping("/admin/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChatQuestion> createQuestion(@RequestBody ChatQuestion question) {
        return ResponseEntity.ok(chatServiceImpl.createQuestion(question));
    }

    @PutMapping("/admin/questions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChatQuestion> updateQuestion(
            @PathVariable Long id,
            @RequestBody ChatQuestion question) {
        return ResponseEntity.ok(chatServiceImpl.updateQuestion(id, question));
    }

    @DeleteMapping("/admin/questions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        chatServiceImpl.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
