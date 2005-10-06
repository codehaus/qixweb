package org.qixweb.core.test;

import java.io.IOException;

import org.qixweb.core.*;


public class FakeResponseHandler implements ResponseHandler
{
	private boolean throwExceptionDisplayingNode;
	private String itsFailureNodeInTemplateMerging;
	protected QixwebUrl itsDestinationUrl;
	private WebNode itsNodeToDisplay;
    private Browsable itsLastBrowsable;

	public FakeResponseHandler()
	{
		itsFailureNodeInTemplateMerging = "";
		throwExceptionDisplayingNode = false;
	}

	public void display(WebNode aDestinationNode) throws IOException
	{
		if (itsFailureNodeInTemplateMerging.equals(aDestinationNode.getClass().getName()))
			throw new RuntimeException("Simulated FailureInTemplateMerging");
		else if (throwExceptionDisplayingNode)
		{
			throwExceptionDisplayingNode = false;
			throw new IOException("Simulated IOException");
		}
		
		itsLastBrowsable = itsNodeToDisplay = aDestinationNode;
	}
	public void simulateExceptionSendingHTMLOnlyOnce()
	{
		throwExceptionDisplayingNode = true;
	}

	public void redirectTo(QixwebUrl aDestinationUrl) throws IOException
	{
        itsLastBrowsable = itsDestinationUrl = aDestinationUrl;
	}

	public QixwebUrl redirectedDestination()
	{
		return itsDestinationUrl;
	}

	public WebNode displayedNode()
	{
		return itsNodeToDisplay;
	}

	public void sendHtml(String aHtmlToSend)
	{
		throw new IllegalAccessError("Not implemented");
	}

	public void simulateFailureInTemplateMergingFor(Class anXpTrackingNode)
	{
		itsFailureNodeInTemplateMerging = anXpTrackingNode.getName();
	}

    public Browsable lastBrowsed()
    {
        return itsLastBrowsable;
    }

}
