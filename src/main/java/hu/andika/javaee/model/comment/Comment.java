package hu.andika.javaee.model.comment;

public class Comment {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer pointOfInterestId;
    private boolean isAuthorized;

    public Comment() {
        this.isAuthorized = false;
    }

    public Comment(String content, Integer userId, Integer pointOfInterestId, boolean isAuthorized) {
        this.content = content;
        this.userId = userId;
        this.pointOfInterestId = pointOfInterestId;
        this.isAuthorized = isAuthorized;
    }

    public Comment(Integer id, String content, Integer userId, Integer pointOfInterestId, boolean isAuthorized) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.pointOfInterestId = pointOfInterestId;
        this.isAuthorized = isAuthorized;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPointOfInterestId() {
        return pointOfInterestId;
    }

    public void setPointOfInterestId(Integer pointOfInterestId) {
        this.pointOfInterestId = pointOfInterestId;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    @Override
    public String toString() {
        return "Comment: " +
                "id: " + id +
                ", content: " + content+
                ", userId: " + userId +
                ", pointOfInterestID: " + pointOfInterestId +
                ", authorized: " + isAuthorized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (userId != comment.userId) return false;
        if (pointOfInterestId != comment.pointOfInterestId) return false;
        if (isAuthorized != comment.isAuthorized) return false;
        return content.equals(comment.content);
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + content.hashCode();
        result = 31 * result + userId;
        result = 31 * result + pointOfInterestId;
        result = 31 * result + (isAuthorized ? 1 : 0);
        return result;
    }
}
