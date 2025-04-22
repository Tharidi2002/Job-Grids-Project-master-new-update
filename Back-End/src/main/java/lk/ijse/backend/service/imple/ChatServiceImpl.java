package lk.ijse.backend.service.imple;

import lk.ijse.backend.dto.ChatResponseDTO;
import lk.ijse.backend.entity.ChatInteraction;
import lk.ijse.backend.entity.ChatQuestion;
import lk.ijse.backend.repository.ChatInteractionRepo;
import lk.ijse.backend.repository.ChatQuestionRepo;
import lk.ijse.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatQuestionRepo questionRepo;
    private final ChatInteractionRepo interactionRepo;


    public List<ChatQuestion> getAllActiveQuestions() {
        return questionRepo.findByIsActiveTrue();
    }

    public List<String> getAllCategories() {
        return questionRepo.findDistinctCategories();
    }

    public ChatQuestion createQuestion(ChatQuestion question) {
        return questionRepo.save(question);
    }

    public ChatQuestion updateQuestion(Long id, ChatQuestion question) {
        ChatQuestion existing = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        existing.setQuestion(question.getQuestion());
        existing.setAnswer(question.getAnswer());
        existing.setCategory(question.getCategory());
        existing.setActive(question.isActive());
        return questionRepo.save(existing);
    }

    public void deleteQuestion(Long id) {
        questionRepo.deleteById(id);
    }

    public ChatResponseDTO handleUserQuery(String question, String sessionId) {
        // Try to find exact match first
        Optional<ChatQuestion> exactMatch = questionRepo
                .findByQuestionContainingIgnoreCaseAndIsActiveTrue(question)
                .stream()
                .filter(q -> q.getQuestion().equalsIgnoreCase(question))
                .findFirst();

        if (exactMatch.isPresent()) {
            saveInteraction(sessionId, question, exactMatch.get().getAnswer(), true);
            return new ChatResponseDTO(exactMatch.get().getAnswer(), true);
        }

        // Try to find similar questions
        List<ChatQuestion> similarQuestions = questionRepo
                .findByQuestionContainingIgnoreCaseAndIsActiveTrue(question);

        if (!similarQuestions.isEmpty()) {
            saveInteraction(sessionId, question, "Did you mean: " +
                    similarQuestions.stream()
                            .limit(3)
                            .map(ChatQuestion::getQuestion)
                            .collect(Collectors.joining(", ")), false);
            return new ChatResponseDTO("Did you mean: " +
                    similarQuestions.stream()
                            .limit(3)
                            .map(ChatQuestion::getQuestion)
                            .collect(Collectors.joining(", ")), false);
        }

        saveInteraction(sessionId, question, "Sorry, I couldn't find an answer to your question.", false);
        return new ChatResponseDTO("Sorry, I couldn't find an answer to your question.", false);
    }

    private void saveInteraction(String sessionId, String question, String answer, boolean foundMatch) {
        ChatInteraction interaction = new ChatInteraction();
        interaction.setSessionId(sessionId);
        interaction.setQuestion(question);
        interaction.setAnswer(answer);
        interaction.setFoundMatch(foundMatch);
        interactionRepo.save(interaction);
    }
}
