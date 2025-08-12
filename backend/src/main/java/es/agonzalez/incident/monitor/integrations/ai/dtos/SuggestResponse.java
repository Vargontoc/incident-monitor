package es.agonzalez.incident.monitor.integrations.ai.dtos;

import java.util.List;
public record SuggestResponse(List<String> suggestions, String reasoning) {}
