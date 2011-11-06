// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookActivityDelegate.java

package com.facebook.katana.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.facebook.katana.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.service.method.PerfLogging;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity:
//            FacebookActivity

public class FacebookActivityDelegate
    implements FacebookActivity
{

    public FacebookActivityDelegate(Activity activity)
    {
        mTransitioningToBackground = false;
        mActivity = activity;
        if(!$assertionsDisabled && !(activity instanceof FacebookActivity))
            throw new AssertionError();
        else
            return;
    }

    public boolean facebookOnBackPressed()
    {
        mActivity.finish();
        return true;
    }

    public void finish()
    {
        if(!(mActivity instanceof HomeActivity))
            mTransitioningToBackground = false;
    }

    public long getActivityId()
    {
        return mActivityId;
    }

    protected String getTag()
    {
        Intent intent = mActivity.getIntent();
        Class class1 = mActivity.getClass();
        String s = (String)TAGS.get(class1);
        if(s == null)
        {
            String s1;
            if(intent.getBooleanExtra("within_tab", false))
                s1 = (new StringBuilder()).append(intent.getStringExtra("extra_parent_tag")).append("|").toString();
            else
                s1 = "";
            s = (new StringBuilder()).append(s1).append(Utils.getClassName(class1)).toString();
            TAGS.put(class1, s);
        }
        return s;
    }

    protected void hideSearchButton()
    {
        ((ImageButton)mActivity.findViewById(0x7f0e0068)).setVisibility(8);
    }

    public boolean isOnTop()
    {
        return mOnTop;
    }

    protected void launchComposer(Uri uri, Bundle bundle, Integer integer, long l)
    {
        Intent intent = new Intent(mActivity, com/facebook/katana/ComposerActivity);
        if(uri != null)
            intent.setData(uri);
        if(bundle != null)
            intent.putExtras(bundle);
        if(l != -1L && l != AppSession.getActiveSession(mActivity, false).getSessionInfo().userId)
            intent.putExtra("extra_fixed_audience_id", l);
        if(integer != null)
            mActivity.startActivityForResult(intent, integer.intValue());
        else
            mActivity.startActivity(intent);
    }

    protected void logStepDataReceived()
    {
        PerfLogging.logStep(mActivity, com.facebook.katana.service.method.PerfLogging.Step.DATA_RECEIVED, getTag(), mActivityId);
    }

    protected void logStepDataRequested()
    {
        PerfLogging.logStep(mActivity, com.facebook.katana.service.method.PerfLogging.Step.DATA_REQUESTED, getTag(), mActivityId);
    }

    public void onContentChanged()
    {
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ((FacebookActivity)mActivity).titleBarPrimaryActionClickHandler(view);
            }

            final FacebookActivityDelegate this$0;

            
            {
                this$0 = FacebookActivityDelegate.this;
                super();
            }
        }
;
        ImageButton imagebutton = (ImageButton)mActivity.findViewById(0x7f0e003e);
        Button button = (Button)mActivity.findViewById(0x7f0e003d);
        if(imagebutton != null)
            imagebutton.setOnClickListener(onclicklistener);
        if(button != null)
            button.setOnClickListener(onclicklistener);
        ImageButton imagebutton1 = (ImageButton)mActivity.findViewById(0x7f0e0144);
        if(imagebutton1 != null)
            imagebutton1.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    ((FacebookActivity)mActivity).titleBarClickHandler(view);
                }

                final FacebookActivityDelegate this$0;

            
            {
                this$0 = FacebookActivityDelegate.this;
                super();
            }
            }
);
        ImageButton imagebutton2 = (ImageButton)mActivity.findViewById(0x7f0e0068);
        if(imagebutton2 != null)
            imagebutton2.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    ((FacebookActivity)mActivity).titleBarSearchClickHandler(view);
                }

                final FacebookActivityDelegate this$0;

            
            {
                this$0 = FacebookActivityDelegate.this;
                super();
            }
            }
);
    }

    protected void onCreate(Bundle bundle)
    {
        mActivityId = Utils.RNG.nextInt();
        PerfLogging.logStep(mActivity, com.facebook.katana.service.method.PerfLogging.Step.ONCREATE, getTag(), mActivityId);
    }

    public Boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(i != 4) goto _L2; else goto _L1
