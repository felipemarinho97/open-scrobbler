package backend.player;

import exceptions.PlayerException;
import exceptions.SongException;

/**
 * Created by darklyn on 02/05/17.
 */
public interface Player {

    Song getCurrentSong() throws SongException, PlayerException;

    PlayerStatus getStatus();

    long getElapsedTime();

    PlayerStatus togglePlayer();

    void previousTrack();

    void nextTrack();
}
