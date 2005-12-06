package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestQixwebBrowserOnCommandExecution extends ExtendedTestCase
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


    
    public void testGoToWarningNodeForNotInstantiableCommand() throws Exception
    {
        itsBrowser = QixwebBrowser.usingEnvironment(itsFakeResponseHandler, new UserData(), new FakeEnvironment());

        itsBrowser.goTo(new QixwebUrl(NotInstantiableCommand.class));
        assertNull("Shouldn't redirect anywhere", itsFakeResponseHandler.redirectedDestination());
        assertNull("Should call goToWarningNode (default don't display any node)", itsFakeResponseHandler.displayedNode());
    }
    
    public void testGoToLoginNodeForNotExecutableCommand() throws Exception
    {
        itsBrowser = QixwebBrowser.usingEnvironment(itsFakeResponseHandler, new UserData(), new FakeEnvironment());

        itsBrowser.goTo(new QixwebUrl(NotExecutableCommand.class));
        assertNull("Shouldn't redirect anywhere", itsFakeResponseHandler.redirectedDestination());
        assertEquals("Should go to login node", new QixwebLoginNode(), itsFakeResponseHandler.displayedNode());
    }    
    
    public void testExecuteWebRefreshableCommand() throws Exception
    {
        QixwebUrl webRefreshableCommandUrl = new QixwebUrl(AnyRefreshableCommand.class);
        itsBrowser.goTo(webRefreshableCommandUrl);

        assertEquals("Wrong displayed node after command execution", new AnyNode(), itsFakeResponseHandler.displayedNode());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.displayedNode());
    }
    


}