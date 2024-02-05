package hu.andika.javaee.model.comment;

public class CommentDto extends Comment{

    private String userName;
    private String poiName;

    public CommentDto(Integer id, String content, Integer userId, String userName, Integer pointOfInterestId, String poiName, boolean isAuthorized) {
        super(id, content, userId, pointOfInterestId, isAuthorized);
        this.userName = userName;
        this.poiName = poiName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }
}