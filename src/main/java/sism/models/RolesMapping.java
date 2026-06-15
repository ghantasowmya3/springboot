package sism.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rolesmapping")
public class RolesMapping {
    @Id
    private Long role;
    private Long mid;
    
    // Getters and Setters
    public Long getRole() { return role; }
    public void setRole(Long role) { this.role = role; }
    public Long getMid() { return mid; }
    public void setMid(Long mid) { this.mid = mid; }
}