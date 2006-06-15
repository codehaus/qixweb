package org.qixweb.sample.command;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.sample.node.SampleValidationNode;
import org.qixweb.util.ClassUtil;

public class SetGuestWithValidationCommandRequest extends WebCommandRequest
{
    public SetGuestWithValidationCommandRequest(Parameters submittedValues)
    {
        super(submittedValues);
    }

    public boolean isValid()
    {
        return StringUtils.isNotEmpty(itsSubmittedValues.get(SampleValidationNode.parameterNameForGuestName()));
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment environment)
    {
        return new SampleValidationNode("Il nome e' obbligatorio");
    }

    public String toString()
    {
        return ClassUtil.shortNameOf(getClass()) + " (Invalid name)";
    }

}
