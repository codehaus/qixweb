package org.qixweb.core;

public class Choice implements Comparable
{
	private String itsValue;
	private String itsLabel;
	private boolean isSelected;

	public String toString()
	{
		return "'" + itsLabel + "' (" + itsValue + ") => " + isSelected;
	}
	
	public Choice(String aValue, String aLabel, boolean isSelected)
	{
		itsLabel = aLabel;
		itsValue = aValue;
		this.isSelected = isSelected;
	}

	public Boolean isSelected()
	{
		return new Boolean(isSelected);
	}

	public String label()
	{
		return itsLabel;
	}

	public String value()
	{
		return itsValue;
	}

	public boolean equals(Object aChoice)
	{
        if (aChoice instanceof Choice)
            return itsLabel.equals(((Choice)aChoice).itsLabel);
        else
            return false;
	}

    public int hashCode()
    {
        return itsLabel.hashCode();
    }
    
    public int compareTo(Object anObject)
    {
        if (anObject instanceof Choice)
        {
            Choice anotherChoice = (Choice) anObject;
            return itsLabel.compareTo(anotherChoice.itsLabel);
        }
        return -1;
    }
	
}
