package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.core.test.support.FakeEnvironment;
import org.qixweb.core.test.support.FakeResponseHandler;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebBrowserOnCommandExecutionThroughBuilder extends ExtendedTestCase
{
    private QixwebBrowser itsBrowser;
    private FakeResponseHandler itsFakeResponseHandler;

    protected void setUp() throws Exception
    {
        itsFakeResponseHandler = new FakeResponseHandler();
        itsBrowser = QixwebBrowser.usingSystem(itsFakeResponseHandler, UserData.EMPTY, new FakeEnvironment());
    }

    public void testExecuteWhenNotValidCommandRequest() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(EmptyNode.class);
        CommandWithValidationBuilder.simulateNotValidWithDestination(expectedDestination);

        itsBrowser.goTo(new QixwebUrl(CommandWithValidation.class));

        assertEquals("Wrong destination url after NOT valid command request", expectedDestination, itsFakeResponseHandler.redirectedDestination());
    }

    public void testExecuteWhenValidCommandRequest() throws Exception
    {
        QixwebUrl expectedDestination = new QixwebUrl(AnyNode.class);
        CommandWithValidation.simulateExecuteReturning(expectedDestination);
        CommandWithValidationBuilder.simulateValidRequest();

        itsBrowser.goTo(new QixwebUrl(CommandWithValidation.class));

        assertEquals("Wrong destination url after valid command request", expectedDestination, itsFakeResponseHandler.redirectedDestination());
    }
}