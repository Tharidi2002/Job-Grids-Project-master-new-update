package lk.ijse.backend.service.imple;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.backend.dto.FAQDTO;
import lk.ijse.backend.dto.FAQUpdateDTO;
import lk.ijse.backend.entity.FAQ;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.FAQRepo;
import lk.ijse.backend.repository.UserRepo;
import lk.ijse.backend.service.FAQService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepo faqRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FAQDTO createFAQ(FAQDTO faqDTO, String adminEmail) {
        User admin = userRepo.findByEmailAndRole(adminEmail, "ADMIN");
        if (admin == null) {
            throw new RuntimeException("Only admins can create FAQs");
        }

        FAQ faq = modelMapper.map(faqDTO, FAQ.class);
        faq.setCreatedBy(admin);
        FAQ savedFAQ = faqRepo.save(faq);
        return modelMapper.map(savedFAQ, FAQDTO.class);
    }

    @Override
    public List<FAQDTO> getAllFAQs() {
        return faqRepo.findAllByOrderByIdAsc().stream()
                .map(faq -> {
                    FAQDTO dto = modelMapper.map(faq, FAQDTO.class);
                    dto.setCreatedByEmail(faq.getCreatedBy().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FAQDTO updateFAQ(int id, FAQUpdateDTO updateDTO, String adminEmail) {
        User admin = userRepo.findByEmailAndRole(adminEmail, "ADMIN");
        if (admin == null) {
            throw new RuntimeException("Only admins can update FAQs");
        }

        FAQ faq = faqRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FAQ not found"));

        faq.setQuestion(updateDTO.getQuestion());
        faq.setAnswer(updateDTO.getAnswer());

        FAQ updatedFAQ = faqRepo.save(faq);
        FAQDTO dto = modelMapper.map(updatedFAQ, FAQDTO.class);
        dto.setCreatedByEmail(admin.getEmail());
        return dto;
    }

    @Override
    public void deleteFAQ(int id, String adminEmail) {
        User admin = userRepo.findByEmailAndRole(adminEmail, "ADMIN");
        if (admin == null) {
            throw new RuntimeException("Only admins can delete FAQs");
        }

        if (!faqRepo.existsById(id)) {
            throw new EntityNotFoundException("FAQ not found");
        }

        faqRepo.deleteById(id);
    }
}
