package org.qixweb.core;

import java.io.Serializable;
import java.util.*;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.qixweb.block.CallGetter;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.util.ArrayComparator;
import org.qixweb.util.StringUtil;

public class QixwebWorkgroup implements Serializable 
{
    static final long serialVersionUID = 1L;
    
    private HashMap itsUsers;
    
    /**
     * @deprecated: use namesOf(Collection) instead of this  
     */
    public static String[] namesOf(QixwebUser[] someUsers)
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(someUsers);
        return (String[]) iterator.collect(new CallGetter("name"), String.class);
    }
    
    public static String[] namesOf(Collection someUsers)
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(someUsers);
        return (String[]) iterator.collect(new CallGetter("name"), String.class);
    } 
    
    public QixwebWorkgroup()
    {
        itsUsers = new HashMap();
    }

    public boolean add(QixwebUser aNewUser)
    {
        if (findUserBy(aNewUser.name()) != null)
            return false;
        else
        {
            itsUsers.put(aNewUser.name(), aNewUser);
            return true;
        }
    }
    
    public QixwebUser findUserBy(final String aUserName)
    {
        return (QixwebUser) itsUsers.get(aUserName);
    }

    public List allUsers()
    {
        List list = new ArrayList();
        list.addAll(itsUsers.values());
        return list;
    }    

    public void remove(QixwebUser[] someUsers)
    {
        for (int i = 0; i < someUsers.length; i++)
            itsUsers.remove(someUsers[i].name());
    }
           
    public boolean equals(Object aObj)
    {
        if (!(aObj instanceof QixwebWorkgroup))
           return false;
    
       QixwebWorkgroup otherFamily = (QixwebWorkgroup) aObj;
       String[] otherNames = namesOf(otherFamily.allUsers());
       String[] names = namesOf(allUsers());

       return ArrayComparator.areEqualsIgnoringOrder(names, otherNames);    
   }
    
   public int hashCode()
   {
        return HashCodeBuilder.reflectionHashCode(namesOf(allUsers()));
   }
    
   public String toString()
   {
       return StringUtil.join(namesOf(itsUsers.values()), ",");
   }
}
