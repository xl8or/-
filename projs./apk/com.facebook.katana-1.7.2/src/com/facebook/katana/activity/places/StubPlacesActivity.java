// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StubPlacesActivity.java

package com.facebook.katana.activity.places;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.ApplicationUtils;

public class StubPlacesActivity extends BaseFacebookActivity
{

    public StubPlacesActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Intent intent = getIntent();
        FacebookPlace facebookplace = (FacebookPlace)intent.getParcelableExtra("extra_place");
        if(facebookplace != null)
        {
            FacebookPage facebookpage = facebookplace.getPageInfo();
            Intent intent1 = new Intent(this, com/facebook/katana/ProfileTabHostActivity);
            intent1.putExtra("extra_user_id", facebookpage.mPageId);
            intent1.putExtra("extra_user_display_name", facebookplace.mName);
            intent1.putExtra("extra_image_url", facebookpage.mPicSmall);
            intent1.putExtra("extra_user_type", 2);
            intent1.putExtra("extra_place", facebookplace);
            intent1.addFlags(0x10000);
            startActivity(intent1);
            finish();
        } else
        {
            ApplicationUtils.OpenPlaceProfile(this, intent.getLongExtra("extra_user_id", -1L));
        }
    }
}
