package hu.andika.javaee.model.user;

public enum Role {

    ADMIN("Admin"),
    REGULARUSER("User");

    public final String label;

    Role(String label) {
        this.label = label;
    }
}
