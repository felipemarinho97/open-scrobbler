package backend;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.gnome.gdk.Pixbuf;

import gui.Language;

public class InfoRequester {
	private String artistName;
	private String langIso;
	private String apiKey;
	
    private static final String API_URL = "http://ws.audioscrobbler.com/2.0/";
  
	public InfoRequester(Language lang, String apiKey) {
		this.langIso = lang.getIso();
		this.apiKey = apiKey;
	}
	
	public String getLangIso() {
		return langIso;
	}

	public void setLangIso(Language lang) {
		this.langIso = lang.getIso();
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}




	
	public static Pixbuf downloadImage(String surl) throws IOException {
		URL url = new URL(surl);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = bis.read(buf))) {
			out.write(buf, 0, n);
		}

		byte[] response = out.toByteArray();
		
		bis.close();
		out.close();
		
		return new Pixbuf(response);
	}

	public static Pixbuf downloadImage(String albumImageURL, int i, int j) throws IOException {
		if (albumImageURL == null || albumImageURL.trim().isEmpty()) {
			return new Pixbuf("blank_album.png",i,j,true);
		}

		URL url = new URL(albumImageURL);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = bis.read(buf))) {
			out.write(buf, 0, n);
		}

		byte[] response = out.toByteArray();
		
		bis.close();
		out.close();
		
		return new Pixbuf(response, i, j, true);
	}

	public static Pixbuf loadPixbuf(String string) throws IOException {
		File url = new File(string);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(url));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = bis.read(buf))) {
			out.write(buf, 0, n);
		}

		byte[] response = out.toByteArray();
		
		bis.close();
		out.close();
		
		return new Pixbuf(response);
	}
	
}
