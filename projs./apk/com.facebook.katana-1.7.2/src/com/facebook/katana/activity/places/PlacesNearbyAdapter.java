// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesNearbyAdapter.java

package com.facebook.katana.activity.places;

import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.StringUtils;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.places:
//            PlacesNearbyActivity

public class PlacesNearbyAdapter extends BaseAdapter
{

    public PlacesNearbyAdapter(PlacesNearbyActivity placesnearbyactivity, List list)
    {
        mAddPlaceString = "";
        mPlacesNearbyActivity = placesnearbyactivity;
        mPlaces = list;
    }

    public int getCount()
    {
        int i = mPlaces.size();
        int j;
        if(mAddPlaceVisible)
            j = 1;
        else
            j = 0;
        return i + j;
    }

    public Object getItem(int i)
    {
        Object obj;
        if(i == mPlaces.size())
            obj = null;
        else
            obj = mPlaces.get(i);
        return obj;
    }

    public long getItemId(int i)
    {
        long l;
        if(i == mPlaces.size())
            l = -1L;
        else
            l = ((FacebookPlace)getItem(i)).mPageId;
        return l;
    }

    public int getItemViewType(int i)
    {
        int j;
        if(i >= mPlaces.size())
            j = 1;
        else
            j = 0;
        return j;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(i == mPlaces.size() && mAddPlaceVisible)
        {
            if(view1 == null)
                view1 = ((LayoutInflater)mPlacesNearbyActivity.getSystemService("layout_inflater")).inflate(0x7f030000, null);
            ((TextView)view1.findViewById(0x7f0e0001)).setText(mAddPlaceString);
        } else
        {
            if(view1 == null)
                view1 = ((LayoutInflater)mPlacesNearbyActivity.getSystemService("layout_inflater")).inflate(0x7f03005b, null);
            FacebookPlace facebookplace = (FacebookPlace)getItem(i);
            ((TextView)view1.findViewById(0x7f0e00ed)).setText(facebookplace.mName);
            TextView textview = (TextView)view1.findViewById(0x7f0e00ee);
            if(StringUtils.isBlank(facebookplace.mDisplaySubtext))
            {
                textview.setVisibility(8);
            } else
            {
                textview.setVisibility(0);
                textview.setText(facebookplace.mDisplaySubtext);
            }
            if(facebookplace.getDealInfo() != null)
                view1.findViewById(0x7f0e00f0).setVisibility(0);
            else
                view1.findViewById(0x7f0e00f0).setVisibility(8);
        }
        return view1;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public void setAddPlaceString(String s)
    {
        mAddPlaceString = s;
    }

    public void setAddPlaceVisibility(boolean flag)
    {
        if(flag != mAddPlaceVisible)
        {
            mAddPlaceVisible = flag;
            notifyDataSetChanged();
        }
    }

    public void setList(List list)
    {
        mPlaces = list;
        notifyDataSetChanged();
    }

    public static final int ADD_PLACE_BUTTON_ID = -1;
    private String mAddPlaceString;
    private boolean mAddPlaceVisible;
    private List mPlaces;
    private PlacesNearbyActivity mPlacesNearbyActivity;
}
