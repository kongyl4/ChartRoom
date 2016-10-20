package chartroo1.message;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public class SendMessage extends Message {
    private String msg=null;
    private String to=null;

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
        return "send:{username="+getUsername()+",msg="+getMsg();
    }
}
