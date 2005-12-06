package org.qixweb.core;


public class QixwebBrowser
{
	private QixwebEnvironment itsEnvironment;
	private ResponseHandler itsResponseHandler;
	private UserData itsUserData;
    private boolean instantiateUrlWithEnvironment;

	public QixwebBrowser(ResponseHandler aResponseHandler, UserData aUserData, QixwebEnvironment environment)
	{
        this(aResponseHandler, aUserData, environment, false);
	}
    public QixwebBrowser(ResponseHandler aResponseHandler, UserData aUserData, QixwebEnvironment environment, boolean useEnvironment)
    {
        
        itsResponseHandler = aResponseHandler;
        itsUserData = aUserData;        
        itsEnvironment = environment;
        instantiateUrlWithEnvironment = useEnvironment;
    }
	
	protected void executeCommand(WebAppUrl anUrl) throws Exception
	{
		WebCommand command = anUrl.materializeTargetCommandWith(itsUserData);
		if (validateExecutionOf(command))
        {
            WebAppUrl url = command.execute(itsEnvironment);
            responseHandler().redirectTo(url);
        }
	}

	protected boolean validateExecutionOf(WebCommand aCommand) throws Exception
    {
        return true;
    }

    private WebNode instantiate(WebAppUrl aUrl)
    {
        if (instantiateUrlWithEnvironment)
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment);
        else
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment.system());
    }
    
    protected void goToNode(WebAppUrl aUrl) throws Exception
	{
		instantiate(aUrl).display(itsResponseHandler);
	}

	public void goTo(WebAppUrl aUrl) throws Exception
	{
		if (aUrl.isGoingToANode())
			goToNode(aUrl);
		else if (aUrl.isExecutingACommand())
			executeCommand(aUrl);
		else if (aUrl.isExecutingARefreshableCommand())
			executeRefreshableCommand(aUrl);
		else
		    gotoWarningNode();
	}

	protected void gotoWarningNode() throws Exception
    {
    }



    protected void executeRefreshableCommand(WebAppUrl aUrl) throws Exception
	{
		WebRefreshableCommand command = aUrl.materializeTargetRefrashableCommand();
		if (validateExecutionOf(command))
        {
			WebNode targetNode = command.execute(itsEnvironment);
			targetNode.display(itsResponseHandler);
        }		
	}
	
    protected boolean validateExecutionOf(WebRefreshableCommand aCommand) throws Exception
    {
        return true;
    }

    public UserData userData()
    {
        return itsUserData;
    }
    
    public ResponseHandler responseHandler()
    {
        return itsResponseHandler;
    }
}
