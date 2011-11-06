// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.sym;


// Referenced classes of package org.codehaus.jackson.sym:
//            Name

public final class NameN extends Name
{

    NameN(String s, int i, int ai[], int j)
    {
        super(s, i);
        if(j < 3)
        {
            throw new IllegalArgumentException("Qlen must >= 3");
        } else
        {
            mQuads = ai;
            mQuadLen = j;
            return;
        }
    }

    public boolean equals(int i)
    {
        return false;
    }

    public boolean equals(int i, int j)
    {
        return false;
    }

    public boolean equals(int ai[], int i)
    {
        if(i == mQuadLen) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        int j = 0;
        do
        {
            if(j >= i)
                break;
            if(ai[j] != mQuads[j])
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            j++;
        } while(true);
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    final int mQuadLen;
    final int mQuads[];
}
