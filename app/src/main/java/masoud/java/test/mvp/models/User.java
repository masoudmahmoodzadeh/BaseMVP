package masoud.java.test.mvp.models;

public class User {

    private long id;
    private String name;

    public User(){

    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
