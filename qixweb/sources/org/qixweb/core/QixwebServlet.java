package org.qixweb.core;
import javax.servlet.http.*;

import org.qixweb.util.XpLogger;


public abstract class QixwebServlet extends HttpServlet
{
	public void service(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
            QixwebEnvironment environment = instantiateEnvironment();
            String templatePath = getServletContext().getRealPath(environment.velocityTemplateDir());
            QixwebBrowser browser = buildBrowser(request, response, environment, templatePath);
                    
			WebAppUrl url = new QixwebUrlFactory(environment).createFrom(request.getParameterMap());
			browser.goTo(url);
		}
		catch (Exception ex)
		{
			XpLogger.logException(ex);
			ServletUtil.reportException(response, ex);
		}
	}

    protected abstract QixwebEnvironment instantiateEnvironment();

    protected QixwebBrowser buildBrowser(HttpServletRequest request, HttpServletResponse response, QixwebEnvironment environment, String templatePath)
    {
        String userSessionID = request.getSession(true).getId();

        SessionID sessionID = new SessionID(userSessionID, request.getPathInfo());
        UserData userSessionData = environment.sessionManager().userDataFor(sessionID);

        ServletResponseHandler responseHandler = new ServletResponseHandler(response, request.getServletPath(), sessionID.nextPageID(), templatePath);    
        return new QixwebBrowser(responseHandler, userSessionData, environment, true);
    }

   
}
