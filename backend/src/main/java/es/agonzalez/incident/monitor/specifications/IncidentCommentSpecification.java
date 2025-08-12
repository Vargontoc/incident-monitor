package es.agonzalez.incident.monitor.specifications;

import org.springframework.data.jpa.domain.Specification;

import es.agonzalez.incident.monitor.models.IncidentComment;

public final class IncidentCommentSpecification {
    public static Specification<IncidentComment> incidentRef(Long incidentId) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if(incidentId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("incidentId"), incidentId);
         });
    }
}
