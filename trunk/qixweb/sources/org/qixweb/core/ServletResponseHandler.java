package org.qixweb.core;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.qixweb.util.StringUtil;
import org.qixweb.util.XpLogger;


public class ServletResponseHandler implements ResponseHandler
{
    private String itsServletPath;
    private String itsPageID;
    private HttpServletResponse itsResponse;
    private static VelocityEngine itsVelocityEngine;

    public ServletResponseHandler(HttpServletResponse aResponse, String aServletPath, String aPageID, String aTemplatePath)
    {
        itsResponse = aResponse;
        itsPageID = aPageID;
        itsServletPath = aServletPath;
        if (itsVelocityEngine == null)
            initializeVelocityEngine(aTemplatePath);
    }

    private static synchronized void initializeVelocityEngine(String aTemplatePath)
    {
        if (itsVelocityEngine == null)
            itsVelocityEngine = VelocityInitializer.init(aTemplatePath);
    }

    public void redirectTo(WebAppUrl aDestinationUrl) throws IOException
    {
        itsResponse.sendRedirect(addPageIdToAllUrlsOf(aDestinationUrl.destination()));
    }

    public void display(WebNode aDestinationNode) throws IOException
    {
        sendHtml(render(aDestinationNode));
    }

    protected void sendHtml(String aHtmlToSend) throws IOException
    {
        itsResponse.setContentType("text/html");
        PrintWriter out = itsResponse.getWriter();
        out.println(addPageIdToAllUrlsOf(aHtmlToSend));
    }

    private String render(WebNode node)
    {
        String html = null;
        StringWriter writer = new StringWriter();
        try
        {
            Template macros = itsVelocityEngine.getTemplate("macro.vm");
            VelocityContext context = new VelocityContext();
            macros.merge(context, writer);

            Template template = relativeVelocityTemplateOf(node);
            VelocityHTMLBinder htmlRelativeToNode = new VelocityHTMLBinder(node);
            htmlRelativeToNode.bindTo(context);
            template.merge(context, writer);

            html = writer.toString();
        }
        catch (Exception e)
        {
            XpLogger.logException(e);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException ioex) { /*never*/ }
        }

        return html;
    }

    private Template relativeVelocityTemplateOf(WebNode node)
    {
        Template template = null;
        try
        {
            String nodeFullyQualifiedName = node.getClass().getName();
            String nodeClassName = nodeFullyQualifiedName.substring(nodeFullyQualifiedName.lastIndexOf(".") + 1);

            String templateFileName = StringUtil.replace_with_in("Node", ".html", nodeClassName);
            template = itsVelocityEngine.getTemplate(templateFileName);
        }
        catch (Exception ex)
        {
            XpLogger.logException(ex);
        }
        return template;
    }

    public String addPageIdToAllUrlsOf(String anHtml)
    {
        String textToFind = itsServletPath;
        String textToReplace = itsServletPath + "/" + itsPageID;

        Pattern pattern = Pattern.compile("(.+/)(.+)");
        Matcher matcher = pattern.matcher(itsServletPath);
        if (matcher.find())
        {
            String pathUpToServletName = matcher.group(1);

            textToFind = "(" + pathUpToServletName + "\\w+)";
            textToReplace = "$1" + "/" + itsPageID;
        }

        return StringUtil.replace_with_in(textToFind, textToReplace, anHtml);
    }

}