package org.qixweb.util;

public class ClassUtil 
{
    public static String fullNameOf(Class aClass)
    {
        return aClass.getName();
    }
    
    public static String shortNameOf(Class aClass)
    {
        return fullNameOf(aClass).substring(fullNameOf(aClass).lastIndexOf(".") + 1);
    }
}
