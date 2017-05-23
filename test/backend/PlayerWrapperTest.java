package backend;

import java.net.UnknownHostException;

import backend.player.PlayerWrapper;
import exceptions.PlayerException;
import org.bff.javampd.exception.MPDConnectionException;
import org.bff.javampd.exception.MPDResponseException;
import org.junit.Test;

public class PlayerWrapperTest {

	@Test
	public void testMPDWrapper() throws UnknownHostException, MPDConnectionException, MPDResponseException, PlayerException {
		PlayerWrapper mw = new PlayerWrapper();
	}

}
