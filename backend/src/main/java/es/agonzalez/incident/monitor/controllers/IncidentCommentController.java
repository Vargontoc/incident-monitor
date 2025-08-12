package es.agonzalez.incident.monitor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import es.agonzalez.incident.monitor.dtos.IncidentCommentDto;
import es.agonzalez.incident.monitor.dtos.IncidentCommentSearch;
import es.agonzalez.incident.monitor.services.IncidentCommentService;
import es.agonzalez.incident.monitor.models.IncidentComment;
import es.agonzalez.incident.monitor.models.User;
import es.agonzalez.incident.monitor.repositories.UserRepository;



@RestController
@RequestMapping("/api/v1/incidents/{incidentId}/comments")  
public class IncidentCommentController {
    
    @Autowired
    private IncidentCommentService incidentCommentService;
    @Autowired
    private UserRepository userService;

    @GetMapping
    public List<IncidentCommentDto> getCommentsByIncidentId(@PathVariable Long incidentId) {
        List<IncidentCommentDto> comments = new ArrayList<>();
        for (IncidentComment comment : incidentCommentService.search(new IncidentCommentSearch(incidentId), null)) {
            IncidentCommentDto dto = new IncidentCommentDto();
            dto.setId(comment.getId().toString());
            dto.setAuthorId(comment.getUserId().toString());
            dto.setContent(comment.getComment());
            dto.setCreatedAt(comment.getCreatedAt().toString());
            comments.add(dto);
        }


        return comments;
    }

    @PostMapping
    public ResponseEntity<IncidentCommentDto> addCommentToIncident(@PathVariable Long incidentId, @RequestBody String commentContent, Authentication authentication) {
        User user = userService.findById(Long.valueOf(authentication.getName()))
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + authentication.getName()));


        var saved = incidentCommentService.addCommentToIncident(incidentId, user.getId(), commentContent);

        IncidentCommentDto dto = new IncidentCommentDto();
        dto.setId(saved.getId().toString());
        dto.setAuthorId(saved.getUserId().toString());
        dto.setContent(saved.getComment());
        dto.setCreatedAt(saved.getCreatedAt().toString());

        return ResponseEntity.ok(dto);
    }
}
