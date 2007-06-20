package org.qixweb.core.validation.test;

import org.qixweb.core.*;
import org.qixweb.core.validation.WebCommandBuilder;


public class SampleBuilderWithOnAdding extends WebCommandBuilder
{
    boolean onAddingHasBeenCalled;

    protected SampleBuilderWithOnAdding(Parameters submittedValues)
    {
        super(submittedValues);
    }
    
    protected void onAdding(Parameters parameters)
    {
        onAddingHasBeenCalled = true;
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment environment)
    {
        return null;
    }

    public WebCommand destinationWhenValid(UserData userData)
    {
        return null;
    }
};
