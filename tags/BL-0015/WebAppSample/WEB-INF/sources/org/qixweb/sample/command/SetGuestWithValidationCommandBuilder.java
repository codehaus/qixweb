package org.qixweb.sample.command;

import org.qixweb.core.*;
import org.qixweb.core.validation.TextControl;
import org.qixweb.core.validation.WebCommandBuilder;
import org.qixweb.sample.node.HelloNode;
import org.qixweb.sample.node.SampleValidationNode;
import org.qixweb.util.ClassUtil;

public class SetGuestWithValidationCommandBuilder extends WebCommandBuilder
{
    public SetGuestWithValidationCommandBuilder(Parameters submittedValues)
    {
        super(submittedValues);
        addControl(new TextControl(submittedValues), MANDATORY, SampleValidationNode.parameterNameForGuestName(), "Name is mandatory");
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment notUsed)
    {
        return new SampleValidationNode(messagesForInvalidParameters());
    }

    public String toString()
    {
        return ClassUtil.shortNameOf(getClass()) + " (Invalid name)";
    }

    public WebCommand destinationWhenValid(UserData userData)
    {
        return new SetGuestWithValidationCommand(submittedValues().get(HelloNode.parameterNameForGuestName()));
    }

}
