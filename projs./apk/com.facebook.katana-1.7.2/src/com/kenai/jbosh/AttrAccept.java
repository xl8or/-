// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrAccept.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractAttr, BOSHException

final class AttrAccept extends AbstractAttr
{

    private AttrAccept(String s)
    {
        super(s);
        encodings = s.split("[\\s,]+");
    }

    static AttrAccept createFromString(String s)
        throws BOSHException
    {
        AttrAccept attraccept;
        if(s == null)
            attraccept = null;
        else
            attraccept = new AttrAccept(s);
        return attraccept;
    }

    boolean isAccepted(String s)
    {
        String as[];
        int i;
        int j;
        as = encodings;
        i = as.length;
        j = 0;
_L3:
        if(j >= i)
            break MISSING_BLOCK_LABEL_43;
        if(!as[j].equalsIgnoreCase(s)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        j++;
          goto _L3
        flag = false;
          goto _L4
    }

    private final String encodings[];
}
