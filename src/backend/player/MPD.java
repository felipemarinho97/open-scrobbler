package backend.player;

import java.net.UnknownHostException;

import org.bff.javampd.MPDException;
import org.bff.javampd.monitor.StandAloneMonitor;
import org.bff.javampd.server.MPD.Builder;
import org.bff.javampd.server.MPDConnectionException;
import org.bff.javampd.song.MPDSong;

import backend.Pref;
import backend.player.dbus.Handler;
import exceptions.PlayerException;
import exceptions.SongException;


public class MPD implements Player {
	private org.bff.javampd.player.Player player;
	private org.bff.javampd.server.MPD mpd;
	private StandAloneMonitor monitor;

	public MPD(Handler handler) throws PlayerException {
		Pref p = new Pref();
		mpd = null;
		try {
			Builder builder  = new org.bff.javampd.server.MPD.Builder();
			builder.server(p.getPref("host"));
			builder.port(Integer.parseInt(p.getPref("port")));
			mpd = builder.build();
		} catch (UnknownHostException | MPDConnectionException e) {
			throw new PlayerException("Algo errado aconteceu!");
		}
		this.player = mpd.getPlayer();
		monitor = mpd.getMonitor();
		monitor.addPlayerChangeListener(handler);
		monitor.addPlaylistChangeListener(handler);
		monitor.start();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			monitor.stop();
			mpd.close();
		} finally {
			super.finalize();
		}
	}
	
	@Override
	public Song getCurrentSong() throws PlayerException, SongException {
		try {
			MPDSong song = this.player.getCurrentSong();
			if (song == null) {
				throw new PlayerException("There is no song.");
			}
			return new Song(song);
		} catch (MPDException e) {
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
		return this.player.getElapsedTime();
	}

	@Override
	public PlayerStatus togglePlayer() {
		switch (this.getStatus()) {
			case STATUS_STOPPED:
				try {
					this.player.play();
				} catch (MPDConnectionException e) {
					e.printStackTrace();
				}
				return this.getStatus();
			case STATUS_PAUSED:
				try {
					this.player.play();
				} catch (MPDException e) {
					e.printStackTrace();
				}
				return this.getStatus();
			case STATUS_PLAYING:
				try {
					this.player.pause();
				} catch (MPDException e) {
					e.printStackTrace();
				}
				return this.getStatus();
		}
		return this.getStatus();
	}

	@Override
	public void previousTrack() {
		try {
			this.player.playPrevious();
		} catch (MPDException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextTrack() {
		try {
			this.player.playNext();
		} catch (MPDException e) {
			e.printStackTrace();
		}
	}
}
