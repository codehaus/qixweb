package org.qixweb.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.qixweb.core.WebUrl;

public class EscapedWebUrl extends WebUrl
{
    public EscapedWebUrl(WebUrl aWebUrl)
    {
        super(aWebUrl.destination(), aWebUrl.label());
    }

    public String label()
    {
        return StringEscapeUtils.escapeHtml(super.label());
    }
}
