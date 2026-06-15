package sism.repository;

import sism.models.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MenusRepository extends JpaRepository<Menus, Long> {
    Optional<Menus> findByMid(Long mid);
    Optional<Menus> findByMenu(String menu);
    
    @Query("SELECT m FROM Menus m ORDER BY m.mid")
    List<Menus> findAllMenusOrdered();
}