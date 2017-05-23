package backend;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import gui.Language;

public class InfoRequesterTest {

	@Test
	public void testGetArtistInfo() throws IOException {
		InfoRequester ir = new InfoRequester(Language.PORTUGUEASE, null);
	}

}
