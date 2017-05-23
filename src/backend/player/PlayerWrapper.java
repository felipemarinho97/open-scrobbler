package backend.player;

import exceptions.PlayerException;
import exceptions.SongException;

/**
 * Created by darklyn on 02/05/17.
 */
public class PlayerWrapper {
    Player player;

    public PlayerWrapper() throws PlayerException {
        // Defaults connect to an MPRIS Interface.
        this.player = new MPD();
    }

    public void changeInterface(Player player) {
        this.player = player;
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
