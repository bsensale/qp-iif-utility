package net.sensale.qp.quickbooks;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ShowTransformer.class})
public class ShowTransformerTest {
	
	@Test(expected = RuntimeException.class)
	public void testBadFile() {
		mockStatic(ShowTransformer.class);
		when(ShowTransformer.getsShowFileName()).thenReturn("bad");
		ShowTransformer.getInstance().getShow("foo");
	}
	
	@Test(expected = RuntimeException.class)
	public void testCallWithEmptyMap() {
		mockStatic(ShowTransformer.class);
		HashMap<String,String> map = null;
		ShowTransformer t = new ShowTransformer(map);
		t.getShow("foo");
		
	}
	

}
