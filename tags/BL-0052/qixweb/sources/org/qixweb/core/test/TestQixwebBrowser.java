package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestQixwebBrowser extends ExtendedTestCase
{
    private QixwebBrowser itsBrowser;
    private FakeResponseHandler itsFakeResponseHandler;
    private FakeEnvironment itsFakeEnvironment;
    
    protected void setUp() throws Exception
    {
        itsFakeResponseHandler = new FakeResponseHandler();
        itsFakeEnvironment = new FakeEnvironment();
        itsBrowser = QixwebBrowser.usingSystem(itsFakeResponseHandler, new UserData(), itsFakeEnvironment);
    }

    public void testGoToNodeWithABrowserUsingSystem() throws Exception
    {
        verifyDisplayedNode();
        assertTrue("When using System, Environment should not be used", itsFakeEnvironment.hasSystemBeenInvoked());
    }

    public void testGotoWarningNode() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), false) 
        {
            protected void gotoWarningNode() throws Exception
            {
                goTo(new QixwebUrl(WrongLinkNodeForTest.class));
            } 
        };
        QixwebUrl wrongLink = new QixwebUrl(Object.class);
        itsBrowser.goTo(wrongLink);

        assertEquals(new WrongLinkNodeForTest(), itsFakeResponseHandler.displayedNode());
    }

    public void testDefaultGotoWarningNodeDoesNothing() throws Exception
    {
        QixwebUrl wrongLink = new QixwebUrl(Object.class);
        itsBrowser.goTo(wrongLink);
        assertNull(itsFakeResponseHandler.displayedNode());
    }

    public void testExecuteCommand() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(AnyNode.class);
        expectedDestination.setParameter("state", "test");

        QixwebUrl commandUrl = new QixwebUrl(AnyCommand.class);
        itsBrowser.userData().store("state", "test");
        itsBrowser.goTo(commandUrl);

        assertEquals("Wrong destination url after command execution", expectedDestination, itsFakeResponseHandler.redirectedDestination());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.redirectedDestination());
    }

    public void testValidateExecutionOfCommand() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), false)
        {
            protected boolean validateExecutionOf(WebCommand aCommand) throws Exception
            {
                return false;
            }
        };

        itsBrowser.goTo(new QixwebUrl(AnyCommand.class));
        assertNull("Wrong destination url after invalid command execution", itsFakeResponseHandler.redirectedDestination());
    }
    
    public void testExecuteWebRefreshableCommand() throws Exception
    {
        QixwebUrl webRefreshableCommandUrl = new QixwebUrl(AnyRefreshableCommand.class);
        itsBrowser.goTo(webRefreshableCommandUrl);

        assertEquals("Wrong displayed node after command execution", new AnyNode(), itsFakeResponseHandler.displayedNode());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testGoToNodeWithABrowserUsingEnvironment() throws Exception
    {
        FakeEnvironment fakeEnvironment = new FakeEnvironment();
        itsBrowser = QixwebBrowser.usingEnvironment(itsFakeResponseHandler, new UserData(), fakeEnvironment);

        verifyDisplayedNode();
        assertFalse("When using Environment, the System should not be used", fakeEnvironment.hasSystemBeenInvoked());

    }

    private void verifyDisplayedNode() throws Exception
    {
        QixwebUrl nodeUrl = new QixwebUrl(AnyNode.class);
        itsBrowser.goTo(nodeUrl);
        
        assertEquals("Wrong displayed node", new AnyNode(), itsFakeResponseHandler.displayedNode());
    }

}