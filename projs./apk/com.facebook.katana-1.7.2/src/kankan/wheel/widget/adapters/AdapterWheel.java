// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdapterWheel.java

package kankan.wheel.widget.adapters;

import android.content.Context;
import kankan.wheel.widget.WheelAdapter;

// Referenced classes of package kankan.wheel.widget.adapters:
//            AbstractWheelTextAdapter

public class AdapterWheel extends AbstractWheelTextAdapter
{

    public AdapterWheel(Context context, WheelAdapter wheeladapter)
    {
        super(context);
        adapter = wheeladapter;
    }

    public WheelAdapter getAdapter()
    {
        return adapter;
    }

    protected CharSequence getItemText(int i)
    {
        return adapter.getItem(i);
    }

    public int getItemsCount()
    {
        return adapter.getItemsCount();
    }

    private WheelAdapter adapter;
}
