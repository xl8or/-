// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DropBoxCollector.java

package org.acra;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.acra.annotation.ReportsCrashes;

// Referenced classes of package org.acra:
//            Compatibility, ACRA

class DropBoxCollector
{

    DropBoxCollector()
    {
    }

    public static String read(Context context, String as[])
    {
        String s1 = Compatibility.getDropBoxServiceName();
        if(s1 == null) goto _L2; else goto _L1
_L1:
        StringBuilder stringbuilder;
        Object obj;
        Method method;
        stringbuilder = new StringBuilder();
        obj = context.getSystemService(s1);
        Class class1 = obj.getClass();
        Class aclass[] = new Class[2];
        aclass[0] = java/lang/String;
        aclass[1] = Long.TYPE;
        method = class1.getMethod("getNextEntry", aclass);
        if(method == null) goto _L4; else goto _L3
_L3:
        String s3;
        long l1;
        Object obj1;
        Time time = new Time();
        time.setToNow();
        time.minute = time.minute - ACRA.getConfig().dropboxCollectionMinutes();
        time.normalize(false);
        long l = time.toMillis(false);
        ArrayList arraylist1;
        Iterator iterator;
        String s4;
        Object aobj[];
        Class class2;
        Class aclass1[];
        Method method1;
        Method method2;
        Method method3;
        Object aobj1[];
        Object aobj2[];
        Object aobj3[];
        Object aobj4[];
        if(ACRA.getConfig().includeDropBoxSystemTags())
        {
            ArrayList arraylist = new ArrayList(Arrays.asList(SYSTEM_TAGS));
            arraylist1 = arraylist;
        } else
        {
            arraylist1 = new ArrayList();
        }
        if(as != null && as.length > 0)
        {
            java.util.List list = Arrays.asList(as);
            arraylist1.addAll(list);
        }
        if(arraylist1.size() <= 0) goto _L6; else goto _L5
_L5:
        iterator = arraylist1.iterator();
        s3 = null;
_L11:
        if(!iterator.hasNext()) goto _L4; else goto _L7
_L7:
        s4 = (String)iterator.next();
        l1 = l;
        stringbuilder.append("Tag: ").append(s4).append('\n');
        aobj = new Object[2];
        aobj[0] = s4;
        aobj[1] = Long.valueOf(l1);
        obj1 = method.invoke(obj, aobj);
        if(obj1 == null) goto _L9; else goto _L8
_L8:
        class2 = obj1.getClass();
        aclass1 = new Class[1];
        aclass1[0] = Integer.TYPE;
        method1 = class2.getMethod("getText", aclass1);
        method2 = obj1.getClass().getMethod("getTimeMillis", (Class[])null);
        method3 = obj1.getClass().getMethod("close", (Class[])null);
        while(obj1 != null) 
        {
            aobj1 = (Object[])null;
            l1 = ((Long)method2.invoke(obj1, aobj1)).longValue();
            time.set(l1);
            stringbuilder.append("@").append(time.format2445()).append('\n');
            aobj2 = new Object[1];
            aobj2[0] = Integer.valueOf(500);
            s3 = (String)method1.invoke(obj1, aobj2);
            if(s3 != null)
                stringbuilder.append("Text: ").append(s3).append('\n');
            else
                stringbuilder.append("Not Text!").append('\n');
            aobj3 = (Object[])null;
            method3.invoke(obj1, aobj3);
            aobj4 = new Object[2];
            aobj4[0] = s4;
            aobj4[1] = Long.valueOf(l1);
            obj1 = method.invoke(obj, aobj4);
        }
          goto _L10
        SecurityException securityexception;
        securityexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
_L2:
        String s = "N/A";
_L13:
        return s;
_L10:
        String s5;
        l1;
        obj1;
        s5 = s3;
_L12:
        s3 = s5;
          goto _L11
_L9:
        stringbuilder.append("Nothing.").append('\n');
        l1;
        obj1;
        s5 = s3;
          goto _L12
_L6:
        stringbuilder.append("No tag configured for collection.");
_L4:
        String s2 = stringbuilder.toString();
        s = s2;
          goto _L13
        NoSuchMethodException nosuchmethodexception;
        nosuchmethodexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
          goto _L2
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
          goto _L2
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
          goto _L2
        InvocationTargetException invocationtargetexception;
        invocationtargetexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
          goto _L2
        NoSuchFieldException nosuchfieldexception;
        nosuchfieldexception;
        Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
          goto _L2
    }

    private static final String SYSTEM_TAGS[];

    static 
    {
        String as[] = new String[15];
        as[0] = "system_app_anr";
        as[1] = "system_app_wtf";
        as[2] = "system_app_crash";
        as[3] = "system_server_anr";
        as[4] = "system_server_wtf";
        as[5] = "system_server_crash";
        as[6] = "BATTERY_DISCHARGE_INFO";
        as[7] = "SYSTEM_RECOVERY_LOG";
        as[8] = "SYSTEM_BOOT";
        as[9] = "SYSTEM_LAST_KMSG";
        as[10] = "APANIC_CONSOLE";
        as[11] = "APANIC_THREADS";
        as[12] = "SYSTEM_RESTART";
        as[13] = "SYSTEM_TOMBSTONE";
        as[14] = "data_app_strictmode";
        SYSTEM_TAGS = as;
    }
}
