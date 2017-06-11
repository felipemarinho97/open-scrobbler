package gui;

import org.gnome.gdk.Event;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowType;

import backend.player.dbus.Spotify;

import java.io.IOException;

public class Main {

	public static final String VERSION = "0.3a";
	private static MyApp c = new MyApp();
	public static Thread t0;
	public static Thread t1;

	public static void main(String[] args) throws IOException {
		new Spotify();
		Gtk.init(args);
		Window w = new Window(WindowType.TOPLEVEL);
		w.connect((Window.DeleteEvent) (arg0, arg1) -> {
            Main.quit();
            return false;
        });
		
		Main.c.buildElements(w);
		
		update();
		Gtk.setProgramName("openLastfm");
		Gtk.setDefaultIcon(new Pixbuf("last.fm-icon.png", 20, 20, true));
		w.showAll();
		Gtk.main();

		w.destroy();
		
		System.exit(0);
	}

	public static void update() {
		t0 = new Thread() {
			
			@Override
			public void run() {
				while (true) {
					try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					c.updateMusicProgress();
				}
			}
			
		};
		t0.start();
		
		t1 = new Thread() {
			
			@Override
			public void run() {
				while (true) {
					try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					c.updateArtist();
				}
			}
			
		};
		t1.start();
	}

	public static void quit() {
		try {
			t0.interrupt();
			t1.interrupt();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		Gtk.mainQuit();
	}
}
