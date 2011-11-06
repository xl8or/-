// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.util;

import java.text.*;
import java.util.Date;
import java.util.TimeZone;

public class StdDateFormat extends DateFormat
{

    public StdDateFormat()
    {
    }

    public static DateFormat getBlueprintISO8601Format()
    {
        return DATE_FORMAT_ISO8601;
    }

    public static DateFormat getBlueprintRFC1123Format()
    {
        return DATE_FORMAT_RFC1123;
    }

    public static DateFormat getISO8601Format(TimeZone timezone)
    {
        SimpleDateFormat simpledateformat = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
        simpledateformat.setTimeZone(timezone);
        return simpledateformat;
    }

    public static DateFormat getRFC1123Format(TimeZone timezone)
    {
        SimpleDateFormat simpledateformat = (SimpleDateFormat)DATE_FORMAT_RFC1123.clone();
        simpledateformat.setTimeZone(timezone);
        return simpledateformat;
    }

    public volatile Object clone()
    {
        return clone();
    }

    public StdDateFormat clone()
    {
        return new StdDateFormat();
    }

    public StringBuffer format(Date date, StringBuffer stringbuffer, FieldPosition fieldposition)
    {
        if(_formatISO8601 == null)
            _formatISO8601 = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
        return _formatISO8601.format(date, stringbuffer, fieldposition);
    }

    protected boolean looksLikeISO8601(String s)
    {
        boolean flag;
        if(s.length() >= 5 && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(3)) && s.charAt(4) == '-')
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Date parse(String s)
        throws ParseException
    {
        String s1 = s.trim();
        ParsePosition parseposition = new ParsePosition(0);
        Date date = parse(s1, parseposition);
        if(date != null)
            return date;
        StringBuilder stringbuilder = new StringBuilder();
        String as[] = ALL_FORMATS;
        int i = as.length;
        int j = 0;
        while(j < i) 
        {
            String s2 = as[j];
            if(stringbuilder.length() > 0)
                stringbuilder.append("\", \"");
            else
                stringbuilder.append('"');
            stringbuilder.append(s2);
            j++;
        }
        stringbuilder.append('"');
        Object aobj[] = new Object[2];
        aobj[0] = s1;
        aobj[1] = stringbuilder.toString();
        throw new ParseException(String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", aobj), parseposition.getErrorIndex());
    }

    public Date parse(String s, ParsePosition parseposition)
    {
        Date date;
        if(looksLikeISO8601(s))
            date = parseAsISO8601(s, parseposition);
        else
            date = parseAsRFC1123(s, parseposition);
        return date;
    }

    protected Date parseAsISO8601(String s, ParsePosition parseposition)
    {
label0:
        {
            {
                int i = s.length();
                if(s.charAt(i - 1) != 'Z')
                    break label0;
                SimpleDateFormat simpledateformat = _formatISO8601_z;
                char c;
                StringBuilder stringbuilder;
                String s1;
                if(simpledateformat == null)
                {
                    simpledateformat = (SimpleDateFormat)DATE_FORMAT_ISO8601_Z.clone();
                    _formatISO8601_z = simpledateformat;
                    s1 = s;
                } else
                {
                    s1 = s;
                }
            }
            return simpledateformat.parse(s1, parseposition);
        }
        c = s.charAt(i - 3);
        if(c == ':')
        {
            stringbuilder = new StringBuilder(s);
            stringbuilder.delete(i - 3, i - 2);
            s1 = stringbuilder.toString();
        } else
        if(c == '+' || c == '-')
            s1 = (new StringBuilder()).append(s).append("00").toString();
        else
            s1 = s;
        simpledateformat = _formatISO8601;
        if(_formatISO8601 == null)
        {
            simpledateformat = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
            _formatISO8601 = simpledateformat;
        }
        if(false)
            ;
        else
            break MISSING_BLOCK_LABEL_48;
    }

    protected Date parseAsRFC1123(String s, ParsePosition parseposition)
    {
        if(_formatRFC1123 == null)
            _formatRFC1123 = (SimpleDateFormat)DATE_FORMAT_RFC1123.clone();
        return _formatRFC1123.parse(s, parseposition);
    }

    static final String ALL_FORMATS[];
    static final SimpleDateFormat DATE_FORMAT_ISO8601;
    static final SimpleDateFormat DATE_FORMAT_ISO8601_Z;
    static final SimpleDateFormat DATE_FORMAT_RFC1123;
    static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final StdDateFormat instance = new StdDateFormat();
    transient SimpleDateFormat _formatISO8601;
    transient SimpleDateFormat _formatISO8601_z;
    transient SimpleDateFormat _formatRFC1123;

    static 
    {
        String as[] = new String[3];
        as[0] = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        as[1] = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        as[2] = "EEE, dd MMM yyyy HH:mm:ss zzz";
        ALL_FORMATS = as;
        TimeZone timezone = TimeZone.getTimeZone("GMT");
        DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        DATE_FORMAT_RFC1123.setTimeZone(timezone);
        DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DATE_FORMAT_ISO8601.setTimeZone(timezone);
        DATE_FORMAT_ISO8601_Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DATE_FORMAT_ISO8601_Z.setTimeZone(timezone);
    }
}
