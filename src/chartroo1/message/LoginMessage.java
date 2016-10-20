package chartroo1.message;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public class LoginMessage extends Message {
    @Override
    public String toString() {
        return "login:"+"{username="+getUsername()+"}";
    }
}
