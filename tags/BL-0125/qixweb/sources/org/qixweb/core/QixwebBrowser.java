package org.qixweb.core;

import org.qixweb.util.XpLogger;



public class QixwebBrowser
{
	private QixwebEnvironment itsEnvironment;
	private ResponseHandler itsResponseHandler;
	private UserData itsUserData;
    private boolean instantiateUrlWithEnvironment;

    protected QixwebBrowser(ResponseHandler aResponseHandler, UserData aUserData, QixwebEnvironment environment, boolean useEnvironment)
    {
        itsResponseHandler = aResponseHandler;
        itsUserData = aUserData;        
        itsEnvironment = environment;
        instantiateUrlWithEnvironment = useEnvironment;
    }

    public static QixwebBrowser usingSystem(ResponseHandler aResponseHandler, UserData aUserData, QixwebEnvironment environment)
    {
        return new QixwebBrowser(aResponseHandler, aUserData, environment, false);
    }

    public static QixwebBrowser usingEnvironment(ResponseHandler aResponseHandler, UserData aUserData, QixwebEnvironment environment)
    {
        return new QixwebBrowser(aResponseHandler, aUserData, environment, true);
    }
    
	protected void executeCommand(QixwebUrl anUrl) throws Exception
	{
		WebCommand command = anUrl.materializeTargetCommandWith(itsUserData);
        XpLogger.info("Executing command (User=" + loggedUser().name() + "): " + command);
        if (command == null)
            gotoWarningNode();
        else if (command.canBeExecutedBy(loggedUser(), itsEnvironment))
        {
            Browsable browsable = command.execute(itsEnvironment);
            browsable.displayThrough(responseHandler());
        }
        else
        {
            XpLogger.info("User " + loggedUser().name() + " cannot execute command: " + command.toString());
            goToLogin();
        }
	}

    protected WebNode instantiate(QixwebUrl aUrl)
    {
        if (instantiateUrlWithEnvironment)
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment);
        else
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment.system());
    }
    
    protected void goToNode(QixwebUrl aUrl) throws Exception
	{
        WebNode node = instantiate(aUrl);
        if (node == null)
            gotoWarningNode();
        else if (node.canBeDisplayedBy(loggedUser()))
        {
            try
            {
                node.displayThrough(responseHandler());
            }
            catch (Exception ex)
            {
                gotoWarningNode();
            }
        }
        else
            goToStart(aUrl);
	}

    protected void goToStart(QixwebUrl aUrl) throws Exception
    {
        goToLogin();
    }
    
	public void goTo(QixwebUrl aUrl) throws Exception
	{
		if (aUrl.isGoingToANode())
			goToNode(aUrl);
		else if (aUrl.isExecutingACommand())
			executeCommand(aUrl);
		else
		    gotoWarningNode();
	}

	protected void gotoWarningNode() throws Exception
    {
    }

    public UserData userData()
    {
        return itsUserData;
    }
    
    public ResponseHandler responseHandler()
    {
        return itsResponseHandler;
    }
    
    protected void goToLogin() throws Exception
    {
        QixwebUrl returnedUrl = new QixwebUrl(QixwebLoginNode.class);
        WebNode node = instantiate(returnedUrl);
        node.displayThrough(responseHandler());
    }
    
    protected QixwebUser loggedUser()
    {
        return QixwebUser.ANONYMOUS;
    }
}
