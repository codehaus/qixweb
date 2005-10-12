package org.qixweb.core.test;

import java.io.IOException;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestServletResponseHandler extends ExtendedTestCase
{
	private FakeHttpServletResponse itsHttpResponse;
	private String itsPageID;
	private String itsServletPath;
	private ServletResponseHandler itsResponseHandler;
    protected void setUp() throws Exception
	{
        super.setUp();
		itsHttpResponse = new FakeHttpServletResponse();
		itsPageID = "1234567";
		itsServletPath = "servlet/WebAppServlet";
        QixwebUrl.initServletPath(itsServletPath);
		
		itsResponseHandler = new ServletResponseHandler(itsHttpResponse, itsServletPath, itsPageID, "./templateVelocity/");
    }
    public void testRenderNodeWithVelocity() throws Exception
	{
        AnyNode node = new AnyNode("Title");
		itsResponseHandler.display(node);

		String renderOutput = itsHttpResponse.outputAsString();
		assert_contains("The page must contain its id", renderOutput, itsPageID);

        assert_contains("The node should be rendered", renderOutput, "Title");
        assert_notContains("The page should not contain any macro", renderOutput, "#enableif");        
	}
	
	public void testRedirectToUrl() throws Exception
	{
		QixwebUrl url = new QixwebUrl(AnyNode.class); 
		itsResponseHandler.redirectTo(url);

		assertEquals("Wrong redirect", itsServletPath + "/" + itsPageID +"?node=AnyNode", itsHttpResponse.redirectedUrl());
		assert_contains("Wrong page id", itsHttpResponse.redirectedUrl(), itsPageID);
	}
    
    public void testInexistentTemplate() throws IOException
    {
        grabSystemOutResettingLogger();
        try
        {
            itsResponseHandler.display(new WebNode()
            {
            
                public QixwebUrl[] connections()
                {
                    return new QixwebUrl[0];
                }
            
            });
            fail("NullPointerException should be thrown because of inexistent template");
        }
        catch (NullPointerException ex)
        {
            assert_matchesRegex(grabbedOut(), "(?s)ResourceNotFoundException.*NullPointerException");
        }
    }
    public void testRewriteAllUrlsAddingPageID() throws Exception
	{
		String expectedHtml =
			"<A href=\"http://"
				+ itsServletPath
				+ "/"
				+ itsPageID
				+ "?param=value\">link</a>"
				+ "<A href=\"http://servlet/ImportCSVServlet"
				+ "/"
				+ itsPageID
				+ "?param=value\">csv link</a>"
				+ "<A href=\"http://servlet/ScriptServlet"
				+ "/"
				+ itsPageID
				+ "\">script link</a>"
				+ "<h3>a title</h3><A href=\"http://"
				+ itsServletPath
				+ "/"
				+ itsPageID
				+ "?userName=pollo\">user name</a>";

		String html =
			"<A href=\"http://"
				+ itsServletPath
				+ "?param=value\">link</a>"
				+ "<A href=\"http://servlet/ImportCSVServlet?param=value\">csv link</a>"
				+ "<A href=\"http://servlet/ScriptServlet\">script link</a>"
				+ "<h3>a title</h3><A href=\"http://"
				+ itsServletPath
				+ "?userName=pollo\">user name</a>";

		String htmlWithAllUrlsRewritten = itsResponseHandler.addPageIdToAllUrlsOf(html);
		assertEquals("Extra path information must be added to all urls", expectedHtml, htmlWithAllUrlsRewritten);
	}
}
