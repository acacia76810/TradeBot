package com.tradebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// ... existing code ...
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    private String role; // ADMIN, USER
    
    public boolean hasRole(String roleName) {
        return this.role != null && this.role.equals(roleName);
    }
}
// ... existing code ...