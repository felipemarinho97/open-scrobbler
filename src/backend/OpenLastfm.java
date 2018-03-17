package backend;

import backend.player.Player;
import backend.player.PlayerStatus;
import backend.player.PlayerWrapper;
import backend.player.Song;
import exceptions.PlayerException;
import exceptions.SongException;

import gui.Language;
import gui.MusicInterface;

import java.util.EventListener;
import java.util.EventObject;

public class OpenLastfm extends EventSource {

	private static final OpenLastfm INSTANCE = new OpenLastfm();
	private Pref prefs;
	private PlayerWrapper playerWrapper;
	private InfoRequester requester;
	private LastFMWrapper lastFM;

	private OpenLastfm() {
		this.prefs = new Pref();
		try {
			this.playerWrapper = new PlayerWrapper();
		} catch (PlayerException e) {
			e.printStackTrace();
		}
		this.requester = new InfoRequester(Language.valueOf(prefs.getPref("language")),
				prefs.getPref("apiKey"));
		this.lastFM = new LastFMWrapper(prefs.getPref("apiKey"),prefs.getPref("user"),prefs.getPref("password"), Language.valueOf(prefs.getPref("language")));
	}

	public void notifyListeners(PlayerEvent event) {
		if (listeners != null) {
			for (EventListener e : listeners) {
				try {
					((PlayerListener) e).onPlayerChanged(event);
				} catch (ClassCastException e1) {
				}
			}
		}
	}

	public static final OpenLastfm getInstance() {
		return INSTANCE;
	}
	
	public void loveTrack(String artist, String track) {
		lastFM.loveTrack(artist, track);
	}

	public int trackTimes(String trackOrMbid, String artist) {
		return lastFM.trackTimes();
	}

	public void updateArtistRequester(String artist) {
		lastFM.getArtistInstance(artist);
	}

	public void updateTrack(String track) {
		lastFM.updateTrackInfo(track);
	}
	
	public void setLangIso(Language lang) {
		lastFM.setLang(lang);
	}

	public void setApiKey(String apiKey) {
		lastFM.setApiKey(apiKey);
	}

	public String getBio(String type) {
		return lastFM.getBio(type);
	}

	public String getArtistImageURL() {
		return lastFM.getArtistImageURL();
	}

	public String getSimilarArtist(int num, String param) {
		return lastFM.getSimilarArtist(num, param);
	}

	public Song getCurrentSong() throws PlayerException, SongException {
		return playerWrapper.getCurrentSong();
	}

	public PlayerStatus getStatus() {
		return playerWrapper.getStatus();
	}

	public long getElapsedTime() {
		return playerWrapper.getElapsedTime();
	}

	public PlayerStatus togglePlayer() {
		return playerWrapper.togglePlayer();
	}
	
	public void previousTrack() {
		playerWrapper.previousTrack();
	}
	
	public void nextTrack() {
		playerWrapper.nextTrack();
	}
	
	public void changeInterface(MusicInterface iface) throws PlayerException {
		playerWrapper.changeInterface(iface);
	}

	public void setPref(String key, String value) {
		prefs.setPref(key, value);
	}

	public String getPref(String key) {
		return prefs.getPref(key);
	}

	public void updatePref() {
		prefs.updatePref();
	}
	
	public void loadPref() {
		prefs.loadPref();
		this.setApiKey(this.getPref("apiKey"));
		this.setLangIso(Language.valueOf(this.getPref("language")));
	}

	public void getAlbumInfo(String album) {
		lastFM.updateAlbumInfo(album);
	}

	public String getAlbumImageURL(int size) {
		return lastFM.getAlbumImageURL(size);
	}

	public int artistTimes(String artist) {
		return lastFM.artistTimes();
	}

	public void unloveTrack(String artist, String currentMusicTitle) {
		lastFM.unloveTrack(artist, currentMusicTitle);
	}

	public boolean isLoved(String currentMusicTitle, String artist) {
		return lastFM.isLoved();
	}


	public String getAlbumLink() {
		return lastFM.getAlbumLink();
	}

	public  String getArtistLink() {
		return lastFM.getArtistLink();
	}
}
