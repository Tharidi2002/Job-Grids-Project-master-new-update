package lk.ijse.backend.controller;

import lk.ijse.backend.dto.CompanyDataDTO;
import lk.ijse.backend.service.CompanyService;
import lk.ijse.backend.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@CrossOrigin
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ResponseUtil> saveCompany(@ModelAttribute CompanyDataDTO companyDataDTO) {
        return ResponseEntity.ok(companyService.saveCompany(companyDataDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseUtil> updateCompany(@ModelAttribute CompanyDataDTO companyDataDTO) {
        return ResponseEntity.ok(companyService.updateCompany(companyDataDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseUtil> getCompany(@PathVariable String email) {
        return ResponseEntity.ok(companyService.getCompanyByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseUtil> deleteCompany(@PathVariable String email) {
        return ResponseEntity.ok(companyService.deleteCompany(email));
    }
}
