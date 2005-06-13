package org.qixweb.block.test;

import org.qixweb.block.*;
import org.qixweb.util.ArrayAsserter;

import junit.framework.TestCase;


public abstract class ParameterizedTestLightInternalIterator extends TestCase
{

	private void assertCollectOn(LightInternalIterator anIterator, String aMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.collect(new DoNothingBlock(), Object.class);
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a Collect after a " + aMethodName, exceptionHasBeenThrown);
	}
	private void assertCountOn(LightInternalIterator anIterator, String aMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.count(new AlwaysTruePredicate());
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a Count after a " + aMethodName, exceptionHasBeenThrown);
	}
	private void assertDetectOn(LightInternalIterator anIterator, String aMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.detect(new AlwaysFalsePredicate());
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a Detect after a " + aMethodName, exceptionHasBeenThrown);
	}
	private void assertForEachOn(LightInternalIterator anIterator, String aMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.forEach(new DoNothingBlock());
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a ForEach after a " + aMethodName, exceptionHasBeenThrown);
	}
	private void assertIntegerArrayType(Object[] anArray)
	{
		assertTrue("the array must be of Integer type", anArray instanceof Integer[]);
	}
	private void assertNumberArrayType(Object[] anArray)
	{
		assertEquals((new Number[0]).getClass(), anArray.getClass());
	}
	private void assertOneAndOnlyOneIterationOn(LightInternalIterator anIterator, String anIteratorName)
	{
		assertCollectOn(anIterator, anIteratorName);
		assertSelectOn(anIterator, anIteratorName);
		assertCountOn(anIterator, anIteratorName);
		assertDetectOn(anIterator, anIteratorName);
		assertForEachOn(anIterator, anIteratorName);
		assertSumUpOn(anIterator, anIteratorName);
	}
	private void assertSelectOn(LightInternalIterator anIterator, String aMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.select(new AlwaysFalsePredicate(), Object.class);
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a Select after a " + aMethodName, exceptionHasBeenThrown);
	}
	private void assertSumUpOn(LightInternalIterator anIterator, String firstMethodName)
	{
		boolean exceptionHasBeenThrown = false;

		try
		{
			anIterator.sumUp(null, new DoNothingBlock());
		}
		catch (IllegalStateException isex)
		{
			exceptionHasBeenThrown = true;
		}

		assertTrue("It is not possible to invoke a SumUp after a " + firstMethodName, exceptionHasBeenThrown);
	}
	abstract public LightInternalIterator createIterator(Object[] theArray);
	public void testCollectManyElementsIntoADifferentCollectionType()
	{
		Object[] theElements = { "theWrongValue", "theRightValue", "anotherWrongValue", "anotherRightValue", };

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theCollectedElements = theIterator.collect(new Function()
		{
			public Object eval(Object each)
			{
				if (each.toString().endsWith("RightValue"))
					return new Integer(1);
				else
					return null;
			}
		}, Integer.class);
		assertIntegerArrayType(theCollectedElements);
		assertEquals(new Integer(1), theCollectedElements[0]);
		assertEquals(new Integer(1), theCollectedElements[1]);
	}
	public void testCollectManyElementsRespectingTheOriginalOrder()
	{
		Object[] theElements = { "theWrongValue", "theRightValue", "anotherWrongValue", "anotherRightValue", };

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theCollectedElements = theIterator.collect(new Function()
		{
			public Object eval(Object each)
			{
				if (each.toString().endsWith("RightValue"))
					return each;
				else
					return null;
			}
		}, Object.class);
		assertEquals(2, theCollectedElements.length);
		assertSame(theElements[1], theCollectedElements[0]);
		assertSame(theElements[3], theCollectedElements[1]);
	}
	
	public void testCollectWithoutDuplications()
	{
		Object[] theElements = { new Integer(1), new Integer(2), new Integer(1) };

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theCollectedElements = theIterator.collectWithoutDuplications(new Function()
		{
			public Object eval(Object each)
			{
				return each;
			}
		}, Object.class);
		assertEquals(2, theCollectedElements.length);
		assertEquals(theElements[0], theCollectedElements[0]);
		assertEquals(theElements[1], theCollectedElements[1]);
	}

    public void testCollectManyElements()
    {
        Object[] theElements = { new Integer(1), new Integer(2), new Integer(3) };

        LightInternalIterator theIterator = createIterator(theElements);
        Object[] theCollectedElements = theIterator.collectWithoutDuplications(new Function()
        {
            public Object eval(Object each)
            {
                return new Integer(((Integer)each).intValue()+1);
            }
        }, Object.class);
        ArrayAsserter.assertEquals(new Object[] { new Integer(2), new Integer(3), new Integer(4) }, theCollectedElements);
    }

