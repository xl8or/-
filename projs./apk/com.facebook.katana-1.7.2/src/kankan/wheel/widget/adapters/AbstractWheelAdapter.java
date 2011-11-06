// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractWheelAdapter.java

package kankan.wheel.widget.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;

// Referenced classes of package kankan.wheel.widget.adapters:
//            WheelViewAdapter

public abstract class AbstractWheelAdapter
    implements WheelViewAdapter
{

    public AbstractWheelAdapter()
    {
    }

    public View getEmptyItem(View view, ViewGroup viewgroup)
    {
        return null;
    }

    protected void notifyDataChangedEvent()
    {
        if(datasetObservers != null)
        {
            for(Iterator iterator = datasetObservers.iterator(); iterator.hasNext(); ((DataSetObserver)iterator.next()).onChanged());
        }
    }

    protected void notifyDataInvalidatedEvent()
    {
        if(datasetObservers != null)
        {
            for(Iterator iterator = datasetObservers.iterator(); iterator.hasNext(); ((DataSetObserver)iterator.next()).onInvalidated());
        }
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
        if(datasetObservers == null)
            datasetObservers = new LinkedList();
        datasetObservers.add(datasetobserver);
    }

    public void unregisterDataSetObserver(DataSetObserver datasetobserver)
    {
        if(datasetObservers != null)
            datasetObservers.remove(datasetobserver);
    }

    private List datasetObservers;
}
