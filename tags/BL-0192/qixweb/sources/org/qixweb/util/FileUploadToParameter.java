package org.qixweb.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.qixweb.core.QixwebUrl;

public class FileUploadToParameter
{
    public static void convert(HttpServletRequest request, QixwebUrl url)
    {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);
        try
        {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext())
            {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField())
                    url.parameters().set(item.getFieldName(), item.getString());
                else
                {
                    url.parameters().set(item.getFieldName(), item.get());
                    item.delete();
                }
            }
        }
        catch (FileUploadException e)
        {
            XpLogger.logException(e);
        }
    }

}
