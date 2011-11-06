// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DClass.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            Mnemonic, InvalidDClassException

public final class DClass
{
    private static class DClassMnemonic extends Mnemonic
    {

        public void check(int i)
        {
            DClass.check(i);
        }

        public DClassMnemonic()
        {
            super("DClass", 2);
            setPrefix("CLASS");
        }
    }


    private DClass()
    {
    }

    public static void check(int i)
    {
        if(i < 0 || i > 65535)
            throw new InvalidDClassException(i);
        else
            return;
    }

    public static String string(int i)
    {
        return classes.getText(i);
    }

    public static int value(String s)
    {
        return classes.getValue(s);
    }

    public static final int ANY = 255;
    public static final int CH = 3;
    public static final int CHAOS = 3;
    public static final int HESIOD = 4;
    public static final int HS = 4;
    public static final int IN = 1;
    public static final int NONE = 254;
    private static Mnemonic classes;

    static 
    {
        classes = new DClassMnemonic();
        classes.add(1, "IN");
        classes.add(3, "CH");
        classes.addAlias(3, "CHAOS");
        classes.add(4, "HS");
        classes.addAlias(4, "HESIOD");
        classes.add(254, "NONE");
        classes.add(255, "ANY");
    }
}
