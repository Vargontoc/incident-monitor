package es.agonzalez.incident.monitor.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import es.agonzalez.incident.monitor.models.IncidentPriority;

public record IncidentCreateRequest(@NotBlank @Size(max = 160) String title,
                                    @Size(max= 10_000) String description,
                                    @NotNull IncidentPriority priority) {
}