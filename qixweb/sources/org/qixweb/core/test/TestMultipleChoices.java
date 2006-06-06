package org.qixweb.core.test;

import java.util.List;

import org.qixweb.core.*;
import org.qixweb.util.CollectionUtil;

public class TestMultipleChoices extends TestAbstractChoices
{
    private MultipleChoices itsMultipleChoices;

    protected void add_to(Choice addedChoice, AbstractChoice multipleChoices)
    {
        ((MultipleChoices) multipleChoices).add(addedChoice);
    }

    protected AbstractChoice create(String name, boolean isEnabled)
    {
        return new MultipleChoices(name, isEnabled);
    }

    protected void setUp() throws Exception
    {
        itsMultipleChoices = new MultipleChoices("name", true);
    }

    public void testSortByLabel()
    {
        Choice secondChoice = new Choice("1", "bbb", false);
        itsMultipleChoices.add(secondChoice);
        Choice thirdChoice = new Choice("3", "ccc", false);
        itsMultipleChoices.add(thirdChoice);
        Choice firstChoice = new Choice("2", "aaa", false);
        itsMultipleChoices.add(firstChoice);

        List sortedChoices = CollectionUtil.listWith(firstChoice, secondChoice, thirdChoice);

        itsMultipleChoices.sortByLabel();
        assertEquals(sortedChoices, itsMultipleChoices.choices());
    }

    public void testSortByKey()
    {
        Choice secondChoice = new Choice("1", "bbb", false);
        itsMultipleChoices.add(secondChoice);
        Choice thirdChoice = new Choice("3", "ccc", false);
        itsMultipleChoices.add(thirdChoice);
        Choice firstChoice = new Choice("2", "aaa", false);
        itsMultipleChoices.add(firstChoice);

        List sortedChoices = CollectionUtil.listWith(secondChoice, firstChoice, thirdChoice);

        itsMultipleChoices.sortByKey();
        assertEquals(sortedChoices, itsMultipleChoices.choices());
    }

    public void testCustomSort()
    {
        Choice secondChoice = new Choice("1", "bbb", false);
        itsMultipleChoices.add(secondChoice);
        Choice thirdChoice = new Choice("3", "ZZZ", false);
        itsMultipleChoices.add(thirdChoice);
        Choice firstChoice = new Choice("2", "aaa", false);
        itsMultipleChoices.add(firstChoice);

        List sortedChoices = CollectionUtil.listWith(firstChoice, secondChoice, thirdChoice);

        itsMultipleChoices.customSortByLabel(MultipleChoices.caseInsensitiveOrderOnStringItem());
        assertEquals("Choices should be sorted by label", sortedChoices, itsMultipleChoices.choices());
    }

    public void testSelectedItem()
    {
        assertNull("Empty multiple choices has no selection", itsMultipleChoices.selectedChoice());
        Choice selectedChoice = new Choice("selval", "selitem", true);
        itsMultipleChoices.add(selectedChoice);
        assertEquals("Selected choice should be returned", selectedChoice, itsMultipleChoices.selectedChoice());
        Choice otherChoice = new Choice("othval", "othitem", false);
        itsMultipleChoices.add(otherChoice);
        assertEquals("Another non selected choice doesn't change return", selectedChoice, itsMultipleChoices.selectedChoice());
        Choice anotherSelectedChoice = new Choice("anoval", "anoitem", true);
        itsMultipleChoices.add(anotherSelectedChoice);
        assertEquals("Even another selected choice doesn't change return", selectedChoice, itsMultipleChoices.selectedChoice());
    }

    public void testSelectExclusively()
    {

        Choice choice1 = new Choice("val1", "it1", false);
        itsMultipleChoices.add(choice1);
        Choice choice2 = new Choice("val2", "it2", false);
        itsMultipleChoices.add(choice2);
        itsMultipleChoices.selectExclusivelyBy("it1");
        assertTrue("choice1 should be selected", choice1.isSelected().booleanValue());
        assertFalse("choice2 should NOT be selected", choice2.isSelected().booleanValue());

        itsMultipleChoices.selectExclusivelyBy("it2");
        assertFalse("choice1 should NOT be selected", choice1.isSelected().booleanValue());
        assertTrue("choice2 should be selected", choice2.isSelected().booleanValue());
    }

    public void testAddOnTop()
    {
        Choice secondChoice = new Choice("2", "bbb", false);
        itsMultipleChoices.add(secondChoice);

        Choice firstChoice = new Choice("1", "aaa", false);
        itsMultipleChoices.addOnTop(firstChoice);

        assertEquals(CollectionUtil.listWith(firstChoice, secondChoice), itsMultipleChoices.choices());
    }
}
