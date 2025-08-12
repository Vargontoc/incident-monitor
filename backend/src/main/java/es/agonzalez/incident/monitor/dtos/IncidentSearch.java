package es.agonzalez.incident.monitor.dtos;


import es.agonzalez.incident.monitor.models.IncidentPriority;
import es.agonzalez.incident.monitor.models.IncidentStatus;
import java.time.Instant;
import java.util.List;


public record IncidentSearch(
    String title,
    List<IncidentPriority> priorities,
    List<IncidentStatus> statuses,
    boolean mine,
    boolean assignedtoMe,
    Instant createdFrom,
    Instant createdTo,
    Long userId
) {
}