// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringUtils.java

package com.facebook.katana.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.format.DateUtils;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package com.facebook.katana.util:
//            Utils, Entities

public class StringUtils
{
    public static interface StringProcessor
    {

        public abstract String formatString(Object obj);
    }

    public static class JMNulledStrippedString extends JMString
    {

        public String formatString(String s)
        {
            String s1 = StringUtils.stripHTMLTags(s);
            String s2;
            if(s1.length() == 0)
                s2 = null;
            else
                s2 = s1;
            return s2;
        }

        public JMNulledStrippedString()
        {
        }
    }

    public static class JMNulledString extends JMString
    {

        public String formatString(String s)
        {
            String s1;
            if(s.length() == 0)
                s1 = null;
            else
                s1 = s;
            return s1;
        }

        public JMNulledString()
        {
        }
    }

    public static class JMStrippedString extends JMString
    {

        public String formatString(String s)
        {
            return StringUtils.stripHTMLTags(s);
        }

        public JMStrippedString()
        {
        }
    }

    public static final class TimeFormatStyle extends Enum
    {

        public static TimeFormatStyle valueOf(String s)
        {
            return (TimeFormatStyle)Enum.valueOf(com/facebook/katana/util/StringUtils$TimeFormatStyle, s);
        }

        public static TimeFormatStyle[] values()
        {
            return (TimeFormatStyle[])$VALUES.clone();
        }

        private static final TimeFormatStyle $VALUES[];
        public static final TimeFormatStyle MAILBOX_RELATIVE_STYLE;
        public static final TimeFormatStyle MONTH_DAY_YEAR_STYLE;
        public static final TimeFormatStyle STREAM_RELATIVE_STYLE;
        public static final TimeFormatStyle WEEK_DAY_STYLE;

        static 
        {
            WEEK_DAY_STYLE = new TimeFormatStyle("WEEK_DAY_STYLE", 0);
            STREAM_RELATIVE_STYLE = new TimeFormatStyle("STREAM_RELATIVE_STYLE", 1);
            MAILBOX_RELATIVE_STYLE = new TimeFormatStyle("MAILBOX_RELATIVE_STYLE", 2);
            MONTH_DAY_YEAR_STYLE = new TimeFormatStyle("MONTH_DAY_YEAR_STYLE", 3);
            TimeFormatStyle atimeformatstyle[] = new TimeFormatStyle[4];
            atimeformatstyle[0] = WEEK_DAY_STYLE;
            atimeformatstyle[1] = STREAM_RELATIVE_STYLE;
            atimeformatstyle[2] = MAILBOX_RELATIVE_STYLE;
            atimeformatstyle[3] = MONTH_DAY_YEAR_STYLE;
            $VALUES = atimeformatstyle;
        }

        private TimeFormatStyle(String s, int i)
        {
            super(s, i);
        }
    }


    public StringUtils()
    {
    }

