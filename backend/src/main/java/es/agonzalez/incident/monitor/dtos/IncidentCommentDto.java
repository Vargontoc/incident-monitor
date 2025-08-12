package es.agonzalez.incident.monitor.dtos;

public class IncidentCommentDto 
{
    private String id;
    private String content;
    private String createdAt;
    private String authorId;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getAuthorId() {
        return authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

}
