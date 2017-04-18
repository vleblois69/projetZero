package iut.zero;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private App app;
	
	@Before
	public void initialisation()
	{
		app = new App();
	}
	
	@Test
	public void testAddition()
	{
		assertEquals(3,app.additionner(1,2));
	}
    
}
