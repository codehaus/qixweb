package org.qixweb.core.validation;

import java.util.HashMap;

import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;

public abstract class WebCommandBuilder
{
    public static final boolean MANDATORY = true;
    public static final boolean OPTIONAL = !MANDATORY;

    private final Parameters itsSubmittedValues;
    private boolean areAllRight;
    private HashMap itsMessageForInvalidParameters;

    protected WebCommandBuilder(Parameters submittedValues)
    {
        itsSubmittedValues = submittedValues;
        areAllRight = true;
        itsMessageForInvalidParameters = new HashMap();

        boolean isUpdating = submittedValues.getAsBoolean(WebNode.PARAMETER_NAME_FOR_IS_UPDATING);
        if (!isUpdating)
            onAdding(submittedValues);
    }

    protected void onAdding(Parameters parameters)
    {
    }

    public abstract WebCommand destinationWhenValid(UserData userData);

    public abstract Browsable destinationWhenNotValid(QixwebEnvironment environment);

    protected void addControl(WebCommandRequestControl control, boolean mandatoryFlag, String parameterKey, String messageWhenInvalid)
    {
        if (mandatoryFlag == MANDATORY || submittedValues().isNotEmpty(parameterKey))
        {
            boolean isKeyValid = control.isValid(parameterKey);
            if (!isKeyValid)
                itsMessageForInvalidParameters.put(parameterKey, messageWhenInvalid);

            areAllRight &= isKeyValid;
        }
    }

    public boolean isValid()
    {
        return areAllRight;
    }

    public HashMap messagesForInvalidParameters()
    {
        return itsMessageForInvalidParameters;
    }

    protected Parameters submittedValues()
    {
        return itsSubmittedValues;
    }

    public boolean equals(Object other)
    {
        return DeepEquals.equals(this, other);
    }

}
