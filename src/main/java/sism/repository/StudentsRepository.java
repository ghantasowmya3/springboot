package sism.repository;

import sism.models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByUserId(Integer userId);
    Optional<Students> findByRollNumber(String rollNumber);
    
    @Query("SELECT s FROM Students s WHERE s.rollNumber LIKE %:keyword% OR s.course LIKE %:keyword% OR s.parentName LIKE %:keyword%")
    List<Students> searchStudents(@Param("keyword") String keyword);
}