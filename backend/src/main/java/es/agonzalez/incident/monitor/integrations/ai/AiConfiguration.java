package es.agonzalez.incident.monitor.integrations.ai;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
@EnableConfigurationProperties(AiProperties.class)
public class AiConfiguration 
{
    @Autowired
    private AiProperties props;

    @Bean
    public WebClient aiWebClient() {
        var timeout = Duration.ofSeconds(props.timeoutSeconds() == null ? 12 : props.timeoutSeconds());
        var http = HttpClient.create()
        .responseTimeout(timeout)
        .compress(true);
        return WebClient.builder()
        .baseUrl(props.url())
        .clientConnector(new ReactorClientHttpConnector(http))
        .build();
    }
  
}
