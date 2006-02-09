package org.qixweb.util;

import org.apache.commons.lang.ClassUtils;

public class ClassUtil 
{
    public static String fullNameOf(Class aClass)
    {
        return aClass.getName();
    }
    
    public static String shortNameOf(Class aClass)
    {
        return ClassUtils.getShortClassName(aClass);
    }
}
