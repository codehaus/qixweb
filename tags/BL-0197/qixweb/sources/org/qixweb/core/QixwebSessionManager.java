package org.qixweb.core;

import java.util.Hashtable;

import org.qixweb.block.*;

public class QixwebSessionManager
{
    private Hashtable itsUserDataTable;

    public QixwebSessionManager()
    {
        itsUserDataTable = new Hashtable();
    }

    public int numberOfUserData()
    {
        return itsUserDataTable.size();
    }

    public UserData userDataFor(SessionID aKey)
    {
        if (!itsUserDataTable.containsKey(aKey.currentPageSessionID()))
        {
            UserData userData = new UserData();
            itsUserDataTable.put(aKey.nextPageSessionID(), userData);
            return userData;
        }
        else
        {
            makeMultiBrowsable(aKey.currentPageSessionID(), aKey.nextPageSessionID());
            return (UserData) itsUserDataTable.get(aKey.nextPageSessionID());
        }
    }

    public void storeUserData(SessionID aKey, UserData aUserData)
    {
        itsUserDataTable.put(aKey.currentPageSessionID(), aUserData);
    }

    public void removeAllUserDataBelongingToSameSessionOf(UserData anUserData)
    {
        String matchedKey = corrispondentKeyOf(anUserData);

        if (matchedKey != null)
        {
            String[] keysToRemove = retrieveKeysWithSameUserSessionOf(matchedKey);
            removeUserDataCorrispondentTo(keysToRemove);
        }
    }

    private void removeUserDataCorrispondentTo(String[] someKeys)
    {
        LightInternalIterator.createOn(someKeys).forEach(new Procedure()
        {
            public void run(Object each)
            {
                itsUserDataTable.remove(each);
            }
        });
    }

    private String[] retrieveKeysWithSameUserSessionOf(String aKey)
    {
        final String userSessionID = SessionID.extractUserSessionIDFrom(aKey);

        return (String[]) LightInternalIterator.createOn(itsUserDataTable.keys()).collect(new Function()
        {
            public Object eval(Object each)
            {
                String currentKey = (String) each;
                return currentKey.regionMatches(0, userSessionID, 0, userSessionID.length()) ? each : null;
            }
        }, String.class);
    }

    private String corrispondentKeyOf(final UserData anUserData)
    {
        return (String) LightInternalIterator.createOn(itsUserDataTable.keys()).detect(new Predicate()
        {
            public boolean is(Object each)
            {
                return itsUserDataTable.get(each) == anUserData;
            }
        });
    }

    private void makeMultiBrowsable(String currentPageSessionID, String nextPageSessionID)
    {
        UserData currentUserData = (UserData) itsUserDataTable.get(currentPageSessionID);

        UserData nextUserData = new UserData(currentUserData);
        itsUserDataTable.put(nextPageSessionID, nextUserData);
    }

}
