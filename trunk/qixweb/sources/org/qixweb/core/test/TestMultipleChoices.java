package org.qixweb.core.test;

import java.util.ArrayList;
import java.util.List;

import org.qixweb.core.*;



public class TestMultipleChoices extends TestAbstractChoices
{
    protected void add_to(Choice addedChoice, AbstractChoice multipleChoices)
    {
        ((MultipleChoices)multipleChoices).add(addedChoice);
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
        
        List sortedChoices = new ArrayList();
        sortedChoices.add(firstChoice);
        sortedChoices.add(secondChoice);
        sortedChoices.add(thirdChoice);
        
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
}
