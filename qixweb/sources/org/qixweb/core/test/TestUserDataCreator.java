package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.UserData;

public class TestUserDataCreator extends TestCase
{
    public void testEqualsOnEmptyUserData() 
    {
        UserData emptyUserData = UserDataCreator.EMPTY;
        UserData userData = new UserData();
        assertEquals(emptyUserData, userData);
    }

    public void testEMPTYIsASingleton() 
    {
        UserData userData = UserDataCreator.EMPTY;
        assertEquals(0, userData.size());
        
        UserData anotherUserData =  UserDataCreator.EMPTY;
        assertSame("EMPTY user data is a singleton", anotherUserData, userData);
    }

    public void testEMPTYIsImmutable() 
    {
        try
        {
            UserDataCreator.EMPTY.store("key", "value");
            fail("Cannot store: EMPTY user data is immutable");
        }
        catch (Exception e)
        {
        }

        try
        {
            UserDataCreator.EMPTY.storeLoggedUserName("username");
            fail("Cannot store: EMPTY user data is immutable");
        }
        catch (Exception e)
        {
        }
    }

}