    public void testCollectExcludeNull()
    {
        Object[] theElements = { new Integer(1), new Integer(3), new Integer(9) };

        LightInternalIterator theIterator = createIterator(theElements);
        Object[] theCollectedElements = theIterator.collectWithoutDuplications(new Function()
        {
            public Object eval(Object each)
            {
                int intValue = ((Integer)each).intValue();
                if (intValue == 1)
                    return null;
                else
                    return new Integer(intValue+1);
            }
        }, Object.class);
        ArrayAsserter.assertEquals(new Object[] { new Integer(4), new Integer(10) }, theCollectedElements);
    }
    
    
    public void testCollectWithoutDuplicationsWithExceptions() throws Exception
    {
        Object[] theElements = { new Integer(1), new Integer(2), new Integer(1) };

        LightInternalIterator theIterator = createIterator(theElements);
        Object[] theCollectedElements = null;
        try
        {
            theCollectedElements = theIterator.collectWithoutDuplicationsWithException(new FunctionWithException()
            {
                public Object eval(Object each) throws Exception
                {
                    if (each.equals(new Integer(3)))
                        throw new NumberFormatException();
                    return each;
                }
            }, Object.class);
        }
        catch(NumberFormatException e)
        {}
        assertEquals(2, theCollectedElements.length);
        assertEquals(theElements[0], theCollectedElements[0]);
        assertEquals(theElements[1], theCollectedElements[1]);
    }   
    public void testCollectWithoutDuplicationsCatchingExceptions() throws Exception
    {
        Object[] theElements = { new Integer(3) };

        LightInternalIterator theIterator = createIterator(theElements);
        Object[] theCollectedElements = null;
        try
        {
            theCollectedElements = theIterator.collectWithoutDuplicationsWithException(new FunctionWithException()
            {
                public Object eval(Object each) throws Exception
                {
                    if (each.equals(new Integer(3)))
                        throw new NumberFormatException();
                    return each;
                }
            }, Object.class);
            fail("NumberFormatException expected");
        }
        catch(NumberFormatException e)
        {}
        assertNull(theCollectedElements);
    }   
	public void testCollectNoElements()
	{
		Object[] theEmptyArray = new Object[0];
		LightInternalIterator theIterator = createIterator(theEmptyArray);
		Object[] theCollectedElements = theIterator.collect(new Function()
		{
			public Object eval(Object anObject)
			{
				fail("Empty collection: the eval method shouldn't be executed");
				return null;
			}
		}, Object.class);

		assertEquals(0, theCollectedElements.length);
	}
	public void testCollectNotCatchingRunTimeException()
	{
		try
		{
			createIterator(new String[] {"Pippo"}).collect(new Function()
			{
				public Object eval(Object each)
				{
					throw new RuntimeException("Must not be catched");
				}
			}, Object.class);
			fail("RuntimeException must be raised");
		}
		// @PMD:REVIEWED:ExceptionAsFlowControl: by bop on 1/25/05 4:56 PM
		catch (RuntimeException e)
		{
		}
	}
	
