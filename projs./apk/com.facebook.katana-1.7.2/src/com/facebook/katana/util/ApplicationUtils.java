// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApplicationUtils.java

package com.facebook.katana.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.FqlGetPlaceById;

// Referenced classes of package com.facebook.katana.util:
//            Log, Toaster

public final class ApplicationUtils
{

    public ApplicationUtils()
    {
    }

    public static void OpenPageProfile(Context context, long l, FacebookProfile facebookprofile)
    {
        Intent intent = IntentUriHandler.getIntentForUri(context, (new StringBuilder()).append("fb://page/").append(l).toString());
        if(facebookprofile != null)
        {
            intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
            intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
        }
        context.startActivity(intent);
    }

    public static void OpenPlaceProfile(Context context, long l)
    {
        FqlGetPlaceById.loadPlaceById(context, l, new com.facebook.katana.binding.AppSessionListener.GetObjectListener() {

            public void onLoadError(Exception exception)
            {
                Object aobj[] = new Object[1];
                aobj[0] = exception.getMessage();
                Log.e("FacebookPlaces", String.format("Exception when loading place: %s", aobj));
                dlg.dismiss();
                Toaster.toast(ctx, 0x7f0a01b4);
            }

            public void onObjectLoaded(FacebookPlace facebookplace)
            {
                dlg.dismiss();
                ApplicationUtils.OpenPlaceProfile(ctx, facebookplace);
            }

            public volatile void onObjectLoaded(Object obj)
            {
                onObjectLoaded((FacebookPlace)obj);
            }

            final Context val$ctx;
            final ProgressDialog val$dlg;

            
            {
                dlg = progressdialog;
                ctx = context;
                super();
            }
        }
);
    }

    public static boolean OpenPlaceProfile(Context context, FacebookPlace facebookplace)
    {
        return OpenPlaceProfile(context, facebookplace, null);
    }

    public static boolean OpenPlaceProfile(Context context, FacebookPlace facebookplace, Integer integer)
    {
        if(facebookplace == null) goto _L2; else goto _L1
_L1:
        FacebookPage facebookpage = facebookplace.getPageInfo();
        if(facebookpage != null) goto _L3; else goto _L2
_L2:
        boolean flag = false;
_L5:
        return flag;
_L3:
        Object aobj[] = new Object[1];
        aobj[0] = Long.valueOf(facebookpage.mPageId);
        Intent intent = IntentUriHandler.getIntentForUri(context, String.format("fb://place/fw?pid=%d", aobj));
        intent.putExtra("extra_place", facebookplace);
        if(integer != null)
            intent.setFlags(integer.intValue());
        context.startActivity(intent);
        flag = true;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static void OpenProfile(Context context, int i, long l, FacebookProfile facebookprofile)
    {
        i;
        JVM INSTR tableswitch 0 2: default 28
    //                   0 29
    //                   1 39
    //                   2 49;
           goto _L1 _L2 _L3 _L4
_L1:
        return;
_L2:
        OpenUserProfile(context, l, facebookprofile);
        continue; /* Loop/switch isn't completed */
_L3:
        OpenPageProfile(context, l, facebookprofile);
        continue; /* Loop/switch isn't completed */
_L4:
        OpenPlaceProfile(context, l);
        if(true) goto _L1; else goto _L5
_L5:
    }

    public static void OpenSearch(Context context)
    {
        IntentUriHandler.handleUri(context, "fb://friends");
    }

    public static void OpenUserProfile(Context context, long l, FacebookProfile facebookprofile)
    {
        Intent intent = IntentUriHandler.getIntentForUri(context, (new StringBuilder()).append("fb://profile/").append(l).toString());
        if(facebookprofile != null)
        {
            intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
            intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
        }
        context.startActivity(intent);
    }

    public static void startDefaultActivity(Context context, Intent intent)
    {
        Intent intent1 = IntentUriHandler.getIntentForUri(context, "fb://root");
        intent1.addFlags(0x10000);
        context.startActivity(intent1);
        Intent intent2 = intent;
        if(intent2 == null)
            intent2 = IntentUriHandler.getIntentForUri(context, "fb://feed");
        context.startActivity(intent2);
    }
}
