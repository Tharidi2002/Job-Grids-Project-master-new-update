package lk.ijse.backend.service;

import lk.ijse.backend.dto.CompanyCategoriesDTO;
import lk.ijse.backend.dto.CategoryUpdateDTO;

import java.util.List;

public interface CompanyCategoriesService {
    CompanyCategoriesDTO saveCategory(CompanyCategoriesDTO companyCategoriesDTO);
//    CompanyCategoriesDTO getCategoryById(String id);
    List<CompanyCategoriesDTO> getAllCategories();
    CompanyCategoriesDTO updateCategory(int id, CategoryUpdateDTO updateDTO, String username);

    void deleteCategory(int id, String username);

}
