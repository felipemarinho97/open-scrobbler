package gui;

import org.freedesktop.dbus.DBusSignal;
import org.gnome.gdk.Event;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowType;

import backend.player.dbus.Spotify;

import java.io.IOException;

public class Main {

	public static final String PROGRAM_ICON = "last.fm-icon.png";
	public static final String PROGRAM_NAME = "OpenScrobbler";
	public static final String VERSION = "0.3a";

	public static void main(String[] args) throws IOException {
		Gtk.init(args);
		Window w = new Window(WindowType.TOPLEVEL);
		Tray tray = new Tray(w);
		w.connect((Window.DeleteEvent) (arg0, arg1) -> {
            w.hide();
            return false;
        });
		MyApp app = new MyApp();
		app.buildElements(w);
		
		Gtk.setProgramName(PROGRAM_NAME);
		Pixbuf icon = new Pixbuf(PROGRAM_ICON, 20, 20, true);
		Gtk.setDefaultIcon(new Pixbuf(PROGRAM_ICON, 20, 20, true));
		w.showAll();
		Gtk.main();

		w.destroy();
		
		System.exit(0);
	}

	public static void quit() {
		Gtk.mainQuit();
	}
}
