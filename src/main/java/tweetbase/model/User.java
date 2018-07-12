package tweetbase.model;

public abstract class User {

    public String user;
    public String name;
    public String email;
    public String password;
    public long tweetCount;

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tweetCount=" + tweetCount +
                '}';
    }
}
