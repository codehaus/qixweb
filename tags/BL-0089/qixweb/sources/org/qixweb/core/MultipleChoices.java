package org.qixweb.core;

import java.util.Collections;

import org.qixweb.block.*;


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

    public Choice selectedChoice()
    {
        Choice[] selectedChoices = (Choice[])LightInternalIterator.createOn(choices()).select(new Predicate()
        {
        
            public boolean is(Object each)
            {
                Choice choice = (Choice)each;
                return choice.isSelected().booleanValue();
            }
        
        }, Choice.class);
        if (selectedChoices.length > 0)
            return (selectedChoices)[0];
        else
            return null;
    }

    public void selectExclusivelyBy(final Comparable anItem)
    {
        LightInternalIterator.createOn(choices()).forEach(new Procedure()
        {
            public void run(Object each)
            {
                Choice choice = (Choice)each;
                if (choice.item().compareTo(anItem) == 0)
                    choice.select();
                else
                    choice.deselect();
            }
        });
    }
}
