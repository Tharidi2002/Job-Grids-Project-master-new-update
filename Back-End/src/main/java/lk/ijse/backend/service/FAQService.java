package lk.ijse.backend.service;

import lk.ijse.backend.dto.FAQDTO;
import lk.ijse.backend.dto.FAQUpdateDTO;

import java.util.List;

public interface FAQService {
    FAQDTO createFAQ(FAQDTO faqDTO, String adminEmail);
    List<FAQDTO> getAllFAQs();
    FAQDTO updateFAQ(int id, FAQUpdateDTO updateDTO, String adminEmail);
    void deleteFAQ(int id, String adminEmail);
}
