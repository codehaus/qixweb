package org.qixweb.util.test;

import java.util.Map;

import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.UrlParametersExtractor;

public class TestUrlParametersExtractor extends ExtendedTestCase
{
    public void testUrlWithoutParametersGetsAnEmptyMap()
    {
        assertTrue(new UrlParametersExtractor("www.aaa.sss").run().isEmpty());
    }
    public void testInvalidUrlGetsAnEmptyMap()
    {
        assertTrue(new UrlParametersExtractor("").run().isEmpty());
        assertTrue(new UrlParametersExtractor(null).run().isEmpty());
        assertTrue(new UrlParametersExtractor("   ").run().isEmpty());
        assertTrue(new UrlParametersExtractor("a b c ").run().isEmpty());
        assertTrue(new UrlParametersExtractor("?").run().isEmpty());
    }
    public void testUrlWithOneParameterGetsAMapWithOneElement()
    {
        Map map = new UrlParametersExtractor("?key=value").run();
        ArrayAsserter.assertEquals(new String[] {"value"}, (String[])map.get("key"));
        assertEquals(1, map.keySet().size());
    }
    public void testUrlWithTwoParametersGetsAMapWithTwoElements()
    {
        Map map = new UrlParametersExtractor("?key1=value1&key2=value2").run();
        ArrayAsserter.assertEquals(new String[] {"value1"}, (String[])map.get("key1"));
        ArrayAsserter.assertEquals(new String[] {"value2"}, (String[])map.get("key2"));
        assertEquals(2, map.keySet().size());
    }
    public void testForgettingAmpersandBetweenTwoParametersGetsAnEmptyMap()
    {
        assertTrue(new UrlParametersExtractor("?key1=value1+key2=value2").run().isEmpty());
    }
    public void testUrlWithOneParameterAndSeveralValuesGetsAMapWithOneElement()
    {
        Map map = new UrlParametersExtractor("?key=value1&key=value2&key=value3").run();
        ArrayAsserter.assertEquals(new String[] {"value1","value2","value3"}, (String[])map.get("key"));
        assertEquals(1, map.keySet().size());
    }
}
