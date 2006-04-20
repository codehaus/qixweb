package org.qixweb.core;

import java.util.Collections;
import java.util.Comparator;

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
    
    public void customSortByLabel(Comparator aComparator)
    {
        Collections.sort(choices(),aComparator);        
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

    public void addOnTop(Choice aChoice)
    {
        choices().add(0, aChoice);
    }
    
    public static final Comparator caseInsensitiveOrderOnStringItem()
    {
        return new Comparator() {
            
            public int compare(Object o1, Object o2)
            {
                Choice choice1 = (Choice) o1;
                Choice choice2 = (Choice) o2;
    
                String string1 = (String) choice1.item();
                String string2 = (String) choice2.item();
                return string1.toLowerCase().compareTo(string2.toLowerCase());
            
            }
        };
    };

}
