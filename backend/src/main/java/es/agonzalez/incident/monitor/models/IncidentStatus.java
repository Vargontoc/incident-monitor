package es.agonzalez.incident.monitor.models;

public enum IncidentStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean isOpen() {
        return this == OPEN || this == IN_PROGRESS;
    }

    public boolean isResolved() {
        return this == RESOLVED || this == CLOSED;
    }
}
