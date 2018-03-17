package gui;

import backend.EventSource;
import backend.OpenLastfm;
import backend.PlayerEvent;
import backend.PlayerListener;
import backend.player.MPDPlayerEventID;
import backend.player.Song;
import exceptions.PlayerException;
import exceptions.SongException;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.StatusIcon;
import org.gnome.gtk.Window;

import java.io.FileNotFoundException;

/**
 * Created by darklyn on 01/07/17.
 */
public class Tray implements PlayerListener {

    private static final String DEFAULT = Main.PROGRAM_NAME + " " + Main.VERSION;
    StatusIcon statusIcon;
    OpenLastfm openScrobbler = OpenLastfm.getInstance();
    String tooltipText;

    public Tray(Window w) {
        this.statusIcon = new StatusIcon();
        statusIcon.connect(new StatusIcon.Activate() {
            @Override
            public void onActivate(StatusIcon statusIcon) {
                w.show();
            }
        });
        try {
            statusIcon.setFromPixbuf(new Pixbuf("last.fm-icon.png", 20, 20, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tooltipText = DEFAULT;
        updateTooltipText();

        openScrobbler.addEventListener(this);
    }

    public void updateTooltipText() {
        statusIcon.setTooltipText(tooltipText);
    }

    @Override
    public void onPlayerChanged(PlayerEvent event) {
        String[] events = event.getEvents();
        for (String e : events) {
            if (e.equals("Metadata") ||
                    e.equals(MPDPlayerEventID.SONG_CHANGED.name()) ||
                    e.equals(MPDPlayerEventID.PLAYER_STARTED.name())) {
                try {
                    Song song = openScrobbler.getCurrentSong();
                    tooltipText = song.getTitle() + " - " + song.getArtist();
                } catch (PlayerException e1) {
                    tooltipText = DEFAULT;
                } catch (SongException e1) {
                    tooltipText = DEFAULT;
                } finally {
                    updateTooltipText();
                }
                break;
            }
        }
    }
}
