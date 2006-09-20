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

    public void testExecuteRedirectingCommand() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(AnyNode.class);
        QixwebUrl commandUrl = RedirectingCommand.urlToMeRedirectingTo(expectedDestination);

        itsBrowser.goTo(commandUrl);

        assertEquals("Wrong destination url after command execution", expectedDestination, itsFakeResponseHandler.redirectedDestination());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.redirectedDestination());
    }
    
    public void testExecuteRefreshableCommand() throws Exception
    {
        QixwebUrl webRefreshableCommandUrl = new QixwebUrl(RefreshableCommand.class);
        itsBrowser.goTo(webRefreshableCommandUrl);
        
        assertEquals("Wrong displayed node after command execution", new AnyNode(), itsFakeResponseHandler.displayedNode());
        assertSame(itsFakeResponseHandler.lastBrowsed(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testExecuteWhenNotValidCommandRequest() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(EmptyNode.class);
        CommandWithValidationRequest.simulateNotValidWithDestination(expectedDestination);

        itsBrowser.goTo(new QixwebUrl(CommandWithValidation.class));

        assertEquals("Wrong destination url after NOT valid command request", expectedDestination, itsFakeResponseHandler.redirectedDestination());
    }
    
    public void testExecuteWhenValidCommandRequest() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(AnyNode.class);
        CommandWithValidation.simulateExecuteReturning(expectedDestination);
        CommandWithValidationRequest.simulateValidRequest();

        itsBrowser.goTo(new QixwebUrl(CommandWithValidation.class));

        assertEquals("Wrong destination url after valid command request", expectedDestination, itsFakeResponseHandler.redirectedDestination());
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
    
    
    public void testAnonymousGoToLoginNodeIfAuthenticationIsRequired() throws Exception
    {
        itsBrowser.goTo(new QixwebUrl(OnlyLoggedUserCommand.class));
        assertEquals(new QixwebLoginNode(), itsFakeResponseHandler.displayedNode());
    }

    public void testLoggedUserExecuteCommand() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), itsFakeEnvironment, false) 
        {
            protected QixwebUser loggedUser()
            {
                return QixwebUser.createUserWith("name", "pwd", "", "", "", "", false, true);
            }
        };
        itsBrowser.goTo(new QixwebUrl(OnlyLoggedUserCommand.class));
        assertEquals(new AnyNode(), itsFakeResponseHandler.displayedNode());
    }
}