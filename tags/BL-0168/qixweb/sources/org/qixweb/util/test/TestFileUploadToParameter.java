package org.qixweb.util.test;

import org.qixweb.core.QixwebUrl;
import org.qixweb.core.test.AnyCommand;
import org.qixweb.core.test.FakeHttpServletRequest;
import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.FileUploadToParameter;

public class TestFileUploadToParameter extends ExtendedTestCase
{
    private static final String REQUEST_CONTENT = 
        "-----1234\r\n" +
        "Content-Disposition: form-data; name=\"file\"; filename=\"foo.jpg\"\r\n" +
        "Content-Type: text/whatever\r\n" +
        "\r\n" +
        "This is the content of the file\n" +
        "\r\n" +
        "-----1234\r\n" +
        "Content-Disposition: form-data; name=\"field\"\r\n" +
        "\r\n" +
        "fieldValue\r\n" +
        "-----1234--\r\n";

     public void testConvert() throws Exception
     {
         FakeHttpServletRequest request = new FakeHttpServletRequest();
         request.setContentType("multipart/form-data; boundary=---1234");
         request.simulatePost(REQUEST_CONTENT.getBytes());
         QixwebUrl url = new QixwebUrl(AnyCommand.class);
         
         FileUploadToParameter.convert(request, url);
         
         ArrayAsserter.assertEquals("This is the content of the file\n".getBytes(), url.parameters().getAsByteArray("file"));
         assertEquals("fieldValue", url.parameters().get("field"));
     }

}
