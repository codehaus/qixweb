package org.qixweb.core;

import java.util.ArrayList;

import org.qixweb.util.DeepEquals;


public class MultipleChoices
{
	private boolean isEnabled;
	private String itsName;
	private ArrayList itsChoices;

	public MultipleChoices(String aName, boolean isEnabled)
	{
		itsName = aName;
		this.isEnabled = isEnabled;
		itsChoices = new ArrayList();
	}

	public void add(Choice aChoice)
	{
		itsChoices.add(aChoice);
	}

	public String name()
	{
		return itsName;
	}

	public Boolean isEnabled()
	{
		return new Boolean(isEnabled);
	}

    public ArrayList choices()
    {
        return itsChoices;
    }

	public boolean equals(Object aMultipleChoices)
	{
		return DeepEquals.equals(this, aMultipleChoices);
	}
	
	public String toString()
	{
		return "[name: " + name() + " - isEnabled: " + isEnabled() + " - itsChoices: " + itsChoices + "]"; 
	}
}
