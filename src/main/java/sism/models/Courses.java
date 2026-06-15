package sism.models;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    
    @Column(name = "course_code")
    private String courseCode;
    
    @Column(name = "course_name")
    private String courseName;
    
    private Integer credits;
    
    @Column(name = "teacher_id")
    private Integer teacherId;
    
    private String description;
    
    // Constructors
    public Courses() {}
    
    // Getters and Setters
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}