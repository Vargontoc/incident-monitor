package es.agonzalez.incident.monitor.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import es.agonzalez.incident.monitor.dtos.IncidentCreateRequest;
import es.agonzalez.incident.monitor.dtos.IncidentSearch;
import es.agonzalez.incident.monitor.dtos.IncidentUpdateRequest;
import es.agonzalez.incident.monitor.mappers.IncidentMapper;
import es.agonzalez.incident.monitor.models.Incident;
import es.agonzalez.incident.monitor.repositories.IncidentRepository;
import es.agonzalez.incident.monitor.models.IncidentStatus;
import es.agonzalez.incident.monitor.specifications.IncidentSpecifications;

@Service
public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    public Page<Incident> search(IncidentSearch search, Pageable pageable) 
    {
        var spec = Specification.where(IncidentSpecifications.titleContains(search.title()))
            .and(IncidentSpecifications.statusIn(search.statuses()))
            .and(IncidentSpecifications.priorityIn(search.priorities()))
            .and(search.mine() ? IncidentSpecifications.reportedBy(search.userId()) : null)
            .and(search.assignedtoMe() ? IncidentSpecifications.assignedTo(search.userId()) : null)
            .and(IncidentSpecifications.createdBetween(search.createdFrom(), search.createdTo()));
        return incidentRepository.findAll(spec, pageable);
        
    }
    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public Incident createIncident(Long authorId, IncidentCreateRequest req) {
        Incident incident = new Incident();
        incident.setTitle(req.title());
        incident.setDescription(req.description());
        incident.setReportedBy(authorId);
        incident.setStatus(IncidentStatus.OPEN);
        incident.setPriority(req.priority());
        return incidentRepository.save(incident);
    }

    public Incident updateIncident(Long id, IncidentUpdateRequest incident) 
    {
        var existingIncident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found with id: " + id));
        IncidentMapper.applyUpdates(existingIncident, incident);
        return incidentRepository.save(existingIncident);
    }

    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }
}
