// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ItemsRange.java

package kankan.wheel.widget;


public class ItemsRange
{

    public ItemsRange()
    {
        this(0, 0);
    }

    public ItemsRange(int i, int j)
    {
        first = i;
        count = j;
    }

    public boolean contains(int i)
    {
        boolean flag;
        if(i >= getFirst() && i <= getLast())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int getCount()
    {
        return count;
    }

    public int getFirst()
    {
        return first;
    }

    public int getLast()
    {
        return (getFirst() + getCount()) - 1;
    }

    private int count;
    private int first;
}
