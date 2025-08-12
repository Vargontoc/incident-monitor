package es.agonzalez.incident.monitor.integrations.ai.dtos;

import java.util.Map;

public record OllamaRequest(
    String model,
    String prompt,
    boolean stream,
    Map<String, Object> options
) {}
