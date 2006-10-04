package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.AbstractChoice;
import org.qixweb.core.Choice;
import org.qixweb.util.EqualsBehaviourVerifier;



public abstract class AbstractTestChoices extends TestCase
{
    public void testCreation()
    {
        boolean isEnabled = true;
        AbstractChoice multipleChoices = create("multiplechoices name", isEnabled);
        
        assertEquals("multiplechoices name", multipleChoices.name());
        assertTrue(multipleChoices.isEnabled().booleanValue());
        assertEquals(0, multipleChoices.choices().size());

        Choice addedChoice = new Choice("a choice value", "a choice label", false);
        add_to(addedChoice, multipleChoices);
        assertEquals(1, multipleChoices.choices().size());
        assertEquals(addedChoice, multipleChoices.choices().get(0));
    }

    protected abstract void add_to(Choice addedChoice, AbstractChoice multipleChoices);
    protected abstract AbstractChoice create(String name, boolean isEnabled);
    
    public void testEquals()
    {
        AbstractChoice choices = create("name", true);
        AbstractChoice sameChoices = create("name", true);
        AbstractChoice differentChoices = create("name", true);
        add_to(new Choice("2", "aaa", false), differentChoices);
        EqualsBehaviourVerifier.check(choices, sameChoices, differentChoices);
    }
}
