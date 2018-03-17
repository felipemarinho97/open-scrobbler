package backend.player;

import backend.player.dbus.Handler;
import backend.player.dbus.Spotify;
import exceptions.PlayerException;
import exceptions.SongException;
import gui.MusicInterface;

/**
 * Created by darklyn on 02/05/17.
 */
public class PlayerWrapper {
    private Player player;
    private MusicInterface ifaceType;
    private Handler handler;

    public PlayerWrapper() throws PlayerException {
        // Defaults connect to an MPRIS Interface.
        this.handler = new Handler();
        this.player = new MPD(this.handler);
        this.ifaceType = MusicInterface.MPD;
    }

    public void changeInterface(MusicInterface iface) throws PlayerException {
    	switch (iface) {
    		case MPD:
    			this.player = new MPD(this.handler);
                break;
            case SPOTIFY:
    			this.player = new Spotify(this.handler);
    			break;
    	}
    	
		this.ifaceType = iface;
    }

    public Song getCurrentSong() throws PlayerException, SongException {
        return player.getCurrentSong();
    }

    public PlayerStatus getStatus() {
        return player.getStatus();
    }

    public long getElapsedTime() {
        return player.getElapsedTime();
    }

    public PlayerStatus togglePlayer() {
        return player.togglePlayer();
    }

    public void previousTrack() {
        player.previousTrack();
    }

    public void nextTrack() {
        player.nextTrack();
    }
}
