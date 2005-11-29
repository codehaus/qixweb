package org.qixweb.core.test;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.*;

import org.qixweb.core.QixwebEnvironment;
import org.qixweb.core.QixwebServlet;
import org.qixweb.util.test.ExtendedTestCase;

public class TestQixwebServlet extends ExtendedTestCase
{

    private ConcreteQixwebServlet itsServlet;
    private FakeHttpServletRequest itsFakeRequest;
    private FakeHttpServletResponse itsFakeResponse;

    public class ConcreteQixwebServlet extends QixwebServlet
    {
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
            return new FakeEnvironment();
        }
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        itsServlet = new ConcreteQixwebServlet();
        itsFakeRequest = new FakeHttpServletRequest();
        itsFakeResponse = new FakeHttpServletResponse();
        systemErr = System.err;
        systemOut = System.out;
    }

    public void testService()
    {
        prepareRequestAndResponse();
        itsServlet.service(itsFakeRequest, itsFakeResponse);
        assert_matchesRegex(itsFakeResponse.outputAsString(), "<A href=\"home/\\d+\\?command=AnyCommand\">Click here to execute Any Command</A>");
    }

    private void prepareRequestAndResponse()
    {
        String servletPath = new FakeEnvironment().servletPath();
        itsFakeRequest.simulateParameter("node", "AnyNode");
        itsFakeRequest.simulateServletPath(servletPath);
        itsFakeRequest.simulateSession(new FakeHttpSession());
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

    public void testExceptionReportingException()
    {
        grabSystemOutResettingLogger();
        grabSystemErr();

        QixwebServlet.reportException(itsFakeResponse, null); // nullRefInOrderToGenerateNullPointerException
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
}