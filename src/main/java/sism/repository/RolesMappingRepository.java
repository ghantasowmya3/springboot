package sism.repository;

import sism.models.RolesMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RolesMappingRepository extends JpaRepository<RolesMapping, Long> {
    
    @Query("SELECT rm FROM RolesMapping rm WHERE rm.role = :role")
    List<RolesMapping> findByRole(@Param("role") Long role);
    
    // Add this new method to get distinct menus
    @Query("SELECT DISTINCT rm FROM RolesMapping rm WHERE rm.role = :role")
    List<RolesMapping> findDistinctByRole(@Param("role") Long role);
}