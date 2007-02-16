package org.qixweb.sample.node;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.core.validation.WebNodeBuilder;

public class HelloWithoutCreateNodeBuilder extends WebNodeBuilder
{
    public HelloWithoutCreateNodeBuilder(QixwebEnvironment qixwebEnvironment)
    {
        super(qixwebEnvironment);
    }

    public WebNode createFrom(QixwebUrl urlToNode)
    {
        String guestName = urlToNode.parameters().get(HelloWithoutCreateNode.parameterNameForGuestName());
        return new HelloWithoutCreateNode(qixwebEnvironment(), StringUtils.isEmpty(guestName) ? "unknown" : guestName);
    }

}
