package org.qixweb.sample.command;

import org.qixweb.core.*;
import org.qixweb.sample.node.SampleValidationNode;
import org.qixweb.util.ClassUtil;

public class SetGuestWithValidationCommand extends WebCommand
{
    private String itsGuestName;

    public SetGuestWithValidationCommand(String guestName)
    {
        itsGuestName = guestName;
    }
    
    public Browsable execute(QixwebEnvironment notUsed) throws Exception
    {
        return new SampleValidationNode(itsGuestName);
    }
    
    public String toString()
    {
        return ClassUtil.shortNameOf(getClass()) + " (Setting name: " + itsGuestName + ")";
    }

}
