package net.sensale.qp.quickbooks;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ShowTransformer.class})
public class ShowTransformerTest {
	
	@Test(expected = RuntimeException.class)
	public void testBadFile() throws IOException {
		spy(ShowTransformer.class);
		when(ShowTransformer.getsShowFileName()).thenReturn("bad");
	    ShowTransformer.loadShows();
	}
	
	@Test(expected = RuntimeException.class)
	public void testCallWithEmptyMap() {
		HashMap<String,String> map = null;
		ShowTransformer t = new ShowTransformer(map);
		when(t.getShowMap()).thenReturn(null);
		t.getShow("foo");
	}
	
//	@Test(expected = IOException.class)
//	public void testBadFileNameLoad
	

}
