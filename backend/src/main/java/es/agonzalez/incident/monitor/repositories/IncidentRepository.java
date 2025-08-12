package es.agonzalez.incident.monitor.repositories;

import es.agonzalez.incident.monitor.models.Incident;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface  IncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident> {

    
}
