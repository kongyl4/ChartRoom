package chartroo1.message;

import java.io.Serializable;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public abstract class Message implements Serializable{//为什么要加一个抽象类 为什么实现Ser
    private String username=null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "{username="+username+"}";
    }
}
