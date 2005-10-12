package org.qixweb.core.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.qixweb.core.QixwebUrl;
import org.qixweb.core.QixwebUrlFactory;



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
        map.put(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });

        assertEquals(QixwebUrl.createAsRequestFrom(map, env.nodePackage(), env.commandPackage()), urlFactory.createFrom(map));
    }
    
    public void testCreateUrlWithClass() throws Exception
    {
        assertEquals(new QixwebUrl(AnyCommand.class), urlFactory.createUrlWith(AnyCommand.class));
    }


}
