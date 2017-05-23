package gui;

import java.io.FileNotFoundException;

import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Align;
import org.gnome.gtk.Box;
import org.gnome.gtk.Image;
import org.gnome.gtk.Label;
import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowType;

public class MyMenu extends MenuBar {
	private MenuItem help;
	private MenuItem file;
	private MenuItem edit;

	public MyMenu() {
		this.file = new MenuItem("File");
		this.edit = new MenuItem("Edit");
		this.help = new MenuItem("Help");
		this.createHelpChilds();
		this.createEditChilds();
		this.createFileChilds();
		this.insert(file, 0);
		this.insert(help, 2);
		this.insert(edit, 1);
	}
	
	private void createHelpChilds() {
		MenuItem sobreChild = new MenuItem("About", new MenuItem.Activate() {
			
			@Override
			public void onActivate(MenuItem arg0) {
				Window w = new Window(WindowType.TOPLEVEL);
				//w.setHasResizeGrip(false);
				w.setTitle("About OpenLast.fm");
				w.setDefaultSize(300, 140);
				Box messageBox = new VBox(true, 5);
				
				Image img = null;
				try {
					img = new Image(new Pixbuf("last.fm-icon.png", 100, 100, true));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Label info = new Label("Obrigado por usar o OpenLast.fm 0.1\n"
						+ "Desenvolvido por Felipe Marinho (C)");
				info.setAlignHorizontal(Align.CENTER);
				info.setAlignVertical(Align.CENTER);
				info.setUseMarkup(true);
				messageBox.packStart(img, true, true, 5);
				messageBox.packStart(info, true, true, 5);
				w.add(messageBox);
				w.showAll();
			}
		});
		Menu menu = new Menu();
		menu.insert(sobreChild, 0);
		this.help.setSubmenu(menu);
	}

	private void createFileChilds() {
		MenuItem quit = new MenuItem("Quit", new MenuItem.Activate() {
			
			@Override
			public void onActivate(MenuItem arg0) {
				Main.quit();
			}
		});
		
		Menu menu = new Menu();
		menu.insert(quit, 0);
		this.file.setSubmenu(menu);
	}
	
	private void createEditChilds() {
		MenuItem settings = new MenuItem("Settings", new MenuItem.Activate() {
			
			@Override
			public void onActivate(MenuItem arg0) {
				Window w = new SettingsWindow();
				w.showAll();
			}
		} );
		
		Menu menu = new Menu();
		menu.insert(settings, 0);
		this.edit.setSubmenu(menu);
	}
}
