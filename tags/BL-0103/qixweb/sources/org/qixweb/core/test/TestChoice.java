package org.qixweb.core.test;

import org.qixweb.core.Choice;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public class TestChoice extends ExtendedTestCase
{
    private Choice choice;
    private Choice sameChoice;
    private Choice differentChoiceForLabel;

    protected void setUp()
    {
        choice = new Choice("value", "label", true);
        sameChoice = new Choice("value", "label", true);
        differentChoiceForLabel = new Choice("value", "lab", true);
    }

    public void testEquals()
    {
        Choice differentChoiceForValue = new Choice("different value", "label", true);
        Choice differentChoiceForSelection = new Choice("value", "label", false);
        EqualsBehaviourVerifier.check(choice, sameChoice, differentChoiceForLabel);
        EqualsBehaviourVerifier.checkHashCode(choice, sameChoice);
        EqualsBehaviourVerifier.check(choice, sameChoice, differentChoiceForValue);
        EqualsBehaviourVerifier.check(choice, sameChoice, differentChoiceForSelection);
    }
    
    public void testCompareTo()
    {
        assertEquals(0, choice.compareTo(sameChoice));
        assertEquals(-differentChoiceForLabel.compareTo(choice), choice.compareTo(differentChoiceForLabel));
        assertTrue(choice.compareTo(new Object()) != 0);
    }
    
    public void testSelection()
    {
        assertTrue(choice.isSelected().booleanValue());
        choice.deselect();
        assertFalse(choice.isSelected().booleanValue());
        choice.select();
        assertTrue(choice.isSelected().booleanValue());
    }
}
