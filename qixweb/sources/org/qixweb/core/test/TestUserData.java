package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.UserData;
import org.qixweb.util.EqualsBehaviourVerifier;

public class TestUserData extends TestCase
{
    private UserData itsUserData;

    protected void setUp() throws Exception
    {
        itsUserData = new UserData();
    }

    public void testEquals()
    {
        UserData sameUserData = new UserData();
        UserData differentUserData = new UserData();

        itsUserData.store("key1", "value1");
        sameUserData.store("key1", "value1");
        differentUserData.store("key2", "value2");
        EqualsBehaviourVerifier.check(itsUserData, sameUserData, differentUserData);
        EqualsBehaviourVerifier.checkHashCode(itsUserData, sameUserData);
    }

    public void testStoreRetrieveAndRemove()
    {
        String key = "key1";
        String expectedValue = "value1";

        itsUserData.store(key, expectedValue);
        assertEquals("the vaule should be available", expectedValue, itsUserData.valueFor(key));

        itsUserData.removeFor(key);
        assertNull("the vaule shouldn't be available any more", itsUserData.valueFor(key));
    }

    public void testStoreAndRetrieveForObjects()
    {
        String key = "key1";

        Object expectedValue = new Object();
        itsUserData.store(key, expectedValue);

        assertSame("wrong object", expectedValue, itsUserData.valueFor(key));

        key = "key2";

        expectedValue = new Integer(22);
        itsUserData.store(key, expectedValue);

        assertEquals("wrong integer", expectedValue, itsUserData.valueFor(key));
    }

    public void testStoreAndRetrieveAndRemoveUser()
    {
        itsUserData.storeLoggedUserName("VAD");

        assertEquals("User should be retrieved successfully from UserData", "VAD", itsUserData.loggedUserName());

        itsUserData.removeLoggedUserName();
        assertNull("User should be removed from UserData", itsUserData.loggedUserName());
    }

    public void testSize()
    {
        assertEquals(0, itsUserData.size());
        itsUserData.store("key", "value");
        assertEquals(1, itsUserData.size());
    }
}
