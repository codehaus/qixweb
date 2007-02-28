package org.qixweb.core;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.FieldMethodizer;

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

        context.put("nodeField", new FieldMethodizer(itsNode));

    }

}
