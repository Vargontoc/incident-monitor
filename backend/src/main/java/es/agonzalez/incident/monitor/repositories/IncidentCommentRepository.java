package es.agonzalez.incident.monitor.repositories;


import es.agonzalez.incident.monitor.models.IncidentComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentCommentRepository extends JpaRepository<IncidentComment, Long>, JpaSpecificationExecutor<IncidentComment> {

}
