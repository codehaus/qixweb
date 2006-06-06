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

    public static String formatDD_MMM_YYYY(QixwebCalendar aCalendar)
    {
        return formatDD_MMM_YYYY(aCalendar, defaultLocale());
    }

    public static String formatDD_MMM_YYYY(QixwebCalendar aCalendar, Locale aLocale)
    {
        return format(aCalendar, new SimpleDateFormat("dd-MMM-yyyy", aLocale));
    }

    public static String formatDDslashMMslashYYYY(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("dd/MM/yyyy"));
    }

    public static String formatDDslashMMslashYYYY_HH_mm(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("dd/MM/yyyy HH:mm"));
    }

    public static String formatDDslashMMslashYYYY_HH_mm_ss(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
    }

    public static String formatyyyyMMdd_HHmmss(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("yyyyMMdd-HHmmss"));
    }

    public static String formatyyyy_MM_dd_HH_mm_ss(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"));
    }

    public static String formatMMM_YY(QixwebCalendar aCalendar)
    {
        return formatMMM_YY(aCalendar, defaultLocale());
    }

    public static String formatMMM_YY(QixwebCalendar aCalendar, Locale aLocale)
    {
        return format(aCalendar, new SimpleDateFormat("MMM-yy", aLocale));
    }

    public static String formatyyyy_MM_dd(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static String formatyyyyMMdd(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("yyyyMMdd"));
    }

    public static String formatyyyyMM(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("yyyyMM"));
    }

    private static String format(QixwebCalendar aCalendar, SimpleDateFormat aDateFormat)
    {
        return aCalendar.format(aDateFormat);
    }

    public static Calendar parseMMM_YY(String aDateAsString, Locale aLocale) throws ParseException
    {
        Date date = new SimpleDateFormat("MMM-yy", aLocale).parse(aDateAsString);
        return toCalendar(date);
    }

    public static Calendar parseMMM_YY(String aDateAsString) throws ParseException
    {
        return parseMMM_YY(aDateAsString, defaultLocale());
    }

    public static Calendar parseDDslashMMslashYYYY(String aDateAsString) throws ParseException
    {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(aDateAsString);
        return toCalendar(date);
    }

    public static QixwebDate parseDDslashMMslashYYYYasQixwebDate(String aDateAsString)
    {
        try
        {
            return new QixwebDate(parseDDslashMMslashYYYY(aDateAsString));
        }
        catch (ParseException e)
        {
            return QixwebDate.NULL;
        }
    }

    public static Calendar parseyyyyMMdd_HHmmss(String aDateAsString) throws ParseException
    {
        Date date = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ITALY).parse(aDateAsString);
        return toCalendar(date);
    }

    public static QixwebCalendar parseDDslashMMslashYYYY_HH_MM_SSasQixwebTime(String aDateAsString)
    {
        try
        {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(aDateAsString);
            return new QixwebTime(toCalendar(date));
        }
        catch (ParseException e)
        {
            return QixwebCalendar.NULL;
        }
    }

    public static QixwebCalendar parseDDslashMMslashYYYY_HH_MMasQixwebTime(String aDateAsString)
    {
        try
        {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(aDateAsString);
            return new QixwebTime(toCalendar(date));
        }
        catch (ParseException e)
        {
            return QixwebCalendar.NULL;
        }
    }

    private static Calendar toCalendar(Date aDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        return calendar;
    }

    public static QixwebTime parseHH_colon_mm(String aTimeAsString)
    {
        try
        {
            Date date = new SimpleDateFormat("HH:mm").parse(aTimeAsString);
            return QixwebTime.timeOnly(date.getHours(), date.getMinutes(), 0);
        }
        catch (ParseException e)
        {
            return QixwebTime.NULL;
        }
    }

    public static String formatHH_colon_mm(QixwebCalendar aCalendar)
    {
        return format(aCalendar, new SimpleDateFormat("HH:mm"));
    }

}
