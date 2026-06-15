package sism.controller;

import sism.models.Courses;
import sism.services.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*")
public class CoursesController {
    
    @Autowired
    private CoursesService coursesService;
    
    @GetMapping("/getallcourses/{page}/{size}")
    public Map<String, Object> getAllCourses(@PathVariable int page, @PathVariable int size,
                                              @RequestHeader("Token") String token) {
        return coursesService.getAllCourses(page, size, token);
    }
    
    @GetMapping("/getcourse/{id}")
    public Map<String, Object> getCourseById(@PathVariable Integer id,
                                              @RequestHeader("Token") String token) {
        return coursesService.getCourseById(id, token);
    }
    
    @PostMapping("/savecourse")
    public Map<String, Object> saveCourse(@RequestBody Courses course,
                                          @RequestHeader("Token") String token) {
        return coursesService.saveCourse(course, token);
    }
    
    @PutMapping("/updatecourse/{id}")
    public Map<String, Object> updateCourse(@PathVariable Integer id,
                                            @RequestBody Courses course,
                                            @RequestHeader("Token") String token) {
        return coursesService.updateCourse(id, course, token);
    }
    
    @DeleteMapping("/deletecourse/{id}")
    public Map<String, Object> deleteCourse(@PathVariable Integer id,
                                            @RequestHeader("Token") String token) {
        return coursesService.deleteCourse(id, token);
    }
    
    @GetMapping("/searchcourse/{keyword}")
    public Map<String, Object> searchCourse(@PathVariable String keyword,
                                            @RequestHeader("Token") String token) {
        return coursesService.searchCourse(keyword, token);
    }
    
    @GetMapping("/getcoursesbyteacher/{teacherId}")
    public Map<String, Object> getCoursesByTeacher(@PathVariable Integer teacherId,
                                                    @RequestHeader("Token") String token) {
        return coursesService.getCoursesByTeacher(teacherId, token);
    }
}