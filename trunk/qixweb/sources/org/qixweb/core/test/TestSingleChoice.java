package org.qixweb.core.test;

import java.util.ArrayList;

import org.qixweb.core.*;

public class TestSingleChoice extends AbstractTestChoices
{
    protected void add_to(Choice choice, AbstractChoice abstractChoice)
    {
        ((SingleChoice) abstractChoice).set(choice);
    }

    protected AbstractChoice create(String name, boolean isEnabled)
    {
        return new SingleChoice(name, isEnabled);
    }

    public void testChoice()
    {
        SingleChoice singleChoice = (SingleChoice) create("name", true);
        assertNull(singleChoice.choice());
        Choice choice = new Choice("val", "label", true);
        add_to(choice, singleChoice);
        assertEquals(choice, singleChoice.choice());
    }

    public void testManipulatingChoicesAlwaysGetFirst()
    {
        SingleChoice singleChoice = (SingleChoice) create("name", true);
        ArrayList choices = singleChoice.choices();
        Choice firstChoice = new Choice("val1", "label1", true);
        choices.add(firstChoice);
        choices.add(new Choice("val2", "label2", true));
        choices.add(new Choice("val3", "label3", true));
        assertEquals(firstChoice, singleChoice.choice());
    }
}
