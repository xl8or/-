// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtendedFlags.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            Mnemonic

public final class ExtendedFlags
{

    private ExtendedFlags()
    {
    }

    public static String string(int i)
    {
        return extflags.getText(i);
    }

    public static int value(String s)
    {
        return extflags.getValue(s);
    }

    public static final int DO = 32768;
    private static Mnemonic extflags;

    static 
    {
        extflags = new Mnemonic("EDNS Flag", 3);
        extflags.setMaximum(65535);
        extflags.setPrefix("FLAG");
        extflags.setNumericAllowed(true);
        extflags.add(32768, "do");
    }
}
