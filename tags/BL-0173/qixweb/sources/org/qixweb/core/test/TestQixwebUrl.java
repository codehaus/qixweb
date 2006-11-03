package org.qixweb.core.test;

import java.io.IOException;

import org.qixweb.core.*;
import org.qixweb.core.test.support.*;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebUrl extends ExtendedTestCase
{
    private UserData itsUserData;
    private TheSystem itsSystem;
    private String itsServletPath;
    private QixwebUrl itsWebUrlForAnyCommand;
    private QixwebUrl itsWebUrlForAnyNode;

    protected void setUp() throws Exception
    {
        super.setUp();
        itsServletPath = "servletPath";
        QixwebUrl.initServletPathAndDefaultNodePackage(itsServletPath);

        itsWebUrlForAnyNode = new QixwebUrl(AnyNode.class);
        itsWebUrlForAnyCommand = new QixwebUrl(AnyCommand.class);

        itsUserData = new UserData();
        itsSystem = new TheSystem()
        {
        };
    }

    public void testMaterializeTargetNode()
    {
        AnyNode expectedNode = new AnyNode();

        QixwebUrl url = new QixwebUrl(AnyNode.class);
        WebNode node = url.materializeTargetNodeWith(itsUserData, itsSystem);

        assertEquals(expectedNode, node);
    }

    public void testNodeDestinationWithPartOfPackageInParameter()
    {
        QixwebUrl.initWith(itsServletPath, "org.qixweb.core.test.", "org.qixweb.core.test.");
        assert_contains(new QixwebUrl(AnyNode.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_NODE_TO_DISPLAY + "=AnyNode");

        QixwebUrl.initWith(itsServletPath, "org.qixweb.core.", "org.qixweb.core.");
        assert_contains(new QixwebUrl(AnyNode.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_NODE_TO_DISPLAY + "=test.AnyNode");

        QixwebUrl.initWith(itsServletPath, "org.qixweb.", "org.qixweb.");
        assert_contains(new QixwebUrl(AnyNode.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_NODE_TO_DISPLAY + "=core.test.AnyNode");

        QixwebUrl.initServletPathAndDefaultNodePackage(itsServletPath);
    }

    public void testCommandDestinationWithPartOfPackageInParameter()
    {
        QixwebUrl.initWith(itsServletPath, "org.qixweb.core.test.", "org.qixweb.core.test.");
        assert_contains(new QixwebUrl(AnyCommand.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE + "=AnyCommand");

        QixwebUrl.initWith(itsServletPath, "org.qixweb.core.", "org.qixweb.core.");
        assert_contains(new QixwebUrl(AnyCommand.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE + "=test.AnyCommand");

        QixwebUrl.initWith(itsServletPath, "org.qixweb.", "org.qixweb.");
        assert_contains(new QixwebUrl(AnyCommand.class).destination(), itsServletPath + "?" + QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE + "=core.test.AnyCommand");

        QixwebUrl.initServletPathAndDefaultNodePackage(itsServletPath);
    }

    protected void tearDown() throws Exception
    {
        QixwebUrl.initServletPathAndDefaultNodePackage("");
        super.tearDown();
    }

    public void testMaterializeNotExistentNode()
    {
        grabSystemOutResettingLogger();
        Class notExistentNode = Integer.class;
        QixwebUrl urlToNotExistentTarget = new QixwebUrl(notExistentNode);

        WebNode node = urlToNotExistentTarget.materializeTargetNodeWith(itsUserData, itsSystem);
        assertNull("It is NOT possible to materialize a not existent node", node);
        assert_contains("Exception expected", grabbedOut(), NoSuchMethodException.class.getName());
    }

    public void testMaterializeNotExistentCommand()
    {
        grabSystemOutResettingLogger();
        Class notExistentCommand = Integer.class;
        QixwebUrl urlToNotExistentTarget = new QixwebUrl(notExistentCommand);

        WebCommand command = urlToNotExistentTarget.materializeTargetCommandWith(itsUserData);
        assertNull("It is NOT possible to materialize a not existent command", command);
        assert_contains("Exception expected", grabbedOut(), NoSuchMethodException.class.getName());
    }

    public void testCommandThrowingExceptionOnCreateIsLoggedWithoutInterruptingNormalFlow()
    {
        grabSystemOutResettingLogger();
        new QixwebUrl(BadCreateCommand.class).materializeTargetCommandWith(itsUserData);
        assert_contains("Exception expected", grabbedOut(), BadCreateCommand.FAKE_MESSAGE);
    }

    public void testMaterializeTargetCommand()
    {
        AnyCommand expectedCommand = new AnyCommand();
        QixwebUrl urlToCommand = new QixwebUrl(AnyCommand.class);
        
        assertTrue(WebCommandComparator.areEquals(expectedCommand, urlToCommand));
    }

    public void testMaterializeTargetCommandInvokesCreateMethod()
    {
        AnyCommand createdCommand = (AnyCommand) new QixwebUrl(AnyCommand.class).materializeTargetCommandWith(itsUserData);
        assertTrue(createdCommand.hasBeenInstantiatedUsingCreate());
    }

    public static String encodeAmpersand(String aStringToEncode)
    {
        return aStringToEncode.replaceAll("&", "&amp;");
    }

    public void testEquals()
    {
        QixwebUrl url = new QixwebUrl(AnyNode.class);
        QixwebUrl sameUrl = new QixwebUrl(AnyNode.class);
        QixwebUrl aDifferentUrl = new QixwebUrl(AnyCommand.class);

        EqualsBehaviourVerifier.check("different type of url", url, sameUrl, aDifferentUrl);
        EqualsBehaviourVerifier.checkHashCode(url, sameUrl);

        sameUrl.destination();
        EqualsBehaviourVerifier.check("calling destination should not affect equals", url, sameUrl, aDifferentUrl);
    }

    public void testClassNameParameter()
    {
        String nodeToDisplayParameter = itsWebUrlForAnyNode.parameters().get(QixwebUrl.PARAMETER_NODE_TO_DISPLAY);
        assertEquals("wrong node name ", "AnyNode", nodeToDisplayParameter);

        String commandToExecuteParameter = itsWebUrlForAnyCommand.parameters().get(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE);
        assertEquals("wrong command name", "AnyCommand", commandToExecuteParameter);

        QixwebUrl neitherCommandNorNodeUrl = new QixwebUrl(Object.class);
        assertNull("No command name should be set", neitherCommandNorNodeUrl.parameters().get(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE));
        assertNull("No node name should be set", neitherCommandNorNodeUrl.parameters().get(QixwebUrl.PARAMETER_NODE_TO_DISPLAY));
    }

    public void testNodeDestination()
    {
        String expectedDestination = itsServletPath + "?" + QixwebUrl.PARAMETER_NODE_TO_DISPLAY + "=AnyNode";
        String returnedDestination = itsWebUrlForAnyNode.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should go the a node", itsWebUrlForAnyNode.isGoingToANode());
    }

    public void testCommandDestination()
    {
        String expectedDestination = itsServletPath + "?" + QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE + "=AnyCommand";
        String returnedDestination = itsWebUrlForAnyCommand.destination();
        assertEquals("wrong destination composition", expectedDestination, returnedDestination);
        assertTrue("should execute a command", itsWebUrlForAnyCommand.isExecutingACommand());
    }

    public void testDestinationWithParametersAndTargetClass()
    {
        String expectedDestination = itsServletPath;
        String expectedFirstParameter = QixwebUrl.PARAMETER_NODE_TO_DISPLAY + "=AnyNode";

        String expectedSecondParameter = "parameter=value";
        itsWebUrlForAnyNode.parameters().set("parameter", "value");

        String returnedDestination = itsWebUrlForAnyNode.destination();

        assert_contains("wrong destination composition", returnedDestination, expectedDestination);
        assert_contains("wrong parameter", returnedDestination, expectedFirstParameter);
        assert_contains("wrong parameter", returnedDestination, expectedSecondParameter);
    }

    public void testCopyOptionalParametersFromRefreshableCommandToAnyCommand()
    {
        QixwebUrl orginalUrl = new QixwebUrl(RefreshableCommand.class);
        orginalUrl.parameters().set(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE, "AnyRefreshableCommand");
        orginalUrl.parameters().set("param1", "value1");
        
        QixwebUrl actualUrl = new QixwebUrl(AnyCommand.class);

        actualUrl.addOptionalParametersFrom(orginalUrl);

        QixwebUrl expectedUrl = new QixwebUrl(AnyCommand.class);
        expectedUrl.parameters().set("param1", "value1");

        assertEquals(expectedUrl, actualUrl);
    }

    public void testAddOptionalParametersNotOverwriteNode()
    {
        QixwebUrl source = new QixwebUrl(AnyNode.class);
        source.parameters().set("param1", "value1");

        QixwebUrl destination = new QixwebUrl(NotAuthorizedNode.class);
    
        destination.addOptionalParametersFrom(source);
        
        QixwebUrl expected = new QixwebUrl(NotAuthorizedNode.class);
        expected.parameters().set("param1", "value1");
        
        assertEquals(expected, destination);
    }
    
    public void testAddOptionalParametersNotOverwriteCommand()
    {
        QixwebUrl source = new QixwebUrl(AnyCommand.class);
        source.parameters().set("param1", "value1");

        QixwebUrl destination = new QixwebUrl(RefreshableCommand.class);
    
        destination.addOptionalParametersFrom(source);
        
        QixwebUrl expected = new QixwebUrl(RefreshableCommand.class);
        expected.parameters().set("param1", "value1");
        
        assertEquals(expected, destination);
    }
    

    public void testCopyOptionalParametersFromRefreshableCommandToAnyNode()
    {
        QixwebUrl orginalUrl = new QixwebUrl(RefreshableCommand.class);
        orginalUrl.parameters().set("param1", "value1");
        orginalUrl.parameters().set("param2", "value2");
        orginalUrl.parameters().set(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, "AnyNode");

        QixwebUrl actualUrl = new QixwebUrl(AnyNode.class);
        
        actualUrl.addOptionalParametersFrom(orginalUrl);
        
        QixwebUrl expectedUrl = new QixwebUrl(AnyNode.class);
        expectedUrl.parameters().set("param1", "value1");
        expectedUrl.parameters().set("param2", "value2");

        assertEquals(expectedUrl, actualUrl);
    }
    
    public void testDisplay() throws IOException
    {
        FakeResponseHandler fakeResponseHandler = new FakeResponseHandler();
        itsWebUrlForAnyNode.displayThrough(fakeResponseHandler);
        assertEquals(itsWebUrlForAnyNode, fakeResponseHandler.redirectedDestination());
    }

    public void testCreateGhost()
    {
        assertFalse(QixwebUrl.createGhost("not available link").isEnabled());
    }
    
    public void testToCommandBuilder() throws Exception
    {
        assertNull
        (
            "Null should be returned for command without related request object",
            itsWebUrlForAnyCommand.toCommandBuilder()
        );
        
        QixwebUrl urlForCommandWithValidation = new QixwebUrl(CommandWithValidation.class);
        assertEquals(new CommandWithValidationBuilder(urlForCommandWithValidation.parameters()), urlForCommandWithValidation.toCommandBuilder());
    }
}
