package org.qixweb.core;

import org.qixweb.time.RealTimeProvider;
import org.qixweb.time.TimeProvider;

public class SessionID
{
	private static final String SEPARATOR = "_";

	private String itsCurrentPageSessionID;
	private String itsCurrentPageID;
	private String itsNextPageID;
	private String itsNextPageSessionID;
	private String itsUserSessionID;
    private TimeProvider itsTimeProvider;

	public SessionID(String aUserSessionID, String anExtraPathInfo)
	{
	    this(aUserSessionID, anExtraPathInfo, new RealTimeProvider());
	}
	
	public SessionID(String aUserSessionID, String anExtraPathInfo, TimeProvider aTimeProvider)
	{
		itsTimeProvider = aTimeProvider;
	    itsUserSessionID = aUserSessionID;

		itsCurrentPageID = extractPageIDFrom(anExtraPathInfo);
		itsCurrentPageSessionID = createPageSessionIDFrom(itsUserSessionID, itsCurrentPageID);

		itsNextPageID = createNextPageID();
		itsNextPageSessionID = createPageSessionIDFrom(itsUserSessionID, itsNextPageID);
	}	

	private String extractPageIDFrom(String anExtraPathInfo)
	{
		String pageID = null;
		if (anExtraPathInfo != null)
			pageID = anExtraPathInfo.substring("/".length());

		return pageID;
	}

	private String createNextPageID()
	{
		return String.valueOf(itsTimeProvider.now().getTimeInMillis());
	}

	public String userSessionID()
	{
		return itsUserSessionID;
	}
	
	public String currentPageSessionID()
	{
		return itsCurrentPageSessionID;
	}

	public String currentPageID()
	{
		return itsCurrentPageID;
	}

	public String nextPageSessionID()
	{
		return itsNextPageSessionID;
	}

	public String nextPageID()
	{
		return itsNextPageID;
	}

	public String toString()
	{
		return "currentPageSessionID: " + currentPageSessionID() + " - nextPageSessionID: " + nextPageSessionID();
	}

	private String createPageSessionIDFrom(String aUserSessionID, String aPageID)
	{
	    return aUserSessionID + SEPARATOR + aPageID;
	}

	public static String extractUserSessionIDFrom(String aPageSessionID)
	{
		return aPageSessionID.substring(0, aPageSessionID.indexOf(SEPARATOR));
	}
}
