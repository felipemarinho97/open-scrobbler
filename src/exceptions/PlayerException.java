package exceptions;

/**
 * Created by darklyn on 01/05/17.
 */
public class PlayerException extends Exception {
    public PlayerException() {
    }

    public PlayerException(String var1) {
        super(var1);
    }

    public PlayerException(String var1, Throwable var2) {
        super(var1, var2);
    }

    public PlayerException(Throwable var1) {
        super(var1);
    }

    protected PlayerException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4);
    }
}
