package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.core.test.support.FakeEnvironment;
import org.qixweb.core.test.support.FakeResponseHandler;
import org.qixweb.util.XpLogger;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebBrowserOnNodeRenderingThroughBuilder extends ExtendedTestCase
{
    private FakeResponseHandler itsFakeResponseHandler;

    protected void setUp() throws Exception
    {
        itsFakeResponseHandler = new FakeResponseHandler();
    }

    public void test() throws Exception
    {
        QixwebBrowser browserUsingEnvironment = QixwebBrowser.usingEnvironment(itsFakeResponseHandler, UserDataCreator.EMPTY, new FakeEnvironment());

        browserUsingEnvironment.goTo(new QixwebUrl(NodeWithoutCreateMethod.class));

        assertTrue(NodeWithoutCreateMethodBuilder.hasBeenCalled());
        assertEquals(new NodeWithoutCreateMethod(), itsFakeResponseHandler.displayedNode());
    }

    public void testNodeCreationThroughBuilderIsNotAvailabileForQixwebBrowserUsingSystem() throws Exception
    {
        XpLogger.off();

        QixwebBrowser browserUsingSystem = QixwebBrowser.usingSystem(itsFakeResponseHandler, UserDataCreator.EMPTY, new FakeEnvironment());
        browserUsingSystem.goTo(new QixwebUrl(NodeWithoutCreateMethod.class));

        assertFalse(NodeWithoutCreateMethodBuilder.hasBeenCalled());
        assertNull("creation with builder is only available for QixwebBrowser using Environment", itsFakeResponseHandler.displayedNode());
    }

    protected void tearDown()
    {
        XpLogger.resume();
        NodeWithoutCreateMethodBuilder.reset();
    }

}