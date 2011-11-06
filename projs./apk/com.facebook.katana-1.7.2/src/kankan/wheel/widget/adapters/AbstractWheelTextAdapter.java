// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractWheelTextAdapter.java

package kankan.wheel.widget.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

// Referenced classes of package kankan.wheel.widget.adapters:
//            AbstractWheelAdapter

public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter
{

    protected AbstractWheelTextAdapter(Context context1)
    {
        this(context1, -1);
    }

    protected AbstractWheelTextAdapter(Context context1, int i)
    {
        this(context1, i, 0);
    }

    protected AbstractWheelTextAdapter(Context context1, int i, int j)
    {
        textColor = 0xff101010;
        textSize = 24;
        context = context1;
        itemResourceId = i;
        itemTextResourceId = j;
        inflater = (LayoutInflater)context1.getSystemService("layout_inflater");
    }

    private TextView getTextView(View view, int i)
    {
        TextView textview;
        textview = null;
        if(i != 0)
            break MISSING_BLOCK_LABEL_21;
        if(view instanceof TextView)
        {
            textview = (TextView)view;
            break MISSING_BLOCK_LABEL_59;
        }
        if(i != 0)
            textview = (TextView)view.findViewById(i);
        break MISSING_BLOCK_LABEL_59;
        ClassCastException classcastexception;
        classcastexception;
        Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
        throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", classcastexception);
        return textview;
    }

    private View getView(int i, ViewGroup viewgroup)
    {
        i;
        JVM INSTR tableswitch -1 0: default 24
    //                   -1 42
    //                   0 37;
           goto _L1 _L2 _L3
_L1:
        Object obj = inflater.inflate(i, viewgroup, false);
_L5:
        return ((View) (obj));
_L3:
        obj = null;
        continue; /* Loop/switch isn't completed */
_L2:
        obj = new TextView(context);
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void configureTextView(TextView textview)
    {
        textview.setTextColor(textColor);
        textview.setGravity(17);
        textview.setTextSize(textSize);
        textview.setLines(1);
        textview.setTypeface(Typeface.SANS_SERIF, 1);
    }

    public View getEmptyItem(View view, ViewGroup viewgroup)
    {
        if(view == null)
            view = getView(emptyItemResourceId, viewgroup);
        if(emptyItemResourceId == -1 && (view instanceof TextView))
            configureTextView((TextView)view);
        return view;
    }

    public int getEmptyItemResource()
    {
        return emptyItemResourceId;
    }

    public View getItem(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        if(i >= 0 && i < getItemsCount())
        {
            if(view == null)
                view = getView(itemResourceId, viewgroup);
            TextView textview = getTextView(view, itemTextResourceId);
            if(textview != null)
            {
                Object obj = getItemText(i);
                if(obj == null)
                    obj = "";
                textview.setText(((CharSequence) (obj)));
                if(itemResourceId == -1)
                    configureTextView(textview);
            }
            view1 = view;
        } else
        {
            view1 = null;
        }
        return view1;
    }

    public int getItemResource()
    {
        return itemResourceId;
    }

    protected abstract CharSequence getItemText(int i);

    public int getItemTextResource()
    {
        return itemTextResourceId;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public int getTextSize()
    {
        return textSize;
    }

    public void setEmptyItemResource(int i)
    {
        emptyItemResourceId = i;
    }

    public void setItemResource(int i)
    {
        itemResourceId = i;
    }

    public void setItemTextResource(int i)
    {
        itemTextResourceId = i;
    }

    public void setTextColor(int i)
    {
        textColor = i;
    }

    public void setTextSize(int i)
    {
        textSize = i;
    }

    public static final int DEFAULT_TEXT_COLOR = 0xff101010;
    public static final int DEFAULT_TEXT_SIZE = 24;
    public static final int LABEL_COLOR = 0xff700070;
    protected static final int NO_RESOURCE = 0;
    public static final int TEXT_VIEW_ITEM_RESOURCE = -1;
    protected Context context;
    protected int emptyItemResourceId;
    protected LayoutInflater inflater;
    protected int itemResourceId;
    protected int itemTextResourceId;
    private int textColor;
    private int textSize;
}
