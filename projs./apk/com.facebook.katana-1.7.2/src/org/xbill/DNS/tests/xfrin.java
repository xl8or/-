// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   xfrin.java

package org.xbill.DNS.tests;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.*;

public class xfrin
{

    public xfrin()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        boolean flag;
        byte byte0;
        int i;
        TSIG tsig;
        int j;
        String s;
        flag = false;
        byte0 = 53;
        i = -1;
        tsig = null;
        j = 0;
        s = null;
_L2:
        if(j >= args.length)
            break MISSING_BLOCK_LABEL_377;
        if(!args[j].equals("-i"))
            break; /* Loop/switch isn't completed */
        j++;
        int j1 = Integer.parseInt(args[j]);
        Name name;
        Lookup lookup;
        Record arecord[];
        String s1;
        ZoneTransferIn zonetransferin;
        List list;
        Iterator iterator;
        org.xbill.DNS.ZoneTransferIn.Delta delta;
        Iterator iterator1;
        Iterator iterator2;
        Iterator iterator3;
        int k;
        int l;
        TSIG tsig1;
        boolean flag1;
        String s2;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        String s3;
        boolean flag6;
        String s4;
        int i1;
        TSIG tsig2;
        boolean flag7;
        if(j1 < 0)
        {
            usage("invalid serial number");
            l = j1;
            k = byte0;
            tsig1 = tsig;
            boolean flag9 = flag;
            s2 = s;
            flag2 = flag9;
        } else
        {
            l = j1;
            k = byte0;
            tsig1 = tsig;
            boolean flag8 = flag;
            s2 = s;
            flag2 = flag8;
        }
_L3:
        j++;
        i = l;
        tsig = tsig1;
        byte0 = k;
        flag3 = flag2;
        s = s2;
        flag = flag3;
        if(true) goto _L2; else goto _L1
_L1:
        if(args[j].equals("-k"))
        {
            j++;
            s4 = args[j];
            i1 = s4.indexOf('/');
            if(i1 < 0)
                usage("invalid key");
            tsig2 = new TSIG(s4.substring(0, i1), s4.substring(i1 + 1));
            k = byte0;
            l = i;
            tsig1 = tsig2;
            flag7 = flag;
            s2 = s;
            flag2 = flag7;
        } else
        if(args[j].equals("-s"))
        {
            j++;
            s3 = args[j];
            k = byte0;
            l = i;
            tsig1 = tsig;
            flag6 = flag;
            s2 = s3;
            flag2 = flag6;
        } else
        if(args[j].equals("-p"))
        {
            j++;
            k = Integer.parseInt(args[j]);
            if(k < 0 || k > 65535)
            {
                usage("invalid port");
                l = i;
                tsig1 = tsig;
                flag4 = flag;
                s2 = s;
                flag2 = flag4;
            } else
            {
                l = i;
                tsig1 = tsig;
                flag5 = flag;
                s2 = s;
                flag2 = flag5;
            }
        } else
        if(args[j].equals("-f"))
        {
            k = byte0;
            s2 = s;
            l = i;
            flag2 = true;
            tsig1 = tsig;
        } else
        {
            if(!args[j].startsWith("-"))
                break MISSING_BLOCK_LABEL_377;
            usage("invalid option");
            k = byte0;
            l = i;
            tsig1 = tsig;
            flag1 = flag;
            s2 = s;
            flag2 = flag1;
        }
          goto _L3
          goto _L2
        if(j >= args.length)
            usage("no zone name specified");
        name = Name.fromString(args[j]);
        if(s == null)
        {
            lookup = new Lookup(name, 2);
            arecord = lookup.run();
            if(arecord == null)
            {
                System.out.println((new StringBuilder()).append("failed to look up NS record: ").append(lookup.getErrorString()).toString());
                System.exit(1);
            }
            s = arecord[0].rdataToString();
            System.out.println((new StringBuilder()).append("sending to server '").append(s).append("'").toString());
        }
        s1 = s;
        if(i >= 0)
            zonetransferin = ZoneTransferIn.newIXFR(name, i, flag, s1, byte0, tsig);
        else
            zonetransferin = ZoneTransferIn.newAXFR(name, s1, byte0, tsig);
        list = zonetransferin.run();
        if(zonetransferin.isAXFR())
        {
            if(i >= 0)
                System.out.println("AXFR-like IXFR response");
            else
                System.out.println("AXFR response");
            for(iterator3 = list.iterator(); iterator3.hasNext(); System.out.println(iterator3.next()));
        } else
        if(zonetransferin.isIXFR())
        {
            System.out.println("IXFR response");
            for(iterator = list.iterator(); iterator.hasNext();)
            {
                delta = (org.xbill.DNS.ZoneTransferIn.Delta)iterator.next();
                System.out.println((new StringBuilder()).append("delta from ").append(delta.start).append(" to ").append(delta.end).toString());
                System.out.println("deletes");
                for(iterator1 = delta.deletes.iterator(); iterator1.hasNext(); System.out.println(iterator1.next()));
                System.out.println("adds");
                iterator2 = delta.adds.iterator();
                while(iterator2.hasNext()) 
                    System.out.println(iterator2.next());
            }

        } else
        if(zonetransferin.isCurrent())
            System.out.println("up to date");
        return;
    }

    private static void usage(String s)
    {
        System.out.println((new StringBuilder()).append("Error: ").append(s).toString());
        System.out.println("usage: xfrin [-i serial] [-k keyname/secret] [-s server] [-p port] [-f] zone");
        System.exit(1);
    }
}
