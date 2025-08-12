package es.agonzalez.incident.monitor.auth;
public record RegisterRequest(String username, String email, String password) {}