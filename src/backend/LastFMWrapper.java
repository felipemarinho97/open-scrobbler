package backend;

import java.io.File;
import java.util.Collection;
import java.util.Locale;

import de.umass.lastfm.*;
import de.umass.lastfm.cache.Cache;
import de.umass.lastfm.cache.DefaultExpirationPolicy;
import de.umass.lastfm.cache.ExpirationPolicy;
import de.umass.lastfm.cache.FileSystemCache;
import de.umass.lastfm.scrobble.ScrobbleData;
import de.umass.lastfm.scrobble.ScrobbleResult;
import gui.Language;
import gui.MyApp;

public class LastFMWrapper {
	private Language lang;
	private String apiKey;
	private Session session;
	private OpenLastfm openLastfm;
	private String user;
	private String pass;
	private Artist artist;
	private Track track;
	private Album album;
	private Artist[] similars;

	public LastFMWrapper(String apiKey, String user, String password, Language lang) {
		Caller c = Caller.getInstance();
		c.setUserAgent("tst");
		File file = new File("/home/darklyn/.cache/openLastfm");
		file.mkdir();
		Cache cache = new FileSystemCache(file);
		cache.setExpirationPolicy(new DefaultExpirationPolicy());
		c.setCache(cache);
		this.lang = lang;
		this.apiKey = apiKey;
		this.user = user;
		this.pass = password;
		session = Authenticator.getMobileSession(user, pass, apiKey, "0ad8b40bd216800b2d69994ea936ce56");
	}
	
	public void loveTrack(String artist, String track) {
		Track.love(artist, track, session);
	}
	
	public int trackTimes() {
		return this.track.getUserPlaycount();
	}

	public int artistTimes() {
		return this.artist.getUserPlaycount();
	}

	public void unloveTrack(String artist, String track) {
		Track.unlove(artist, track, session);
	}

	public boolean isLoved() {
		return this.track.isLoved();
	}

	public void getArtistInstance(String artist) {
		this.artist = Artist.getInfo(artist, new Locale(lang.getIso()), user, apiKey);
		this.similars = this.artist.getSimilar().toArray(new Artist[6]);
	}

	public void updateTrackInfo(String track) {
		this.track = Track.getInfo(artist.getName(), track, Locale.ENGLISH, user, apiKey);
	}

	public void updateAlbumInfo(String album) {
		this.album = Album.getInfo(artist.getName(), album, user, apiKey);
	}

	public String getAlbumImageURL(int size) {
		if (album == null) {
			return "";
		}
		if (size == 1) {
			return this.album.getImageURL(ImageSize.MEDIUM);
		} else if (size == 2) {
			return this.album.getImageURL(ImageSize.LARGE);
		}
		return null;
	}

	public String getSimilarArtist(int num, String param) {
		if (similars[num] == null) {
			return "";
		}
		switch (param) {
			case "imageURL":
				return similars[num].getImageURL(ImageSize.SMALL);
			case "url":
				return concertaUrl(similars[num].getUrl());
			case "name":
				return similars[num].getName();
			default:
				return similars[num].getName();
		}
	}

	public String getArtistImageURL() {
		return this.artist.getImageURL(ImageSize.LARGE);
	}

	public String getBio(String type) {
		switch (type) {
			case "content":
				return this.artist.getWikiText();
			case "summary":
				return this.artist.getWikiSummary();
			default:
				return this.artist.getWikiSummary();
		}
	}

	private String concertaUrl(String url) {
		return url.substring(0,20) + lang.getIso() + "/" + url.substring(20, url.length());
	}

	public String getAlbumLink() {
		if (album == null) {
			return "";
		}
		return concertaUrl(this.album.getUrl());
	}

	public String getArtistLink() {
		return concertaUrl(this.artist.getUrl());
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setLang(Language lang) {
		this.lang = lang;
	}
}
