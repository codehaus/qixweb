package org.qixweb.core;

import org.qixweb.util.DeepEquals;

public class Choice implements Comparable
{
	private String itsID;
	private Comparable itsItemToDisplay;
	private boolean isSelected;

	
	public Choice(String anID, Comparable anItemToDisplay, boolean isSelected)
	{
		itsItemToDisplay = anItemToDisplay;
		itsID = anID;
		this.isSelected = isSelected;
	}

	public Boolean isSelected()
	{
		return new Boolean(isSelected);
	}

	public Comparable item()
	{
		return itsItemToDisplay;
	}

	public String value()
	{
		return itsID;
	}

    public int compareTo(Object anObject)
    {
        if (anObject instanceof Choice)
        {
            Choice anotherChoice = (Choice) anObject;
            return itsItemToDisplay.compareTo(anotherChoice.itsItemToDisplay);
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

    public boolean equals(Object aChoice)
    {
        return DeepEquals.equals(this, aChoice);
    }

    public int hashCode()
    {
        return itsItemToDisplay.hashCode();
    }
    
    public String toString()
    {
        return "'" + itsItemToDisplay + "' (" + itsID + ") => " + isSelected;
    }    
}
