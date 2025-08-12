package es.agonzalez.incident.monitor.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "incidents")
public class Incident 
{
    @Id
    private Long id;
    @Column(nullable = false, name = "title", length= 160)
    private String title;
    @Column(name = "description", columnDefinition= "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private IncidentStatus status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "priority")
    private IncidentPriority priority;
    @Column(nullable = false, name = "reported_by")
    private Long reportedBy;
    @Column(nullable= false, name = "assigned_to")
    private Long assignedTo;
    @Column(nullable = false, name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        if(id == null) {
            id = System.currentTimeMillis(); 
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public IncidentStatus getStatus() {
        return status;
    }
    public void setStatus(IncidentStatus status) {
        this.status = status;
    }
    public IncidentPriority getPriority() {
        return priority;
    }
    public void setPriority(IncidentPriority priority) {
        this.priority = priority;
    }
    public Long getReportedBy() {
        return reportedBy;
    }
    public void setReportedBy(Long reportedBy) {
        this.reportedBy = reportedBy;
    }
    public Long getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }   
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


}
