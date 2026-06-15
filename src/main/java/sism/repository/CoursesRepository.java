package sism.repository;

import sism.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    
    Optional<Courses> findByCourseCode(String courseCode);
    
    @Query("SELECT c FROM Courses c WHERE c.teacherId = :teacherId")
    List<Courses> findByTeacherId(@Param("teacherId") Integer teacherId);
    
    @Query("SELECT c FROM Courses c WHERE c.courseName LIKE %:keyword% OR c.courseCode LIKE %:keyword%")
    List<Courses> searchCourses(@Param("keyword") String keyword);
    
    @Query(value = "SELECT * FROM courses ORDER BY course_id LIMIT :size OFFSET :offset", nativeQuery = true)
    List<Courses> findAllWithPagination(@Param("offset") int offset, @Param("size") int size);
}