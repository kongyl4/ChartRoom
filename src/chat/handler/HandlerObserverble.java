package chat.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import chat.net.SocketConnector;
import chat.packet.Packet;

/**
 * Created by wyx on 2016/10/17.
 */
public abstract class HandlerObserverble {

    private final List<Handler<? extends Packet>> handlerList = new CopyOnWriteArrayList<>();

    public <T extends Packet> void notifyHandlers(T packet, SocketConnector connector) {
        for (Handler handler : handlerList) {
            Class<? extends Packet> entityClass = null;
            Type t = handler.getClass().getGenericInterfaces()[0];
            if (t instanceof ParameterizedType) {
                Type[] p = ((ParameterizedType) t).getActualTypeArguments();
                entityClass = (Class<? extends Packet>) p[0];
            }
            if (entityClass == null || entityClass.isAssignableFrom(packet.getClass())) {
                if (!handler.handle(packet, connector)) {
                    break;
                }
            }
        }
    }

    public void registerHandler(Handler<? extends Packet> handler) {
        handlerList.add(handler);
    }

}
