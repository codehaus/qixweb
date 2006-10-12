package org.qixweb.core.validation.test;

import java.util.HashMap;

import junit.framework.TestCase;

import org.qixweb.core.*;
import org.qixweb.core.test.AnyCommand;
import org.qixweb.core.validation.*;

public class TestWebCommandBuilder extends TestCase
{
    private class SampleWebCommandBuilder extends WebCommandBuilder
    {
        protected SampleWebCommandBuilder(Parameters submittedValues)
        {
            super(submittedValues);
        }

        public Browsable destinationWhenNotValid(QixwebEnvironment notUsed) 
        { 
            return QixwebUrl.EMPTY_URL; 
        }
        
        public void addControl(WebCommandRequestControl control, boolean mandatoryFlag, String parameterKey, String messageWhenInvalid)
        {
            super.addControl(control, mandatoryFlag, parameterKey, messageWhenInvalid);
        }

        public WebCommand destinationWhenValid(UserData notUsedUserData)
        {
            return new AnyCommand();
        }
    }

    private SampleWebCommandBuilder itsRequest;
    private Parameters itsSubmittedValues;

    protected void setUp() throws Exception
    {
        super.setUp();
        itsSubmittedValues = new Parameters();
        itsRequest = new SampleWebCommandBuilder(itsSubmittedValues);
    }
    
    public void testWithNothingToControl() throws Exception
    {
        assertTrue("With no constraints it should response OK", itsRequest.isValid());
        assertTrue(itsRequest.messagesForInvalidParameters().isEmpty());
    }

    public void testMandatoryControlWhenParameterIsNotPresent() throws Exception
    {
        HashMap expectedInvalidParameters = new HashMap();
        expectedInvalidParameters.put("notPresent", "The parameter must be present");

        itsRequest.addControl(new TextControl(itsSubmittedValues), WebCommandBuilder.MANDATORY, "notPresent", "The parameter must be present");
        
        assertFalse("Mandatory parameter should be NOT valid if not present", itsRequest.isValid());
        assertEquals(expectedInvalidParameters, itsRequest.messagesForInvalidParameters());
    }

    public void testOptionalControlWhenParameterIsNotPresent() throws Exception
    {
        itsRequest.addControl(new DoubleControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "empty", "Wrong");
        
        assertTrue("Optional controls should be valid if not present", itsRequest.isValid());
        assertTrue(itsRequest.messagesForInvalidParameters().isEmpty());
    }
    
    public void testOptionalControlWhenParameterIsEmpty() throws Exception
    {
        itsSubmittedValues.set("empty", "");
        
        itsRequest.addControl(new IntegerControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "empty", "Wrong");
        
        assertTrue("Optional controls may be empty", itsRequest.isValid());
        assertTrue(itsRequest.messagesForInvalidParameters().isEmpty());
    }
    
    public void testIgnoreOrderWhenChecking() throws Exception
    {
        itsSubmittedValues.set("invalidDate", "30-40--50");        
        itsSubmittedValues.set("validDate", "02/02/2006");
        itsRequest.addControl(new DateAsDDslashMMslashYYYYControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "validDate", "Wrong 1st date. Expected format is: DD/MM/YYYY");
        itsRequest.addControl(new DateAsDDslashMMslashYYYYControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "invalidDate", "Wrong 2nd date. Expected format is: DD/MM/YYYY");
        assertFalse(itsRequest.isValid());        
    }

    public void testWithMixedControls() throws Exception
    {
        itsSubmittedValues.set("emptyText", "");
        itsSubmittedValues.set("notEmptyText", "lordtom.blogspot.com");
        itsSubmittedValues.set("invalidTime", "12-30");
        itsSubmittedValues.set("validDate", "02/02/2006");
        itsSubmittedValues.set("invalidInteger", "not numeric");
        itsSubmittedValues.set("validDouble", "2.022006");
              
        itsRequest.addControl(new TextControl(itsSubmittedValues), WebCommandBuilder.MANDATORY, "emptyText", "1st text should not be empty");
        itsRequest.addControl(new TextControl(itsSubmittedValues), WebCommandBuilder.MANDATORY, "notEmptyText", "2nd text should not be empty");
        itsRequest.addControl(new TimeAsHHcolonMMControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "invalidTime", "Invalid time format.");
        itsRequest.addControl(new DateAsDDslashMMslashYYYYControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "validDate", "Invalid date format.");
        itsRequest.addControl(new IntegerControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "invalidInteger", "Wrong Integer format.");
        itsRequest.addControl(new DoubleControl(itsSubmittedValues), WebCommandBuilder.OPTIONAL, "validDouble", "Wrong Double format.");

        HashMap expectedInvalidParameters = new HashMap();
        expectedInvalidParameters.put("emptyText", "1st text should not be empty");
        expectedInvalidParameters.put("invalidTime", "Invalid time format.");
        expectedInvalidParameters.put("invalidInteger", "Wrong Integer format.");
        
        assertFalse(itsRequest.isValid());
        assertEquals(expectedInvalidParameters, itsRequest.messagesForInvalidParameters());
    }
}
