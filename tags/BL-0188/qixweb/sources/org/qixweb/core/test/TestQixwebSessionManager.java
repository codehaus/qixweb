package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebSessionManager extends ExtendedTestCase
{
    private SessionID itsSessionId;
    private QixwebSessionManager itsSessionManager;

    protected void setUp() throws Exception
    {
        itsSessionManager = new QixwebSessionManager();
        itsSessionId = new SessionID("a user session id", "/123456789");
    }

    public void testStoreAnRetrieveUserData()
    {
        UserData uglUserData = new UserData();
        uglUserData.storeLoggedUserName("ugl");

        itsSessionManager.storeUserData(itsSessionId, uglUserData);

        assertEquals("ONE user data should be stored", 1, itsSessionManager.numberOfUserData());
        assertEquals("The right user data should be stored", uglUserData, itsSessionManager.userDataFor(itsSessionId));
        assertEquals("Another user data should be stored (for multibrowsing)", 2, itsSessionManager.numberOfUserData());
    }

    public void testUserDataForStoresIfNotPresent()
    {
        assertEquals("No user data should be present", 0, itsSessionManager.numberOfUserData());

        SessionID notExistentSessionID = new SessionID("not present in SessionManager", "/123");
        UserData returnedUserData = itsSessionManager.userDataFor(notExistentSessionID);

        assertEquals("a NEW user data should be stored", new UserData(), returnedUserData);
        assertEquals("ONE user data should be stored", 1, itsSessionManager.numberOfUserData());
    }

    public void testUserSessionData()
    {
        UserData firstUserData = new UserData();
        String valueForFirstUser = "pollo paolino";
        firstUserData.store("key", valueForFirstUser);
        itsSessionManager.storeUserData(itsSessionId, firstUserData);

        SessionID secondUserID = new SessionID("ID2", "/456789123");
        UserData secondUserData = new UserData();
        String valueForSecondUser = "speedy pizza";
        secondUserData.store("key", valueForSecondUser);
        itsSessionManager.storeUserData(secondUserID, secondUserData);

        assertEquals("Two user data should be stored", 2, itsSessionManager.numberOfUserData());
        UserData temp = itsSessionManager.userDataFor(itsSessionId);
        assertEquals("The user data for first user is wrong", valueForFirstUser, temp.valueFor("key"));
        assertEquals("The user data for second user is wrong", valueForSecondUser, itsSessionManager.userDataFor(secondUserID).valueFor("key"));
    }

    public void testRemoveAllUserDataBelongingToSameSessionWithOneUserData()
    {
        UserData userData = new UserData();
        UserData anotherUserData = new UserData();
        itsSessionManager.storeUserData(itsSessionId, userData);

        itsSessionManager.removeAllUserDataBelongingToSameSessionOf(anotherUserData);
        assertEquals("No data must be deleted", 1, itsSessionManager.numberOfUserData());

        itsSessionManager.removeAllUserDataBelongingToSameSessionOf(userData);
        assertEquals("Data must be deleted", 0, itsSessionManager.numberOfUserData());
    }

    public void testRemoveAllUserDataBelongingToSameSessionWithManyUserData()
    {
        UserData userData = new UserData();
        UserData userDataOfSameSession = new UserData();
        UserData anotherUserData = new UserData();

        SessionID key = new SessionID("USER-SESSION", "12345");
        SessionID keyWithSameUserSession = new SessionID("USER-SESSION", "54321");
        SessionID anotherKey = new SessionID("ANOTHER-USER-SESSION", "999999");

        itsSessionManager.storeUserData(key, userData);
        itsSessionManager.storeUserData(keyWithSameUserSession, userDataOfSameSession);
        itsSessionManager.storeUserData(anotherKey, anotherUserData);
        UserData anUserData = userData;

        itsSessionManager.removeAllUserDataBelongingToSameSessionOf(anUserData);

        assertEquals("Two user data should be deleted", 1, itsSessionManager.numberOfUserData());
        assertEquals("One data must not be deleted", anotherUserData, itsSessionManager.userDataFor(anotherKey));
    }

    public void testMultiBrowsing()
    {
        SessionID currentSessionID = new SessionID("userSessionID", "currentPageID");
        UserData currentUserData = new UserData();
        currentUserData.storeLoggedUserName("ugl");
        itsSessionManager.storeUserData(currentSessionID, currentUserData);

        UserData returnedUserData = itsSessionManager.userDataFor(currentSessionID);

        assertEquals("another user data should be stored for multibrowsing", 2, itsSessionManager.numberOfUserData());
        assertEquals("the new user data should be equals to the current", currentUserData, returnedUserData);
    }
}
