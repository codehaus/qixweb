package org.qixweb.sample.command;

import org.qixweb.core.*;
import org.qixweb.sample.node.SampleValidationNode;
import org.qixweb.util.ClassUtil;

public class SetGuestWithValidationCommandRequest extends WebCommandRequest
{
    public SetGuestWithValidationCommandRequest(Parameters submittedValues)
    {
        super(submittedValues);
        addMandatoryControlText(SampleValidationNode.parameterNameForGuestName(), "Name is mandatory");
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment notUsed)
    {
        return new SampleValidationNode("Name is mandatory");
    }

    public String toString()
    {
        return ClassUtil.shortNameOf(getClass()) + " (Invalid name)";
    }

}
