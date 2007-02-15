package org.qixweb.core.test;

import java.util.HashMap;
import java.util.Map;

import org.qixweb.core.WebAppUrl;
import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.XpLogger;
import org.qixweb.util.test.ExtendedTestCase;

public class TestWebAppUrlCreation extends ExtendedTestCase
{
	private String itsBaseUrl;
    private String itsNodePackage;
    private String itsCommandPackage;

    public void testCreateFromMapWithNodeDestination()
	{
		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });

		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		WebAppUrl expectedUrl = new WebAppUrl(AnyNode.class, itsBaseUrl);
		assertEquals("in the url, the node target class should be set", expectedUrl, url);
	}

	public void testCreateFromMapWithCommandDestination()
	{
		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE, new String[] { "AnyCommand" });

		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		WebAppUrl expectedUrl = new WebAppUrl(AnyCommand.class, itsBaseUrl);

		assertEquals("in the url, the command target class should be set (as string)", expectedUrl, url);
	}

	public void testCreateFromMapWithRefreshableCommandDestination()
	{
		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE, new String[] { "AnyRefreshableCommand" });
		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		WebAppUrl expectedUrl = new WebAppUrl(AnyRefreshableCommand.class, itsBaseUrl);

		assertEquals("in the url, the refreshable command target class should be set (as string)", expectedUrl, url);
	}

	public void testCreateFromMapWithNotExistentDestination() throws Exception
	{
		XpLogger.off();

		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_COMMAND_TO_EXECUTE, new String[] { "NotExistentCommand" });
		assertEquals(WebAppUrl.EMPTY_URL, WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl));

		map = new HashMap();
		map.put(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "NotExistentNode" });
		assertEquals(WebAppUrl.EMPTY_URL, WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl));

		XpLogger.resume();
	}

	public void testCreateFromMapWithDestinationNotPresent() throws Exception
	{
		XpLogger.off();

		Map map = new HashMap();
		map.put("param1", new String[] { "param1 value" });

		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		assertEquals(WebAppUrl.EMPTY_URL, url);
	}

	public void testCreateFromDestination()
	{
		WebAppUrl simpleUrl = new WebAppUrl(AnyNode.class, itsBaseUrl);
		assertEquals("Wrong Url from " + simpleUrl.toString(), simpleUrl, WebAppUrl.createWithTarget(simpleUrl.destination(), itsNodePackage, itsCommandPackage, itsBaseUrl));
	}
    
    protected void setUp() 
    {
        FakeEnvironment env = new FakeEnvironment();
        itsBaseUrl = env.servletPath();
        itsNodePackage = env.nodePackage();
        itsCommandPackage = env.commandPackage();
    }

	public void testCreateFromMapWithMultipleValues()
	{
		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });
		map.put("single", new String[] { "singleValue" });
		map.put("multiple", new String[] { "firstValue", "secondValue" });
		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		assertEquals("Wrong target", AnyNode.class, url.target());
		assertEquals("Wrong single parameter", "singleValue", url.getParameter("single"));
		ArrayAsserter.assertEquals("Wrong multiple value parameter", new String[] { "firstValue", "secondValue" }, url.getParameterValuesOf("multiple"));
	}

	public void testCreateFromMapGettingParameterWithPrefix()
	{
		Map map = new HashMap();
		map.put(WebAppUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });
		map.put("prefix_single", new String[] { "singleValue" });

		WebAppUrl url = WebAppUrl.createFrom(map, itsNodePackage, itsCommandPackage, itsBaseUrl);

		Map parametersMatchingPrefix = url.parametersBeginningWith("prefix_");
		assertEquals("singleValue", (String) parametersMatchingPrefix.get("single"));
	}

    public void testCreateFromNullMap() 
    {
        XpLogger.off();
        assertEquals(WebAppUrl.EMPTY_URL, WebAppUrl.createFrom(null, itsNodePackage, itsCommandPackage, itsBaseUrl));
    }
    
    protected void tearDown()
    {
        XpLogger.resume();
    }
}
