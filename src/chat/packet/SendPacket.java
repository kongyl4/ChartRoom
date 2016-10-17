package chat.packet;

/**
 * Created by wyx on 2016/10/17.
 */
public class SendPacket extends Packet {

    private String to;

    private String msg;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SendPacket{username='" + getUsername() +
                "',to='" + to + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
