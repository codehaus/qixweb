package org.qixweb.block;

import java.lang.reflect.Array;
import java.util.*;

public abstract class LightInternalIterator
{
    private boolean itsAlreadyBeenUsed;

    protected LightInternalIterator()
    {
        itsAlreadyBeenUsed = false;
    }

    protected void checkAlreadyBeenUsed()
    {
        if (itsAlreadyBeenUsed)
            throw new IllegalStateException("LightInternalIterator can be used just once");
        itsAlreadyBeenUsed = true;
    }

    public static LightInternalIterator createOn(Object[] aCollection)
    {
        return new LightInternalIteratorOnArray(aCollection);
    }

    public static LightInternalIterator createOn(Object[] firstCollection, Object[] secondCollection)
    {
        int index = 0;
        Object[][] product = new Object[firstCollection.length * secondCollection.length][2];

        for (int i = 0; i < firstCollection.length; i++)
            for (int j = 0; j < secondCollection.length; j++)
                product[index++] = new Object[] { firstCollection[i], secondCollection[j] };

        return new LightInternalIteratorOnArray(product);
    }

    public static LightInternalIterator createOn(Enumeration anEnumeration)
    {
        return new LightInternalIteratorOnEnumeration(anEnumeration);
    }

    public static LightInternalIterator createOn(Iterator anIterator)
    {
        return new LightInternalIteratorOnIterator(anIterator);
    }

    public static LightInternalIterator createOn(Collection aCollection)
    {
        return new LightInternalIteratorOnIterator(aCollection.iterator());
    }

    public abstract Object currentValue();

    public abstract boolean hasNext();

    public Object[] collect(Function aFunction, Class aClassType)
    {
        return collectWithoutException(aFunction, aClassType, false);
    }

    public List collectAsList(Function aFunction)
    {
        checkAlreadyBeenUsed();

        List theCollectedElements = new ArrayList();
        while (hasNext())
        {
            Object theEvaluedObject = aFunction.eval(currentValue());
            if (theEvaluedObject != null)
                theCollectedElements.add(theEvaluedObject);
        }

        return theCollectedElements;
    }

    public int count(Predicate aPredicate)
    {
        checkAlreadyBeenUsed();

        int accumulated = 0;
        while (hasNext())
        {
            if (aPredicate.is(currentValue()))
                accumulated++;
        }
        return accumulated;
    }

    public Object detect(Predicate aPredicate)
    {
        checkAlreadyBeenUsed();

        Object theDetectedObject = null;

        while (hasNext())
        {
            Object theObject = currentValue();
            if (aPredicate.is(theObject))
            {
                theDetectedObject = theObject;
                break;
            }
        }
        return theDetectedObject;
    }

    public void forEach(Procedure aProcedure)
    {
        checkAlreadyBeenUsed();

        while (hasNext())
            aProcedure.run(currentValue());
    }

    public Object[] select(Predicate aPredicate, Class aClassType)
    {
        checkAlreadyBeenUsed();

        Vector theSelectedElements = new Vector();

        while (hasNext())
        {
            Object each = currentValue();
            if (aPredicate.is(each))
                theSelectedElements.add(each);
        }

        Object[] theSelectedElementsArray = (Object[]) Array.newInstance(aClassType, theSelectedElements.size());
        theSelectedElements.copyInto(theSelectedElementsArray);

        return theSelectedElementsArray;
    }

    public List selectAsList(Predicate aPredicate)
    {
        checkAlreadyBeenUsed();
        List theSelectedElements = new ArrayList();

        while (hasNext())
        {
            Object each = currentValue();
            if (aPredicate.is(each))
                theSelectedElements.add(each);
        }
        return theSelectedElements;
    }
    
    public Object sumUp(Object aRunningValue, BinaryFunction aFunction)
    {
        checkAlreadyBeenUsed();

        Object theAccumulatedValue = aRunningValue;
        while (hasNext())
            theAccumulatedValue = aFunction.eval(theAccumulatedValue, currentValue());

        return theAccumulatedValue;
    }

    public int intSumUp(int aRunningValue, IntBinaryFunction aFunction)
    {
        checkAlreadyBeenUsed();

        int theAccumulatedValue = aRunningValue;
        while (hasNext())
            theAccumulatedValue = aFunction.eval(theAccumulatedValue, currentValue());

        return theAccumulatedValue;
    }

    public double doubleSumUp(double aRunningValue, DoubleBinaryFunction aFunction)
    {
        checkAlreadyBeenUsed();

        double theAccumulatedValue = aRunningValue;
        while (hasNext())
            theAccumulatedValue = aFunction.eval(theAccumulatedValue, currentValue());

        return theAccumulatedValue;
    }

    public Object[] collectWithoutDuplications(Function aFunction, Class aClassType)
    {
        return collectWithoutException(aFunction, aClassType, true);
    }

    public Object[] collectWithoutDuplicationsWithException(FunctionWithException aFunction, Class aClassType) throws Exception
    {
        return collectWithException(aFunction, aClassType, true);
    }

    private Object[] collectWithoutException(final Function aFunction, Class aClassType, final boolean withoutDuplications)
    {
        FunctionWithException functionWithException = new FunctionWithException()
        {
            public Object eval(Object each) throws Exception
            {
                return aFunction.eval(each);
            }
        };

        try
        {
            return collectWithException(functionWithException, aClassType, withoutDuplications);
        }
        catch (Exception mustBeRuntimeException)
        {
            throw (RuntimeException) mustBeRuntimeException;
        }
    }

    private Object[] collectWithException(FunctionWithException aFunction, Class aClassType, boolean withoutDuplications) throws Exception
    {
        checkAlreadyBeenUsed();

        Vector theCollectedElements = new Vector();

        while (hasNext())
        {
            Object theEvaluedObject = aFunction.eval(currentValue());
            if (theEvaluedObject != null)
                if (withoutDuplications && theCollectedElements.contains(theEvaluedObject))
                    continue;
                else
                    theCollectedElements.add(theEvaluedObject);
        }

        Object[] theCollectedElementsArray = (Object[]) Array.newInstance(aClassType, theCollectedElements.size());
        theCollectedElements.copyInto(theCollectedElementsArray);

        return theCollectedElementsArray;
    }

    public Object[] collectWithException(FunctionWithException aFunction, Class aClassType) throws Exception
    {
        return collectWithException(aFunction, aClassType, false);
    }

}
