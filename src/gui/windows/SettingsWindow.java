package gui.windows;

import java.net.InterfaceAddress;

import org.gnome.gdk.EventButton;
import org.gnome.gdk.RGBA;
import org.gnome.gtk.*;

import backend.OpenLastfm;
import exceptions.PlayerException;
import gui.Language;
import gui.Main;
import gui.MusicInterface;

public class SettingsWindow extends Window {
	
	private Notebook tabs;
	private Box musicPlayerBox;
	private Box openLastFMBox;
	private ComboBoxText languageComboBox;
	private Entry apiKeyEntry;
	private String apiKey;
	private OpenLastfm openLastFm;
	private Box settingsBox;
	private Box buttonsBox;
	private Entry passEntry;
	private Entry userEntry;
	private Entry hostEntry;
	private Entry portEntry;
	private ComboBoxText interfaceComboBox;

	public SettingsWindow() {
		super();
		this.openLastFm = OpenLastfm.getInstance();
		
		this.setTitle("Preferences");
		this.settingsBox = new Box(Orientation.VERTICAL, 0);
		this.tabs = new Notebook();
		
		this.openLastFMBox = new Box(Orientation.VERTICAL, 3);
		this.musicPlayerBox = new Box(Orientation.VERTICAL, 3);
		this.buttonsBox = new Box(Orientation.HORIZONTAL, 5);

		this.tabs.setShowBorder(false);
		this.tabs.appendPage(this.openLastFMBox, new Label(Main.PROGRAM_NAME));
		this.tabs.appendPage(this.musicPlayerBox, new Label("Music Player"));
		
		Frame tabsFrame = new Frame(null);
		tabsFrame.overrideBackground(StateFlags.NORMAL, RGBA.WHITE);
		tabsFrame.add(this.tabs);
		
		this.buildWindow();
		this.settingsBox.setBorderWidth(5);
		this.settingsBox.packStart(tabsFrame, true, true, 3);
		this.settingsBox.packStart(buttonsBox, false, false, 5);
		this.add(settingsBox);
	}
	
