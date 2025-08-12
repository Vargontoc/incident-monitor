package es.agonzalez.incident.monitor.dtos;

public record IncidentResponse(Long id,
                                String title,
                                String description,
                                String reportedBy,
                                String assignedTo,
                                String priority,
                                String status,
                                String createdAt,
                                String updatedAt) {
}
