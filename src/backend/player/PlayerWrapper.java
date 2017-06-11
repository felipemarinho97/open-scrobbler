package backend.player;

import backend.player.dbus.Spotify;
import exceptions.PlayerException;
import exceptions.SongException;
import gui.MusicInterface;

/**
 * Created by darklyn on 02/05/17.
 */
public class PlayerWrapper {
    Player player;
    MusicInterface ifaceType;

    public PlayerWrapper() throws PlayerException {
        // Defaults connect to an MPRIS Interface.
        this.player = new MPD();
    }

    public void changeInterface(MusicInterface iface) throws PlayerException {
    	switch (iface) {
    		case MPD:
    			this.player = new MPD();
    		case SPOTIFY:
    			this.player = new Spotify();
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
