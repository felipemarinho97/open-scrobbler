package backend;

import java.util.EventObject;

/**
 * Created by darklyn on 01/07/17.
 */
public class PlayerEvent extends EventObject {
    private String[] events;

    public PlayerEvent(Object source, String[] events) {
        super(source);
        this.events = events;
    }

    public String[] getEvents() {
        return this.events;
    }
}
