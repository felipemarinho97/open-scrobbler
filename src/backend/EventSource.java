package backend;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by darklyn on 01/07/17.
 */
public abstract class EventSource {
    ArrayList<EventListener> listeners;

    public void addEventListener(EventListener listener) {
        if (listeners == null) {
            this.listeners = new ArrayList<>();
        }

        this.listeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        if (listeners != null && listener != null) {
            for (int i = 0; i < listeners.size(); i++) {
                if (listener.equals(listeners.get(i))) {
                    listeners.remove(i);
                    break;
                }
            }
        }
    }
}