	public void testCollectOneElementWithException()
	{
		Object[] theElements = { new Object()};

		LightInternalIterator theIterator = createIterator(theElements);
		
		try
		{
			theIterator.collectWithException(new FunctionWithException()
			{
				public Object eval(Object each) throws Exception
				{
					throw new NumberFormatException();
				}
			}, Object.class);
			fail("Should throw an exception");
			
		}
		catch (Exception e)
		{
		}
	}
	
	
	public void testCountBooleanElementsThatAreTrue()
	{
		Boolean[] theArrayWithBooleanElements = { Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE };
		LightInternalIterator theIterator = createIterator(theArrayWithBooleanElements);
		assertEquals(5, theIterator.count(new Predicate()
		{
			public boolean is(Object anObject)
			{
				return ((Boolean) anObject).booleanValue();
			}
		}));
	}
	public void testCountManyElements()
	{
		Object[] theArrayWithManyElements = new Object[34];
		LightInternalIterator theIterator = createIterator(theArrayWithManyElements);
		assertEquals(34, theIterator.count(new AlwaysTruePredicate()));
	}
	public void testCountNoElements()
	{
		Object[] theEmptyArray = new Object[0];
		LightInternalIterator theIterator = createIterator(theEmptyArray);
		assertEquals(0, theIterator.count(new AlwaysTruePredicate()));
	}
	public void testCountOneElement()
	{
		Object[] theOneElementArray = new Object[1];
		LightInternalIterator theIterator = createIterator(theOneElementArray);
		assertEquals(1, theIterator.count(new AlwaysTruePredicate()));
	}
	public void testDetectManyElements()
	{
		Object[] someElements = { new Integer(1), new Integer(2), new Integer(3), };

		LightInternalIterator theIterator = createIterator(someElements);
		Object theMatchingElement = theIterator.detect(new Predicate()
		{
			public boolean is(Object each)
			{
				return each.equals(new Integer(2));
			}
		});
		assertSame(someElements[1], theMatchingElement);
	}
	public void testDetectNoElements()
	{
		Object[] theElements = new Object[0];

		LightInternalIterator theIterator = createIterator(theElements);
		Object theMatchingElement = theIterator.detect(new Predicate()
		{
			public boolean is(Object each)
			{
				fail("Empty collection: the is method shouldn't be executed");
				return false;
			}
		});
		assertNull(theMatchingElement);
	}
	public void testDetectOneElement()
	{
		Object[] theElements = { new Object()};

		LightInternalIterator theIterator = createIterator(theElements);
		Object theMatchingElement = theIterator.detect(new AlwaysTruePredicate());
		assertSame(theElements[0], theMatchingElement);
	}
	public void testForEachManyElements()
	{
		final Object[] manyElements = { new Integer(1), new Integer(2), new Integer(3), new Integer(4)};
		final boolean[] gotIntoTheCollection = new boolean[4];

		LightInternalIterator theIterator = createIterator(manyElements);
		theIterator.forEach(new Procedure()
		{
			private int theElementNum = 0;
			public void run(Object anObject)
			{
				assertSame(manyElements[theElementNum], anObject);
				gotIntoTheCollection[theElementNum] = true;
				theElementNum++;
			}
		});

		for (int i = 0; i < gotIntoTheCollection.length; i++)
		{
			assertTrue("It must iterate on element " + i, gotIntoTheCollection[i]);
		}
	}
	public void testForEachNoElements()
	{
		Object[] theEmptyArray = new Object[0];
		LightInternalIterator theIterator = createIterator(theEmptyArray);
		theIterator.forEach(new Procedure()
		{
			public void run(Object anObject)
			{
				fail("Empty collection: the run method shouldn't be executed");
			}
		});
	}
	public void testForEachOneElement()
	{
		final Object[] theOneElementArray = {new Integer(1)};
		final boolean[] gotIntoTheCollection = new boolean[1];

		LightInternalIterator theIterator = createIterator(theOneElementArray);
		theIterator.forEach(new Procedure()
		{
			public void run(Object anObject)
			{
				assertSame(theOneElementArray[0], anObject);
				gotIntoTheCollection[0] = true;
			}
		});
		assertTrue("It must iterate into the collection for each element", gotIntoTheCollection[0]);
	}
	public void testOneAndOnlyOneIterationAfterCollect()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.collect(new DoNothingBlock(), Object.class);

