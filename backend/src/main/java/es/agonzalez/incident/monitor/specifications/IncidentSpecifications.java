package es.agonzalez.incident.monitor.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.time.Instant;

import es.agonzalez.incident.monitor.models.Incident;
import es.agonzalez.incident.monitor.models.IncidentPriority;
import es.agonzalez.incident.monitor.models.IncidentStatus;
public final class IncidentSpecifications {

    public static Specification<Incident> titleContains(String title) 
    {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };    
    }

    public static Specification<Incident> statusIn(List<IncidentStatus> statuses) 
    {
        return (root, query, criteriaBuilder) -> {
            if (statuses == null || statuses.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("status").in(statuses);
        };  
    }

    public static Specification<Incident> priorityIn(List<IncidentPriority> priorities) 
    {
        return (root, query, criteriaBuilder) -> {
            if (priorities == null || priorities.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("priority").in(priorities);
        };  
    }

    public static Specification<Incident> assignedTo(Long assignedTo) 
    {
        return (root, query, criteriaBuilder) -> {
            if (assignedTo == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("assignedTo"), assignedTo);
        };  
    }

    public static Specification<Incident> reportedBy(Long reportedBy) 
    {
        return (root, query, criteriaBuilder) -> {
            if (reportedBy == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("reportedBy"), reportedBy);
        };  
    }

    public static Specification<Incident> createdBetween(Instant start, Instant end) 
    {
        return (root, query, criteriaBuilder) -> {
            if(start == null && end == null) return criteriaBuilder.conjunction();
            if(start != null && end == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), start);
            if(start == null && end != null) return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
            return criteriaBuilder.between(root.get("createdAt"), start, end);
        };  
    }
}
