package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.*;
import org.qixweb.core.test.support.FakeEnvironment;

public class TestCreateMethodInvoker extends TestCase
{
    class SonOfAnyNode extends FakeNode
    {
    }

    public void testInvokeCreateOnClassOrInItsParents() throws Exception
    {
        CreateMethodInvoker.onNode(SonOfAnyNode.class, QixwebUrl.EMPTY_URL, UserDataCreator.EMPTY, new FakeEnvironment());
        assertTrue("Should be able to invoke the 'create' method on its parent", FakeNode.createMethodHasBeenCalled);
    }
}
