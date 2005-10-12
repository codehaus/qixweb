package org.qixweb.core;


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
		if (validateExecutionOf(command))
        {
            Browsable browsable = command.execute(itsEnvironment);
            browsable.displayThrough(responseHandler());
        }
	}

	protected boolean validateExecutionOf(WebCommand aCommand) throws Exception
    {
        return true;
    }

    private WebNode instantiate(QixwebUrl aUrl)
    {
        if (instantiateUrlWithEnvironment)
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment);
        else
            return aUrl.materializeTargetNodeWith(itsUserData, itsEnvironment.system());
    }
    
    protected void goToNode(QixwebUrl aUrl) throws Exception
	{
		instantiate(aUrl).displayThrough(itsResponseHandler);
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
}
