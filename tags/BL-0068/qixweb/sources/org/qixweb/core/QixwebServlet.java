package org.qixweb.core;

import javax.servlet.http.*;

import org.qixweb.util.XpLogger;

public abstract class QixwebServlet extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response)
    {
        QixwebEnvironment environment = null;

        try
        {
            environment = instantiateEnvironment();
            QixwebUrl.initWith(environment.servletPath(), environment.nodePackage(), environment.commandPackage());

            String templatePath = getServletContext().getRealPath(environment.velocityTemplateDir());
            QixwebBrowser browser = buildBrowser(request, response, environment, templatePath);

            QixwebUrl url = new QixwebUrlFactory(environment).createFrom(request.getParameterMap());
            browser.goTo(url);
        }
        catch (Exception ex)
        {
            handleException(response, ex);
        }
        finally
        {
            freeResourcesOn(environment);
        }
    }

    protected void freeResourcesOn(QixwebEnvironment aEnvironment)
    {
    }

    protected void addDataFrom_To(HttpServletRequest aRequest, UserData aUserData)
    {
    }

    protected abstract QixwebEnvironment instantiateEnvironment();

    protected QixwebBrowser buildBrowser(HttpServletRequest request, HttpServletResponse response, QixwebEnvironment environment, String templatePath)
    {
        String userSessionID = request.getSession(true).getId();

        SessionID sessionID = new SessionID(userSessionID, request.getPathInfo());
        UserData userSessionData = environment.sessionManager().userDataFor(sessionID);

        addDataFrom_To(request, userSessionData);
        
        ServletResponseHandler responseHandler = new ServletResponseHandler(response, request.getServletPath(), sessionID.nextPageID(), templatePath);
        return createBrowser(responseHandler, userSessionData, environment);
    }

    protected QixwebBrowser createBrowser(ServletResponseHandler responseHandler, UserData userSessionData, QixwebEnvironment environment)
    {
        return QixwebBrowser.usingEnvironment(responseHandler, userSessionData, environment);
    }

    protected void handleException(HttpServletResponse response, Exception ex)
    {
        defaultHandleException(response, ex);
    }

    protected void defaultHandleException(HttpServletResponse response, Exception ex)
    {
        try
        {
            XpLogger.logException(ex);
            ex.printStackTrace();
            ex.printStackTrace(response.getWriter());
        }
        catch (Exception e)
        {
            XpLogger.logException(e);
        }
    }
}
