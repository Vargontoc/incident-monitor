package es.agonzalez.incident.monitor.integrations.ai.dtos;

public record OllamaResponse(String model,
                            String response,
                            boolean done) {
}
