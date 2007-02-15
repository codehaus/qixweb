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
		WebRefreshableCommand command = anUrl.materializeTargetCommandWith(itsUserData);
		if (validateExecutionOf(command))
        {
            Browsable browsable = command.execute(itsEnvironment);
            browsable.displayThrough(responseHandler());
        }
	}

	protected boolean validateExecutionOf(WebRefreshableCommand aCommand) throws Exception
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
		instantiate(aUrl).displayThrough(itsResponseHandler);
	}

	public void goTo(WebAppUrl aUrl) throws Exception
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
}