    public static StringBuilder appendEscapedFQLString(StringBuilder stringbuilder, String s)
    {
        stringbuilder.append('\'');
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c == '\'' || c == '\\')
                stringbuilder.append('\\');
            stringbuilder.append(c);
        }

        stringbuilder.append('\'');
        return stringbuilder;
    }

    public static String formatDistance(Context context, float f)
    {
        int ai[] = new int[3];
        ai[0] = 0x7f0a024b;
        ai[1] = 0x7f0a025f;
        ai[2] = 0x7f0a0251;
        int ai1[] = new int[3];
        ai1[0] = 0x7f0a024a;
        ai1[1] = 0x7f0a025e;
        ai1[2] = 0x7f0a0252;
        Locale locale = context.getResources().getConfiguration().locale;
        float f1;
        int ai2[];
        String s;
        if(locale.equals(Locale.UK) || locale.equals(Locale.US))
        {
            f1 = f / 1609.344F;
            ai2 = ai;
        } else
        {
            f1 = f / 1000F;
            ai2 = ai1;
        }
        if((double)f1 < 0.80000000000000004D)
            s = context.getString(ai2[0]);
        else
        if((double)f1 < 1.2D)
        {
            s = context.getString(ai2[1]);
        } else
        {
            int i = ai2[2];
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf((int)f1);
            s = context.getString(i, aobj);
        }
        return s;
    }

    public static String formatLocation(Map map)
    {
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        if(map.containsKey("street"))
            s = ((Serializable)map.get("street")).toString();
        else
            s = null;
        if(map.containsKey("city"))
            s1 = ((Serializable)map.get("city")).toString();
        else
            s1 = null;
        if(map.containsKey("state"))
            s2 = ((Serializable)map.get("state")).toString();
        else
            s2 = null;
        if(map.containsKey("zip"))
            s3 = ((Serializable)map.get("zip")).toString();
        else
            s3 = null;
        if(!isBlank(s) && !isBlank(s1) && !isBlank(s2) && !isBlank(s3))
        {
            Object aobj6[] = new Object[4];
            aobj6[0] = s;
            aobj6[1] = s1;
            aobj6[2] = s2;
            aobj6[3] = s3;
            s4 = String.format("%s\n%s %s %s", aobj6);
        } else
        if(!isBlank(s) && !isBlank(s1) && !isBlank(s2))
        {
            Object aobj5[] = new Object[3];
            aobj5[0] = s;
            aobj5[1] = s1;
            aobj5[2] = s2;
            s4 = String.format("%s\n%s %s", aobj5);
        } else
        if(!isBlank(s) && !isBlank(s1) && !isBlank(s3))
        {
            Object aobj4[] = new Object[3];
            aobj4[0] = s;
            aobj4[1] = s1;
            aobj4[2] = s3;
            s4 = String.format("%s\n%s %s", aobj4);
        } else
        if(!isBlank(s) && !isBlank(s1))
        {
            Object aobj3[] = new Object[2];
            aobj3[0] = s;
            aobj3[1] = s1;
            s4 = String.format("%s\n%s", aobj3);
        } else
        if(!isBlank(s) && !isBlank(s3))
        {
            Object aobj2[] = new Object[2];
            aobj2[0] = s;
            aobj2[1] = s3;
            s4 = String.format("%s\n%s", aobj2);
        } else
        if(!isBlank(s1) && !isBlank(s2))
        {
            Object aobj1[] = new Object[2];
            aobj1[0] = s1;
            aobj1[1] = s2;
            s4 = String.format("%s, %s", aobj1);
        } else
        if(!isBlank(s1) && !isBlank(s3))
        {
            Object aobj[] = new Object[2];
            aobj[0] = s1;
            aobj[1] = s3;
            s4 = String.format("%s %s", aobj);
        } else
        if(!isBlank(s))
            s4 = s;
        else
        if(!isBlank(s1))
            s4 = s1;
        else
        if(!isBlank(s3))
            s4 = s3;
        else
            s4 = "";
        return s4;
    }

    public static String getErrorString(Context context, String s, int i, String s1, Exception exception)
    {
        String s2;
        if(i == 0)
        {
            if(exception != null)
            {
                if(exception instanceof FacebookApiException)
                {
                    FacebookApiException facebookapiexception = (FacebookApiException)exception;
                    Object aobj2[] = new Object[3];
                    aobj2[0] = s;
                    aobj2[1] = Integer.valueOf(facebookapiexception.getErrorCode());
                    aobj2[2] = facebookapiexception.getErrorMsg();
                    s2 = context.getString(0x7f0a004f, aobj2);
                } else
                {
                    Object aobj1[] = new Object[2];
                    aobj1[0] = s;
                    aobj1[1] = exception.getMessage();
                    s2 = context.getString(0x7f0a0050, aobj1);
                }
            } else
            {
                s2 = s;
            }
        } else
        {
            Object aobj[] = new Object[3];
            aobj[0] = s;
            aobj[1] = Integer.valueOf(i);
            aobj[2] = s1;
            s2 = context.getString(0x7f0a004f, aobj);
        }
        return s2;
    }

    public static String getTimeAsString(Context context, TimeFormatStyle timeformatstyle, long l)
    {
        Date date = new Date(l);
        String s;
        if(timeformatstyle == TimeFormatStyle.STREAM_RELATIVE_STYLE)
        {
            long l1 = System.currentTimeMillis() - l;
            if(l1 < 0x1d4c0L)
                s = context.getString(0x7f0a01ed);
            else
            if(l1 < 0x36ee80L)
            {
                Object aobj7[] = new Object[1];
                aobj7[0] = Long.valueOf(l1 / 60000L);
                s = context.getString(0x7f0a01f2, aobj7);
            } else
            if(l1 < 0x5265c00L)
            {
                if(l1 < 0x6ddd00L)
                {
                    s = context.getString(0x7f0a01ee);
                } else
                {
                    Date date2 = new Date();
                    if(date.getDate() == date2.getDate())
                    {
                        Object aobj6[] = new Object[1];
                        aobj6[0] = Long.valueOf(l1 / 0x36ee80L);
                        s = context.getString(0x7f0a01f1, aobj6);
                    } else
                    {
                        Object aobj5[] = new Object[1];
                        aobj5[0] = DateUtils.formatDateTime(context, l, 2561);
                        s = context.getString(0x7f0a01fa, aobj5);
                    }
                }
            } else
            if(l1 < 0x14997000L)
            {
                Object aobj4[] = new Object[2];
                aobj4[0] = DateUtils.formatDateTime(context, l, 32770);
                aobj4[1] = DateUtils.formatDateTime(context, l, 2561);
                s = context.getString(0x7f0a01f9, aobj4);
            } else
            {
                Object aobj3[] = new Object[2];
                aobj3[0] = DateUtils.formatDateTime(context, l, 0x10018);
                aobj3[1] = DateUtils.formatDateTime(context, l, 2561);
                s = context.getString(0x7f0a01ef, aobj3);
            }
        } else
        if(timeformatstyle == TimeFormatStyle.MAILBOX_RELATIVE_STYLE)
        {
            Date date1 = new Date();
            if(date.getDate() == date1.getDate())
            {
                Object aobj2[] = new Object[1];
                aobj2[0] = DateUtils.formatDateTime(context, l, 2561);
                s = context.getString(0x7f0a01f7, aobj2);
            } else
            if((new Date(0x83d600L + l)).getDay() == date1.getDate())
            {
                Object aobj1[] = new Object[1];
                aobj1[0] = DateUtils.formatDateTime(context, l, 2561);
                s = context.getString(0x7f0a01fa, aobj1);
            } else
            {
                Object aobj[] = new Object[2];
                aobj[0] = DateUtils.formatDateTime(context, l, 0x10018);
                aobj[1] = DateUtils.formatDateTime(context, l, 2561);
                s = context.getString(0x7f0a01ef, aobj);
            }
        } else
        if(timeformatstyle == TimeFormatStyle.WEEK_DAY_STYLE)
            s = DateUtils.formatDateTime(context, l, 32770);
        else
        if(timeformatstyle == TimeFormatStyle.MONTH_DAY_YEAR_STYLE)
            s = DateUtils.formatDateTime(context, l, 0x10014);
        else
            throw new IllegalArgumentException("Unknown style");
        return s;
    }

    public static boolean isBlank(String s)
    {
        boolean flag;
        if(s == null || s.trim().length() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static transient String join(String s, Object aobj[])
    {
        StringBuilder stringbuilder = new StringBuilder();
        join(stringbuilder, s, null, aobj);
        return stringbuilder.toString();
    }

    public static transient void join(StringBuilder stringbuilder, String s, StringProcessor stringprocessor, Object aobj[])
    {
        boolean flag = true;
        int i = aobj.length;
        int j = 0;
        while(j < i) 
        {
            Object obj = aobj[j];
            if(flag)
                flag = false;
            else
                stringbuilder.append(s);
            if(obj instanceof Collection)
                join(stringbuilder, s, stringprocessor, ((Collection)obj).toArray());
            else
            if(obj instanceof Object[])
                join(stringbuilder, s, stringprocessor, (Object[])(Object[])obj);
            else
            if(stringprocessor != null)
                stringbuilder.append(stringprocessor.formatString(obj));
            else
                stringbuilder.append(obj.toString());
            j++;
        }
    }

    public static void parseExpression(String s, String s1, String s2, List list, int i)
    {
        if(s == null)
            break MISSING_BLOCK_LABEL_164;
        Matcher matcher;
        int j;
        int k;
        int l;
        matcher = Pattern.compile(s1).matcher(s);
        j = 0;
        k = 0;
        l = s.length();
_L1:
        int i1;
        if(j >= l)
            break MISSING_BLOCK_LABEL_164;
        matcher.region(j, l);
        if(!matcher.find())
            break MISSING_BLOCK_LABEL_156;
        String s3 = matcher.group();
        if(s2 != null)
            s3 = s3.replaceAll(s2, "");
        else
        if(s1.equals("^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*") && !s3.startsWith("http"))
            s3 = (new StringBuilder()).append("http://").append(s3).toString();
        list.add(s3);
        if(++k == i)
            break MISSING_BLOCK_LABEL_164;
        i1 = matcher.end();
        j = i1 + 1;
          goto _L1
        j++;
          goto _L1
        Exception exception;
        exception;
    }

    public static String randomString(int i)
    {
        String s;
        if(i < 1)
        {
            s = null;
        } else
        {
            char ac[] = new char[i];
            for(int j = 0; j < ac.length; j++)
                ac[j] = numbersAndLetters[Utils.RNG.nextInt(numbersAndLetters.length)];

            s = new String(ac);
        }
        return s;
    }

    public static String rsvpStatusToString(Context context, int i)
    {
        String s = "";
        if(i != com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.ATTENDING.ordinal()) goto _L2; else goto _L1
_L1:
        s = context.getString(0x7f0a0058);
_L4:
        return s;
_L2:
        if(i == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.UNSURE.ordinal())
            s = context.getString(0x7f0a0063);
        else
        if(i == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.DECLINED.ordinal())
            s = context.getString(0x7f0a0059);
        else
        if(i == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.NOT_REPLIED.ordinal())
            s = context.getString(0x7f0a0060);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static boolean saneStringEquals(String s, String s1)
    {
        boolean flag;
        if(s == null)
        {
            if(s1 == null)
                flag = true;
            else
                flag = false;
        } else
        {
            flag = s.equals(s1);
        }
        return flag;
    }

    public static String stripHTMLTags(String s)
    {
        String s2;
        if(s == null)
        {
            s2 = null;
        } else
        {
            String s1 = s.replaceAll("<br", "\n<br").replaceAll("<div", "\n<div");
            s2 = Entities.HTML40.unescape(s1.replaceAll("<(.|\n)*?>", "")).trim();
        }
        return s2;
    }

    public static String xmlEncodeNonLatin(String s)
    {
        StringBuilder stringbuilder = new StringBuilder(s.length());
        int i = 0;
        while(i < s.length()) 
        {
            char c = s.charAt(i);
            if(c == '&')
                stringbuilder.append("&amp;");
            else
            if(c >= ' ' && c <= '~')
            {
                stringbuilder.append(c);
            } else
            {
                stringbuilder.append("&#");
                stringbuilder.append(Integer.toString(c));
                stringbuilder.append(";");
            }
            i++;
        }
        return stringbuilder.toString();
    }

    public static StringProcessor FQLEscaper = new StringProcessor() {

        public String formatString(Object obj)
        {
            return StringUtils.appendEscapedFQLString(new StringBuilder(), obj.toString()).toString();
        }

    }
;
    public static final int HOUR_MIN_TIME = 2561;
    public static final int MONTH_DAY = 0x10018;
    public static final int MONTH_DAY_YEAR = 0x10014;
    public static final String URL_REG_EXPRESSION = "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*";
    public static final int WEEK_DAY = 32770;
    private static final char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

}
