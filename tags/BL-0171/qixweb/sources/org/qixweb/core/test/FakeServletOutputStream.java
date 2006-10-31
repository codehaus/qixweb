package org.qixweb.core.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FakeServletOutputStream extends javax.servlet.ServletOutputStream
{
	private ByteArrayOutputStream itsByteArrayStream;

    public FakeServletOutputStream()
	{
		itsByteArrayStream = new ByteArrayOutputStream();
	}
	public void write(int aByte) throws IOException
	{
		itsByteArrayStream.write(aByte);
	}

	public byte[] outputAsBytes()
	{
		return itsByteArrayStream.toByteArray();
	}
}