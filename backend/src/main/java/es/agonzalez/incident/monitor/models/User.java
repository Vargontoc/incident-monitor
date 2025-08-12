package es.agonzalez.incident.monitor.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity @Table(name = "users")
public class User {

    @Id
    private Long id;
    @Column(nullable = false, unique = true, name= "username")
    private String username;
    @Column(nullable = false, name= "password")
    private String password;
    @Column(nullable = false, unique = true, name= "email")
    private String email;
    @Column(nullable = false, unique = true, name= "first_name")
    private String firstName;
    @Column(name= "last_name")
    private String lastName;
    @Column(nullable = false, name= "created_at")
    private Instant createdAt;
    @Column(nullable = false, name= "updated_at")
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        if(id == null) {
            id = System.currentTimeMillis(); // Simple ID generation, replace with a better strategy if needed
        }

        Instant now = Instant.now();
        this.createdAt = now;
    }
    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
