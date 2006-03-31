package org.qixweb.core.test;

import java.util.List;

import org.qixweb.core.*;
import org.qixweb.util.CollectionUtil;

public class TestMultipleChoices extends TestAbstractChoices
{
    protected void add_to(Choice addedChoice, AbstractChoice multipleChoices)
    {
        ((MultipleChoices) multipleChoices).add(addedChoice);
    }

    protected AbstractChoice create(String name, boolean isEnabled)
    {
        return new MultipleChoices(name, isEnabled);
    }

    public void testSortByLabel()
    {
        MultipleChoices multipleChoices = new MultipleChoices("name", true);
        Choice secondChoice = new Choice("1", "bbb", false);
        multipleChoices.add(secondChoice);
        Choice thirdChoice = new Choice("3", "ccc", false);
        multipleChoices.add(thirdChoice);
        Choice firstChoice = new Choice("2", "aaa", false);
        multipleChoices.add(firstChoice);

        List sortedChoices = CollectionUtil.listWith(firstChoice, secondChoice, thirdChoice);

        multipleChoices.sortByLabel();
        assertEquals("Choices should be sorted by label", sortedChoices, multipleChoices.choices());
    }

    public void testSelectedItem()
    {
        MultipleChoices multipleChoices = new MultipleChoices("name", true);
        assertNull("Empty multiple choices has no selection", multipleChoices.selectedChoice());
        Choice selectedChoice = new Choice("selval", "selitem", true);
        multipleChoices.add(selectedChoice);
        assertEquals("Selected choice should be returned", selectedChoice, multipleChoices.selectedChoice());
        Choice otherChoice = new Choice("othval", "othitem", false);
        multipleChoices.add(otherChoice);
        assertEquals("Another non selected choice doesn't change return", selectedChoice, multipleChoices.selectedChoice());
        Choice anotherSelectedChoice = new Choice("anoval", "anoitem", true);
        multipleChoices.add(anotherSelectedChoice);
        assertEquals("Even another selected choice doesn't change return", selectedChoice, multipleChoices.selectedChoice());
    }

    public void testSelectExclusively()
    {
        MultipleChoices multipleChoices = new MultipleChoices("name", true);
        Choice choice1 = new Choice("val1", "it1", false);
        multipleChoices.add(choice1);
        Choice choice2 = new Choice("val2", "it2", false);
        multipleChoices.add(choice2);
        multipleChoices.selectExclusivelyBy("it1");
        assertTrue("choice1 should be selected", choice1.isSelected().booleanValue());
        assertFalse("choice2 should NOT be selected", choice2.isSelected().booleanValue());

        multipleChoices.selectExclusivelyBy("it2");
        assertFalse("choice1 should NOT be selected", choice1.isSelected().booleanValue());
        assertTrue("choice2 should be selected", choice2.isSelected().booleanValue());
    }

    public void testAddOnTop()
    {
        MultipleChoices multipleChoices = new MultipleChoices("name", true);
        Choice secondChoice = new Choice("2", "bbb", false);
        multipleChoices.add(secondChoice);

        Choice firstChoice = new Choice("1", "aaa", false);
        multipleChoices.addOnTop(firstChoice);

        assertEquals(CollectionUtil.listWith(firstChoice, secondChoice), multipleChoices.choices());
    }
}