_L1:
        if(!PlatformUtils.isEclairOrLater()) goto _L4; else goto _L3
_L3:
        if(!EclairKeyHandler.onKeyDown(keyevent)) goto _L2; else goto _L5
_L5:
        Boolean boolean1 = Boolean.valueOf(true);
_L7:
        return boolean1;
_L4:
        if(((FacebookActivity)mActivity).facebookOnBackPressed())
        {
            boolean1 = Boolean.valueOf(true);
            continue; /* Loop/switch isn't completed */
        }
_L2:
        boolean1 = null;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public Boolean onKeyUp(int i, KeyEvent keyevent)
    {
        Boolean boolean1;
        if(i == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(keyevent) && ((FacebookActivity)mActivity).facebookOnBackPressed())
            boolean1 = Boolean.valueOf(true);
        else
            boolean1 = null;
        return boolean1;
    }

    protected void onPause()
    {
        FacebookService.pause(mActivity.getIntent().getBooleanExtra("within_tab", false), mTransitioningToBackground, mActivity);
        mOnTop = false;
    }

    protected void onResume()
    {
        mTransitioningToBackground = FacebookService.resume(mActivity.getIntent().getBooleanExtra("within_tab", false), mActivity);
        PerfLogging.logStep(mActivity, com.facebook.katana.service.method.PerfLogging.Step.ONRESUME, getTag(), mActivityId);
        PerfLogging.logPageView(mActivity, getTag(), mActivityId);
        mOnTop = true;
    }

    public boolean onSearchRequested()
    {
        ApplicationUtils.OpenSearch(mActivity);
        return true;
    }

    protected void setListLoading(boolean flag)
    {
        int i;
        byte byte0;
        if(flag)
            i = 0;
        else
            i = 8;
        if(flag)
            byte0 = 8;
        else
            byte0 = 0;
        mActivity.findViewById(0x7f0e0057).setVisibility(i);
        mActivity.findViewById(0x7f0e0056).setVisibility(byte0);
    }

    protected void setPrimaryActionFace(int i, String s)
    {
        ImageButton imagebutton = (ImageButton)mActivity.findViewById(0x7f0e003e);
        Button button = (Button)mActivity.findViewById(0x7f0e003d);
        if(i < 0)
        {
            imagebutton.setVisibility(8);
        } else
        {
            imagebutton.setImageResource(i);
            imagebutton.setVisibility(0);
        }
        if(s == null)
        {
            button.setVisibility(8);
        } else
        {
            button.setText(s);
            button.setVisibility(0);
        }
    }

    protected void setPrimaryActionIcon(int i)
    {
        setPrimaryActionFace(i, null);
    }

    public void setTransitioningToBackground(boolean flag)
    {
        mTransitioningToBackground = flag;
    }

    public void startActivity(Intent intent)
    {
        mTransitioningToBackground = FacebookService.processActivityChange(intent);
    }

    public void startActivityForResult(Intent intent, int i)
    {
        mTransitioningToBackground = FacebookService.processActivityChange(intent);
    }

    public void titleBarClickHandler(View view)
    {
        Intent intent = IntentUriHandler.getIntentForUri(mActivity, "fb://root");
        intent.setFlags(0x4000000);
        mActivity.startActivity(intent);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        throw new IllegalStateException("titleBarPrimaryActionClickHandler has no operation.");
    }

    public void titleBarSearchClickHandler(View view)
    {
        IntentUriHandler.handleUri(mActivity, "fb://search");
    }

    static final boolean $assertionsDisabled;
    protected static Map TAGS = Collections.synchronizedMap(new HashMap());
    private Activity mActivity;
    protected long mActivityId;
    private boolean mOnTop;
    public boolean mTransitioningToBackground;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/activity/FacebookActivityDelegate.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }

}
