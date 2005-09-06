package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.XpLogger;
import org.qixweb.util.test.ExtendedTestCase;

public class TestWebAppUrl extends ExtendedTestCase
{
	public void testMaterializeTargetNode()
	{
		AnyNode expectedNode = new AnyNode();
		
		WebAppUrl url = new WebAppUrl(AnyNode.class, "");
		WebNode node = url.materializeTargetNodeWith(itsUserData, itsSystem);
	
		assertEquals(expectedNode, node);
	}
	public void testMaterializeNotExistentNode()
	{
		XpLogger.off();
		
		Class notExistentNode = Integer.class;
		WebAppUrl urlToNotExistentTarget = new WebAppUrl(notExistentNode, "");
		
		WebNode node = urlToNotExistentTarget.materializeTargetNodeWith(itsUserData, itsSystem);
		assertNull("It is NOT possible to materialize a not existent node", node);
		
		XpLogger.resume();	
	}
	
	public void testMaterializeNotExistentCommand()
	{
		XpLogger.off();
	
		Class notExistentCommand = Integer.class;
		WebAppUrl urlToNotExistentTarget = new WebAppUrl(notExistentCommand, "");
	
		WebCommand command = urlToNotExistentTarget.materializeTargetCommandWith(itsUserData);
		assertNull("It is NOT possible to materialize a not existent command", command);
	
		XpLogger.resume();	
	}
	
	public void testMaterializeTargetCommand()
	{
		AnyCommand expectedCommand = new AnyCommand("materializeTest");
		
		WebAppUrl url = new WebAppUrl(AnyCommand.class, "");
        url.setParameter("state", "materializeTest");
        itsUserData.store("state", "materializeTest");
		WebCommand command = url.materializeTargetCommandWith(itsUserData);
	
		assertEquals(expectedCommand, command);
	}
	
	public void testMaterializeTargetRefreshableCommand()
	{
		AnyRefreshableCommand expectedCommand = new AnyRefreshableCommand();
		
		WebAppUrl url = new WebAppUrl(AnyRefreshableCommand.class, "");
		WebRefreshableCommand command = url.materializeTargetRefrashableCommand();
	
		assertEquals(expectedCommand, command);
	}	

	
    private UserData itsUserData;
	private TheSystem itsSystem;
	private String itsBaseUrlBeforeParameters;
	private WebAppUrl itsWebUrlForAnyCommand;
	private WebAppUrl itsWebUrlForAnyNode;

    public static String encodeAmpersand(String aStringToEncode)
    {
        return aStringToEncode.replaceAll("&", "&amp;");
    }
    
    protected void setUp() throws Exception
    {
    	itsBaseUrlBeforeParameters = "baseUrl";
		
		itsWebUrlForAnyNode = new WebAppUrl(AnyNode.class, itsBaseUrlBeforeParameters);
		itsWebUrlForAnyCommand = new WebAppUrl(AnyCommand.class, itsBaseUrlBeforeParameters);
		
		itsUserData = new UserData();
		itsSystem = new TheSystem() {};
    }
    
    public void testIsEnabled()
    {
    	assertTrue("By default an url is enabled", itsWebUrlForAnyNode.isEnabled());
		itsWebUrlForAnyNode.disable();
		assertFalse("The url should be disabled", itsWebUrlForAnyNode.isEnabled());
    }
    
    public void testEquals()
    {
        WebAppUrl sameUrl = new WebAppUrl(AnyNode.class, itsBaseUrlBeforeParameters);
        WebAppUrl aDifferentUrl = new WebAppUrl(AnyCommand.class, itsBaseUrlBeforeParameters);
        
        EqualsBehaviourVerifier.check("different type of url", itsWebUrlForAnyNode, sameUrl, aDifferentUrl);
        EqualsBehaviourVerifier.check("different type of url", new Integer(itsWebUrlForAnyNode.hashCode()), new Integer(sameUrl.hashCode()), new Integer(aDifferentUrl.hashCode()));
        
        sameUrl.destination();
		EqualsBehaviourVerifier.check("calling destination should not affect equals", itsWebUrlForAnyNode, sameUrl, aDifferentUrl);        
    }
    
	public void testClassNameParameter()
	{
		String nodeToDisplayParameter = itsWebUrlForAnyNode.getParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY);
		assertEquals("wrong node name ", "AnyNode", nodeToDisplayParameter);
		
		String commandToExecuteParameter = itsWebUrlForAnyCommand.getParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE);
		assertEquals("wrong command name", "AnyCommand", commandToExecuteParameter);

		WebAppUrl neitherCommandNorNodeUrl = new WebAppUrl(Object.class, itsBaseUrlBeforeParameters);
		assertNull("No command name should be set", neitherCommandNorNodeUrl.getParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE));
		assertNull("No node name should be set", neitherCommandNorNodeUrl.getParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY));
	}
	
    public void testDestinationForNode()
    {
        String expectedDestination = itsBaseUrlBeforeParameters + "?" + WebAppUrl.PARAMETER_NODE_TO_DISPLAY+"=AnyNode";
        String returnedDestination = itsWebUrlForAnyNode.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should go the a node", itsWebUrlForAnyNode.isGoingToANode());
    }

    public void testDestinationForCommand()
    {
        String expectedDestination = itsBaseUrlBeforeParameters + "?" + WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE+"=AnyCommand";
        String returnedDestination = itsWebUrlForAnyCommand.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should execute a command", itsWebUrlForAnyCommand.isExecutingACommand());
    }
     
	public void testDestinationForWebRefreshableCommand()
	{
		WebAppUrl url = new WebAppUrl(AnyRefreshableCommand.class, itsBaseUrlBeforeParameters);
		String expectedDestination = itsBaseUrlBeforeParameters + "?" + WebAppUrl.PARAMETER_REFRESHABLE_COMMAND_TO_EXECUTE+"=AnyRefreshableCommand";
		String returnedDestination = url.destination();
		assertEquals("wrong destination composition", expectedDestination, returnedDestination);
		assertTrue("should execute a refreshable command", url.isExecutingARefreshableCommand());
	}
	
	public void testDestinationWithParametersAndTargetClass()
	{
		String expectedDestination = itsBaseUrlBeforeParameters;
		String expectedFirstParameter = WebAppUrl.PARAMETER_NODE_TO_DISPLAY+"=AnyNode";
	    
		String expectedSecondParameter = "parameter=value";
		itsWebUrlForAnyNode.setParameter("parameter", "value");
	    
		String returnedDestination = itsWebUrlForAnyNode.destination();
		
		assert_contains("wrong destination composition", returnedDestination, expectedDestination);
		assert_contains("wrong parameter", returnedDestination, expectedFirstParameter);     
		assert_contains("wrong parameter", returnedDestination, expectedSecondParameter);     
	}
	        
}
