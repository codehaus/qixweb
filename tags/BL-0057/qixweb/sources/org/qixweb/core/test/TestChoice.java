package org.qixweb.core.test;

import org.qixweb.core.Choice;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public class TestChoice extends ExtendedTestCase
{
    private Choice choice;
    private Choice sameChoice;
    private Choice differentChoice;

    protected void setUp()
    {
        choice = new Choice("val", "label", true);
        sameChoice = new Choice("value", "label", false);
        differentChoice = new Choice("val", "lab", true);
    }

    public void testEquals()
    {
        EqualsBehaviourVerifier.check(choice, sameChoice, differentChoice);
        EqualsBehaviourVerifier.checkHashCode(choice, sameChoice);
    }
    
    public void testCompareTo()
    {
        assertEquals(0, choice.compareTo(sameChoice));
        assertEquals(-differentChoice.compareTo(choice), choice.compareTo(differentChoice));
        assertTrue(choice.compareTo(new Object()) != 0);
    }
}
