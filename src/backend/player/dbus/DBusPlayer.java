package backend.player.dbus;

import java.util.Map;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.types.DBusMapType;

/**
 * Created by darklyn on 02/05/17.
 */
@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
public interface DBusPlayer extends DBusInterface {

    public void Play();

    public void Pause();
	
    public void Next();
	
    public void Previous();
	
    public void PlayPause();
	
    public void Stop();
	
    public void Seek(Long offset);
	
    public void Seeked(Long position);

}
