// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlAboutActivity.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.IOException;
import java.io.InputStream;

public class HtmlAboutActivity extends Activity
{

    public HtmlAboutActivity()
    {
    }

    public String getContentFromResource(int i)
        throws IOException
    {
        InputStream inputstream = getResources().openRawResource(i);
        byte abyte0[] = new byte[inputstream.available()];
        inputstream.read(abyte0);
        inputstream.close();
        return new String(abyte0, "UTF-8");
    }

    public void onCreate(Bundle bundle)
    {
        requestWindowFeature(3);
        super.onCreate(bundle);
        setContentView(0x7f030033);
        setFeatureDrawableResource(3, 0x7f0200df);
        WebView webview = (WebView)findViewById(0x7f0e00a2);
        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView webview1, String s)
            {
                if(s.equals("/license")) goto _L2; else goto _L1
_L1:
                boolean flag;
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s)));
                flag = true;
_L4:
                return flag;
_L2:
                webview1.loadDataWithBaseURL(null, getContentFromResource(0x7f060002), "text/txt", "utf-8", null);
                findViewById(0x7f0e00a3).setVisibility(8);
                flag = true;
                continue; /* Loop/switch isn't completed */
                IOException ioexception1;
                ioexception1;
                flag = false;
                if(true) goto _L4; else goto _L3
_L3:
            }

            final HtmlAboutActivity this$0;

            
            {
                this$0 = HtmlAboutActivity.this;
                super();
            }
        }
);
        try
        {
            PackageInfo packageinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            webview.loadDataWithBaseURL(null, getContentFromResource(0x7f060000).replaceFirst("[0-9]+.[0-9]+.[0-9]+", packageinfo.versionName), "text/html", "utf-8", null);
        }
        catch(IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
        {
            throw new RuntimeException(namenotfoundexception);
        }
        findViewById(0x7f0e00a3).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/terms.php")));
            }

            final HtmlAboutActivity this$0;

            
            {
                this$0 = HtmlAboutActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e00a4).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }

            final HtmlAboutActivity this$0;

            
            {
                this$0 = HtmlAboutActivity.this;
                super();
            }
        }
);
    }
}
