package backend.player;

import exceptions.SongException;
import org.bff.javampd.MPDSong;

/**
 * Created by darklyn on 02/05/17.
 */
public class Song {

	private String title = "";
    private String album = "";
    private String artist = "";
    private int length = 0;

    public Song() {
        super();
    }

    public Song(MPDSong song) throws SongException {
        super();
        this.title = song.getTitle();
        this.album = song.getAlbum();
        this.artist = song.getArtist();
        this.length = song.getLength();
        if (title == null || title.trim().isEmpty() 
        		|| album == null || album.trim().isEmpty() 
        		|| artist == null || artist.trim().isEmpty()) {
            throw new SongException("Missing Tags");
        }
    }
    
    public Song(String title, String album, String artist, int length) throws SongException {
		super();
        if (title == null || title.trim().isEmpty() 
        		|| album == null || album.trim().isEmpty() 
        		|| artist == null || artist.trim().isEmpty()) {
            throw new SongException("Missing Tags");
        }
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.length = length;
	}

	public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (length != song.length) return false;
        if (!title.equals(song.title)) return false;
        if (!album.equals(song.album)) return false;
        return artist.equals(song.artist);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + album.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + length;
        return result;
    }
    
    @Override
	public String toString() {
		return "Song [title=" + title + ", album=" + album + ", artist=" + artist + ", length=" + length + "]";
	}
}