	private void buildWindow() {
		// OpenLast.fm Tab
		this.languageComboBox = new ComboBoxText();
		
		Language[] langs = Language.values(); 
		
		for (Language lang : langs) {
			languageComboBox.appendText(lang.getName());
		}
		
		this.languageComboBox.setActive(Language.valueOf(openLastFm.getPref("language")).getNum());
		
		
		Box apiKeyBox = new Box(Orientation.HORIZONTAL,5);
		this.apiKeyEntry = new Entry(this.openLastFm.getPref("apiKey"));
		this.apiKeyEntry.setSizeRequest(260, 0);
		apiKeyBox.packStart(new Label("Api Key:"), false, false, 1);
		apiKeyBox.packStart(this.apiKeyEntry, false, true, 1);
		
		Box languageBox = new Box(Orientation.HORIZONTAL,5);
		languageBox.packStart(new Label("Language:"), false, false, 1);
		languageBox.packStart(languageComboBox, false, false, 1);
		
		Box configRequesterBox = new Box(Orientation.VERTICAL,3);
		
		configRequesterBox.packStart(languageBox, false, true, 2);
		configRequesterBox.packStart(apiKeyBox, false, true, 2);
		configRequesterBox.setBorderWidth(5);
		
		Frame configRequesterFrame = new Frame("General");
		configRequesterFrame.add(configRequesterBox);
		configRequesterFrame.setBorderWidth(5);
		
		this.openLastFMBox.packStart(configRequesterFrame, false, true, 2);
		
		/*** USER SETTINGS ***/

		Box userSettingsBox = new Box(Orientation.VERTICAL,5);
		userSettingsBox.setBorderWidth(5);
		
		this.userEntry = new Entry();
		userEntry.setText(openLastFm.getPref("user"));
		Box userEntryBox = new Box(Orientation.HORIZONTAL,5);
		userEntryBox.packStart(new Label("User:"), false, false, 1);
		userEntryBox.packStart(userEntry, false, true, 1);
		
		this.passEntry = new Entry();
		passEntry.setVisibility(false);
		passEntry.setText(openLastFm.getPref("password"));
		Box passEntryBox = new Box(Orientation.HORIZONTAL,5);
		passEntryBox.packStart(new Label("Password:"), false, false, 1);
		passEntryBox.packStart(passEntry, false, true, 1);
		
		
		userSettingsBox.packStart(userEntryBox, false, true, 1);
		userSettingsBox.packStart(passEntryBox, false, true, 1);

		Frame userSettingsFrame = new Frame("User Settings");
		userSettingsFrame.add(userSettingsBox);
		userSettingsFrame.setBorderWidth(5);
		
		this.openLastFMBox.packStart(userSettingsFrame, false, true, 2);
		
		/*** Music Player ***/

        Frame playerSettingsFrame = new Frame("Player Settings");
		Box musicIntefaceBox = new Box(Orientation.HORIZONTAL, 5);
		musicIntefaceBox.setBorderWidth(5);

		interfaceComboBox = new ComboBoxText();
		interfaceComboBox.appendText("MPD Client");
		interfaceComboBox.appendText("Spotify (DBus)");
		interfaceComboBox.setActive(0);
		interfaceComboBox.connect((ComboBoxText.Changed) comboBox -> {
			if (interfaceComboBox.getActiveText().equals("MPD Client")) {
				playerSettingsFrame.showAll();
			} else {
				playerSettingsFrame.hide();
			}
        });

		musicIntefaceBox.packStart(new Label("Player Interface:"), false, false,1);
		musicIntefaceBox.packStart(interfaceComboBox, false, false,1);

		Frame musicInterfaceFrame = new Frame("Music Interface");
		musicInterfaceFrame.add(musicIntefaceBox);
		musicInterfaceFrame.setBorderWidth(5);

		Box playerSettingsBox = new Box(Orientation.VERTICAL,5);
		playerSettingsBox.setBorderWidth(5);
		
		this.hostEntry = new Entry();
		hostEntry.setText(openLastFm.getPref("host"));
		Box hostEntryBox = new Box(Orientation.HORIZONTAL,5);
		hostEntryBox.packStart(new Label("Host:"), false, false, 1);
		hostEntryBox.packStart(hostEntry, false, true, 1);
		
		this.portEntry = new Entry();
		portEntry.setText(openLastFm.getPref("port"));
		Box portEntryBox = new Box(Orientation.HORIZONTAL,5);
		portEntryBox.packStart(new Label("Port:"), false, false, 1);
		portEntryBox.packStart(portEntry, false, true, 1);
		
		
		playerSettingsBox.packStart(hostEntryBox, false, true, 1);
		playerSettingsBox.packStart(portEntryBox, false, true, 1);

		playerSettingsFrame.add(playerSettingsBox);
		playerSettingsFrame.setBorderWidth(5);

		this.musicPlayerBox.packStart(musicInterfaceFrame, false, true, 2);
		this.musicPlayerBox.packStart(playerSettingsFrame, false, true, 2);
		
		/*** BUTTONS ***/
		
		Button applyButton = new Button(Stock.APPLY);
		applyButton.setAlignHorizontal(Align.END);
		applyButton.setAlignVertical(Align.END);
		applyButton.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button arg0) {
				Language[] langs = Language.values();
				
				Language lang = langs[languageComboBox.getActive()];
				openLastFm.setPref("language", lang.name());
				openLastFm.setLangIso(lang);
				
				MusicInterface[] ifaces = MusicInterface.values();
				try {
					openLastFm.changeInterface(ifaces[interfaceComboBox.getActive()]);
				} catch (PlayerException e) {
					new ErrorWindow(e);
				}
				
				String key = apiKeyEntry.getText();
				openLastFm.setPref("apiKey", key);
				openLastFm.setApiKey(key);
				
				openLastFm.setPref("user", userEntry.getText());
				openLastFm.setPref("password", passEntry.getText());
				openLastFm.setPref("host", hostEntry.getText());
				openLastFm.setPref("port", portEntry.getText());
			}
		});
				
	
		Button okButton = new Button(Stock.OK);
		okButton.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button arg0) {
				applyButton.emitClicked();
				openLastFm.updatePref();
				destroy();
			}
		});
		
		Button cancelButton = new Button(Stock.CANCEL);
		cancelButton.connect(new Button.Clicked() {

			@Override
			public void onClicked(Button arg0) {
				openLastFm.loadPref();
				destroy();
			}
		});
		
		this.buttonsBox.packEnd(okButton, false, false, 0);
		this.buttonsBox.packEnd(cancelButton, false, false, 0);
		this.buttonsBox.packEnd(applyButton, false, false,0);
	}
	
	public String getLanguage() {
		String lang = this.languageComboBox.getActiveText();
		if (lang.equals("English")) {
			return "en";
		} else if (lang.equals("Deutsch")) {
			return "de";
		} else if (lang.equals("Español")) {
			return "es";
		} else if (lang.equals("Français")) {
			return "fr";
		} else if (lang.equals("Italiano")) {
			return "it";
		} else if (lang.equals("日本語")) {
			return "ja";
		} else if (lang.equals("Polski")) {
			return "pl";
		} else if (lang.equals("Português")) {
			return "pt";
		} else if (lang.equals("Русский")) {
			return "ru";
		} else if (lang.equals("Svenska")) {
			return "sv";
		} else if (lang.equals("Türkçe")) {
			return "tr";
		} else if (lang.equals("简体中文")) {
			return "zh";
		}
		return "en";
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	

}
