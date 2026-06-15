package sism.services;

import sism.models.Courses;
import sism.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CoursesService {
    
    @Autowired
    private CoursesRepository coursesRepository;
    
    @Autowired
    private JwtService jwtService;
    
    public Map<String, Object> getAllCourses(int page, int size, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            int offset = (page - 1) * size;
            List<Courses> courses = coursesRepository.findAllWithPagination(offset, size);
            long total = coursesRepository.count();
            
            response.put("code", 200);
            response.put("courses", courses);
            response.put("total", total);
            response.put("page", page);
            response.put("size", size);
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> getCourseById(Integer id, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            Optional<Courses> course = coursesRepository.findById(id);
            
            if (course.isPresent()) {
                response.put("code", 200);
                response.put("course", course.get());
            } else {
                response.put("code", 404);
                response.put("message", "Course not found");
            }
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> saveCourse(Courses course, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer userId = jwtService.getUserIdFromToken(token);
            
            // Check if course code already exists
            Optional<Courses> existingCourse = coursesRepository.findByCourseCode(course.getCourseCode());
            if (existingCourse.isPresent()) {
                response.put("code", 400);
                response.put("message", "Course code already exists");
                return response;
            }
            
            Courses saved = coursesRepository.save(course);
            response.put("code", 200);
            response.put("message", "Course saved successfully");
            response.put("courseId", saved.getCourseId());
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> updateCourse(Integer id, Courses course, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Courses> existingCourse = coursesRepository.findById(id);
            if (existingCourse.isEmpty()) {
                response.put("code", 404);
                response.put("message", "Course not found");
                return response;
            }
            
            course.setCourseId(id);
            coursesRepository.save(course);
            response.put("code", 200);
            response.put("message", "Course updated successfully");
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> deleteCourse(Integer id, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Courses> existingCourse = coursesRepository.findById(id);
            if (existingCourse.isEmpty()) {
                response.put("code", 404);
                response.put("message", "Course not found");
                return response;
            }
            
            coursesRepository.deleteById(id);
            response.put("code", 200);
            response.put("message", "Course deleted successfully");
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> searchCourse(String keyword, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            List<Courses> courses = coursesRepository.searchCourses(keyword);
            response.put("code", 200);
            response.put("courses", courses);
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> getCoursesByTeacher(Integer teacherId, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            List<Courses> courses = coursesRepository.findByTeacherId(teacherId);
            response.put("code", 200);
            response.put("courses", courses);
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
}