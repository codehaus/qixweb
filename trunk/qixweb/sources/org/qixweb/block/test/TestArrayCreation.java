package org.qixweb.block.test;



import junit.framework.TestCase;

import org.qixweb.block.ArrayCreator;
import org.qixweb.block.Block;


public class TestArrayCreation extends TestCase
{
	private Block itsCreatingAlphabetBlock;
	private Block itsCreatingMultipleOfThreeBlock;
	
	
	protected void setUp()
	{
		itsCreatingAlphabetBlock = new Block()
		{
			public Object eval(Object anIndex)
			{
				Integer theIndex = (Integer) anIndex;
				Character theChar = new Character((char) ('A' + theIndex.intValue()));
				return theChar.toString();
			}
		};
		itsCreatingMultipleOfThreeBlock = new Block()
		{
			public Object eval(Object anIndex)
			{
				Integer theIndex = (Integer) anIndex;
				Integer theMultiple = new Integer(3 * (theIndex.intValue() + 1));
				return theMultiple;
			}
		};
	}
	public void testArrayWithManyCellsMultipleOfThree()
	{
		Integer[] theExpectedArray = { new Integer(3), new Integer(6), new Integer(9), new Integer(12)};

		ArrayCreator theCreator = new ArrayCreator(4, Integer.class);
		Integer[] theCreatedArray = (Integer[]) theCreator.create(itsCreatingMultipleOfThreeBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testArrayWithManyCellsOfAlphabet()
	{
		String[] theExpectedArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

		ArrayCreator theCreator = new ArrayCreator(9, String.class);
		String[] theCreatedArray = (String[]) theCreator.create(itsCreatingAlphabetBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testArrayWithOneCellMultipleOfThree()
	{
		Integer[] theExpectedArray = { new Integer(3)};

		ArrayCreator theCreator = new ArrayCreator(1, Integer.class);
		Integer[] theCreatedArray = (Integer[]) theCreator.create(itsCreatingMultipleOfThreeBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testArrayWithOneCellOfAlphabet()
	{
		String[] theExpectedArray = { "A" };

		ArrayCreator theCreator = new ArrayCreator(1, String.class);
		String[] theCreatedArray = (String[]) theCreator.create(itsCreatingAlphabetBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testArrayWithThreeCellsOfAlphabet()
	{
		String[] theExpectedArray = { "A", "B", "C" };

		ArrayCreator theCreator = new ArrayCreator(3, String.class);
		String[] theCreatedArray = (String[]) theCreator.create(itsCreatingAlphabetBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testArrayWithTwoCellsOfAlphabet()
	{
		String[] theExpectedArray = { "A", "B" };

		ArrayCreator theCreator = new ArrayCreator(2, String.class);
		String[] theCreatedArray = (String[]) theCreator.create(itsCreatingAlphabetBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));
	}
	public void testTwoSubsequentCreations()
	{
		String[] theExpectedArray = { "A", "B" };

		ArrayCreator theCreator = new ArrayCreator(2, String.class);
		String[] theCreatedArray = (String[]) theCreator.create(itsCreatingAlphabetBlock);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArray, theCreatedArray));

		Block anotherBlockReturningIndexAsString = new Block()
		{
			public Object eval(Object index)
			{
				Integer theIndex = (Integer) index;
				return theIndex.toString();
			}
		};
		String[] theExpectedArrayOfTheSecondCreation = { "0", "1" };
		String[] theSecondTimeCreatedArray = (String[]) theCreator.create(anotherBlockReturningIndexAsString);
		assertTrue("The created array is different than the expected", CompareTwoObjectArray.areEqualsConsideringOrder(theExpectedArrayOfTheSecondCreation, theSecondTimeCreatedArray));
	}
}
