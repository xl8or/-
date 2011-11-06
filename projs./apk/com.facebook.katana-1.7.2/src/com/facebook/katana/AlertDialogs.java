// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlertDialogs.java

package com.facebook.katana;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.view.*;
import android.widget.*;

public class AlertDialogs
{

    public AlertDialogs()
    {
    }

    public static AlertDialog createAlert(Context context, String s, int i, String s1, String s2, android.content.DialogInterface.OnClickListener onclicklistener, String s3, android.content.DialogInterface.OnClickListener onclicklistener1, 
            android.content.DialogInterface.OnCancelListener oncancellistener, boolean flag)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(s);
        if(i != 0)
            builder.setIcon(context.getResources().getDrawable(i));
        builder.setMessage(s1);
        builder.setPositiveButton(s2, onclicklistener);
        builder.setNegativeButton(s3, onclicklistener1);
        builder.setOnCancelListener(oncancellistener);
        builder.setCancelable(flag);
        return builder.create();
    }

    public static AlertDialog showSubMenu(final Context context, String s, final String texts[], final int icons[], android.content.DialogInterface.OnClickListener onclicklistener, android.content.DialogInterface.OnCancelListener oncancellistener)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        if(s != null)
            builder.setTitle(s);
        builder.setAdapter(new ListAdapter() {

            public boolean areAllItemsEnabled()
            {
                return true;
            }

            public int getCount()
            {
                return texts.length;
            }

            public Object getItem(int i)
            {
                return Integer.valueOf(i);
            }

            public long getItemId(int i)
            {
                return (long)i;
            }

            public int getItemViewType(int i)
            {
                return 0;
            }

            public View getView(int i, View view, ViewGroup viewgroup)
            {
                View view1;
                if(view == null)
                    view1 = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(0x7f03003b, null);
                else
                    view1 = view;
                if(icons != null)
                    ((ImageView)view1.findViewById(0x7f0e00ae)).setImageResource(icons[i]);
                else
                    ((ImageView)view1.findViewById(0x7f0e00ae)).setImageBitmap(null);
                ((TextView)view1.findViewById(0x7f0e00af)).setText(texts[i]);
                return view1;
            }

            public int getViewTypeCount()
            {
                return 1;
            }

            public boolean hasStableIds()
            {
                return true;
            }

            public boolean isEmpty()
            {
                return false;
            }

            public boolean isEnabled(int i)
            {
                return true;
            }

            public void registerDataSetObserver(DataSetObserver datasetobserver)
            {
            }

            public void unregisterDataSetObserver(DataSetObserver datasetobserver)
            {
            }

            final Context val$context;
            final int val$icons[];
            final String val$texts[];

            
            {
                texts = as;
                context = context1;
                icons = ai;
                super();
            }
        }
, onclicklistener);
        if(oncancellistener != null)
            builder.setOnCancelListener(oncancellistener);
        return builder.create();
    }
}
