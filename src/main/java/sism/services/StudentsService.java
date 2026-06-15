package sism.services;

import sism.models.Students;
import sism.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StudentsService {
    
    @Autowired
    private StudentsRepository studentsRepository;
    
    @Autowired
    private JwtService jwtService;
    
    public Map<String, Object> getAllStudents(int page, int size, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            List<Students> students = studentsRepository.findAll();
            
            response.put("code", 200);
            response.put("students", students);
            response.put("total", students.size());
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> getStudentById(Integer id, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            Optional<Students> student = studentsRepository.findById(id);
            
            if (student.isPresent()) {
                response.put("code", 200);
                response.put("student", student.get());
            } else {
                response.put("code", 404);
                response.put("message", "Student not found");
            }
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
    
    public Map<String, Object> saveStudent(Students student, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            student.setCreatedAt(new Date());
            student.setUpdatedAt(new Date());
            
            // Check if roll number already exists
            if (student.getRollNumber() != null) {
                Optional<Students> existing = studentsRepository.findByRollNumber(student.getRollNumber());
                if (existing.isPresent()) {
                    response.put("code", 400);
                    response.put("message", "Roll number already exists");
                    return response;
                }
            }
            
            Students saved = studentsRepository.save(student);
            response.put("code", 200);
            response.put("message", "Student saved successfully");
            response.put("studentId", saved.getStudentId());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("message", "Error: " + e.getMessage());
        }
        
        return response;
    }
    
    public Map<String, Object> updateStudent(Integer id, Students student, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Students> existing = studentsRepository.findById(id);
            if (existing.isEmpty()) {
                response.put("code", 404);
                response.put("message", "Student not found");
                return response;
            }
            
            student.setStudentId(id);
            student.setCreatedAt(existing.get().getCreatedAt());
            student.setUpdatedAt(new Date());
            
            studentsRepository.save(student);
            response.put("code", 200);
            response.put("message", "Student updated successfully");
            
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error: " + e.getMessage());
        }
        
        return response;
    }
    
    public Map<String, Object> deleteStudent(Integer id, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            
            Optional<Students> existing = studentsRepository.findById(id);
            if (existing.isEmpty()) {
                response.put("code", 404);
                response.put("message", "Student not found");
                return response;
            }
            
            studentsRepository.deleteById(id);
            response.put("code", 200);
            response.put("message", "Student deleted successfully");
            
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error: " + e.getMessage());
        }
        
        return response;
    }
    
    public Map<String, Object> searchStudent(String keyword, String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            jwtService.getUserIdFromToken(token);
            List<Students> students = studentsRepository.searchStudents(keyword);
            response.put("code", 200);
            response.put("students", students);
            
        } catch (Exception e) {
            response.put("code", 401);
            response.put("message", "Invalid token");
        }
        
        return response;
    }
}