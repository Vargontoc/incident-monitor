package es.agonzalez.incident.monitor.integrations.ai.dtos;

import java.util.List;

public record SuggestRequest(List<String> contextExtras) {}
