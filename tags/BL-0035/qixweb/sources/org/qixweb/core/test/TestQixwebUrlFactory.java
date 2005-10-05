package org.qixweb.core.test;

import java.util.HashMap;
import java.util.Map;

import org.qixweb.core.QixwebUrlFactory;
import org.qixweb.core.WebAppUrl;

import junit.framework.TestCase;



public class TestQixwebUrlFactory extends TestCase
{

    private FakeEnvironment env;
    private QixwebUrlFactory urlFactory;

    protected void setUp() throws Exception
    {
        env = new FakeEnvironment();
        urlFactory = new QixwebUrlFactory(env);
    }
    
    public void testCreateFromMap() throws Exception
    {
        Map map = new HashMap();
        map.put(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });

        assertEquals(WebAppUrl.createFrom(map, env.nodePackage(), env.commandPackage()), urlFactory.createFrom(map));
    }
    
    public void testCreateUrlWithClass() throws Exception
    {
        assertEquals(new WebAppUrl(AnyCommand.class), urlFactory.createUrlWith(AnyCommand.class));
    }


}
