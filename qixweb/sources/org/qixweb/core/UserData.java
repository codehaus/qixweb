package org.qixweb.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.qixweb.util.DeepEquals;

public class UserData implements Serializable
{
    public static final UserData EMPTY = new UserData();

    private static final String LOGGED_USERNAME_KEY = "LOGGED_USERNAME_KEY";
    private Map itsData;

    public UserData()
    {
        itsData = new HashMap();
    }

    public UserData(UserData anotherUserData)
    {
        itsData = new HashMap(anotherUserData.itsData);
    }

    public int size()
    {
        return itsData.size();
    }

    public Object valueFor(String aKey)
    {
        return itsData.get(aKey);
    }

    public void store(String key, Object value)
    {
        itsData.put(key, value);
    }

    public void removeFor(String key)
    {
        itsData.remove(key);
    }

    public String loggedUserName()
    {
        return (String) itsData.get(LOGGED_USERNAME_KEY);
    }

    public void removeLoggedUserName()
    {
        itsData.remove(LOGGED_USERNAME_KEY);
    }

    public void storeLoggedUserName(String username)
    {
        store(LOGGED_USERNAME_KEY, username);
    }

    public boolean equals(Object obj)
    {
        return DeepEquals.equals(this, obj);
    }

    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
