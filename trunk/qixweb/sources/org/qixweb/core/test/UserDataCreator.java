package org.qixweb.core.test;

import org.qixweb.core.UserData;

public class UserDataCreator
{
    public static final UserData EMPTY = new EmptyImmutableUserData();
    
    private static class EmptyImmutableUserData extends UserData
    {
        public EmptyImmutableUserData()
        {
            super();
        }
        
        public EmptyImmutableUserData(UserData anotherUserData)
        {
            this();
        }
        
        public void store(String key, Object value)
        {
            throw new UnsupportedOperationException();
        }

        public void storeLoggedUserName(String username)
        {
            throw new UnsupportedOperationException();
        }
    }
}
