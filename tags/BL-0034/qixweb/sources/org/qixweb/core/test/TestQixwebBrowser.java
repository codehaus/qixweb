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
        WebAppUrl nodeUrl = WebAppUrl.createFor(AnyNode.class);
        itsBrowser.goTo(nodeUrl);

        assertEquals("Wrong displayed node", new AnyNode(), itsFakeResponseHandler.displayedNode());
    }

    public void testGotoWarningNode() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment())
        {
            protected void gotoWarningNode() throws Exception
            {
                goTo(WebAppUrl.createFor(WrongLinkNodeForTest.class));
            }            
        };
        WebAppUrl wrongLink = WebAppUrl.createFor(Object.class);
        itsBrowser.goTo(wrongLink);

        assertEquals(new WrongLinkNodeForTest(), itsFakeResponseHandler.displayedNode());
    }

    public void testDefaultGotoWarningNodeDoesNothing() throws Exception
    {
        WebAppUrl wrongLink = WebAppUrl.createFor(Object.class);
        itsBrowser.goTo(wrongLink);
        assertNull(itsFakeResponseHandler.displayedNode());
    }
    public void testExecuteCommand() throws Exception
    {
        WebAppUrl expectedDestination = WebAppUrl.createFor(AnyNode.class);
        expectedDestination.setParameter("state", "test");

        WebAppUrl commandUrl = WebAppUrl.createFor(AnyCommand.class);
        itsBrowser.userData().store("state", "test");
        itsBrowser.goTo(commandUrl);

        assertEquals("Wrong destination url after command execution", expectedDestination, itsFakeResponseHandler.redirectedDestination());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.redirectedDestination());
    }

    public void testValidateExecutionOfCommand() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment())
        {
            protected boolean validateExecutionOf(WebCommand aCommand) throws Exception
            {
                return false;
            }
        };

        itsBrowser.goTo(WebAppUrl.createFor(AnyCommand.class));
        assertNull("Wrong destination url after invalid command execution", itsFakeResponseHandler.redirectedDestination());
    }
    
    public void testExecuteWebRefreshableCommand() throws Exception
    {
        WebAppUrl webRefreshableCommandUrl = WebAppUrl.createFor(AnyRefreshableCommand.class);
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