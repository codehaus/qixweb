package org.qixweb.sample.node;

import java.util.Calendar;
import java.util.HashMap;

import org.qixweb.core.*;
import org.qixweb.sample.command.SetGuestWithValidationCommand;
import org.qixweb.time.DateFormatter;
import org.qixweb.time.QixwebTime;


public class SampleValidationNode extends WebNode
{
    private String itsMessage;

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        return new SampleValidationNode();
    }

    public SampleValidationNode()
    {
        itsMessage = "";
    }
    
    public SampleValidationNode(String name)
    {
        itsMessage = "You've submitted: " + name;
    }
    
    public SampleValidationNode(HashMap errorMessages)
    {
        this();
        setErrorMessages(errorMessages);
    }

    public String currentTime()
    {
        return DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(new QixwebTime(Calendar.getInstance()));
    }
    
    public QixwebUrl setGuestNameUrl()
    {
        return new QixwebUrl(SetGuestWithValidationCommand.class);
    }
    
    public static String parameterNameForGuestName()
    {
        return "guestName";
    }
    
    public String message()
    {
        return itsMessage;
    }
}
