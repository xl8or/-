// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractIntegerAttr.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractAttr, BOSHException

abstract class AbstractIntegerAttr extends AbstractAttr
{

    protected AbstractIntegerAttr(int i)
        throws BOSHException
    {
        super(Integer.valueOf(i));
    }

    protected AbstractIntegerAttr(String s)
        throws BOSHException
    {
        super(Integer.valueOf(parseInt(s)));
    }

    private static int parseInt(String s)
        throws BOSHException
    {
        int i;
        try
        {
            i = Integer.parseInt(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new BOSHException((new StringBuilder()).append("Could not parse an integer from the value provided: ").append(s).toString(), numberformatexception);
        }
        return i;
    }

    protected final void checkMinValue(int i)
        throws BOSHException
    {
        int j = ((Integer)getValue()).intValue();
        if(j < i)
            throw new BOSHException((new StringBuilder()).append("Illegal attribute value '").append(j).append("' provided.  ").append("Must be >= ").append(i).toString());
        else
            return;
    }

    public int intValue()
    {
        return ((Integer)getValue()).intValue();
    }
}
