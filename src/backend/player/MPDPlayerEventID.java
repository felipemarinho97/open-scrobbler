package backend.player;

/**
 * Created by darklyn on 01/07/17.
 */
public enum MPDPlayerEventID {
    PLAYER_STOPPED(0),
    PLAYER_STARTED(1),
    PLAYER_PAUSED(2),
    PLAYER_NEXT(3),
    PLAYER_PREVIOUS(4),
    PLAYER_MUTED(5),
    PLAYER_UNMUTED(6),
    PLAYER_SONG_SET(9),
    PLAYER_UNPAUSED(10),
    PLAYER_SEEKING(11),
    PLAYLIST_CHANGED(12),
    PLAYLIST_ENDED(13),
    SONG_ADDED(14), 
    SONG_CHANGED(15), 
    SONG_DELETED(16);

    public int id;

    MPDPlayerEventID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
}
