// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UriTemplateMap.java

package com.facebook.katana.util;

import android.net.Uri;
import android.os.Bundle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package com.facebook.katana.util:
//            Tuple

public class UriTemplateMap
{
    public static class InvalidUriException extends Exception
    {

        private static final long serialVersionUID = 0xfb1d2adL;

        public InvalidUriException(String s)
        {
            super(s);
        }
    }

    public static class InvalidUriTemplateException extends RuntimeException
    {

        private static final long serialVersionUID = 0xf1d0c6c7L;

        public InvalidUriTemplateException(String s)
        {
            super(s);
        }
    }

    private class MapEntry
    {

        UriMatch match(String s)
            throws InvalidUriException
        {
            String s2;
            Matcher matcher;
            String as[] = UriTemplateMap.getComponents(s);
            String s1 = as[0];
            s2 = as[1];
            matcher = mSchemeHostPathRegex.matcher(s1);
            if(matcher.matches()) goto _L2; else goto _L1
_L1:
            UriMatch urimatch = null;
_L4:
            return urimatch;
_L2:
            Bundle bundle = new Bundle();
            int i = 0;
            do
            {
                int j = mTemplateParams.size();
                if(i >= j)
                    break;
                Tuple tuple = (Tuple)mTemplateParams.get(i);
                if(tuple.d0 == java/lang/Long)
                    bundle.putLong((String)tuple.d1, Long.parseLong(matcher.group(i + 1)));
                else
                    bundle.putString((String)tuple.d1, matcher.group(i + 1));
                i++;
            } while(true);
            Map map = UriTemplateMap.getQueryAsMap(s2);
            Iterator iterator = mQueryParameters.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                String s3 = (String)entry.getKey();
                QueryParameter queryparameter = (QueryParameter)entry.getValue();
                String s4 = queryparameter.mFieldName;
                if(queryparameter.mRequired && !map.containsKey(s3))
                {
                    urimatch = null;
                    continue; /* Loop/switch isn't completed */
                }
                String s5;
                if(map.containsKey(s3))
                    s5 = (String)map.get(s3);
                else
                    s5 = queryparameter.mDefaultValue;
                if(queryparameter.mType == TemplateValueType.LONG)
                    bundle.putLong(s4, Long.parseLong(s5));
                else
                if(queryparameter.mType == TemplateValueType.BOOLEAN)
                    bundle.putBoolean(s4, Boolean.valueOf(s5).booleanValue());
                else
                    bundle.putString(s4, s5);
            } while(true);
            Object obj = mValue;
            urimatch = new UriMatch(obj, bundle);
            if(true) goto _L4; else goto _L3
_L3:
        }

        private final Map mQueryParameters;
        private final Pattern mSchemeHostPathRegex;
        List mTemplateParams;
        private final Object mValue;
        final UriTemplateMap this$0;

