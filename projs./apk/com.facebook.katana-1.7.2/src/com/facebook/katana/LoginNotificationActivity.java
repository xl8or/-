// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginNotificationActivity.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;

// Referenced classes of package com.facebook.katana:
//            PasswordDialogActivity, LoginActivity

public class LoginNotificationActivity extends Activity
{

    public LoginNotificationActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Intent intent = getIntent();
        AppSession appsession = AppSession.getActiveSession(this, false);
        Intent intent1 = new Intent(intent);
        Object obj;
        if(appsession != null)
            obj = com/facebook/katana/PasswordDialogActivity;
        else
            obj = com/facebook/katana/LoginActivity;
        intent1.setClass(this, ((Class) (obj)));
        startActivity(intent1);
        finish();
    }
}
