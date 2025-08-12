package es.agonzalez.incident.monitor.mappers;

import es.agonzalez.incident.monitor.dtos.IncidentResponse;
import es.agonzalez.incident.monitor.dtos.IncidentUpdateRequest;
import es.agonzalez.incident.monitor.models.Incident;

public class IncidentMapper {
    
    public static IncidentResponse toResponse(Incident incident) {
        return new IncidentResponse(
            incident.getId(),
            incident.getTitle(),
            incident.getDescription(),
            incident.getReportedBy().toString(),
            incident.getAssignedTo() != null ? incident.getAssignedTo().toString() : null,
            incident.getPriority().name(),
            incident.getStatus().name(),
            incident.getCreatedAt() != null ? incident.getCreatedAt().toString() : null,
            incident.getUpdatedAt() != null ? incident.getUpdatedAt().toString() : null
        );
    }

    public static void applyUpdates(Incident target, IncidentUpdateRequest source) 
    {
        if(source.title() != null) {
            target.setTitle(source.title());
        }
        if(source.description() != null) {
            target.setDescription(source.description());
        }
        if(source.priority() != null) {
            target.setPriority(source.priority());
        }
        if(source.status() != null) {
            target.setStatus(source.status());
        }
        if(source.assignedTo() != null && !source.assignedTo().isBlank()) {
            target.setAssignedTo(Long.valueOf(source.assignedTo()));
        }
    }
}
