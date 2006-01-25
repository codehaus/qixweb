package org.qixweb.sample.node;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.sample.command.SetGuestCommand;
import org.qixweb.time.*;



public class HelloNode extends WebNode
{
    private String itsName;
    private QixwebEnvironment itsEnvironment;

    public static WebNode create(QixwebUrl anUrl, UserData aUserData, QixwebEnvironment environment)
    {
        String guestName = anUrl.parameters().get(parameterNameForGuestName());
        return new HelloNode(environment, StringUtils.isEmpty(guestName) ? "unknown" : guestName);
    }

    public String currentTime()
    {
        return DateFormatter.formatDD_MM_YYYY_HH_mm_ss(new QixwebTime(Calendar.getInstance()));
    }
    public QixwebUrl setGuestNameUrl()
    {
        return new QixwebUrlFactory(itsEnvironment).createUrlWith(SetGuestCommand.class);
    }
    
    public static QixwebUrl urlToMe(QixwebEnvironment environment, String guestName)
    {
        QixwebUrl url = new QixwebUrlFactory(environment).createUrlWith(HelloNode.class);
        url.parameters().set(parameterNameForGuestName(), guestName);
        return url;
    }
    
    public HelloNode(QixwebEnvironment environment, String name)
    {
        itsEnvironment = environment;
        itsName = name;
    }
    
    public static String parameterNameForGuestName()
    {
        return "guestName";
    }
    
    public String guestName()
    {
        return itsName;
    }
}
