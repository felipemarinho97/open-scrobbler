package exceptions;

/**
 * Created by darklyn on 02/05/17.
 */
public class SongException extends Exception {
    public SongException() {
        super();
    }

    public SongException(String s) {
        super(s);
    }

    public SongException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SongException(Throwable throwable) {
        super(throwable);
    }

    protected SongException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
