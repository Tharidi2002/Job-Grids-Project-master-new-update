package lk.ijse.backend.controller;

import lk.ijse.backend.dto.FAQDTO;
import lk.ijse.backend.dto.FAQUpdateDTO;
import lk.ijse.backend.dto.ResponseDTO;
import lk.ijse.backend.service.FAQService;
import lk.ijse.backend.util.JwtUtil;
import lk.ijse.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faqs")
@CrossOrigin
public class FAQController {
    @Autowired
    private FAQService faqService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> createFAQ(
            @RequestBody FAQDTO faqDTO,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String adminEmail = jwtUtil.getUsernameFromToken(token);

            FAQDTO createdFAQ = faqService.createFAQ(faqDTO, adminEmail);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "FAQ created", createdFAQ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllFAQs() {
        List<FAQDTO> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok()
                .body(new ResponseDTO(VarList.OK, "Success", faqs));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateFAQ(
            @PathVariable int id,
            @RequestBody FAQUpdateDTO updateDTO,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String adminEmail = jwtUtil.getUsernameFromToken(token);

            FAQDTO updatedFAQ = faqService.updateFAQ(id, updateDTO, adminEmail);
            return ResponseEntity.ok()
                    .body(new ResponseDTO(VarList.OK, "FAQ updated", updatedFAQ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteFAQ(
            @PathVariable int id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String adminEmail = jwtUtil.getUsernameFromToken(token);

            faqService.deleteFAQ(id, adminEmail);
            return ResponseEntity.ok()
                    .body(new ResponseDTO(VarList.OK, "FAQ deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, e.getMessage(), null));
        }
    }
}
