package org.qixweb.core;

import java.util.ArrayList;
import java.util.Collections;

import org.qixweb.util.DeepEquals;


public abstract class AbstractChoice
{
	private boolean isEnabled;
	private String itsName;
	private ArrayList itsChoices;

	public AbstractChoice(String aName, boolean isEnabled)
	{
		itsName = aName;
		this.isEnabled = isEnabled;
		itsChoices = new ArrayList();
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

    public void sortByLabel()
    {
        Collections.sort(itsChoices);
    }
}
