package es.agonzalez.incident.monitor.integrations.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.ai")
public record AiProperties(
    String provider,
    String url,
    String model,
    Double temperature,
    Integer timeoutSeconds
) {}
