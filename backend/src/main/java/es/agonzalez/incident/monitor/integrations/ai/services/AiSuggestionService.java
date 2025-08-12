package es.agonzalez.incident.monitor.integrations.ai.services;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import es.agonzalez.incident.monitor.dtos.IncidentCommentSearch;
import es.agonzalez.incident.monitor.integrations.ai.AiProperties;
import es.agonzalez.incident.monitor.integrations.ai.AiSuggestionUtils;
import es.agonzalez.incident.monitor.repositories.IncidentRepository;
import es.agonzalez.incident.monitor.models.Incident;
import es.agonzalez.incident.monitor.models.IncidentComment;
import es.agonzalez.incident.monitor.integrations.ai.dtos.SuggestResponse;
import es.agonzalez.incident.monitor.integrations.ai.dtos.OllamaRequest;
import es.agonzalez.incident.monitor.integrations.ai.dtos.OllamaResponse;   
import es.agonzalez.incident.monitor.services.IncidentCommentService;


@Service
public class AiSuggestionService 
{   
    @Autowired
    private WebClient webClient;
    @Autowired
    private AiProperties aiProperties;
    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private IncidentCommentService commentsService;


    public SuggestResponse suggest(Long incidentId, List<String> extra) 
    {
        var incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found"));

        var comments = commentsService.search(new IncidentCommentSearch(incidentId), null).getContent();
        var last5 = comments.stream()
            .map(comment -> comment)
            .skip(Math.max(0, comments.size() - 5)) // Get last 5 comments
            .toList();

        var prompt = buildPrompt(incident, last5, extra);



        var request = new OllamaRequest(
            aiProperties.model(),
            prompt,
            false,
            Map.of("temperature", Optional.ofNullable(aiProperties.temperature()).orElse(.2),
             "top_p", .9)
        );



        OllamaResponse response = webClient.post()
            .uri("/api/generate")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(OllamaResponse.class)
            .block();

        if (response == null || response.response() == null || response.response().isBlank()) 
            return new SuggestResponse(List.of("No se recibieron sugerencias del modelo"), "IA sin respuesta");

        var parsed = AiSuggestionUtils.tryParseJson(response.response().trim());
        if(parsed != null) return parsed;

        var lines = Arrays.stream(response.response().split("\n"))
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .limit(5)
            .toList();

        return new SuggestResponse(lines, "Generado por IA (texto libre)");

    }


    private String buildPrompt(Incident i, List<IncidentComment> last5, List<String> extras) {
        
        var coments = last5.stream()
            .map(comment -> "-" +  comment.getComment())
            .collect(Collectors.joining("\n"));

        var extraText = (extras == null || extras.isEmpty()) ? "" : "\n\n- Pistas adicionales:\n" + String.join("\n", extras);

        return """
                Eres un asistente de soporte técnico. Da 3 a 5 pasos **concretos y accionables** para resolver la incidencia. Si no sabes qué hacer, di que no lo sabes.
                Devuelve la respuesta en formato **solo JSON** con el siguiente formato:
                {
                    "suggestions" : ["Paso 1", "Paso 2", "Paso 3", ...],
                    "reasoning": "Explicación de por qué se sugiere esto"
                }

                Contexto: 
                 - Título: %s
                 - Prioridad: %s
                 - Estado: %s
                 - Descripción: %s
                 - Comentarios recientes:
                 %s
                 %s
                """.formatted(nullSafe(i.getTitle()),
                nullSafe(i.getPriority() == null? null : i.getPriority().name()),
                nullSafe(i.getStatus() == null? null : i.getStatus().name()),
                nullSafe(i.getDescription()),
                coments.isBlank() ? "(no hay comentarios)" : coments,
                extraText);
    }

    private String nullSafe(String value) {
        return value == null ? "(no especificado)" : value;
    }
}
