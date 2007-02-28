package org.qixweb.core;

import java.io.IOException;

public interface ResponseHandler
{
    void redirectTo(QixwebUrl aDestinationUrl) throws IOException;

    void display(WebNode aDestinationNode) throws IOException;

}
