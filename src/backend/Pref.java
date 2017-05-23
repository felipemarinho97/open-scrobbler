package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import gui.Language;

public class Pref {
	private Properties prefs;
	private File path;
	
	public Pref() {
		this.prefs = new Properties();
		this.path = new File("openLastfm.properties");
		
		if (this.path.exists()) {
			this.loadPref();
		} else {
			this.createNewPref();
		}
	}

	public void loadPref() {
		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
			prefs.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createNewPref() {
		prefs.setProperty("apiKey", "");
		prefs.setProperty("language", Language.ENGLISH.name());
		prefs.setProperty("host", "localhost");
		prefs.setProperty("port", "6600");
		prefs.setProperty("user", "");
		prefs.setProperty("password", "");
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			prefs.store(out, "OpenLast.fm configuration file");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setPref(String key, String value) {
		prefs.setProperty(key, value);
	}
	
	public String getPref(String key) {
		return prefs.getProperty(key);
	}
	
	public void updatePref() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			prefs.store(out, "OpenLast.fm configuration file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
	}
}
