package org.qixweb.core;

import java.util.Collections;



public class SingleChoice extends AbstractChoice
{
    public SingleChoice(String aName, boolean enabledState)
    {
        super(aName, enabledState);
    }
    
    public Choice choice()
    {
        if (choices().size() > 0)
            return (Choice)choices().get(0);
        else
            return null;
    }

    public void set(Choice aChoice)
    {
        choices().clear();
        choices().add(aChoice);
    }
}
