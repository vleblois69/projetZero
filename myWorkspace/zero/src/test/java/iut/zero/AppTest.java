package iut.zero;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	public AppTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

    public void testAddition()
    {
    	assertEquals(2, App.addition(1, 1));
    }
}
