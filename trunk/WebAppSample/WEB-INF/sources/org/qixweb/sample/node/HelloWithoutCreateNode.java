package org.qixweb.sample.node;

import java.util.Calendar;

import org.qixweb.core.*;
import org.qixweb.sample.command.SetGuestCommand;
import org.qixweb.sample.command.SetGuestWithValidationCommand;
import org.qixweb.time.DateFormatter;
import org.qixweb.time.QixwebTime;

public class HelloWithoutCreateNode extends WebNode
{
    private String itsName;
    private QixwebEnvironment itsEnvironment;

    public String currentTime()
    {
        return DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(new QixwebTime(Calendar.getInstance()));
    }
    
    public QixwebUrl setGuestNameUrl()
    {
        return new QixwebUrlFactory(itsEnvironment).createUrlWith(SetGuestCommand.class);
    }
    
    public static QixwebUrl urlToMe(QixwebEnvironment environment, String guestName)
    {
        QixwebUrl url = new QixwebUrlFactory(environment).createUrlWith(HelloWithoutCreateNode.class);
        url.parameters().set(parameterNameForGuestName(), guestName);
        return url;
    }
    
    public HelloWithoutCreateNode(QixwebEnvironment environment, String name)
    {
        itsEnvironment = environment;
        itsName = name;
    }
    
    public String guestName()
    {
        return itsName;
    }

    public static String parameterNameForGuestName()
    {
        return "guestName";
    }
}
