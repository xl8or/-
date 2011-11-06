// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utils.java

package com.facebook.katana.util;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import java.io.Serializable;
import java.util.*;
import org.acra.ErrorReporter;

public class Utils
{

    public Utils()
    {
    }

    public static transient Map constantMap(Object aobj[])
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        for(int i = 0; i < aobj.length; i += 2)
            linkedhashmap.put(aobj[i], aobj[i + 1]);

        return Collections.unmodifiableMap(linkedhashmap);
    }

    public static String convertMentionTags(Editable editable)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(editable);
        FacebookProfile afacebookprofile[] = (FacebookProfile[])spannablestringbuilder.getSpans(0, spannablestringbuilder.length(), com/facebook/katana/model/FacebookProfile);
        int i = afacebookprofile.length;
        for(int j = 0; j < i; j++)
        {
            FacebookProfile facebookprofile = afacebookprofile[j];
            int k = spannablestringbuilder.getSpanStart(facebookprofile);
            int l = spannablestringbuilder.getSpanEnd(facebookprofile);
            Object aobj[] = new Object[2];
            aobj[0] = Long.valueOf(facebookprofile.mId);
            aobj[1] = facebookprofile.mDisplayName;
            spannablestringbuilder.replace(k, l, String.format("@[%d:%s]", aobj));
        }

        return spannablestringbuilder.toString().trim();
    }

    public static String getClassName(Class class1)
    {
        String s = class1.getName();
        int i = s.lastIndexOf(".");
        String s1;
        if(i != -1)
            s1 = s.substring(i + 1);
        else
            s1 = s;
        return s1;
    }

    public static String getStringValue(Bundle bundle, String s)
    {
        String s1;
        if(!bundle.getBoolean(s, true) || bundle.getBoolean(s, false))
            s1 = Boolean.toString(bundle.getBoolean(s));
        else
        if(bundle.getLong(s, -1L) != -1L || bundle.getLong(s, 0L) != 0L)
            s1 = Long.toString(bundle.getLong(s));
        else
        if(bundle.getInt(s, -1) != -1 || bundle.getInt(s, 0) != 0)
            s1 = Integer.toString(bundle.getInt(s));
        else
            s1 = bundle.getString(s);
        return s1;
    }

    public static transient int hashItems(Object aobj[])
    {
        long l = hashItemsLong(aobj);
        return (int)(l ^ l >>> 32);
    }

    public static transient long hashItemsLong(Object aobj[])
    {
        long l = 0L;
        int i = aobj.length;
        int j = 0;
        while(j < i) 
        {
            Object obj = aobj[j];
            long l1 = l * 31L;
            if(obj != null)
                l = l1 + (long)obj.hashCode();
            else
                l = l1 + 0xd6d40a59L;
            j++;
        }
        return l;
    }

    public static transient Bundle makeBundle(Object aobj[])
    {
        Bundle bundle = new Bundle();
        int i = 0;
        while(i < aobj.length) 
        {
            String s = (String)aobj[i];
            Object obj = aobj[i + 1];
            if(obj instanceof String)
                bundle.putString(s, (String)obj);
            else
            if(obj instanceof Long)
                bundle.putLong(s, ((Long)obj).longValue());
            else
            if(obj instanceof Integer)
                bundle.putInt(s, ((Integer)obj).intValue());
            else
            if(obj instanceof Boolean)
                bundle.putBoolean(s, ((Boolean)obj).booleanValue());
            else
            if(obj instanceof Serializable)
                bundle.putSerializable(s, (Serializable)obj);
            i += 2;
        }
        return bundle;
    }

    public static void reportSoftError(final String category, final String message)
    {
        (new Thread(new Runnable() {

            public void run()
            {
                ErrorReporter.getInstance().putCustomData("soft_error_category", category.replace("\n", "\\n"));
                ErrorReporter.getInstance().putCustomData("soft_error_message", message.replace("\n", "\\n"));
                ErrorReporter.getInstance().handleException(ex);
                ErrorReporter.getInstance().removeCustomData("soft_error_category");
                ErrorReporter.getInstance().removeCustomData("soft_error_message");
            }

            final String val$category;
            final Exception val$ex;
            final String val$message;

            
            {
                category = s;
                message = s1;
                ex = exception;
                super();
            }
        }
)).start();
    }

    public static void updateErrorReportingUserId(Context context)
    {
        updateErrorReportingUserId(context, false);
    }

    public static void updateErrorReportingUserId(Context context, boolean flag)
    {
        if(flag) goto _L2; else goto _L1
_L1:
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession == null) goto _L2; else goto _L3
_L3:
        FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
        if(facebooksessioninfo == null || facebooksessioninfo.userId == -1L) goto _L2; else goto _L4
_L4:
        ErrorReporter.getInstance().putCustomData("uid", String.valueOf(facebooksessioninfo.userId));
_L6:
        return;
_L2:
        ErrorReporter.getInstance().removeCustomData("uid");
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static final Random RNG = new Random();

}