        MapEntry(String s, Object obj)
            throws InvalidUriTemplateException
        {
            HashSet hashset;
            Iterator iterator;
            this$0 = UriTemplateMap.this;
            super();
            mQueryParameters = new HashMap();
            mTemplateParams = new ArrayList();
            mValue = obj;
            String as[] = UriTemplateMap.getComponents(s);
            String s1 = as[0];
            String s2 = as[1];
            Matcher matcher = UriTemplateMap.SHP_TEMPLATE_REGEX.matcher(s1);
            String s3 = s1;
            hashset = new HashSet();
            while(matcher.find()) 
            {
                String s7 = matcher.group(0);
                boolean flag = matcher.group(1).equals("#");
                Object obj1;
                String s8;
                List list;
                Tuple tuple;
                if(flag)
                    obj1 = java/lang/Long;
                else
                    obj1 = java/lang/String;
                s8 = matcher.group(2);
                list = mTemplateParams;
                tuple = new Tuple(obj1, s8);
                list.add(tuple);
                if(!hashset.add(s8))
                    throw new InvalidUriTemplateException("Duplicate template key");
                String s9;
                if(flag)
                    s9 = "(-?[0-9]+)";
                else
                    s9 = "([^/]+)";
                s3 = s3.replace(s7, s9);
            }
            if(s1 == s3 && s1.matches("[A-Za-z]+://[A-Za-z]+"))
                s3 = (new StringBuilder()).append(s3).append("[/]?").toString();
            mSchemeHostPathRegex = Pattern.compile(s3);
            iterator = UriTemplateMap.getQueryAsMap(s2).entrySet().iterator();
_L7:
            if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
            java.util.Map.Entry entry;
            Matcher matcher1;
            TemplateValueType templatevaluetype;
            entry = (java.util.Map.Entry)iterator.next();
            matcher1 = UriTemplateMap.QUERY_TEMPLATE_REGEX.matcher((CharSequence)entry.getValue());
            if(!matcher1.matches())
                throw new InvalidUriTemplateException("Query parameter does not match templating syntax");
            templatevaluetype = TemplateValueType.STRING;
            if(!"#".equals(matcher1.group(1))) goto _L4; else goto _L3
_L3:
            templatevaluetype = TemplateValueType.LONG;
_L6:
            String s4;
            String s5;
            String s6;
            s4 = matcher1.group(2);
            s5 = matcher1.group(3);
            s6 = (String)entry.getKey();
            if(!hashset.add(s6))
                throw new InvalidUriTemplateException("Duplicate template key");
            break; /* Loop/switch isn't completed */
_L4:
            if("!".equals(matcher1.group(1)))
                templatevaluetype = TemplateValueType.BOOLEAN;
            if(true) goto _L6; else goto _L5
_L5:
            Map map = mQueryParameters;
            QueryParameter queryparameter = new QueryParameter(s4, templatevaluetype, s5);
            map.put(s6, queryparameter);
              goto _L7
_L2:
        }
    }

    private class QueryParameter
    {

        String mDefaultValue;
        String mFieldName;
        boolean mRequired;
        TemplateValueType mType;
        final UriTemplateMap this$0;

        QueryParameter(String s, TemplateValueType templatevaluetype, String s1)
        {
            this$0 = UriTemplateMap.this;
            super();
            mFieldName = s;
            mType = templatevaluetype;
            mDefaultValue = s1;
            boolean flag;
            if(s1 == null)
                flag = true;
            else
                flag = false;
            mRequired = flag;
        }
    }

    private static final class TemplateValueType extends Enum
    {

        public static TemplateValueType valueOf(String s)
        {
            return (TemplateValueType)Enum.valueOf(com/facebook/katana/util/UriTemplateMap$TemplateValueType, s);
        }

        public static TemplateValueType[] values()
        {
            return (TemplateValueType[])$VALUES.clone();
        }

        private static final TemplateValueType $VALUES[];
        public static final TemplateValueType BOOLEAN;
        public static final TemplateValueType LONG;
        public static final TemplateValueType STRING;

        static 
        {
            STRING = new TemplateValueType("STRING", 0);
            LONG = new TemplateValueType("LONG", 1);
            BOOLEAN = new TemplateValueType("BOOLEAN", 2);
            TemplateValueType atemplatevaluetype[] = new TemplateValueType[3];
            atemplatevaluetype[0] = STRING;
            atemplatevaluetype[1] = LONG;
            atemplatevaluetype[2] = BOOLEAN;
            $VALUES = atemplatevaluetype;
        }

        private TemplateValueType(String s, int i)
        {
            super(s, i);
        }
    }

    public static class UriMatch
    {

        public final Bundle parameters;
        public final Object value;

        public UriMatch(Object obj, Bundle bundle)
        {
            value = obj;
            parameters = bundle;
        }
    }


    public UriTemplateMap()
    {
    }

    private static String[] getComponents(String s)
    {
        boolean flag;
        int i;
        flag = false;
        i = 0;
_L7:
        if(i >= s.length()) goto _L2; else goto _L1
_L1:
        s.charAt(i);
        JVM INSTR lookupswitch 3: default 52
    //                   63: 68
    //                   123: 58
    //                   125: 63;
           goto _L3 _L4 _L5 _L6
_L3:
        i++;
          goto _L7
_L5:
        flag = true;
          goto _L3
_L6:
        flag = false;
          goto _L3
_L4:
        if(flag) goto _L3; else goto _L8
_L8:
        String as[];
        as = new String[2];
        as[0] = s.substring(0, i);
        as[1] = s.substring(i + 1);
_L10:
        return as;
_L2:
        as = new String[2];
        as[0] = s;
        as[1] = "";
        if(true) goto _L10; else goto _L9
_L9:
    }

    private static Map getQueryAsMap(String s)
    {
        HashMap hashmap = new HashMap();
        for(Matcher matcher = QUERY_REGEX.matcher(s); matcher.find(); hashmap.put(Uri.decode(matcher.group(1)), Uri.decode(matcher.group(2))));
        return hashmap;
    }

    public UriMatch get(String s)
        throws InvalidUriException
    {
        Iterator iterator;
        if(s == null)
            throw new InvalidUriException("Key may not be null");
        iterator = mEntries.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        UriMatch urimatch1 = ((MapEntry)iterator.next()).match(s);
        if(urimatch1 == null) goto _L4; else goto _L3
_L3:
        UriMatch urimatch = urimatch1;
_L6:
        return urimatch;
_L2:
        urimatch = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void put(String s, Object obj)
        throws InvalidUriTemplateException
    {
        if(s == null)
        {
            throw new InvalidUriTemplateException("Key template may not be null");
        } else
        {
            mEntries.add(new MapEntry(s, obj));
            return;
        }
    }

    private static final Pattern QUERY_REGEX = Pattern.compile("&?([^=]+)=([^&]+)");
    private static final Pattern QUERY_TEMPLATE_REGEX = Pattern.compile("\\{([#!]?)([^ }]+)(?: ([^}]+))?\\}");
    private static final Pattern SHP_TEMPLATE_REGEX = Pattern.compile("\\{([#]?)([^ }]+)\\}");
    private final List mEntries = new ArrayList();





}
