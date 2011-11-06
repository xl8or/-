// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.sym;


// Referenced classes of package org.codehaus.jackson.sym:
//            Name

public final class Name1 extends Name
{

    Name1(String s, int i, int j)
    {
        super(s, i);
        mQuad = j;
    }

    static final Name1 getEmptyName()
    {
        return sEmptyName;
    }

    public boolean equals(int i)
    {
        boolean flag;
        if(i == mQuad)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean equals(int i, int j)
    {
        boolean flag;
        if(i == mQuad && j == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean equals(int ai[], int i)
    {
        boolean flag;
        if(i == 1 && ai[0] == mQuad)
            flag = true;
        else
            flag = false;
        return flag;
    }

    static final Name1 sEmptyName = new Name1("", 0, 0);
    final int mQuad;

}
