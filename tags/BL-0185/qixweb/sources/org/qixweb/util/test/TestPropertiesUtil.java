package org.qixweb.util.test;

import java.util.Properties;

import junit.framework.TestCase;

import org.qixweb.util.PropertiesUtil;

public class TestPropertiesUtil extends TestCase
{
    public void testCreation() throws Exception
    {
        Properties properties = new Properties();
        properties.put("key1", "value1");
        assertEquals(properties, PropertiesUtil.with("key1", "value1"));
        properties.put("key2", "value2");
        assertEquals(properties, PropertiesUtil.with("key1", "value1", "key2", "value2"));
        properties.put("key3", "value3");
        assertEquals(properties, PropertiesUtil.with("key1", "value1", "key2", "value2", "key3", "value3"));
    }
}
