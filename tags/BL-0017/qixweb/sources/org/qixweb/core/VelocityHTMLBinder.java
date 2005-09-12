package org.qixweb.core;

import org.apache.velocity.VelocityContext;

public class VelocityHTMLBinder
{
	private WebNode itsNode;

	public VelocityHTMLBinder(WebNode aNode)
	{
		itsNode = aNode;
	}

	public void bindTo(VelocityContext context)
	{		
		context.put("node", itsNode);
	}
	

}
