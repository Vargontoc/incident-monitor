package es.agonzalez.incident.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.agonzalez.incident.monitor.models.User;
import es.agonzalez.incident.monitor.repositories.UserRepository;


@Component
public class DataSeeder implements CommandLineRunner
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passEncoder;

    @Override
    public void run(String... args) 
    {
        if(userRepository.count() == 0)
        {
            var user = new User();
            user.setEmail("fakemail@mimail.es");
            user.setFirstName("Alvaro");
            user.setLastName("Gonzalez Toca");
            user.setPassword(passEncoder.encode("Secret123!"));
            user.setUsername("agonzalez");
            userRepository.save(user);
            
        }
    }    
}
