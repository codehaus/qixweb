package org.qixweb.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{
    public static final String EMPTY = "";

    public static int nthIndexOf(String aString, int occurrenceNumber, char aCharToFind)
    {
        int counter = 1;

        char[] str = aString.toCharArray();
        for (int i = 0; i < str.length; i++)
        {
            if (str[i] == aCharToFind)
            {
                if (counter == occurrenceNumber)
                    return i;
                counter++;
            }
        }

        return -1;
    }

    public static boolean string_containsRegex(String text, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static String replace_with_in(String aStringToReplace, String aNewString, String aString)
    {
        Pattern pattern = Pattern.compile(aStringToReplace);
        Matcher matcher = pattern.matcher(aString);
        return matcher.replaceAll(aNewString);
    }

    public static String join(String[] strings, String separator)
    {
        if (strings.length == 0)
        {
            return "";
        }

        StringBuffer list = new StringBuffer(strings[0]);
        for (int i = 1; i < strings.length; i++)
            list.append(separator + strings[i]);

        return list.toString();
    }

    public static String join(Collection strings, String separator)
    {
        return join((String[]) strings.toArray(new String[0]), separator);
    }

    public static String compactWhitespaces(String aString)
    {
        return StringUtils.join(StringUtils.split(aString), " ");
    }

    public static String extractFirst(String text, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find())
            return matcher.group();
        else
            return null;
    }

    public static boolean containsIgnoringCase(String container, String contained)
    {
        return StringUtils.contains(container.toLowerCase(), contained.toLowerCase());
    }

    public static String onlyFirst1024Chars(String string)
    {
        if (string != null && string.length() > 1024)
            return string.substring(0, 1023);
        else
            return string;
    }

}
