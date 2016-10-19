package chat.packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyx on 2016/10/19.
 */
public class UserListPacket extends Packet {
    private List<String> userList = new ArrayList<>();

    public List<String> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "UserListPacket{" +
                "userList=" + userList +
                '}';
    }
}
