package org.qixweb.core.test;

import org.qixweb.core.SessionID;
import org.qixweb.time.test.FakeTimeProvider;
import org.qixweb.util.test.ExtendedTestCase;


public class TestSessionID extends ExtendedTestCase
{
	private FakeTimeProvider itsFakeTimeProvider;

    public void testNextIds()
	{
        SessionID sessionID = new SessionID("user session id", "/1234567890", itsFakeTimeProvider);
	    
		String expectedNextPageID = String.valueOf(itsFakeTimeProvider.now().getTimeInMillis());
        
		assertEquals(expectedNextPageID, sessionID.nextPageID());
		assertEquals("user session id_"+expectedNextPageID, sessionID.nextPageSessionID());
	}

	public void testCurrentIds()
	{
        SessionID sessionID = new SessionID("user session id", "/1234567890", itsFakeTimeProvider);
	    
		assertEquals("user session id", sessionID.userSessionID());
		
		assertEquals("1234567890", sessionID.currentPageID());
		assertEquals("user session id_1234567890", sessionID.currentPageSessionID());
	}
	
	protected void setUp() throws Exception
    {
	    itsFakeTimeProvider = new FakeTimeProvider();
	    itsFakeTimeProvider.simulateTime(10, 3, 2004, 10, 30, 56);
    }

}
