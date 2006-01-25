package org.qixweb.core.test;

import java.util.HashMap;
import java.util.Map;

import org.qixweb.core.QixwebUrl;
import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.XpLogger;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebUrlCreation extends ExtendedTestCase
{
    private String itsNodePackage;
    private String itsCommandPackage;

    public void testCreateFromMapWithNodeDestination()
	{
		Map map = new HashMap();
		map.put(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });

		QixwebUrl url = QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage);

		QixwebUrl expectedUrl = new QixwebUrl(AnyNode.class);
		assertEquals("in the url, the node target class should be set", expectedUrl, url);
	}

	public void testCreateFromMapWithCommandDestination()
	{
		Map map = new HashMap();
		map.put(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE, new String[] { "AnyCommand" });
		QixwebUrl url = QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage);

		QixwebUrl expectedUrl = new QixwebUrl(AnyCommand.class);

		assertEquals("in the url, the command target class should be set (as string)", expectedUrl, url);
	}

	public void testCreateFromMapWithNotExistentDestination() throws Exception
	{
		XpLogger.off();

		Map map = new HashMap();
		map.put(QixwebUrl.PARAMETER_COMMAND_TO_EXECUTE, new String[] { "NotExistentCommand" });
		assertEquals(QixwebUrl.EMPTY_URL, QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage));

		map = new HashMap();
		map.put(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "NotExistentNode" });
		assertEquals(QixwebUrl.EMPTY_URL, QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage));

		XpLogger.resume();
	}

	public void testCreateFromMapWithDestinationNotPresent() throws Exception
	{
		XpLogger.off();

		Map map = new HashMap();
		map.put("param1", new String[] { "param1 value" });

		QixwebUrl url = QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage);

		assertEquals(QixwebUrl.EMPTY_URL, url);
	}

	public void testCreateFromDestination()
	{
		QixwebUrl simpleUrl = new QixwebUrl(AnyNode.class);
		assertEquals("Wrong Url from " + simpleUrl.toString(), simpleUrl, QixwebUrl.createAsRequestWithTarget(simpleUrl.destination(), itsNodePackage, itsCommandPackage));
	}
    
    protected void setUp() 
    {
        FakeEnvironment env = new FakeEnvironment();
        itsNodePackage = env.nodePackage();
        itsCommandPackage = env.commandPackage();
    }

	public void testCreateFromMapWithMultipleValues()
	{
		Map map = new HashMap();
		map.put(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });
		map.put("single", new String[] { "singleValue" });
		map.put("multiple", new String[] { "firstValue", "secondValue" });
		QixwebUrl url = QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage);

		assertEquals("Wrong target", AnyNode.class, url.target());
		assertEquals("Wrong single parameter", "singleValue", url.parameters().get("single"));
		ArrayAsserter.assertEquals("Wrong multiple value parameter", new String[] { "firstValue", "secondValue" }, url.parameters().getAllValuesOf("multiple"));
	}

	public void testCreateFromMapGettingParameterWithPrefix()
	{
		Map map = new HashMap();
		map.put(QixwebUrl.PARAMETER_NODE_TO_DISPLAY, new String[] { "AnyNode" });
		map.put("prefix_single", new String[] { "singleValue" });

		QixwebUrl url = QixwebUrl.createAsRequestFrom(map, itsNodePackage, itsCommandPackage);

		Map parametersMatchingPrefix = url.parameters().allBeginningWith("prefix_");
		assertEquals("singleValue", (String) parametersMatchingPrefix.get("single"));
	}

    public void testCreateFromNullMap() 
    {
        XpLogger.off();
        assertEquals(QixwebUrl.EMPTY_URL, QixwebUrl.createAsRequestFrom(null, itsNodePackage, itsCommandPackage));
    }
    
    protected void tearDown()
    {
        XpLogger.resume();
    }

    public void testCostructorWithDefaultNodePackage()
    {
        QixwebUrl.initServletPathAndDefaultNodePackage("");
        QixwebUrl url = new QixwebUrl(AnyRefreshableCommand.class);
        assertEquals
        (
                "the url should be composed without servlet path", 
                "?command=AnyRefreshableCommand", 
                url.destination()
        );
        QixwebUrl.initServletPathAndDefaultNodePackage("http://localhost/MyWebApp/servlet/MyServlet");
        url = new QixwebUrl(AnyRefreshableCommand.class);
        assertEquals
        (
                "the url should be composed using the servlet path", 
                "http://localhost/MyWebApp/servlet/MyServlet?command=AnyRefreshableCommand", 
                url.destination()
        );
    }
    

}
