package es.agonzalez.incident.monitor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;

import es.agonzalez.incident.monitor.dtos.IncidentCommentSearch;
import es.agonzalez.incident.monitor.repositories.IncidentCommentRepository;
import es.agonzalez.incident.monitor.models.IncidentComment;
import es.agonzalez.incident.monitor.specifications.IncidentCommentSpecification;

@Service
public class IncidentCommentService 
{
    @Autowired
    private IncidentCommentRepository incidentCommentRepository;



    public Page<IncidentComment> search(IncidentCommentSearch search, Pageable pageable) 
    {
        var spec = Specification.where(IncidentCommentSpecification.incidentRef(search.incidentId()));
        if(pageable == null) 
            return new PageImpl<>(incidentCommentRepository.findAll(spec));
        return incidentCommentRepository.findAll(spec, pageable);
    }

    public IncidentComment addCommentToIncident(Long incidentId, Long authorId, String comment) {
        IncidentComment incidentComment = new IncidentComment();
        incidentComment.setIncidentId(incidentId);
        incidentComment.setUserId(authorId);
        incidentComment.setComment(comment);


        return incidentCommentRepository.save(incidentComment);
    }
}
