package org.qixweb.core.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.qixweb.core.Choice;
import org.qixweb.core.MultipleChoices;



public class TestMultipleChoices extends TestCase
{
    public void testCreation()
    {
        boolean isEnabled = true;
        MultipleChoices multipleChoices = new MultipleChoices("multiplechoices name", isEnabled);
        
        assertEquals("multiplechoices name", multipleChoices.name());
        assertTrue(multipleChoices.isEnabled().booleanValue());
        assertEquals(0, multipleChoices.choices().size());

        Choice addedChoice = new Choice("a choice value", "a choice label", false);
        multipleChoices.add(addedChoice);
        assertEquals(1, multipleChoices.choices().size());
        assertEquals(addedChoice, multipleChoices.choices().get(0));
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
}
