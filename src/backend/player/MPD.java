package backend.player;

import java.net.UnknownHostException;

import backend.Pref;
import exceptions.PlayerException;
import exceptions.SongException;
import gui.MyApp;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.MPDSong;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDException;
import org.bff.javampd.exception.MPDPlayerException;

public class MPD implements Player {
	private MPDPlayer player;

	public MPD() throws PlayerException {
		Pref p = new Pref();
		org.bff.javampd.MPD mpd = null;
		try {
			mpd = new org.bff.javampd.MPD(p.getPref("host"), Integer.parseInt(p.getPref("port")));
		} catch (UnknownHostException | MPDConnectionException e) {
			throw new PlayerException("Algo errado aconteceu!");
		}
		this.player = mpd.getMPDPlayer();
	}

	@Override
	public Song getCurrentSong() throws PlayerException, SongException {
		try {
			MPDSong song = this.player.getCurrentSong();
			if (song == null) {
				throw new PlayerException("There is no song.");
			}
			return new Song(song);
		} catch (MPDPlayerException | MPDConnectionException e) {
			System.err.println("Algo errado aconteceu!");
			throw new PlayerException("There is no current song.");
		}
	}

	@Override
	public PlayerStatus getStatus() {
		try {
			return PlayerStatus.valueOf(this.player.getStatus().name());
		} catch (MPDException e) {
			System.err.println("Algo errado aconteceu!");
		}
		return null;
	}

	@Override
	public long getElapsedTime() {
		try {
			return this.player.getElapsedTime();
		} catch (MPDPlayerException | MPDConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public PlayerStatus togglePlayer() {
		switch (this.getStatus()) {
			case STATUS_STOPPED:
				try {
					this.player.play();
				} catch (MPDPlayerException e) {
					e.printStackTrace();
				} catch (MPDConnectionException e) {
					e.printStackTrace();
				}
				return this.getStatus();
			case STATUS_PAUSED:
				try {
					this.player.play();
				} catch (MPDPlayerException e) {
					e.printStackTrace();
				} catch (MPDConnectionException e) {
					e.printStackTrace();
				}
				return this.getStatus();
			case STATUS_PLAYING:
				try {
					this.player.pause();
				} catch (MPDPlayerException e) {
					e.printStackTrace();
				} catch (MPDConnectionException e) {
					e.printStackTrace();
				}
				return this.getStatus();
		}
		return this.getStatus();
	}

	@Override
	public void previousTrack() {
		try {
			this.player.playPrev();
		} catch (MPDPlayerException | MPDConnectionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextTrack() {
		try {
			this.player.playNext();
		} catch (MPDPlayerException | MPDConnectionException e) {
			e.printStackTrace();
		}
	}
}
