package chat.packet;

/**
 * Created by wyx on 2016/10/17.
 */
public class BroadcastPacket extends Packet {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BroadcastPacket{username='" + getUsername() +
                "',msg='" + msg + '\'' +
                '}';
    }
}
