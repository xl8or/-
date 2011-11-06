// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Opcode.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            Mnemonic

public final class Opcode
{

    private Opcode()
    {
    }

    public static String string(int i)
    {
        return opcodes.getText(i);
    }

    public static int value(String s)
    {
        return opcodes.getValue(s);
    }

    public static final int IQUERY = 1;
    public static final int NOTIFY = 4;
    public static final int QUERY = 0;
    public static final int STATUS = 2;
    public static final int UPDATE = 5;
    private static Mnemonic opcodes;

    static 
    {
        opcodes = new Mnemonic("DNS Opcode", 2);
        opcodes.setMaximum(15);
        opcodes.setPrefix("RESERVED");
        opcodes.setNumericAllowed(true);
        opcodes.add(0, "QUERY");
        opcodes.add(1, "IQUERY");
        opcodes.add(2, "STATUS");
        opcodes.add(4, "NOTIFY");
        opcodes.add(5, "UPDATE");
    }
}
