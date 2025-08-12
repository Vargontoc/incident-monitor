package es.agonzalez.incident.monitor;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.agonzalez.incident.monitor.models.Incident;
import es.agonzalez.incident.monitor.models.IncidentPriority;
import es.agonzalez.incident.monitor.models.IncidentStatus;
import es.agonzalez.incident.monitor.models.User;
import es.agonzalez.incident.monitor.repositories.IncidentRepository;
import es.agonzalez.incident.monitor.repositories.UserRepository;

@Profile("dev")
@Component
public class DevDataSeeder implements CommandLineRunner
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passEncoder;
    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public void run(String... args) 
    {
        if(userRepository.count() == 0)
        {
            var user = new User();
            user.setEmail("agonzalez@cic.es");
            user.setFirstName("Alvaro");
            user.setLastName("Gonzalez Toca");
            user.setPassword(passEncoder.encode("Secret123!"));
            user.setUsername("agonzalez");

            var u = userRepository.save(user);
            IntStream.range(0, 8).mapToObj(i -> {
                var e = new Incident();
                e.setTitle("Incidencia demo #" + (i + 1));
                e.setDescription("Descripcion prueba " +  (i +1));
                e.setPriority(i%2 == 0? IncidentPriority.HIGH: IncidentPriority.MEDIUM);
                e.setStatus(IncidentStatus.OPEN);
                e.setReportedBy(u.getId());
                e.setAssignedTo(e.getId());
                incidentRepository.save(e);
                return e;

            }).toList();
            
        }
    }    
}
