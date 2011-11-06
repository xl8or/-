// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbumInfoDialog.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.StringUtils;

public class AlbumInfoDialog
{

    public AlbumInfoDialog()
    {
    }

    public static Dialog create(Context context, FacebookAlbum facebookalbum)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setIcon(0x108009b);
        builder.setTitle(context.getString(0x7f0a000e));
        View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(0x7f030001, null);
        builder.setView(view);
        fillView(context, view, facebookalbum);
        builder.setPositiveButton(context.getString(0x7f0a00dd), null);
        return builder.create();
    }

    private static void fillView(Context context, View view, FacebookAlbum facebookalbum)
    {
        ImageView imageview = (ImageView)view.findViewById(0x7f0e0004);
        byte abyte0[] = facebookalbum.getImageBytes();
        if(abyte0 != null)
        {
            android.graphics.Bitmap bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
            if(bitmap != null)
                imageview.setImageBitmap(bitmap);
        } else
        {
            imageview.setImageResource(0x7f020100);
        }
        ((TextView)view.findViewById(0x7f0e0005)).setText(facebookalbum.getName());
        ((TextView)view.findViewById(0x7f0e0007)).setText(StringUtils.getTimeAsString(context, com.facebook.katana.util.StringUtils.TimeFormatStyle.MONTH_DAY_YEAR_STYLE, 1000L * facebookalbum.getCreated()));
        ((TextView)view.findViewById(0x7f0e0009)).setText(facebookalbum.getDescription());
        ((TextView)view.findViewById(0x7f0e000b)).setText(facebookalbum.getLocation());
    }

    public static void update(Dialog dialog, FacebookAlbum facebookalbum)
    {
        fillView(dialog.getContext(), dialog.findViewById(0x7f0e0002), facebookalbum);
    }
}
