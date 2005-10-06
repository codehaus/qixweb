package org.qixweb.core.test;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.qixweb.core.DefaultWebForm;
import org.qixweb.core.WebAppUrl;

import junit.framework.TestCase;

public class TestDefaultWebForm extends TestCase
{

    public static class MyDefaultWebForm extends DefaultWebForm
    {
        public String field1 = "field1";
        public String field2 = "field2";
        
        protected WebAppUrl concreteActionUrl()
        {
            return null;
        }
    }

    private MyDefaultWebForm itsForm;
    
    protected void setUp() throws Exception
    {
        itsForm = new MyDefaultWebForm();
    }

    public void testEquals()
    {
        assertEquals(itsForm, new MyDefaultWebForm());
    }
    
    public void testToString()
    {
        assertEquals(ToStringBuilder.reflectionToString(itsForm), itsForm.toString());
    }
}
