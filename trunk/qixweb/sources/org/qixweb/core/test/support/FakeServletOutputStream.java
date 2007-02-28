package org.qixweb.core.test.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FakeServletOutputStream extends javax.servlet.ServletOutputStream
{
    private ByteArrayOutputStream itsByteArrayStream;
    private boolean hasBeenClosed;

    public FakeServletOutputStream()
    {
        itsByteArrayStream = new ByteArrayOutputStream();
        hasBeenClosed = false;
    }

    public void write(int aByte) throws IOException
    {
        itsByteArrayStream.write(aByte);
    }

    public byte[] outputAsBytes()
    {
        return itsByteArrayStream.toByteArray();
    }

    public void close() throws IOException
    {
        hasBeenClosed = true;
    }

    public boolean hasBeenClosed()
    {
        return hasBeenClosed;
    }
}