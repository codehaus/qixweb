package org.qixweb.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateFormatter 
{
    private static Locale itsDefaultLocale;
    
    static
    {
        itsDefaultLocale = Locale.ITALY;
    }

    public static Locale defaultLocale()
    {
        return itsDefaultLocale;
    }
    
    public static void changeDefaultLocale(Locale aLocale)
    {
        itsDefaultLocale = aLocale;
    }
    
    public static String formatDD_MMM_YYYY(Calendar aCalendar)
    {
        return formatDD_MMM_YYYY(aCalendar, defaultLocale());
    }
    
	public static String formatDD_MMM_YYYY(Calendar aCalendar, Locale aLocale)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", aLocale);
		return dateFormat.format(aCalendar.getTime());
	}
	
	public static String formatDD_MM_YYYY(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(aCalendar.getTime());
	}

	public static String formatDD_MM_YYYY_HH_mm(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(aCalendar.getTime());
	}
	
	public static String formatDD_MM_YYYY_HH_mm_ss(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(aCalendar.getTime());
	}	

	public static String formatyyyyMMdd_HHmmss(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		return dateFormat.format(aCalendar.getTime());
	}

    public static String formatMMM_YY(Calendar aCalendar)
    {
        return formatMMM_YY(aCalendar, defaultLocale());
    }
    
	public static String formatMMM_YY(Calendar aCalendar, Locale aLocale)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yy", aLocale);
		return dateFormat.format(aCalendar.getTime());
	}

	public static Calendar parseMMM_YY(String aDateAsString, Locale aLocale) throws ParseException
	{
		Date date = new SimpleDateFormat("MMM-yy", aLocale).parse(aDateAsString);

		return convertToCalendar(date);
	}
    
    public static Calendar parseMMM_YY(String aDateAsString) throws ParseException
    {
        return  parseMMM_YY(aDateAsString, defaultLocale());
    }

	private static Calendar convertToCalendar(Date aDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		return calendar;
	}
	
	public static Calendar parseDD_MM_YYYY(String aDateAsString) throws ParseException
	{
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(aDateAsString);
		return convertToCalendar(date);
	}

	public static Calendar parseyyyyMMdd_HHmmss(String aDateAsString) throws ParseException
	{
		Date date = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ITALY).parse(aDateAsString);
		return convertToCalendar(date);
	}

	public static String formatyyyyMMdd(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(aCalendar.getTime());
	}

	public static String formatyyyyMM(Calendar aCalendar)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		return dateFormat.format(aCalendar.getTime());
	}
}
