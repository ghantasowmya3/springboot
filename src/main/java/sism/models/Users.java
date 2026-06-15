package sism.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String fullname;
    private String phone;
    private String email;
    
    @JsonIgnore
    private String password;
    
    private Integer role;
    private Integer status;
    
    // Constructors
    public Users() {}
    
    public Users(Integer id, String fullname, String phone, String email, 
                 String password, Integer role, Integer status) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}