// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumericWheelAdapter.java

package kankan.wheel.widget.adapters;

import android.content.Context;

// Referenced classes of package kankan.wheel.widget.adapters:
//            AbstractWheelTextAdapter

public class NumericWheelAdapter extends AbstractWheelTextAdapter
{

    public NumericWheelAdapter(Context context)
    {
        this(context, 0, 9);
    }

    public NumericWheelAdapter(Context context, int i, int j)
    {
        this(context, i, j, null);
    }

    public NumericWheelAdapter(Context context, int i, int j, String s)
    {
        super(context);
        minValue = i;
        maxValue = j;
        format = s;
    }

    public CharSequence getItemText(int i)
    {
        String s;
        if(i >= 0 && i < getItemsCount())
        {
            int j = i + minValue;
            if(format != null)
            {
                String s1 = format;
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(j);
                s = String.format(s1, aobj);
            } else
            {
                s = Integer.toString(j);
            }
        } else
        {
            s = null;
        }
        return s;
    }

    public int getItemsCount()
    {
        return 1 + (maxValue - minValue);
    }

    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE;
    private String format;
    private int maxValue;
    private int minValue;
}
