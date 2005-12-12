package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestQixwebBrowserOnNodeRendering extends ExtendedTestCase
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

    public void testGotoWarningNodeForUrlToNeitherNodeNorCommand() throws Exception
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

    public void testGotoWarningNodeForProblemsDuringDisplay() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), false) 
        {
            protected void gotoWarningNode() throws Exception
            {
                goTo(new QixwebUrl(WrongLinkNodeForTest.class));
            } 
        };
        QixwebUrl wrongLink = new QixwebUrl(NotDisplayableNode.class);
        itsBrowser.goTo(wrongLink);

        assertEquals(new WrongLinkNodeForTest(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testGotoWarningNodeForUrlToNotInstantiableNode() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), false) 
        {
            protected void gotoWarningNode() throws Exception
            {
                goTo(new QixwebUrl(WrongLinkNodeForTest.class));
            } 
        };
        QixwebUrl wrongLink = new QixwebUrl(NotInstantiableNode.class);
        itsBrowser.goTo(wrongLink);

        assertEquals(new WrongLinkNodeForTest(), itsFakeResponseHandler.displayedNode());
    }
    

    public void testGoToLoginNodeForNotLoggedUser() throws Exception
    {
        itsBrowser.goTo(new QixwebUrl(OnlyLoggedUserNode.class));

        assertEquals("If user is not logged should go to default node", new QixwebLoginNode(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testGoToNodeForLoggedUser() throws Exception
    {
        itsBrowser = new QixwebBrowser(itsFakeResponseHandler, new UserData(), new FakeEnvironment(), false) 
        {
            protected QixwebUser loggedUser()
            {
                return QixwebUser.createUserWith("name", "pwd", "", "", "", "", false, true);
            }
        };
        itsBrowser.goTo(new QixwebUrl(OnlyLoggedUserNode.class));

        assertEquals("If user is not logged should go to default node", new OnlyLoggedUserNode(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testGoToLoginNodeForNotAuthorizedUser() throws Exception
    {
        itsBrowser.goTo(new QixwebUrl(NotAuthorizedNode.class));

        assertEquals("If user in not authorized should go to login node", new QixwebLoginNode(), itsFakeResponseHandler.displayedNode());
    }
    
    public void testDefaultGotoWarningNodeDoesNothing() throws Exception
    {
        QixwebUrl wrongLink = new QixwebUrl(Object.class);
        itsBrowser.goTo(wrongLink);
        assertNull(itsFakeResponseHandler.displayedNode());
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