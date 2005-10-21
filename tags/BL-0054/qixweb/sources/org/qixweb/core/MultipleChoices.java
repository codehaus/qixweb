package org.qixweb.core;

import java.util.Collections;


public class MultipleChoices extends AbstractChoice
{
    public MultipleChoices(String aName, boolean isEnabled)
    {
        super(aName, isEnabled);
    }
    
	public void add(Choice aChoice)
	{
		choices().add(aChoice);
	}

    public void sortByLabel()
    {
        Collections.sort(choices());
    }
}
