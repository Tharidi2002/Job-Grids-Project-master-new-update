package lk.ijse.backend.service.imple;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.ijse.backend.dto.CompanyCategoriesDTO;
import lk.ijse.backend.dto.CategoryUpdateDTO;
import lk.ijse.backend.entity.CompanyCategories;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.CompanyCategoriesRepo;
import lk.ijse.backend.repository.UserRepo;
import lk.ijse.backend.service.CompanyCategoriesService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CompanyCategoriesServiceImpl implements CompanyCategoriesService {
    @Autowired
    private final CompanyCategoriesRepo companyCategoriesRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final ModelMapper modelMapper;

    public CompanyCategoriesServiceImpl(CompanyCategoriesRepo companyCategoriesRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.companyCategoriesRepo = companyCategoriesRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public CompanyCategoriesDTO saveCategory(CompanyCategoriesDTO companyCategoriesDTO) {
        if (companyCategoriesRepo.existsByName(companyCategoriesDTO.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        CompanyCategoriesDTO category = new CompanyCategoriesDTO();
        category.setCategoryId(companyCategoriesDTO.getCategoryId());
        category.setName(companyCategoriesDTO.getName());

        CompanyCategories savedCategory = companyCategoriesRepo.save(modelMapper.map(category, CompanyCategories.class));
        return modelMapper.map(savedCategory, CompanyCategoriesDTO.class);
    }

    @Override
    public List<CompanyCategoriesDTO> getAllCategories() {
        List<CompanyCategories> categories = companyCategoriesRepo.findAll();
        return modelMapper.map(categories,
                new TypeToken<List<CompanyCategoriesDTO>>(){}.getType());
    }

    @Override
    public CompanyCategoriesDTO updateCategory(int id, CategoryUpdateDTO updateDTO, String username) {
        CompanyCategories category = companyCategoriesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        if (updateDTO.getName() != null && !updateDTO.getName().isBlank()) {
            if (companyCategoriesRepo.existsByNameAndIdNot(updateDTO.getName(), id)) {
                throw new IllegalArgumentException("Category name already exists");
            }
            category.setName(updateDTO.getName());
        }

        // 5. Save changes
        CompanyCategories updatedCategory = companyCategoriesRepo.save(category);
        return modelMapper.map(updatedCategory, CompanyCategoriesDTO.class);

    }

    @Override
    public void deleteCategory(int id, String username) {
        CompanyCategories category = companyCategoriesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // 2. Verify user is Admin
        User admin = userRepo.findByEmailAndRole(username, "ADMIN");
        if (admin == null) {
            throw new RuntimeException("Admin not found or invalid role");
        }

        companyCategoriesRepo.deleteById(id);

        companyCategoriesRepo.delete(category);
    }


}




