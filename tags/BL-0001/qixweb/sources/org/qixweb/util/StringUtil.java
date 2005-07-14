package org.qixweb.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{	
	public static boolean string_contains(String aString, String aSubstring)
	{
		return aString.indexOf(aSubstring) != -1;
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
		if( strings.length == 0 ) {
			return "";
		}

		StringBuffer list = new StringBuffer(strings[0]);
		for (int i = 1; i < strings.length; i++)
			list.append(separator + strings[i]);			
		
		return list.toString();
	}
    
    public static String join(Collection strings, String separator)
    {
        return join((String[])strings.toArray(new String[0]), separator);
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
	
}