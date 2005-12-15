package org.qixweb.util;

import java.lang.reflect.*;


/**
 *  Warning! Don't use DeepEquals in inner classes, because it calls equals()
 *  on outer class that, in turn, calls equals() of inner class looping.
 */  
public class DeepEquals
{
    private static final Method ARE_VALUES_EQUAL_METHOD = compareMethodNamed("areValuesEqual");
    private static final Method ARE_ARRAY_EQUAL_METHOD = compareMethodNamed("areArrayEqual");
    private static final Method COMPARE_FIELDS_METHOD = compareMethodNamed("compareFields");
    
    private static final Method compareMethodNamed(String methodName)
    {
        try
        {
            return DeepEquals.class.getDeclaredMethod(methodName, new Class[] {Object.class, Object.class});
        }
        catch (Exception impossibleException)
        {
            throw new RuntimeException("A my private method doesn't exist!? Maybe someone has renamed it.", impossibleException);
        }
    }

    
    public static boolean equals(Object firstObject, Object secondObject) 
    {        
        try
        {
            return compareConsideringNull(firstObject, secondObject, COMPARE_FIELDS_METHOD);
        }
        catch (Exception e)
        {
            XpLogger.logException(e);
            return false;
        }
    }
    
	private static boolean haveSameClass(Object firstObject, Object secondObject)
	{
		return firstObject.getClass().equals(secondObject.getClass());
	}

    private static boolean compareFields(Object firstObject, Object secondObject) throws Exception
    {
        boolean areEquals = true;
        if (haveSameClass(firstObject, secondObject))
        {
            Class clazz = firstObject.getClass();
            while (clazz != null && areEquals)
            {
                areEquals = compareDeclaredFields(firstObject, secondObject, clazz);
                clazz = clazz.getSuperclass();
            }             
        }
        else
            areEquals = false;

        return areEquals;
    }

    private static boolean isArray(Field aField)
    {
        return aField.getType().isArray();
    }

	private static boolean compareDeclaredFields(Object firstObject, Object secondObject, Class clazz) throws Exception
	{
		boolean areEquals = true;
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length && areEquals; i++)
		{
			fields[i].setAccessible(true);
			if (!Modifier.isStatic(fields[i].getModifiers()))
			{
				Object firstValue = fields[i].get(firstObject);
				Object secondValue = fields[i].get(secondObject);
	
                Method compareMethod;
				if (isArray(fields[i]))
					compareMethod = ARE_ARRAY_EQUAL_METHOD;
                else
                    compareMethod = ARE_VALUES_EQUAL_METHOD;

                areEquals = compareConsideringNull(firstValue, secondValue, compareMethod);
			}
		}
	
		return areEquals;
	}

    private static boolean compareConsideringNull(Object firstValue, Object secondValue, Method compareMethod) throws Exception
    {
        if (firstValue == null)
            return secondValue == null;
        else if (secondValue == null)
            return false;
        else
            return ((Boolean)compareMethod.invoke(null, new Object[] {firstValue, secondValue})).booleanValue();
    }
    
    private static boolean areValuesEqual(Object firstValue, Object secondValue)
	{
        return firstValue.equals(secondValue);
	}

	private static boolean areArrayEqual(Object firstValue, Object secondValue)
    {
        boolean areEquals;
        if (Object.class.isAssignableFrom(firstValue.getClass().getComponentType()))
            areEquals = ArrayComparator.areEquals((Object[]) firstValue, (Object[]) secondValue);
        else
            areEquals = ArrayComparator.areEquals((byte[]) firstValue, (byte[]) secondValue);
        return areEquals;
    }
}
