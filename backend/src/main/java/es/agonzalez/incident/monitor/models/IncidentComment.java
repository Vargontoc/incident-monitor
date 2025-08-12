package es.agonzalez.incident.monitor.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
@Entity
@Table(name = "incident_comments")
public class IncidentComment 
{
    @Id
    private Long id;
    @Column(nullable = false, name = "incident_id")
    private Long incidentId;
    @Column(nullable = false, name = "author_id")
    private Long userId;
    @Column(nullable = false, name = "comment", columnDefinition = "TEXT")
    private String comment;
    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if(id == null) {
            id = System.currentTimeMillis(); // Simple ID generation, replace with a better strategy if needed
        }
        this.createdAt = Instant.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }    
}
