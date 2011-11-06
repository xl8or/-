// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGeneratedQuery.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public abstract class FqlGeneratedQuery extends FqlQuery
{

    public FqlGeneratedQuery(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, String s2, Class class1)
    {
        this(context, intent, s, apimethodlistener, s1, s2, getJsonFieldsFromClass(class1));
    }

    public FqlGeneratedQuery(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, String s2, Set set)
    {
        super(context, intent, s, buildQuery(s1, s2, set), apimethodlistener);
    }

    protected static String buildQuery(String s, String s1, Set set)
    {
        StringBuffer stringbuffer = new StringBuffer("SELECT ");
        Object aobj[] = new Object[1];
        aobj[0] = set;
        stringbuffer.append(StringUtils.join(",", aobj)).append(" FROM ").append(s).append(" WHERE ").append(s1);
        return stringbuffer.toString();
    }

    protected static Set getJsonFieldsFromClass(Class class1)
    {
        Set set = (Set)fieldListCache.get(class1);
        Set set2;
        if(set != null)
        {
            set2 = set;
        } else
        {
            Set set1 = JMAutogen.getJsonFieldsFromClass(class1);
            fieldListCache.put(class1, set1);
            set2 = set1;
        }
        return set2;
    }

    protected static Map fieldListCache = new HashMap();

}
