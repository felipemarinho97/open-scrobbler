package backend.player.dbus;

import exceptions.PlayerException;
import exceptions.SongException;

import java.util.Map;

import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDPlayerException;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

import backend.player.Player;
import backend.player.PlayerStatus;
import backend.player.Song;


/**
 * Created by darklyn on 02/05/17.
 */
public class Spotify implements Player {

    private Map metadata;
    
	private Properties propertiesInteface;
	private DBusPlayer dBusPlayer;

	public Spotify() {
        DBusConnection conn = null;
        try {
            conn = DBusConnection.getConnection(DBusConnection.SESSION);
        } catch (DBusException e) {
            e.printStackTrace();
        }
        
        this.dBusPlayer = null;
        try {
            dBusPlayer = conn.getRemoteObject("org.mpris.MediaPlayer2.spotify","/org/mpris/MediaPlayer2", DBusPlayer.class);
        } catch (DBusException e) {
            e.printStackTrace();
        }
        
        this.propertiesInteface = null;
        try {
            propertiesInteface = conn.getRemoteObject("org.mpris.MediaPlayer2.spotify","/org/mpris/MediaPlayer2", Properties.class);
        } catch (DBusException e) {
            e.printStackTrace();
        }
    }

	public void fetchMetadata() throws PlayerException {
		Variant<Map> res;
		
		try {
			res = propertiesInteface.Get("org.mpris.MediaPlayer2.Player", "Metadata");
		} catch (Exception e) {
			throw new PlayerException("Error when getting Dbus metadata");
		}
		
		this.metadata = res.getValue();
	}
	
	public static String cleanMeta(String meta) {
		return meta.substring(1, meta.length()-1);
	}
	
    @Override
    public Song getCurrentSong() throws SongException, PlayerException {
    	fetchMetadata();
    	
        String title = cleanMeta(metadata.get("xesam:title").toString());
		String album = cleanMeta(metadata.get("xesam:album").toString());
		String artist = cleanMeta(cleanMeta(metadata.get("xesam:albumArtist").toString()));
		String lengthStr = cleanMeta(metadata.get("mpris:length").toString());
		
		int length = Integer.parseInt(lengthStr) / 1000000;

		return new Song(title, album, artist, length);
    }

    @Override
    public PlayerStatus getStatus() {
    	Variant<String> res;
		
		res = propertiesInteface.Get("org.mpris.MediaPlayer2.Player", "PlaybackStatus");
		
		String playerStatus = res.getValue();
    	
		PlayerStatus status = PlayerStatus.valueOf(("status_" + playerStatus).toUpperCase());
        return status;
    }

    @Override
    public long getElapsedTime() {
    	Variant<Long> res;
		
		res = propertiesInteface.Get("org.mpris.MediaPlayer2.Player", "Position");
        return res.getValue();
    }

    @Override
    public PlayerStatus togglePlayer() {
    	PlayerStatus status = this.getStatus();

//    	this.getInterface.PlayPause();
		switch (status) {
			case STATUS_STOPPED:
				this.dBusPlayer.Play();
			case STATUS_PAUSED:
				this.dBusPlayer.Play();
			case STATUS_PLAYING:
				this.dBusPlayer.Pause();
		}
		
		status = this.getStatus();
		
		return status;
    }

    @Override
    public void previousTrack() {
    	dBusPlayer.Previous();
    }

    @Override
    public void nextTrack() {
    	dBusPlayer.Next();
    }

}
