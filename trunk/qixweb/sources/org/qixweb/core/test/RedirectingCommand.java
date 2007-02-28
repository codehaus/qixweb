package org.qixweb.core.test;

import org.qixweb.core.*;

public class RedirectingCommand extends WebCommand
{
    public static final String DESTINATION = "destination";
    private final QixwebUrl itsDestination;

    public static WebCommand create(QixwebUrl anUrl, UserData aUserData)
    {
        String destination = anUrl.parameters().get(DESTINATION);
        return new RedirectingCommand(QixwebUrl.createAsRequestWithTarget(destination, "org.qixweb.core.test.", "org.qixweb.core.test."));
    }

    public static QixwebUrl urlToMeRedirectingTo(WebUrl expectedDestination)
    {
        QixwebUrl qixwebUrl = new QixwebUrl(RedirectingCommand.class);
        qixwebUrl.parameters().set(DESTINATION, expectedDestination.destination());
        return qixwebUrl;
    }

    public RedirectingCommand(QixwebUrl destination)
    {
        itsDestination = destination;
    }

    public Browsable execute(QixwebEnvironment system)
    {
        return itsDestination;
    }

    public boolean equals(Object obj)
    {
        return obj instanceof RedirectingCommand;
    }
}
