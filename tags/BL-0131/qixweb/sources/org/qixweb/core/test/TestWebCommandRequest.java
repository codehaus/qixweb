package org.qixweb.core.test;

import java.util.*;

import junit.framework.TestCase;

import org.qixweb.core.*;

public class TestWebCommandRequest extends TestCase
{
    private class SampleWebCommandRequest extends WebCommandRequest
    {
        protected SampleWebCommandRequest(Parameters submittedValues)
        {
            super(submittedValues);
        }

        public Browsable destinationWhenNotValid(QixwebEnvironment environment) 
        { 
            return QixwebUrl.EMPTY_URL; 
        }
        
        public void addMandatoryControlText(String parameterKey, String messageWhenInvalid)
        {
            super.addMandatoryControlText(parameterKey, messageWhenInvalid);
        }

        public void addOptionalControlDateAsDDslashMMslashYYYY(String parameterKey, String messageWhenInvalid)
        {
            super.addOptionalControlDateAsDDslashMMslashYYYY(parameterKey, messageWhenInvalid);
        }
        
        public void addOptionalControlTimeAsTimeHH_colon_mm(String parameterKey, String messageWhenInvalid)
        {
            super.addOptionalControlTimeAsTimeHH_colon_mm(parameterKey, messageWhenInvalid);
        }
    }

    private SampleWebCommandRequest webCommandRequest;
    private Parameters parameters;

    protected void setUp() throws Exception
    {
        super.setUp();
        parameters = new Parameters();
        webCommandRequest = new SampleWebCommandRequest(parameters);
    }
    
    public void testWithNothingAppended() throws Exception
    {
        assertTrue("With no constraints it should response OK", webCommandRequest.isValid());
    }

    public void testMandatoryControlWhenParameterIsNotPresent() throws Exception
    {
        HashMap expectedInvalidParameters = new HashMap();
        expectedInvalidParameters.put("notPresent", "The parameter must be present");

        webCommandRequest.addMandatoryControlText("notPresent", "The parameter must be present");
        
        assertFalse("Mandatory parameter should be NOT valid if not present", webCommandRequest.isValid());
        assertEquals(expectedInvalidParameters, webCommandRequest.invalidParameters());
    }

    public void testOptionalControlWhenParameterIsNotPresent() throws Exception
    {
        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("emptyDate", "Wrong date. Expected format is: DD/MM/YYYY");
        
        assertTrue("Optional date should be valid if not present", webCommandRequest.isValid());
        assertEquals(new HashMap(), webCommandRequest.invalidParameters());
    }
    
    public void testOptionalControlWhenParameterIsEmpty() throws Exception
    {
        parameters.set("emptyDate", "");
        
        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("emptyDate", "Wrong date. Expected format is: DD/MM/YYYY");
        
        assertTrue("Optional date can be empty", webCommandRequest.isValid());
        assertEquals(new HashMap(), webCommandRequest.invalidParameters());
    }
    
    public void testControlText() throws Exception
    {
        parameters.set("anyText", "On text parameter no controls are made");
        
        webCommandRequest.addMandatoryControlText("anyText", "text should not be empty");

        assertTrue(webCommandRequest.isValid());        
        assertEquals(new HashMap(), webCommandRequest.invalidParameters());
    }

    public void testControlDateWhenValid() throws Exception
    {
        parameters.set("validDate", "02/02/2006");
        
        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("validDate", "Wrong date. Expected format is: DD/MM/YYYY");
        
        assertTrue(webCommandRequest.isValid());
        assertEquals(new HashMap(), webCommandRequest.invalidParameters());        
    }
    
    public void testControlDateWhenNotValid() throws Exception
    {
        parameters.set("invalidDate", "30-40--50");
        HashMap expectedInvalidParameters = new HashMap();
        expectedInvalidParameters.put("invalidDate", "Wrong date. Expected format is: DD/MM/YYYY");

        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("invalidDate", "Wrong date. Expected format is: DD/MM/YYYY");
        
        assertFalse(webCommandRequest.isValid());
        assertEquals(expectedInvalidParameters, webCommandRequest.invalidParameters());
    }

    public void testControlTimeWhenValid() throws Exception
    {
        parameters.set("validTime", "12:30");
        
        webCommandRequest.addOptionalControlTimeAsTimeHH_colon_mm("validTime", "Wrong Time. Expected format is: HH:MM");
        
        assertTrue(webCommandRequest.isValid());
        assertEquals(new HashMap(), webCommandRequest.invalidParameters());        
    }
    
    public void testControlTimeWhenNotValid() throws Exception
    {
        parameters.set("invalidTime", "12-10");
        HashMap expectedInvalidParameters = new HashMap();
        expectedInvalidParameters.put("invalidTime", "Wrong Time. Expected format is: HH:MM");

        webCommandRequest.addOptionalControlTimeAsTimeHH_colon_mm("invalidTime", "Wrong Time. Expected format is: HH:MM");
        
        assertFalse(webCommandRequest.isValid());
        assertEquals(expectedInvalidParameters, webCommandRequest.invalidParameters());
    }

    public void testIgnoreOrderWhenChecking() throws Exception
    {
        parameters.set("invalidDate", "30-40--50");        
        parameters.set("validDate", "02/02/2006");
        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("validDate", "Wrong 1st date. Expected format is: DD/MM/YYYY");
        webCommandRequest.addOptionalControlDateAsDDslashMMslashYYYY("invalidDate", "Wrong 2nd date. Expected format is: DD/MM/YYYY");
        assertFalse(webCommandRequest.isValid());        
    }

//    public void testInvalidParametersList() throws Exception
//    {
//        Parameters parameters = new Parameters();
//        parameters.set("emptyText", "");
//        parameters.set("notEmptyText", "lordtom.blogspot.com");
//        parameters.set("invalidTime", "12-30");
//        parameters.set("validDate", "02/02/2006");
//        
//        List expected = CollectionUtil.listWith("emptyText", "invalidTime");
//        ParameterValidator validator = new ParameterValidator(parameters);
//        validator.appendNotEmptyText("emptyText").appendNotEmptyText("notEmptyText").
//        appendTimeAsTimeHH_colon_mm("invalidTime").appendDateAsDDslashMMslashYYYY("validDate");
//        assertFalse(validator.areAllRight());
//        assertEquals(expected, validator.invalidParameters());
//    }
}
