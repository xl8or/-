// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   primary.java

package org.xbill.DNS.tests;

import java.io.PrintStream;
import java.util.Iterator;
import org.xbill.DNS.Name;
import org.xbill.DNS.Zone;

public class primary
{

    public primary()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        int i;
        boolean flag;
        boolean flag1;
        boolean flag2;
        int j;
        Name name;
        String s;
        long l;
        Zone zone;
        long l1;
        if(args.length < 2)
        {
            i = 0;
            flag = false;
            flag1 = false;
            flag2 = false;
            usage();
        } else
        {
            i = 0;
            flag = false;
            flag1 = false;
            flag2 = false;
        }
        while(args.length - i > 2) 
        {
            if(args[0].equals("-t"))
                flag2 = true;
            else
            if(args[0].equals("-a"))
                flag1 = true;
            else
            if(args[0].equals("-i"))
                flag = true;
            i++;
        }
        j = i + 1;
        name = Name.fromString(args[i], Name.root);
        int _tmp = j + 1;
        s = args[j];
        l = System.currentTimeMillis();
        zone = new Zone(name, s);
        l1 = System.currentTimeMillis();
        if(flag1)
        {
            for(Iterator iterator1 = zone.AXFR(); iterator1.hasNext(); System.out.println(iterator1.next()));
        } else
        if(flag)
        {
            for(Iterator iterator = zone.iterator(); iterator.hasNext(); System.out.println(iterator.next()));
        } else
        {
            System.out.println(zone);
        }
        if(flag2)
            System.out.println((new StringBuilder()).append("; Load time: ").append(l1 - l).append(" ms").toString());
    }

    private static void usage()
    {
        System.out.println("usage: primary [-t] [-a | -i] origin file");
        System.exit(1);
    }
}
