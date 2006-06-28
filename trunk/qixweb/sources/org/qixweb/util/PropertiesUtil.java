package org.qixweb.util;

import java.util.Properties;

public class PropertiesUtil
{
    public static Properties with(String key1, String value1)
    {
        Properties properties = new Properties();
        properties.setProperty(key1, value1);
        return properties;
    }

    public static Properties with(String key1, String value1, String key2, String value2)
    {
        Properties base = with(key1, value1);
        base.setProperty(key2, value2);
        return base;
    }

    public static Properties with(String key1, String value1, String key2, String value2, String key3, String value3)
    {
        Properties base = with(key1, value1, key2, value2);
        base.setProperty(key3, value3);
        return base;
    }
}
