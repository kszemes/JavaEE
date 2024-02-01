package hu.andika.javaee.model.poi;

public class PointOfInterest {
    private int id;
    private String name;
    private String location;
    private String type;
    private int likes;

    public PointOfInterest() {
        this.likes = 0;
    }

    public PointOfInterest(int id, String name, String location, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.likes = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "PointOfInterest: " +
                "id: " + id +
                ", name: " + name +
                ", location: " + location +
                ", type: " + type +
                ", likes: " + likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterest that = (PointOfInterest) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!location.equals(that.location)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
