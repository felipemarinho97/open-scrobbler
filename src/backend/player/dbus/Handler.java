package backend.player.dbus;

import backend.OpenLastfm;
import backend.PlayerEvent;
import backend.player.MPDPlayerEventID;

import org.bff.javampd.player.PlayerBasicChangeEvent;
import org.bff.javampd.player.PlayerBasicChangeListener;
import org.bff.javampd.player.PlayerChangeEvent;
import org.bff.javampd.player.PlayerChangeEvent.Event;
import org.bff.javampd.player.PlayerChangeListener;
import org.bff.javampd.playlist.PlaylistBasicChangeEvent;
import org.bff.javampd.playlist.PlaylistBasicChangeListener;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.Variant;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by darklyn on 11/06/17.
 */
public class Handler implements DBusSigHandler<Properties.PropertiesChanged>, PlayerBasicChangeListener, PlayerChangeListener, PlaylistBasicChangeListener {

    MPDPlayerEventID[] eventIDs;

    public Handler() {
        this.eventIDs = MPDPlayerEventID.values();
    }

    @Override
    public void handle(Properties.PropertiesChanged propertiesChanged) {
        System.out.println("Rigth way DBUS!");
        Set<String> keySet = propertiesChanged.getPropertiesChanged().keySet();
        String[] array = keySet.toArray(new String[keySet.size()]);
        for (int i = 0; i < array.length; i++) {
			if (array[i].equals("PlaybackStatus")) {
				Variant variant = propertiesChanged.getPropertiesChanged().get(array[i]);
				array[i] = (String) variant.getValue();
			}
		}
        System.out.println(Arrays.toString(array));
        PlayerEvent e = new PlayerEvent(this, array);
        OpenLastfm.getInstance().notifyListeners(e);
    }

    @Override
    public void playerChanged(PlayerChangeEvent playerChangeEvent) {
        System.out.println("Rigth way PLAYER CHANGE!");
        Event playerEvent = playerChangeEvent.getEvent();
        System.out.println(playerEvent.name());
        String[] array = new String[] { playerEvent.name() };
//
//        for (MPDPlayerEventID ID : eventIDs) {
//            if (ID.getID() == eventID) {
//                array = new String[] { ID.name() };
//                break;
//            }
//        }

        PlayerEvent e = new PlayerEvent(this, array);
        OpenLastfm.getInstance().notifyListeners(e);
    }

	@Override
	public void playerBasicChange(PlayerBasicChangeEvent playerBasicChangeEvent) {
		System.out.println("Rigth way PLAYER BASIC CHANGE!");
		PlayerBasicChangeEvent.Status playerStatus = playerBasicChangeEvent.getStatus();
		String[] array = new String[] { playerStatus.name() };

        PlayerEvent e = new PlayerEvent(this, array);
        OpenLastfm.getInstance().notifyListeners(e);
		
	}

	@Override
	public void playlistBasicChange(PlaylistBasicChangeEvent playlistBasicChangeEvent) {
		System.out.println("BASIC PLAYLIST");
		PlaylistBasicChangeEvent.Event playlistEvent = playlistBasicChangeEvent.getEvent();
		String[] array = new String[] { playlistEvent.name() };

        PlayerEvent e = new PlayerEvent(this, array);
        OpenLastfm.getInstance().notifyListeners(e);
	}
}