		assertOneAndOnlyOneIterationOn(theIterator, "Collect");
	}
	public void testOneAndOnlyOneIterationAfterCount()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.count(new AlwaysTruePredicate());

		assertOneAndOnlyOneIterationOn(theIterator, "Count");
	}
	public void testOneAndOnlyOneIterationAfterDetect()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.detect(new AlwaysFalsePredicate());

		assertOneAndOnlyOneIterationOn(theIterator, "Detect");
	}
	public void testOneAndOnlyOneIterationAfterForEach()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.forEach(new DoNothingBlock());

		assertOneAndOnlyOneIterationOn(theIterator, "ForEach");
	}
	public void testOneAndOnlyOneIterationAfterSelect()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.select(new AlwaysFalsePredicate(), Object.class);

		assertOneAndOnlyOneIterationOn(theIterator, "Select");
	}
	public void testOneAndOnlyOneIterationAfterSumUp()
	{
		LightInternalIterator theIterator = createIterator(new Object[0]);

		theIterator.sumUp(null, new DoNothingBlock());

		assertOneAndOnlyOneIterationOn(theIterator, "SumUp");
	}
	public void testSelectCheckingReturnedCollectionType()
	{
		Object[] theElements = { new Integer(12), new Float(34.56), new Long(879883432), };

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theCollectedElements = theIterator.select(new AlwaysTruePredicate(), Number.class);
		assertNumberArrayType(theCollectedElements);
	}
	public void testSelectManyElementsRespectingTheOriginalOrder()
	{
		Object[] theElements = { "theWrongValue", "theRightValue", "anotherWrongValue", "anotherRightValue" };

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theSelectedElements = theIterator.select(new Predicate()
		{
			public boolean is(Object each)
			{
				return each.toString().endsWith("RightValue");
			}
		}, Object.class);
		assertEquals(2, theSelectedElements.length);
		assertSame(theElements[1], theSelectedElements[0]);
		assertSame(theElements[3], theSelectedElements[1]);
	}
	public void testSelectNoElements()
	{
		Object[] theEmptyArray = new Object[0];
		LightInternalIterator theIterator = createIterator(theEmptyArray);
		Object[] theSelectedElements = theIterator.select(new Predicate()
		{
			public boolean is(Object anObject)
			{
				fail("Empty collection: the is method shouldn't be executed");
				return false;
			}
		}, Object.class);

		assertEquals(0, theSelectedElements.length);
	}
	public void testSelectOneElement()
	{
		Object[] theElements = { new Object()};

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theSelectedElements = theIterator.select(new AlwaysTruePredicate(), Object.class);

		assertEquals(1, theSelectedElements.length);
		assertSame(theElements[0], theSelectedElements[0]);
	}
	public void testSumUpOnManyElements()
	{
		Object[] manyStrings = { "pippo", " - ", "pluto"};
		LightInternalIterator theIterator = createIterator(manyStrings);
		Object result = theIterator.sumUp("Some names: ", new BinaryFunction()
		{
			public Object eval(Object aRunningValue, Object each)
			{
				return (String) aRunningValue + (String) each;
			}
		});
		
		assertEquals("Some names: pippo - pluto", result);
	}
	public void testSumUpOnNoElements()
	{
		Object[] theEmptyArray = new Object[0];
		LightInternalIterator theIterator = createIterator(theEmptyArray);
		String runningValue = "Start string";
		String expected = "Start string";
		String result = (String) theIterator.sumUp(runningValue, new BinaryFunction()
		{
			public Object eval(Object aRunningValue, Object each)
			{
				return "";
			}
		});
		assertEquals("It should not iterate on empty array", expected, result);
		assertSame("It should return just the running value", runningValue, result);
	}

	public void testIntSumUpNoElements()
	{
		Object[] emptyArray = new Object[0];
		
		int result = createIterator(emptyArray).intSumUp(8713, new IntBinaryFunction()
		{
			public int eval(int aRunningValue, Object each)
			{
				return 1;
			}
		});
		
		assertEquals("It should not iterate on empty array", 8713, result);
	}
	public void testIntSumUpOnManyElements()
	{
		Object[] someIntegers = { new Integer(1), new Integer(3), new Integer(7), new Integer(11), new Integer(13)};
		int result = createIterator(someIntegers).intSumUp(10, new IntBinaryFunction()
		{
			public int eval(int aRunningValue, Object each)
			{
				return ((Integer)each).intValue() + aRunningValue;
			}
		});
		
		assertEquals(10+35, result);
	}
	
	
	public void testDoubleSumUpOnManyElements()
	{
		Object[] someDoubles = { new Double(1.0), new Double(3.5), new Double(7.1)};
		double result = createIterator(someDoubles).doubleSumUp(2.2, new DoubleBinaryFunction()
		{
			public double eval(double aRunningValue, Object each)
			{
				return ((Double) each).doubleValue() + aRunningValue;
			}
		});

		assertEquals(13.8, result,0.0);
	}	

	public void testDetectWithEqualsPredicate()
	{
		Integer[] theElements = { new Integer(1), new Integer(2), new Integer(3), new Integer(4)};

		LightInternalIterator theIterator = createIterator(theElements);
		Object theMatchingElement = theIterator.detect(new EqualsPredicate(new Integer(3)));
		assertEquals(theElements[2], theMatchingElement);
	}

	public void testSelectWithEqualsPredicate()
	{
		Integer[] theElements = {new Integer(1), new Integer(2), new Integer(3), new Integer(1), new Integer(1), new Integer(4), new Integer(1)};

		LightInternalIterator theIterator = createIterator(theElements);
		Object[] theMatchingElements = theIterator.select(new EqualsPredicate(new Integer(1)), Integer.class);
		assertEquals(4, theMatchingElements.length);
	}
// @PMD:REVIEWED:ExcessivePublicCount: by bop on 1/26/05 4:57 PM
}
