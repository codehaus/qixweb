package org.qixweb.core.test;

import java.io.IOException;

import org.qixweb.core.*;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public class TestWebAppUrl extends ExtendedTestCase
{
    private UserData itsUserData;
    private TheSystem itsSystem;
    private String itsServletPath;
    private WebAppUrl itsWebUrlForAnyCommand;
    private WebAppUrl itsWebUrlForAnyNode;

    
    protected void setUp() throws Exception
    {
        super.setUp();
        itsServletPath = "servletPath";
        WebAppUrl.initServletPath(itsServletPath);
        
        itsWebUrlForAnyNode = new WebAppUrl(AnyNode.class);
        itsWebUrlForAnyCommand = new WebAppUrl(AnyCommand.class);
        
        itsUserData = new UserData();
        itsSystem = new TheSystem() {};
    }

    public void testMaterializeTargetNode()
	{
		AnyNode expectedNode = new AnyNode();
		
		WebAppUrl url = new WebAppUrl(AnyNode.class);
		WebNode node = url.materializeTargetNodeWith(itsUserData, itsSystem);
	
		assertEquals(expectedNode, node);
	}
    
	public void testMaterializeNotExistentNode()
	{
        grabSystemOutResettingLogger();
		Class notExistentNode = Integer.class;
		WebAppUrl urlToNotExistentTarget = new WebAppUrl(notExistentNode);
		
		WebNode node = urlToNotExistentTarget.materializeTargetNodeWith(itsUserData, itsSystem);
		assertNull("It is NOT possible to materialize a not existent node", node);
        assert_contains("Exception expected", grabbedOut(), NoSuchMethodException.class.getName());
	}
	
	public void testMaterializeNotExistentCommand()
	{
        grabSystemOutResettingLogger();
		Class notExistentCommand = Integer.class;
		WebAppUrl urlToNotExistentTarget = new WebAppUrl(notExistentCommand);
	
        WebCommand command = urlToNotExistentTarget.materializeTargetCommandWith(itsUserData);
		assertNull("It is NOT possible to materialize a not existent command", command);
        assert_contains("Exception expected", grabbedOut(), NoSuchMethodException.class.getName());
	}
    
    public void testCommandThrowingExceptionOnCreateIsLoggedWithoutInterruptingNormalFlow()
    {
        grabSystemOutResettingLogger();
        new WebAppUrl(BadCreateCommand.class).materializeTargetCommandWith(itsUserData);
        assert_contains("Exception expected", grabbedOut(), BadCreateCommand.FAKE_MESSAGE);
    }
	
	public void testMaterializeTargetCommand()
	{
		AnyCommand expectedCommand = new AnyCommand("materializeTest");
		
		WebAppUrl url = new WebAppUrl(AnyCommand.class);
        url.setParameter("state", "materializeTest");
        itsUserData.store("state", "materializeTest");
        WebCommand command = url.materializeTargetCommandWith(itsUserData);
	
		assertEquals(expectedCommand, command);
	}
	
    public static String encodeAmpersand(String aStringToEncode)
    {
        return aStringToEncode.replaceAll("&", "&amp;");
    }
    
    public void testEquals()
    {
        WebAppUrl url = new WebAppUrl(AnyNode.class);
        WebAppUrl sameUrl = new WebAppUrl(AnyNode.class);
        WebAppUrl aDifferentUrl = new WebAppUrl(AnyCommand.class);
        
        EqualsBehaviourVerifier.check("different type of url", url, sameUrl, aDifferentUrl);
        EqualsBehaviourVerifier.checkHashCode(url, sameUrl);
        
        sameUrl.destination();
		EqualsBehaviourVerifier.check("calling destination should not affect equals", url, sameUrl, aDifferentUrl);        
    }
    
	public void testClassNameParameter()
	{
		String nodeToDisplayParameter = itsWebUrlForAnyNode.getParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY);
		assertEquals("wrong node name ", "AnyNode", nodeToDisplayParameter);
		
		String commandToExecuteParameter = itsWebUrlForAnyCommand.getParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE);
		assertEquals("wrong command name", "AnyCommand", commandToExecuteParameter);

		WebAppUrl neitherCommandNorNodeUrl = new WebAppUrl(Object.class);
		assertNull("No command name should be set", neitherCommandNorNodeUrl.getParameter(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE));
		assertNull("No node name should be set", neitherCommandNorNodeUrl.getParameter(WebAppUrl.PARAMETER_NODE_TO_DISPLAY));
	}
	
    public void testDestinationForNode()
    {
        String expectedDestination = itsServletPath + "?" + WebAppUrl.PARAMETER_NODE_TO_DISPLAY+"=AnyNode";
        String returnedDestination = itsWebUrlForAnyNode.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should go the a node", itsWebUrlForAnyNode.isGoingToANode());
    }

    public void testDestinationForCommand()
    {
        String expectedDestination = itsServletPath + "?" + WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE+"=AnyCommand";
        String returnedDestination = itsWebUrlForAnyCommand.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should execute a command", itsWebUrlForAnyCommand.isExecutingACommand());
    }
     
	public void testDestinationWithParametersAndTargetClass()
	{
		String expectedDestination = itsServletPath;
		String expectedFirstParameter = WebAppUrl.PARAMETER_NODE_TO_DISPLAY+"=AnyNode";
	    
		String expectedSecondParameter = "parameter=value";
		itsWebUrlForAnyNode.setParameter("parameter", "value");
	    
		String returnedDestination = itsWebUrlForAnyNode.destination();
		
		assert_contains("wrong destination composition", returnedDestination, expectedDestination);
		assert_contains("wrong parameter", returnedDestination, expectedFirstParameter);     
		assert_contains("wrong parameter", returnedDestination, expectedSecondParameter);     
	}

    public void testCopyOptionalParameters()
    {
        WebAppUrl orginUrl = new WebAppUrl(AnyRefreshableCommand.class);
        orginUrl.setParameter("param1", "value1");
        orginUrl.setParameter("param2", "value2");
        WebAppUrl targetCommandUrl = new WebAppUrl(AnyCommand.class);
        WebAppUrl expectedCommandUrl = new WebAppUrl(AnyCommand.class);
        expectedCommandUrl.setParameter("param1", "value1");
        expectedCommandUrl.setParameter("param2", "value2");
        targetCommandUrl.copyOptionalParametersFrom(orginUrl);
        assertEquals(expectedCommandUrl, targetCommandUrl);

        targetCommandUrl = new WebAppUrl(AnyRefreshableCommand.class);
        expectedCommandUrl = new WebAppUrl(AnyRefreshableCommand.class);
        expectedCommandUrl.setParameter("param1", "value1");
        expectedCommandUrl.setParameter("param2", "value2");
        targetCommandUrl.copyOptionalParametersFrom(orginUrl);
        assertEquals(expectedCommandUrl, targetCommandUrl);

        targetCommandUrl = new WebAppUrl(AnyNode.class);
        expectedCommandUrl = new WebAppUrl(AnyNode.class);
        expectedCommandUrl.setParameter("param1", "value1");
        expectedCommandUrl.setParameter("param2", "value2");
        targetCommandUrl.copyOptionalParametersFrom(orginUrl);
        assertEquals(expectedCommandUrl, targetCommandUrl);
        
    }

    public void testDisplay() throws IOException
    {
        FakeResponseHandler fakeResponseHandler = new FakeResponseHandler();
        itsWebUrlForAnyNode.displayThrough(fakeResponseHandler);
        assertEquals(itsWebUrlForAnyNode, fakeResponseHandler.redirectedDestination());
    }

    public void testCreateGhost()
    {
        WebAppUrl expected = new WebAppUrl(Object.class, "label");
        expected.disable();
        assertEquals(expected, WebAppUrl.createGhost("label"));
    }
}
