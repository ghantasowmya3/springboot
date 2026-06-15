package sism.repository;

import sism.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    
    @Query("SELECT u FROM Users u WHERE u.fullname LIKE %:keyword% OR u.email LIKE %:keyword% OR u.phone LIKE %:keyword%")
    List<Users> searchUsers(@Param("keyword") String keyword);
    
    @Query(value = "SELECT * FROM users u ORDER BY u.id LIMIT :size OFFSET :offset", nativeQuery = true)
    List<Users> findAllWithPagination(@Param("offset") int offset, @Param("size") int size);
}