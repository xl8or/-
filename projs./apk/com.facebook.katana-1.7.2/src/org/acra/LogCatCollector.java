// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogCatCollector.java

package org.acra;

import android.util.Log;
import java.io.*;
import java.util.*;
import org.acra.annotation.ReportsCrashes;
import org.acra.util.BoundedLinkedList;

// Referenced classes of package org.acra:
//            ACRA, Compatibility

class LogCatCollector
{

    LogCatCollector()
    {
    }

    protected static String collectLogCat(String s)
    {
        BoundedLinkedList boundedlinkedlist = null;
        ArrayList arraylist;
        int i;
        ArrayList arraylist1;
        arraylist = new ArrayList();
        arraylist.add("logcat");
        if(s != null)
        {
            arraylist.add("-b");
            arraylist.add(s);
        }
        i = -1;
        arraylist1 = new ArrayList(Arrays.asList(ACRA.getConfig().logcatArguments()));
        int j = arraylist1.indexOf("-t");
        if(j > -1 && j < arraylist1.size())
        {
            i = Integer.parseInt((String)arraylist1.get(j + 1));
            if(Compatibility.getAPILevel() < 8)
            {
                arraylist1.remove(j + 1);
                arraylist1.remove(j);
                arraylist1.add("-d");
            }
        }
        if(i <= 0) goto _L2; else goto _L1
_L1:
        int k = i;
_L4:
        BoundedLinkedList boundedlinkedlist1 = new BoundedLinkedList(k);
          goto _L3
        ioexception2;
        IOException ioexception1;
        ioexception1 = ioexception2;
        boundedlinkedlist = boundedlinkedlist1;
_L6:
        Log.e(ACRA.LOG_TAG, "LogCatCollector.collectLogcat could not retrieve data.", ioexception1);
_L5:
        return boundedlinkedlist.toString();
_L2:
        k = 100;
          goto _L4
_L3:
        arraylist.addAll(arraylist1);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[])arraylist.toArray(new String[arraylist.size()])).getInputStream()));
        Log.d(ACRA.LOG_TAG, "Retrieving logcat output...");
        IOException ioexception2;
        do
        {
            String s1 = bufferedreader.readLine();
            if(s1 == null)
                break;
            boundedlinkedlist1.add((new StringBuilder()).append(s1).append("\n").toString());
        } while(true);
        boundedlinkedlist = boundedlinkedlist1;
          goto _L5
        IOException ioexception;
        ioexception;
        ioexception1 = ioexception;
          goto _L6
    }

    private static final int DEFAULT_TAIL_COUNT = 100;
}
