package org.qixweb.core.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebServlet extends ExtendedTestCase
{
    private ConcreteQixwebServlet itsServlet;
    private FakeEnvironment itsFakeEnvironment; 
    
    private FakeHttpServletRequest itsFakeRequest;
    private FakeHttpServletResponse itsFakeResponse;

    public class ConcreteQixwebServlet extends QixwebServlet
    {
        UserData userDataForTest = null;
        
        public ConcreteQixwebServlet() throws ServletException
        {
            init(new ServletConfig()
            {
                public String getServletName()
                {
                    return null;
                }

                public ServletContext getServletContext()
                {
                    return new ServletContext()
                    {
                        public ServletContext getContext(String aS)
                        {
                            return null;
                        }

                        public int getMajorVersion()
                        {
                            return 0;
                        }

                        public int getMinorVersion()
                        {
                            return 0;
                        }

                        public String getMimeType(String aS)
                        {
                            return null;
                        }

                        public Set getResourcePaths(String aS)
                        {
                            return null;
                        }

                        public URL getResource(String aS) throws MalformedURLException
                        {
                            return null;
                        }

                        public InputStream getResourceAsStream(String aS)
                        {
                            return null;
                        }

                        public RequestDispatcher getRequestDispatcher(String aS)
                        {
                            return null;
                        }

                        public RequestDispatcher getNamedDispatcher(String aS)
                        {
                            return null;
                        }

                        public Servlet getServlet(String aS) throws ServletException
                        {
                            return null;
                        }

                        public Enumeration getServlets()
                        {
                            return null;
                        }

                        public Enumeration getServletNames()
                        {
                            return null;
                        }

                        public void log(String aS)
                        {
                        }

                        public void log(Exception aException, String aS)
                        {
                        }

                        public void log(String aS, Throwable aThrowable)
                        {
                        }

                        public String getRealPath(String aS)
                        {
                            return aS;
                        }

                        public String getServerInfo()
                        {
                            return null;
                        }

                        public String getInitParameter(String aS)
                        {
                            return null;
                        }

                        public Enumeration getInitParameterNames()
                        {
                            return null;
                        }

                        public Object getAttribute(String aS)
                        {
                            return null;
                        }

                        public Enumeration getAttributeNames()
                        {
                            return null;
                        }

                        public void setAttribute(String aS, Object aObj)
                        {
                        }

                        public void removeAttribute(String aS)
                        {
                        }

                        public String getServletContextName()
                        {
                            return null;
                        }
                    };
                }

                public String getInitParameter(String aS)
                {
                    return null;
                }

                public Enumeration getInitParameterNames()
                {
                    return null;
                }
            });
        }

        protected QixwebEnvironment instantiateEnvironment()
        {
            return itsFakeEnvironment;
        }
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        itsFakeEnvironment = new FakeEnvironment(); 
        itsServlet = new ConcreteQixwebServlet();
        itsFakeRequest = new FakeHttpServletRequest();
        itsFakeResponse = new FakeHttpServletResponse();
    }

    public void testService()
    {
        prepareRequestWith(itsFakeRequest, "sessionID", "123456");
        itsServlet.service(itsFakeRequest, itsFakeResponse);
        assert_matchesRegex(itsFakeResponse.outputAsString(), "<A href=\"home/\\d+\\?command=AnyCommand\">Click here to execute Any Command</A>");
    }
    
    
    public void testUserDataRetrieval()
    {
        String sessionID = "sessionID";
        String pathInfo = "/123456789";
        UserData userData = new UserData();
        userData.store("MARKER_KEY", "MARKER_VALUE");

        prepareRequestWith(itsFakeRequest, sessionID, pathInfo);

        itsFakeEnvironment.sessionManager().storeUserData(new SessionID(sessionID, pathInfo), userData);
        
        itsServlet.service(itsFakeRequest, itsFakeResponse);
        String html = itsFakeResponse.outputAsString();
        
        assert_matchesRegex(html, "<A href=\"home/\\d+\\?command=AnyCommand\">Click here to execute Any Command</A>");
        assertEquals(userData, itsFakeEnvironment.sessionManager().userDataFor(new SessionID(sessionID, "/"+extractNextPageIDFrom(html))));
    }

    private String extractNextPageIDFrom(String htmlPage)
    {
        Pattern pattern = Pattern.compile("<A href=\"home/(\\d+)");
        Matcher matcher = pattern.matcher(htmlPage);
        assertTrue(matcher.find());
        String generatedPageID = matcher.group(1);
        return generatedPageID;
    }

    private void prepareRequestWith(FakeHttpServletRequest fakeRequest, String sessionID, String pathInfo)
    {
        fakeRequest.simulateParameter("node", "AnyNode");
        fakeRequest.simulateServletPath(itsFakeEnvironment.servletPath());
        fakeRequest.simulatePathInfo(pathInfo);        
        fakeRequest.simulateSession(createSessionFor(sessionID));
    }

    private FakeHttpSession createSessionFor(String sessionID)
    {
        FakeHttpSession session = new FakeHttpSession();
        session.simulateSessionID(sessionID);
        return session;
    }    



    public void testException() throws ServletException
    {
        grabSystemOutResettingLogger();
        grabSystemErr();
        itsServlet = new ConcreteQixwebServlet()
        {
            protected QixwebEnvironment instantiateEnvironment()
            {
                throw new RuntimeException("Fake generated exception");
            }
        };
        itsServlet.service(itsFakeRequest, itsFakeResponse);
        assert_contains(grabbedErr(), "Fake generated exception");
        assert_contains(grabbedOut(), "Fake generated exception");
    }

    public void testExceptionWithCustomHandleException() throws ServletException
    {
        grabSystemOutResettingLogger();

        itsServlet = new ConcreteQixwebServlet()
        {
            protected QixwebEnvironment instantiateEnvironment()
            {
                throw new RuntimeException("Fake generated exception");
            }

            protected void handleException(HttpServletResponse aResponse, Exception aEx)
            {
                try
                {
                    aResponse.getWriter().print("Custom Error page");
                }
                catch (IOException e)
                {
                }
            }
        };

        itsServlet.service(itsFakeRequest, itsFakeResponse);

        assertEquals("Custom Error page", itsFakeResponse.outputAsString());
    }

    public void testExceptionReportingException() throws ServletException
    {
        grabSystemOutResettingLogger();
        grabSystemErr();

        itsServlet = new ConcreteQixwebServlet()
        {
            protected QixwebEnvironment instantiateEnvironment()
            {
                throw new RuntimeException("Fake generated exception");
            }

            protected void handleException(HttpServletResponse aResponse, Exception aEx)
            {
                super.defaultHandleException(itsFakeResponse, null);
            }
        };
        itsServlet.service(itsFakeRequest, itsFakeResponse);

        assert_contains(grabbedOut(), "NullPointerException");
    }

    public void testFreeResources() throws Exception
    {
        String servletPath = new FakeEnvironment().servletPath();
        itsFakeRequest.simulateParameter("command", "FakeCommandVerifyingResouceLocking");
        itsFakeRequest.simulateServletPath(servletPath);
        itsFakeRequest.simulateSession(new FakeHttpSession());

        final FakeEnvironment environment = new FakeEnvironment();

        itsServlet = new ConcreteQixwebServlet()
        {
            protected QixwebEnvironment instantiateEnvironment()
            {
                environment.lockResources();
                return environment;
            }

            protected void freeResourcesOn(QixwebEnvironment aEnvironment)
            {
                FakeEnvironment fakeEnvironment = (FakeEnvironment) aEnvironment;
                fakeEnvironment.freeResources();
            }
        };

        assertTrue("Before the request resources should be free", environment.areResourcesFree());
        itsServlet.service(itsFakeRequest, itsFakeResponse);
        assertTrue("At the end of the request resources should be free again", environment.areResourcesFree());
    }

    public void testAddDataFrom_To() throws Exception
    {
        prepareRequestWith(itsFakeRequest, "sessionID", "123456");
        itsFakeRequest.simulateParameter("aCustomParameter", "aValue");
                
        itsServlet = new ConcreteQixwebServlet()
        {           
            protected void addDataFrom_To(HttpServletRequest aRequest, UserData aUserData)
            {
                aUserData.store("aKey", aRequest.getParameter("aCustomParameter"));
                userDataForTest = aUserData;
            }
        };

        itsServlet.service(itsFakeRequest, itsFakeResponse);
        assertEquals("A value should be found in UserData", "aValue", itsServlet.userDataForTest.valueFor("aKey"));
    }
}
