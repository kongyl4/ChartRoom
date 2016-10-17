package chat.packet;

import java.io.Serializable;

/**
 * Created by wyx on 2016/10/17.
 */
public abstract class Packet implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "username='" + username + '\'' +
                '}';
    }
}
