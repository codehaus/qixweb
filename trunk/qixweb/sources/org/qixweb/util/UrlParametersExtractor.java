package org.qixweb.util;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.block.Procedure;
import org.qixweb.core.WebUrl;

public class UrlParametersExtractor
{
    public static final String AMPERSAND = "&";
    public static final String QUESTION_MARK = "?";
    public static final String EQUAL = "=";
    private String itsUrl;

    public UrlParametersExtractor(String url)
    {
        itsUrl = url;
    }
    public Map run()
    {
    	HashMap parameters = new HashMap();
        
    	if (urlHasSomeParameters())
    	{
    		String[] allKeyValues = extractKeyValuepairsFromUrl();
    
    		for (int i = 0; i < allKeyValues.length; i++)
    			put_into(allKeyValues[i], parameters);
            transformArrayListsBackToStringArrays(parameters);
    	}
    	return parameters;
    }
    private String[] extractKeyValuepairsFromUrl()
    {
        String fromQuestionMark = itsUrl.split("\\"+QUESTION_MARK)[1];
        return fromQuestionMark.split(AMPERSAND);
    }
    private boolean urlHasSomeParameters()
    {
        return StringUtils.contains(itsUrl, QUESTION_MARK) && !itsUrl.endsWith(QUESTION_MARK);
    }
    private void transformArrayListsBackToStringArrays(final Map aParameters)
    {
        LightInternalIterator.createOn(aParameters.keySet()).forEach(new Procedure()
        {
            public void run(Object eachKey)
            {
                ArrayList list = (ArrayList)aParameters.get(eachKey);
                aParameters.put(eachKey, CollectionUtil.toArray(list, String.class));
            }
        });
    }
    private void put_into(String aKeyValuePair, Map aParametersMap)
    {
    	String[] keyValue = aKeyValuePair.split(EQUAL);
    	if (keyValue.length == 2)
        {
            String key = keyValue[0];
            String value = WebUrl.decode(keyValue[1]);
            createOrFeedListAt_with(aParametersMap, key, value);
        }
        else if (keyValue.length == 1)
        {
            String key = keyValue[0];
            String value = "";
            createOrFeedListAt_with(aParametersMap, key, value);
        }
    }
    private void createOrFeedListAt_with(Map aParametersMap, String key, String value)
    {
        if (aParametersMap.containsKey(key))
            ((ArrayList)aParametersMap.get(key)).add(value);
        else
        {
            ArrayList list = new ArrayList();
            list.add(value);
            aParametersMap.put(key, list);
        }
    }
}
