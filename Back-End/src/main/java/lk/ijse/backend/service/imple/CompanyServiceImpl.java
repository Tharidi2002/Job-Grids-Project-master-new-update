package lk.ijse.backend.service.imple;

import lk.ijse.backend.dto.CompanyDTO;
import lk.ijse.backend.dto.CompanyDataDTO;
import lk.ijse.backend.entity.Company;
import lk.ijse.backend.entity.CompanyCategories;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.CompanyCategoriesRepo;
import lk.ijse.backend.repository.CompanyRepo;
import lk.ijse.backend.repository.UserRepo;
import lk.ijse.backend.service.CompanyService;
import lk.ijse.backend.service.FileStorageService;
import lk.ijse.backend.util.ResponseUtil;
import lk.ijse.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyCategoriesRepo companyCategoriesRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseUtil saveCompany(CompanyDataDTO companyDataDTO) {
        if (companyRepo.existsByUserEmail(companyDataDTO.getUserEmail())) {
            return new ResponseUtil(VarList.Bad_Request, "Company already exists for this user", null);
        }

        User user = userRepo.findByEmail(companyDataDTO.getUserEmail());
        if (user == null) {
            return new ResponseUtil(VarList.Not_Found, "User not found", null);
        }

        CompanyCategories category = companyCategoriesRepo.findById(companyDataDTO.getCompanyCategoryId()).orElse(null);
        if (category == null) {
            return new ResponseUtil(VarList.Not_Found, "Company category not found", null);
        }

        Company company = new Company();
        company.setCompanyName(companyDataDTO.getCompanyName());
        company.setLocation(companyDataDTO.getLocation());
        company.setEmail(companyDataDTO.getEmail());
        company.setContact(companyDataDTO.getContact());
        company.setFacebookUrl(companyDataDTO.getFacebookUrl());
        company.setWebsiteUrl(companyDataDTO.getWebsiteUrl());
        company.setCompanyCategory(category);
        company.setUser(user);

        // Handle logo upload
        if (companyDataDTO.getLogo() != null && !companyDataDTO.getLogo().isEmpty()) {
            try {
                String logoPath = fileStorageService.storeProfileImage(companyDataDTO.getLogo());
                company.setLogo(logoPath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Logo upload failed", null);
            }
        }

        companyRepo.save(company);
        CompanyDTO responseDto = modelMapper.map(company, CompanyDTO.class);
        return new ResponseUtil(VarList.Created, "Success", responseDto);
    }

    @Override
    public ResponseUtil updateCompany(CompanyDataDTO companyDataDTO) {
        Company existingCompany = companyRepo.findByUserEmail(companyDataDTO.getUserEmail());
        if (existingCompany == null) {
            return new ResponseUtil(VarList.Not_Found, "Company not found", null);
        }

        CompanyCategories category = companyCategoriesRepo.findById(companyDataDTO.getCompanyCategoryId()).orElse(null);
        if (category == null) {
            return new ResponseUtil(VarList.Not_Found, "Company category not found", null);
        }

        existingCompany.setCompanyName(companyDataDTO.getCompanyName());
        existingCompany.setLocation(companyDataDTO.getLocation());
        existingCompany.setEmail(companyDataDTO.getEmail());
        existingCompany.setContact(companyDataDTO.getContact());
        existingCompany.setFacebookUrl(companyDataDTO.getFacebookUrl());
        existingCompany.setWebsiteUrl(companyDataDTO.getWebsiteUrl());
        existingCompany.setCompanyCategory(category);

        // Handle logo update
        if (companyDataDTO.getLogo() != null && !companyDataDTO.getLogo().isEmpty()) {
            try {
                // Delete old logo if exists
                if (existingCompany.getLogo() != null) {
                    fileStorageService.deleteProfileImage(existingCompany.getLogo());
                }
                String logoPath = fileStorageService.storeProfileImage(companyDataDTO.getLogo());
                existingCompany.setLogo(logoPath);
            } catch (Exception e) {
                return new ResponseUtil(VarList.Internal_Server_Error, "Logo upload failed", null);
            }
        }

        companyRepo.save(existingCompany);
        CompanyDTO responseDto = modelMapper.map(existingCompany, CompanyDTO.class);
        return new ResponseUtil(VarList.OK, "Success", responseDto);
    }

    @Override
    public ResponseUtil getCompanyByEmail(String email) {
        Company company = companyRepo.findByUserEmail(email);
        if (company == null) {
            return new ResponseUtil(VarList.Not_Found, "Company not found", null);
        }
        CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);
        return new ResponseUtil(VarList.OK, "Success", companyDTO);
    }

    @Override
    public ResponseUtil deleteCompany(String email) {
        Company company = companyRepo.findByUserEmail(email);
        if (company == null) {
            return new ResponseUtil(VarList.Not_Found, "Company not found", null);
        }

        // Delete associated logo
        if (company.getLogo() != null) {
            fileStorageService.deleteProfileImage(company.getLogo());
        }

        companyRepo.delete(company);
        return new ResponseUtil(VarList.OK, "Success", null);
    }
}
