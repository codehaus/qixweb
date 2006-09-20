package org.qixweb.core;

import org.qixweb.util.DeepEquals;


public class Choice
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
		return DeepEquals.equals(this, aChoice);
	}
	
}
