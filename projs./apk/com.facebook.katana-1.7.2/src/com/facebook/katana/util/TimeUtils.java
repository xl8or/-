// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeUtils.java

package com.facebook.katana.util;

import android.content.Context;
import android.text.format.DateUtils;
import java.text.DateFormatSymbols;
import java.util.*;

public class TimeUtils
{

    public TimeUtils()
    {
    }

    public static final int getAge(int i, long l)
    {
        return (1900 + (new Date(l)).getYear()) - i;
    }

    public static int getPstOffset()
    {
        TimeZone timezone = TimeZone.getDefault();
        TimeZone timezone1 = TimeZone.getTimeZone("America/Los_Angeles");
        Date date = new Date();
        int i;
        int j;
        if(timezone.inDaylightTime(date))
            i = timezone.getDSTSavings();
        else
            i = 0;
        if(timezone1.inDaylightTime(date))
            j = timezone1.getDSTSavings();
        else
            j = 0;
        return (j + timezone1.getRawOffset()) - timezone.getRawOffset() - i;
    }

    public static final String getStringOfTimePeriod(int i, Context context)
    {
        i;
        JVM INSTR tableswitch -1 4: default 40
    //                   -1 57
    //                   0 65
    //                   1 75
    //                   2 85
    //                   3 95
    //                   4 105;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L7:
        break MISSING_BLOCK_LABEL_105;
_L1:
        String s1 = (new DateFormatSymbols()).getMonths()[i - 5];
_L8:
        return s1;
_L2:
        String s = "";
_L9:
        s1 = s;
        if(true) goto _L8; else goto _L3
_L3:
        s = context.getString(0x7f0a01f0);
          goto _L9
_L4:
        s = context.getString(0x7f0a01f6);
          goto _L9
_L5:
        s = context.getString(0x7f0a01f8);
          goto _L9
_L6:
        s = context.getString(0x7f0a01f5);
          goto _L9
        s = context.getString(0x7f0a01f4);
          goto _L9
    }

    public static final String getTimeAsStringForTimePeriod(Context context, int i, long l)
    {
        i;
        JVM INSTR lookupswitch 5: default 52
    //                   0: 94
    //                   1: 105
    //                   2: 105
    //                   3: 118
    //                   100: 131;
           goto _L1 _L2 _L3 _L3 _L4 _L5
_L1:
        String s;
        Object aobj[] = new Object[2];
        aobj[0] = DateUtils.formatDateTime(context, l, 0x10018);
        aobj[1] = DateUtils.formatDateTime(context, l, 2561);
        s = context.getString(0x7f0a01ef, aobj);
_L7:
        return s;
_L2:
        s = context.getString(0x7f0a01f3);
        continue; /* Loop/switch isn't completed */
_L3:
        s = DateUtils.formatDateTime(context, l, 2561);
        continue; /* Loop/switch isn't completed */
_L4:
        s = DateUtils.formatDateTime(context, l, 2563);
        continue; /* Loop/switch isn't completed */
_L5:
        s = DateUtils.formatDateTime(context, l, 26);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static final int getTimePeriod(long l)
    {
        return getTimePeriod(System.currentTimeMillis(), l, 0L);
    }

    public static final int getTimePeriod(long l, long l1)
    {
        return getTimePeriod(System.currentTimeMillis(), l, l1);
    }

    public static final int getTimePeriod(long l, long l1, long l2)
    {
        Date date = new Date(l1);
        Date date1 = new Date();
        long l3 = l1 - l;
        int i;
        if(l2 != 0L && l1 <= l && l <= l2)
            i = 0;
        else
        if(l3 < 0L)
            i = -1;
        else
        if(l3 < 0x5265c00L && date.getDate() == date1.getDate())
            i = 1;
        else
        if(l3 < 0xa4cb800L && (1 + date1.getDay()) % 7 == date.getDay())
            i = 2;
        else
        if(date.getDay() >= date1.getDay() && l3 < 0x240c8400L)
        {
            i = 3;
        } else
        {
            long l4 = 3600L * (744L * 1000L);
            if(date.getMonth() == date1.getMonth() && l3 < l4)
                i = 4;
            else
                i = 5 + date.getMonth();
        }
        return i;
    }

    public static final long timeInSeconds(int i, int j, boolean flag)
    {
        int k = (new Date()).getYear();
        if(flag)
            k++;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(k, i, j, 23, 59, 59));
        return calendar.getTimeInMillis() / 1000L;
    }

    public static final int AFTER_THIS_MONTH = 5;
    public static final int ANY_TIME = 100;
    public static final int HAPPENING_NOW = 0;
    public static final int IN_PAST = -1;
    public static final int THIS_MONTH = 4;
    public static final int THIS_WEEK = 3;
    public static final int TODAY = 1;
    public static final int TOMORROW = 2;
}
