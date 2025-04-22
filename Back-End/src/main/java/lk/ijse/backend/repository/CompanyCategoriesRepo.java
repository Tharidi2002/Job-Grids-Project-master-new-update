package lk.ijse.backend.repository;


import lk.ijse.backend.entity.CompanyCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCategoriesRepo extends JpaRepository<CompanyCategories,Integer> {


    boolean existsByName(String name);

    @Query("SELECT COUNT(c) > 0 FROM CompanyCategories c WHERE c.name = :name AND c.categoryId <> :id")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") int id);
}
