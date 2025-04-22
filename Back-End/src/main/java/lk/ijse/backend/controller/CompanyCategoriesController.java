package lk.ijse.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lk.ijse.backend.dto.CompanyCategoriesDTO;
import lk.ijse.backend.dto.CategoryUpdateDTO;
import lk.ijse.backend.dto.ResponseDTO;
import lk.ijse.backend.service.CompanyCategoriesService;
import lk.ijse.backend.util.JwtUtil;
import lk.ijse.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CompanyCategoriesController {
    @Autowired
    private final CompanyCategoriesService companyCategoriesService;
    @Autowired
    private final JwtUtil jwtUtil;

    public CompanyCategoriesController(CompanyCategoriesService companyCategoriesService, JwtUtil jwtUtil) {
        this.companyCategoriesService = companyCategoriesService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CompanyCategoriesDTO> createCategory(@RequestBody CompanyCategoriesDTO categoryDTO) {
        CompanyCategoriesDTO savedCategory = companyCategoriesService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }


    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','CompanyUser')")
    public ResponseEntity<ResponseDTO> getAllCategories() {
        List<CompanyCategoriesDTO> categories = companyCategoriesService.getAllCategories();
        return ResponseEntity.ok()
                .body(new ResponseDTO(VarList.OK, "Success", categories));
    }

    @PutMapping(path = "update",params = "id",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateCategory(
            @RequestParam ("id") int id,
            @Valid @RequestBody CategoryUpdateDTO updateDTO,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.getUsernameFromToken(token);

            CompanyCategoriesDTO updatedCategory = companyCategoriesService.updateCategory(id, updateDTO, username);

            return ResponseEntity.ok()
                    .body(new ResponseDTO(VarList.OK, "Category updated successfully", updatedCategory));

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, ex.getMessage(), null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, ex.getMessage(), null));
        }
    }

    @DeleteMapping(path = "delete",params = "id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory(
            @RequestParam ("id") int id,
            @RequestHeader("Authorization") String authHeader) {

        try {

            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.getUsernameFromToken(token);

            companyCategoriesService.deleteCategory(id, username);

            return ResponseEntity.ok()
                    .body(new ResponseDTO(VarList.OK, "Category deleted successfully", null));

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, ex.getMessage(), null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error deleting category", null));
        }
    }

}
