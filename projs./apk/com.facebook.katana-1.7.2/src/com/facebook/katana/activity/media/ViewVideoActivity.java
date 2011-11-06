// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViewVideoActivity.java

package com.facebook.katana.activity.media;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.util.StringUtils;
import java.util.List;

public class ViewVideoActivity extends BaseFacebookActivity
{

    public ViewVideoActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        String s = getIntent().getStringExtra("href");
        if(s != null)
        {
            Intent intent = new Intent("android.intent.action.VIEW");
            Uri uri = Uri.parse(s);
            if(StringUtils.saneStringEquals(uri.getScheme(), "https"))
                uri = uri.buildUpon().scheme("http").build();
            intent.setDataAndType(uri, "video/*");
            if(getPackageManager().queryIntentActivities(intent, 0).size() > 0)
                startActivity(intent);
        }
        finish();
    }

    public static final String EXTRA_HREF = "href";
}
