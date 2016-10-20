package chartroo1.message;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public class BroadcastMessage extends Message {
    private String msg=null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "broadcast:{username="+getUsername()+",msg="+getMsg();
    }
}
