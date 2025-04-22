package lk.ijse.backend.service;

import lk.ijse.backend.dto.CompanyDataDTO;
import lk.ijse.backend.util.ResponseUtil;

public interface CompanyService {
    ResponseUtil saveCompany(CompanyDataDTO companyDataDTO);
    ResponseUtil updateCompany(CompanyDataDTO companyDataDTO);
    ResponseUtil getCompanyByEmail(String email);
    ResponseUtil deleteCompany(String email);
}
