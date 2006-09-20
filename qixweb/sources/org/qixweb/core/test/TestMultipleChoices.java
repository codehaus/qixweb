package org.qixweb.core.test;

import org.qixweb.core.Choice;
import org.qixweb.core.MultipleChoices;

import junit.framework.TestCase;



public class TestMultipleChoices extends TestCase
{
    public void test()
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
}
