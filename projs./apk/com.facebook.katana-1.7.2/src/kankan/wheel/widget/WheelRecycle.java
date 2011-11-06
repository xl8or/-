// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WheelRecycle.java

package kankan.wheel.widget;

import android.view.View;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;
import kankan.wheel.widget.adapters.WheelViewAdapter;

// Referenced classes of package kankan.wheel.widget:
//            WheelView, ItemsRange

public class WheelRecycle
{

    public WheelRecycle(WheelView wheelview)
    {
        wheel = wheelview;
    }

    private List addView(View view, List list)
    {
        if(list == null)
            list = new LinkedList();
        list.add(view);
        return list;
    }

    private View getCachedView(List list)
    {
        View view;
        if(list != null && list.size() > 0)
        {
            View view1 = (View)list.get(0);
            list.remove(0);
            view = view1;
        } else
        {
            view = null;
        }
        return view;
    }

    private void recycleView(View view, int i)
    {
        int j = wheel.getViewAdapter().getItemsCount();
        if((i < 0 || i >= j) && !wheel.isCyclic())
        {
            emptyItems = addView(view, emptyItems);
        } else
        {
            for(; i < 0; i += j);
            int _tmp = i % j;
            items = addView(view, items);
        }
    }

    public void clearAll()
    {
        if(items != null)
            items.clear();
        if(emptyItems != null)
            emptyItems.clear();
    }

    public View getEmptyItem()
    {
        return getCachedView(emptyItems);
    }

    public View getItem()
    {
        return getCachedView(items);
    }

    public int recycleItems(LinearLayout linearlayout, int i, ItemsRange itemsrange)
    {
        int j = i;
        int k = 0;
        while(k < linearlayout.getChildCount()) 
        {
            if(!itemsrange.contains(j))
            {
                recycleView(linearlayout.getChildAt(k), j);
                linearlayout.removeViewAt(k);
                if(k == 0)
                    i++;
            } else
            {
                k++;
            }
            j++;
        }
        return i;
    }

    private List emptyItems;
    private List items;
    private WheelView wheel;
}
