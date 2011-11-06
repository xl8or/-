// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DumpSysCollector.java

package org.acra;

import android.os.Process;
import android.util.Log;
import java.io.*;
import java.util.ArrayList;

// Referenced classes of package org.acra:
//            ACRA

class DumpSysCollector
{

    DumpSysCollector()
    {
    }

    protected static String collectMemInfo()
    {
        StringBuilder stringbuilder = new StringBuilder();
        try
        {
            ArrayList arraylist = new ArrayList();
            arraylist.add("dumpsys");
            arraylist.add("meminfo");
            arraylist.add(Integer.toString(Process.myPid()));
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[])arraylist.toArray(new String[arraylist.size()])).getInputStream()));
            do
            {
                String s = bufferedreader.readLine();
                if(s == null)
                    break;
                stringbuilder.append(s);
                stringbuilder.append("\n");
            } while(true);
        }
        catch(IOException ioexception)
        {
            Log.e(ACRA.LOG_TAG, "DumpSysCollector.meminfo could not retrieve data", ioexception);
        }
        return stringbuilder.toString();
    }
}
