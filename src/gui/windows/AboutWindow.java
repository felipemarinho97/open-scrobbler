package gui.windows;

import java.io.FileNotFoundException;

import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Align;
import org.gnome.gtk.Box;
import org.gnome.gtk.Image;
import org.gnome.gtk.Justification;
import org.gnome.gtk.Label;
import org.gnome.gtk.Notebook;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowType;

import gui.Main;
import util.Util;

public class AboutWindow extends Window {
	public AboutWindow() {
		super(WindowType.TOPLEVEL);
		//w.setHasResizeGrip(false);
		this.setTitle("About " + Main.PROGRAM_NAME);
		this.setDefaultSize(350, 240);
		Box messageBox = new VBox(true, 5);
		Box imageBox = new VBox(true, 5);
		Notebook tab = new Notebook();
		
		Image img = null;
		try {
			img = new Image(new Pixbuf(Main.PROGRAM_ICON, 100, 100, true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageBox.packStart(img, false, false, 15);
		
		// About Tab
		Label info = new Label();
		info.setAlignHorizontal(Align.CENTER);
		info.setAlignVertical(Align.CENTER);
		info.setPadding(5, 10);
		info.setUseMarkup(true);
		info.setSelectable(true);
		info.setLabel("Obrigado por usar o " + Main.PROGRAM_NAME + " " + Main.VERSION + "\n"
				+ "Desenvolvido por Felipe Marinho (C) sob os termos da licença GPLv3\n"
				+ "Sinta-se livre para contribuir com o projeto clicando "
				+ "<a href='http://github.com/FelipeMarinho97/openScrobbler'>aqui</a>.\n"
				+ "\nCaso deseje reportar algum bug ou enviar alguma sugestão, contate-me:\n"
				+ "GitHub: <a href='http://github.com/FelipeMarinho97'>FelipeMarinho97</a>\n"
				+ "Email: <a href='mailto:felipevm97@gmail.com'>felipevm97@gmail.com</a>\n"
				+ "\nSe este programa foi útil para você, por favor considere fazer uma doação:\n"
				+ "Bitcoin: 195rHUQhbRt2c5ncxc2zyqmLgSTB3PwTn2");

		
		// License Tab
        Label licenseLabel = new Label(Util.loadLicense());
        licenseLabel.setSelectable(true);
        licenseLabel.setJustify(Justification.CENTER);
        
        Box textBoxContent = new Box(Orientation.VERTICAL, 1);
        textBoxContent.packStart(licenseLabel, true, true, 1);
		
        ScrolledWindow sw = new ScrolledWindow();
        sw.addWithViewport(textBoxContent);
		
        tab.appendPage(info, new Label("About"));
		tab.appendPage(sw, new Label("License"));

		messageBox.packStart(imageBox, false, false, 5);
		messageBox.packStart(tab, true, true, 5);
		this.add(messageBox);
	}
}
