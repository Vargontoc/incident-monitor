package es.agonzalez.incident.monitor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import java.util.List;

import es.agonzalez.incident.monitor.dtos.IncidentCreateRequest;
import es.agonzalez.incident.monitor.dtos.IncidentResponse;
import es.agonzalez.incident.monitor.dtos.IncidentUpdateRequest;
import es.agonzalez.incident.monitor.integrations.ai.dtos.SuggestRequest;
import es.agonzalez.incident.monitor.integrations.ai.dtos.SuggestResponse;
import es.agonzalez.incident.monitor.integrations.ai.services.AiSuggestionService;
import es.agonzalez.incident.monitor.services.IncidentService;
import jakarta.validation.Valid;
import es.agonzalez.incident.monitor.mappers.IncidentMapper;
import es.agonzalez.incident.monitor.models.IncidentPriority;
import es.agonzalez.incident.monitor.models.IncidentStatus;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController
{
    @Autowired
    private IncidentService incidentService;

    @Autowired
    private AiSuggestionService aiService;

    @GetMapping()
    public Page<IncidentResponse> searchIncidents(
        @RequestParam(required = false) String title
        , @RequestParam(required = false) List<IncidentStatus> statuses
        , @RequestParam(required = false) List<IncidentPriority> priorities
        , @RequestParam(required = false) boolean mine
        , @RequestParam(required = false) boolean assignedtoMe
        , @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate createdFrom
        , @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate createdTo
        , Pageable pageable, Authentication authentication) {

        var userId = authentication != null ? Long.valueOf(authentication.getName()) : null;
        java.time.Instant from = createdFrom == null ? null : createdFrom.atStartOfDay(java.time.ZoneOffset.UTC).toInstant();
        
        java.time.Instant to = null;
        if (createdTo != null) {
            to = createdTo.plusDays(1).atStartOfDay(java.time.ZoneOffset.UTC).toInstant().minusMillis(1);
        }
        var search = new es.agonzalez.incident.monitor.dtos.IncidentSearch(
            title,
            priorities,
            statuses,
             mine,
            assignedtoMe,
            from,
            to,
            userId
        );
        return incidentService.search(search, pageable)
            .map(IncidentMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable  Long id) {
        return incidentService.getIncidentById(id).map(IncidentMapper::toResponse).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(@RequestBody @Valid IncidentCreateRequest incident, Authentication authentication) {
        var userId = Long.valueOf(authentication.getName());
        var saved = incidentService.createIncident(userId, incident);
        return ResponseEntity.ok(IncidentMapper.toResponse(saved));
    }

    @PostMapping("/{id}/suggest")
    public ResponseEntity<SuggestResponse> suggest(@PathVariable Long id, @RequestBody(required = false) SuggestRequest request, Authentication auth)
    {
        if(incidentService.getIncidentById(id) == null) return ResponseEntity.notFound().build();
        var extras = request == null ? java.util.List.<String>of(): request.contextExtras();
        var out = aiService.suggest(id, extras);
        return ResponseEntity.ok(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponse> updateIncident(@RequestParam Long id, @RequestBody @Valid IncidentUpdateRequest incident) {
        var updated = incidentService.updateIncident(id, incident);

        return ResponseEntity.ok(IncidentMapper.toResponse(updated));
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@RequestParam Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

}
