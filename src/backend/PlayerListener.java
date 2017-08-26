package backend;

import org.freedesktop.dbus.DBusSignal;

import java.util.EventListener;

/**
 * Created by darklyn on 11/06/17.
 */
public interface PlayerListener extends EventListener {

    void onPlayerChanged(PlayerEvent event);

}
