package backend;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class PrefTest {
	
	private Pref pref;

	@Before
	public void createProp() {
		this.pref = new Pref();
	}

	@Test
	public void testPref() {
		Pref pref2 = new Pref();
		assertTrue((new File("openLastfm.properties")).exists());
	}

	@Test
	public void testSetPref() {
		pref.setPref("apiKey", "15645");
	}

	@Test
	public void testGetPref() {
		pref.setPref("apiKey", "123456");
		assertEquals("123456", pref.getPref("apiKey"));
	}
	
	@Test
	public void testUpdatePref() {
		this.testGetPref();
		pref.updatePref();
		
		// Loading
		this.pref = new Pref();
		assertEquals("123456", pref.getPref("apiKey"));
	}

}
