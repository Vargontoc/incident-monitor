package es.agonzalez.incident.monitor.dtos;

import jakarta.validation.constraints.Size;
import es.agonzalez.incident.monitor.models.IncidentPriority;
import es.agonzalez.incident.monitor.models.IncidentStatus;

public record IncidentUpdateRequest(@Size(max = 160) String title,
                                @Size(max = 10_000) String description,
                                IncidentPriority priority,
                                IncidentStatus status,
                                String assignedTo) {

}
