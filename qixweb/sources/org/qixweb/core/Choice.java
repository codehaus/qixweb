package org.qixweb.core;

import org.qixweb.util.DeepEquals;

public class Choice implements Comparable
{
	private String itsValue;
	private Comparable itsItem;
	private boolean isSelected;

	public String toString()
	{
		return "'" + itsItem + "' (" + itsValue + ") => " + isSelected;
	}
	
	public Choice(String aValue, Comparable anItem, boolean isSelected)
	{
		itsItem = anItem;
		itsValue = aValue;
		this.isSelected = isSelected;
	}

	public Boolean isSelected()
	{
		return new Boolean(isSelected);
	}

	public Comparable item()
	{
		return itsItem;
	}

	public String value()
	{
		return itsValue;
	}

	public boolean equals(Object aChoice)
	{
//        if (aChoice instanceof Choice)
//            return itsItem.equals(((Choice)aChoice).itsItem);
//        else
//            return false;
        return DeepEquals.equals(this, aChoice);
	}

    public int hashCode()
    {
        return itsItem.hashCode();
    }
    
    public int compareTo(Object anObject)
    {
        if (anObject instanceof Choice)
        {
            Choice anotherChoice = (Choice) anObject;
            return itsItem.compareTo(anotherChoice.itsItem);
        }
        return -1;
    }

    public void select()
    {
        isSelected = true;
    }

    public void deselect()
    {
        isSelected = false;
    }

}
