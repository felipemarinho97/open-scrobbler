package gui.windows;

import org.freedesktop.icons.StateIcon;
import org.gnome.gtk.Frame;
import org.gnome.gtk.Label;
import org.gnome.gtk.Window;

public class ErrorWindow extends Window {
	public ErrorWindow(Exception e) {
        this.setTitle(e.getMessage());
        this.setIcon(StateIcon.DIALOG_ERROR);
        
        Frame frameError = new Frame(e.getCause().toString());
        
        frameError.add(new Label(e.fillInStackTrace().toString()));
        this.add(frameError);
        this.showAll();
	}

}
