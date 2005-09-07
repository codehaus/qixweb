package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestQixwebBrowser extends ExtendedTestCase
{
    private QixwebBrowser itsBrowser;
    private FakeResponseHandler itsFakeResponseHandler;
    protected void setUp() throws Exception
    {
        itsFakeResponseHandler = new FakeResponseHandler();
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment());
    }

    public void testGoToNode() throws Exception
    {
        WebAppUrl nodeUrl = new WebAppUrl(AnyNode.class, FakeSystem.BASE_URL);
        itsBrowser.goTo(nodeUrl);

        assertEquals("Wrong displayed node", new AnyNode(), itsFakeResponseHandler.displayedNode());
    }

    public void testGotoWarningNode() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment())
        {
            protected void gotoWarningNode() throws Exception
            {
                goTo(new WebAppUrl(WrongLinkNodeForTest.class, FakeSystem.BASE_URL));
            }            
        };
        WebAppUrl wrongLink = new WebAppUrl(Object.class, FakeSystem.BASE_URL);
        itsBrowser.goTo(wrongLink);

        assertEquals(new WrongLinkNodeForTest(), itsFakeResponseHandler.displayedNode());
    }

    public void testDefaultGotoWarningNodeDoesNothing() throws Exception
    {
        WebAppUrl wrongLink = new WebAppUrl(Object.class, FakeSystem.BASE_URL);
        itsBrowser.goTo(wrongLink);
        assertNull(itsFakeResponseHandler.displayedNode());
    }
    public void testExecuteCommand() throws Exception
    {
        WebAppUrl expectedDestination = new WebAppUrl(AnyNode.class, FakeSystem.BASE_URL);
        expectedDestination.setParameter("state", "test");

        WebAppUrl commandUrl = new WebAppUrl(AnyCommand.class, FakeSystem.BASE_URL);
        itsBrowser.userData().store("state", "test");
        itsBrowser.goTo(commandUrl);

        assertEquals("Wrong destination url after command execution", expectedDestination, itsFakeResponseHandler.redirectedDestination());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.redirectedDestination());
    }

    public void testValidateExecutionOfCommand() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment())
        {
            protected boolean validateExecutionOf(WebRefreshableCommand aCommand) throws Exception
            {
                return false;
            }
        };

        itsBrowser.goTo(new WebAppUrl(AnyCommand.class, FakeSystem.BASE_URL));
        assertNull("Wrong destination url after invalid command execution", itsFakeResponseHandler.redirectedDestination());
    }
    
    public void testValidateExecutionOfRefreshableCommand() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment())
        {
            protected boolean validateExecutionOf(WebRefreshableCommand aCommand) throws Exception
            {
                return false;
            }
        };

        itsBrowser.goTo(new WebAppUrl(AnyRefreshableCommand.class, FakeSystem.BASE_URL));
        assertNull("Wrong destination url after invalid command execution", itsFakeResponseHandler.displayedNode());
    }    

    public void testExecuteWebRefreshableCommand() throws Exception
    {
        WebAppUrl webRefreshableCommandUrl = new WebAppUrl(AnyRefreshableCommand.class, FakeSystem.BASE_URL);
        itsBrowser.goTo(webRefreshableCommandUrl);

        assertEquals("Wrong displayed node after command execution", new AnyNode(), itsFakeResponseHandler.displayedNode());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testUseEnvironmentWhenGoingToNode() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), true);
        testGoToNode();
    }

}