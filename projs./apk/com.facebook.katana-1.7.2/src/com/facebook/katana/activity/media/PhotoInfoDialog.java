// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoInfoDialog.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.StringUtils;

public class PhotoInfoDialog
{

    public PhotoInfoDialog()
    {
    }

    public static Dialog create(Context context, FacebookPhoto facebookphoto)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setIcon(0x108009b);
        builder.setTitle(context.getString(0x7f0a012d));
        View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(0x7f030001, null);
        builder.setView(view);
        fillView(context, view, facebookphoto);
        builder.setPositiveButton(context.getString(0x7f0a00dd), null);
        return builder.create();
    }

    private static void fillView(Context context, View view, FacebookPhoto facebookphoto)
    {
        ImageView imageview = (ImageView)view.findViewById(0x7f0e0004);
        byte abyte0[] = facebookphoto.getImageBytes();
        if(abyte0 != null)
        {
            android.graphics.Bitmap bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
            if(bitmap != null)
                imageview.setImageBitmap(bitmap);
        } else
        {
            imageview.setImageResource(0x7f020100);
        }
        ((TextView)view.findViewById(0x7f0e0005)).setText(facebookphoto.getCaption());
        ((TextView)view.findViewById(0x7f0e0007)).setText(StringUtils.getTimeAsString(context, com.facebook.katana.util.StringUtils.TimeFormatStyle.MONTH_DAY_YEAR_STYLE, facebookphoto.getCreatedMs()));
        view.findViewById(0x7f0e0008).setVisibility(8);
        view.findViewById(0x7f0e0009).setVisibility(8);
        view.findViewById(0x7f0e000a).setVisibility(8);
        view.findViewById(0x7f0e000b).setVisibility(8);
    }

    public static void update(Dialog dialog, FacebookPhoto facebookphoto)
    {
        fillView(dialog.getContext(), dialog.findViewById(0x7f0e0002), facebookphoto);
    }
}
