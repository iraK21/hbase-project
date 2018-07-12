package tweetbase.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedInputStream;
import java.io.IOException;


public class Users {

    public static final byte[] TABLE_NAME = Bytes.toBytes("users");
    public static final byte[] INFO_FAMILY = Bytes.toBytes("info");

    public static final byte[] USER_COLUMN = Bytes.toBytes("user");
    public static final byte[] NAME_COLUMN = Bytes.toBytes("name");
    public static final byte[] EMAIL_COLUMN = Bytes.toBytes("email");
    public static final byte[] PASSWORD_COLUMN = Bytes.toBytes("password");
    public static final byte[] TWEETS_COLUMN = Bytes.toBytes("tweet_count");

    private Connection connection;

    public Users(Connection connection) {
        this.connection = connection;
    }

    private static class User extends tweetbase.model.User {

        private User(String user, String name, String email, String password) {
            this.user = user;
            this.name = name;
            this.email = email;
            this.password = password;
        }

        private User(byte[] user, byte[] name, byte[] email, byte[] password, byte[] tweetCount) {
            this(Bytes.toString(user), Bytes.toString(name), Bytes.toString(email), Bytes.toString(password));
            this.tweetCount = Bytes.toLong(tweetCount);
        }

        private User(Result result) {
            //this(result.)
        }

    }

    private static Get get(String user) {
        Get get = new Get(Bytes.toBytes("user"));
        get.addFamily(INFO_FAMILY);
        return get;
    }

    private static Put put(User user) {
        Put put = new Put(user.user.getBytes());
        put.addColumn(INFO_FAMILY, USER_COLUMN, user.user.getBytes());
        put.addColumn(INFO_FAMILY, NAME_COLUMN, user.name.getBytes());
        put.addColumn(INFO_FAMILY, EMAIL_COLUMN, user.email.getBytes());
        put.addColumn(INFO_FAMILY, PASSWORD_COLUMN, user.password.getBytes());
        return put;
    }

    private static Delete delete(String user) {
        return new Delete(user.getBytes());
    }

    public void addUser(String user, String name, String email, String password) throws IOException {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
        Put put = put(new User(user, name, email, password));
        table.put(put);
        table.close();
    }

    public User getUser(String user) throws IOException {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
        Get get = get(user);
        Result result = table.get(get);

        if(result.isEmpty())
            return null;

        User u = new User(result);
        table.close();
        return u;
    }
}