package org.qixweb.sample.command;

import org.qixweb.core.*;
import org.qixweb.sample.node.HelloNode;



public class SetGuestCommand implements WebCommand
{

    private String itsGuestName;

    public static WebCommand create(QixwebUrl someProperties, UserData userData)
    {
        return new SetGuestCommand(someProperties.getParameter(HelloNode.parameterNameForGuestName()));
    }
    
    public SetGuestCommand(String guestName)
    {
        itsGuestName = guestName;
    }
    
    public Browsable execute(QixwebEnvironment aEnvironment) throws Exception
    {
        return HelloNode.urlToMe(aEnvironment, itsGuestName);
    }

}
