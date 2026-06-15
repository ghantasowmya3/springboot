package sism.controller;

import sism.models.Students;
import sism.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentsController {
    
    @Autowired
    private StudentsService studentsService;
    
    @GetMapping("/getallstudents/{page}/{size}")
    public Map<String, Object> getAllStudents(@PathVariable int page, @PathVariable int size,
                                               @RequestHeader("Token") String token) {
        return studentsService.getAllStudents(page, size, token);
    }
    
    @GetMapping("/getstudent/{id}")
    public Map<String, Object> getStudentById(@PathVariable Integer id,
                                               @RequestHeader("Token") String token) {
        return studentsService.getStudentById(id, token);
    }
    
    @PostMapping("/savestudent")
    public Map<String, Object> saveStudent(@RequestBody Students student,
                                            @RequestHeader("Token") String token) {
        return studentsService.saveStudent(student, token);
    }
    
    @PutMapping("/updatestudent/{id}")
    public Map<String, Object> updateStudent(@PathVariable Integer id,
                                              @RequestBody Students student,
                                              @RequestHeader("Token") String token) {
        return studentsService.updateStudent(id, student, token);
    }
    
    @DeleteMapping("/deletestudent/{id}")
    public Map<String, Object> deleteStudent(@PathVariable Integer id,
                                              @RequestHeader("Token") String token) {
        return studentsService.deleteStudent(id, token);
    }
    
    @GetMapping("/searchstudent/{keyword}")
    public Map<String, Object> searchStudent(@PathVariable String keyword,
                                              @RequestHeader("Token") String token) {
        return studentsService.searchStudent(keyword, token);
    }
}