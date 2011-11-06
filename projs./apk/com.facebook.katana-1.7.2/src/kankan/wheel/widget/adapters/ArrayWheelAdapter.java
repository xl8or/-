// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrayWheelAdapter.java

package kankan.wheel.widget.adapters;

import android.content.Context;

// Referenced classes of package kankan.wheel.widget.adapters:
//            AbstractWheelTextAdapter

public class ArrayWheelAdapter extends AbstractWheelTextAdapter
{

    public ArrayWheelAdapter(Context context, Object aobj[])
    {
        super(context);
        items = aobj;
    }

    public CharSequence getItemText(int i)
    {
        Object obj;
        if(i >= 0 && i < items.length)
        {
            Object obj1 = items[i];
            if(obj1 instanceof CharSequence)
                obj = (CharSequence)obj1;
            else
                obj = obj1.toString();
        } else
        {
            obj = null;
        }
        return ((CharSequence) (obj));
    }

    public int getItemsCount()
    {
        return items.length;
    }

    private Object items[];
}
