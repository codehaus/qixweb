package org.qixweb.core;



public class SingleChoice extends MultipleChoices
{
    public SingleChoice(String aName, boolean enabledState)
    {
        super(aName, enabledState);
    }
    
    public Choice choice()
    {
        if (choices().size() == 1)
            return (Choice)choices().get(0);
        else 
            throw new IllegalStateException("Invalid Choice - should contains just one choice");
    }

}
