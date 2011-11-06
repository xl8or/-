// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacewebChromeActivity.java

package com.facebook.katana.activity.faceweb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.activity.media.MediaUploader;
import com.facebook.katana.activity.messages.MessageComposeActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.dialog.Dialogs;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.composer.ComposerUserSettings;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.*;
import com.facebook.katana.ui.TaggingAutoCompleteTextView;
import com.facebook.katana.util.*;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.webview.*;
import java.io.IOException;
import java.util.*;
import kankan.wheel.widget.*;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.*;

// Referenced classes of package com.facebook.katana.activity.faceweb:
//            SetNavBarTitleHandler, AppLogHandler

public class FacewebChromeActivity extends BaseFacebookActivity
    implements TabProgressSource, com.facebook.katana.util.FBLocationManager.FBLocationListener
{
    protected class FacewebAppSessionListener extends AppSessionListener
    {

        public void onCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
        {
            if(s.equals(mCheckinReqId))
            {
                mCheckinReqId = null;
                if(i == 200)
                {
                    JSONObject jsonobject = new JSONObject();
                    ArrayList arraylist;
                    try
                    {
                        jsonobject.put("action", "didCheckin");
                        jsonobject.put("checkin_id", facebookpost.postId);
                    }
                    catch(JSONException jsonexception)
                    {
                        Log.e(getTag(), "inconceivable exception", jsonexception);
                    }
                    arraylist = new ArrayList();
                    arraylist.add(jsonobject);
                    showDialog(2);
                    mWv.executeJsFunction(mPublisherCallback, arraylist, new ShareHandler());
                } else
                {
                    String s2 = StringUtils.getErrorString(FacewebChromeActivity.this, getString(0x7f0a0240), i, s1, exception);
                    Toaster.toast(FacewebChromeActivity.this, s2);
                }
            }
        }

        public void onGkSettingsGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, boolean flag)
        {
            if(i == 200 && "meta_composer".equals(s2) && flag)
                mIsInElder = true;
        }

        public void onLoginComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            mWv.reset();
        }

        public void onPhotoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, FacebookPhoto facebookphoto, 
                String s2, long l, long l1, long l2)
        {
            if(l1 == mProfileId)
                mWv.refresh();
        }

        final FacewebChromeActivity this$0;

        protected FacewebAppSessionListener()
        {
            this$0 = FacewebChromeActivity.this;
            super();
        }
    }

    private class NewsFeedToggleOptionsAdapter extends AbstractWheelTextAdapter
    {

        protected CharSequence getItemText(int i)
        {
            return ((NewsFeedToggleOption)mOptions.get(i)).title;
        }

        public int getItemsCount()
        {
            return mOptions.size();
        }

        private List mOptions;
        final FacewebChromeActivity this$0;


        protected NewsFeedToggleOptionsAdapter(Context context, List list)
        {
            this$0 = FacewebChromeActivity.this;
            super(context, 0x7f030049, 0);
            mOptions = list;
            setItemTextResource(0x7f0e00d3);
        }
    }

    protected class ShowPickerView extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        private void dismissToggle(FacebookWebView facebookwebview)
        {
            findViewById(0x7f0e006d).setVisibility(8);
            facebookwebview.executeJs(dismissScript, null);
        }

        public void handleUI(final FacebookWebView wv, FacebookRpcCall facebookrpccall)
        {
            dismissScript = facebookrpccall.getParameterByName("dismiss_script");
            shouldDismissOnScroll = Boolean.valueOf(false);
            String s = new String(facebookrpccall.getParameterByName("options"));
            JsonParser jsonparser = (new FBJsonFactory()).createJsonParser(s);
            jsonparser.nextToken();
            List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/NewsFeedToggleOption);
            View view = findViewById(0x7f0e006d);
            view.setVisibility(0);
            view.bringToFront();
            WheelView wheelview = (WheelView)findViewById(0x7f0e006f);
            wheelview.setVisibleItems(Math.min(5, list.size()));
            wheelview.setViewAdapter(new NewsFeedToggleOptionsAdapter(FacewebChromeActivity.this, list));
            wheelview.setCurrentItem(Integer.parseInt(facebookrpccall.getParameterByName("selected_index")));
            wheelview.addClickingListener(new OnWheelClickedListener() {

                public void onItemClicked(WheelView wheelview1, int i)
                {
                    if(wheelview1.getCurrentItem() != i)
                    {
                        wheelview1.setCurrentItem(i, true);
                        wv.executeJs(((NewsFeedToggleOption)((NewsFeedToggleOptionsAdapter)wheelview1.getViewAdapter()).mOptions.get(i)).script, null);
                        shouldDismissOnScroll = Boolean.valueOf(true);
                    } else
                    {
                        dismissToggle(wv);
                    }
                }

                final ShowPickerView this$1;
                final FacebookWebView val$wv;

                
                {
                    this$1 = ShowPickerView.this;
                    wv = facebookwebview;
                    super();
                }
            }
);
            wheelview.addScrollingListener(new OnWheelScrollListener() {

                public void onScrollingFinished(WheelView wheelview1)
                {
                    wv.executeJs(((NewsFeedToggleOption)((NewsFeedToggleOptionsAdapter)wheelview1.getViewAdapter()).mOptions.get(wheelview1.getCurrentItem())).script, null);
                    if(shouldDismissOnScroll.booleanValue())
                    {
                        shouldDismissOnScroll = Boolean.valueOf(false);
                        dismissToggle(wv);
                    }
                }

                public void onScrollingStarted(WheelView wheelview1)
                {
                }

                final ShowPickerView this$1;
                final FacebookWebView val$wv;

                
                {
                    this$1 = ShowPickerView.this;
                    wv = facebookwebview;
                    super();
                }
            }
);
            findViewById(0x7f0e006e).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    dismissToggle(wv);
                }

                final ShowPickerView this$1;
                final FacebookWebView val$wv;

                
                {
                    this$1 = ShowPickerView.this;
                    wv = facebookwebview;
                    super();
                }
            }
);
_L1:
            return;
            JsonParseException jsonparseexception;
            jsonparseexception;
            Log.e(getTag(), "received bad faceweb data", jsonparseexception);
              goto _L1
            IOException ioexception;
            ioexception;
            Log.e(getTag(), "received bad faceweb data", ioexception);
              goto _L1
            JMException jmexception;
            jmexception;
            Log.e(getTag(), "received bad faceweb data", jmexception);
              goto _L1
        }

        private static final String PARAM_KEY_DISMISS_SCRIPT = "dismiss_script";
        private static final String PARAM_KEY_OPTIONS = "options";
        private static final String PARAM_KEY_SELECTED_INDEX = "selected_index";
        protected String dismissScript;
        protected Boolean shouldDismissOnScroll;
        final FacewebChromeActivity this$0;


        public ShowPickerView(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class DismissModalDialog extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            ComposerUserSettings.setSetting(FacewebChromeActivity.this, "composer_tour_completed", "1");
            ComposerUserSettings.get(FacewebChromeActivity.this, "composer_share_location");
            finish();
        }

        private static final String PARAM_ANIMATED = "animated";
        final FacewebChromeActivity this$0;

        public DismissModalDialog(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class SetNavBarHiddenHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            byte byte0;
            if(Boolean.valueOf(facebookrpccall.getParameterByName("hidden")).booleanValue())
                byte0 = 8;
            else
                byte0 = 0;
            findViewById(0x7f0e0016).setVisibility(byte0);
        }

        private static final String PARAM_ANIMATED = "animated";
        private static final String PARAM_KEY_HIDDEN = "hidden";
        final FacewebChromeActivity this$0;

        public SetNavBarHiddenHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class SetNavBarButton extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {
        class NavBarButtonHandler
            implements android.view.View.OnClickListener
        {

            public void onClick(View view)
            {
                mWebview.executeJs(mNavButtonCallback, null);
            }

            protected FacebookWebView mWebview;
            final SetNavBarButton this$1;

            public NavBarButtonHandler(FacebookWebView facebookwebview)
            {
                this$1 = SetNavBarButton.this;
                super();
                mWebview = facebookwebview;
            }
        }


        public int getIconForType(String s)
        {
            int i;
            if(s.equals("compose"))
                i = 0x7f0200fa;
            else
                i = -1;
            return i;
        }

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mNavButtonCallback = FacewebChromeActivity.getStringParam(facebookrpccall, "script");
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            if(!"true".equals(facebookrpccall.getParameterByName("isDisabled")) && "right".equals(facebookrpccall.getParameterByName("position")))
            {
                String s = facebookrpccall.getParameterByName("title");
                int i = getIconForType(facebookrpccall.getParameterByName("type"));
                if(i >= 0)
                {
                    setPrimaryActionFace(i, null);
                    ((ImageButton)findViewById(0x7f0e003e)).setOnClickListener(new NavBarButtonHandler(facebookwebview));
                } else
                {
                    setPrimaryActionFace(-1, s);
                    ((Button)findViewById(0x7f0e003d)).setOnClickListener(new NavBarButtonHandler(facebookwebview));
                }
            }
        }

        private static final String BUTTON_TYPE_COMPOSE = "compose";
        private static final String PARAM_KEY_ANIMATED = "animated";
        private static final String PARAM_KEY_IS_DISABLED = "isDisabled";
        private static final String PARAM_KEY_POSITION = "position";
        private static final String PARAM_KEY_SCRIPT = "script";
        private static final String PARAM_KEY_TITLE = "title";
        private static final String PARAM_KEY_TYPE = "type";
        final FacewebChromeActivity this$0;

        public SetNavBarButton(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    private class UpdateNativeLoadingIndicator extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mLoading = facebookrpccall.method.equals("pageLoading");
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            int i;
            FacewebChromeActivity facewebchromeactivity;
            com.facebook.katana.service.method.PerfLogging.Step step;
            if(mLoading)
                i = 0;
            else
                i = 8;
            if(mProgressListener != null)
                mProgressListener.onShowProgress(mLoading);
            findViewById(0x7f0e0011).setVisibility(i);
            mShowingProgress = mLoading;
            facewebchromeactivity = FacewebChromeActivity.this;
            if(mLoading)
                step = com.facebook.katana.service.method.PerfLogging.Step.DATA_REQUESTED;
            else
                step = com.facebook.katana.service.method.PerfLogging.Step.DATA_RECEIVED;
            PerfLogging.logStep(facewebchromeactivity, step, getTag(), getActivityId(), mHref);
        }

        final FacewebChromeActivity this$0;

        public UpdateNativeLoadingIndicator(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class ShowReplyPublisherHandler extends ShowTextPublisherHandler
    {

        public void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1)
        {
            removeDialog(4);
            if(flag)
                Toaster.toast(FacewebChromeActivity.this, 0x7f0a01ac);
            else
                super.handle(facebookwebview, s, flag, s1);
        }

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mReplyCallback = facebookrpccall.getParameterByName("callback");
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            super.handleUI(facebookwebview, facebookrpccall);
            ((EditText)findViewById(0x7f0e0076)).setHint(0x7f0a00b9);
            ((Button)findViewById(0x7f0e0077)).setText(0x7f0a00bc);
        }

        protected void submitText(FacebookWebView facebookwebview, TextView textview)
        {
            super.submitText(facebookwebview, textview);
            JSONObject jsonobject = new JSONObject();
            ArrayList arraylist;
            try
            {
                jsonobject.put("text", textview.getText().toString().trim());
            }
            catch(JSONException jsonexception)
            {
                Log.e(getTag(), (new StringBuilder()).append("inconceivable exception ").append(jsonexception.toString()).toString());
            }
            arraylist = new ArrayList();
            arraylist.add(jsonobject);
            showDialog(4);
            facebookwebview.executeJsFunction(mReplyCallback, arraylist, this);
        }

        String mReplyCallback;
        final FacewebChromeActivity this$0;

        public ShowReplyPublisherHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class ShowCommentPublisherHandler extends ShowTextPublisherHandler
    {

        public void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1)
        {
            removeDialog(3);
            if(flag)
                Toaster.toast(FacewebChromeActivity.this, 0x7f0a01ac);
            else
                super.handle(facebookwebview, s, flag, s1);
        }

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mCommentCallback = facebookrpccall.getParameterByName("callback");
            mPostId = facebookrpccall.getParameterByName("post_id");
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            super.handleUI(facebookwebview, facebookrpccall);
            AppSessionListener appsessionlistener = ((TaggingAutoCompleteTextView)(EditText)findViewById(0x7f0e0076)).configureView(FacewebChromeActivity.this, mAppSession.getUserImagesCache());
            if(isOnTop())
                mAppSession.addListener(appsessionlistener);
            mAppSessionListeners.add(appsessionlistener);
        }

        protected void submitText(FacebookWebView facebookwebview, TextView textview)
        {
            super.submitText(facebookwebview, textview);
            String s = Utils.convertMentionTags(((EditText)textview).getEditableText());
            if(s.length() > 0)
            {
                JSONObject jsonobject = new JSONObject();
                ArrayList arraylist;
                try
                {
                    jsonobject.put("text", s);
                    jsonobject.put("post_id", mPostId);
                }
                catch(JSONException jsonexception)
                {
                    Log.e(getTag(), "inconceivable exception", jsonexception);
                }
                arraylist = new ArrayList();
                arraylist.add(jsonobject);
                showDialog(3);
                facebookwebview.executeJsFunction(mCommentCallback, arraylist, this);
            }
        }

        protected String mCommentCallback;
        protected String mPostId;
        final FacewebChromeActivity this$0;

        public ShowCommentPublisherHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected abstract class ShowTextPublisherHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
        implements com.facebook.katana.webview.FacebookWebView.JsReturnHandler
    {

        public void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1)
        {
            mHandler.post(new Runnable() {

                public void run()
                {
                    ((EditText)findViewById(0x7f0e0076)).setText(null);
                }

                final ShowTextPublisherHandler this$1;

                
                {
                    this$1 = ShowTextPublisherHandler.this;
                    super();
                }
            }
);
        }

        public void handleUI(final FacebookWebView wv, FacebookRpcCall facebookrpccall)
        {
            findViewById(0x7f0e0075).setVisibility(0);
            final EditText et = (EditText)findViewById(0x7f0e0076);
            et.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
                {
                    if(i == textview.getImeActionId() && textview.getText().toString().trim().length() > 0)
                        submitText(wv, textview);
                    return false;
                }

                final ShowTextPublisherHandler this$1;
                final FacebookWebView val$wv;

                
                {
                    this$1 = ShowTextPublisherHandler.this;
                    wv = facebookwebview;
                    super();
                }
            }
);
            et.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

                public void onFocusChange(View view, boolean flag1)
                {
                    Button button1 = mSendButton;
                    int i;
                    if(flag1)
                        i = 0;
                    else
                        i = 8;
                    button1.setVisibility(i);
                }

                final ShowTextPublisherHandler this$1;

                
                {
                    this$1 = ShowTextPublisherHandler.this;
                    super();
                }
            }
);
            Button button = mSendButton;
            boolean flag;
            if(et.getText().length() > 0)
                flag = true;
            else
                flag = false;
            button.setEnabled(flag);
            et.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable editable)
                {
                }

                public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
                {
                }

                public void onTextChanged(CharSequence charsequence, int i, int j, int k)
                {
                    Button button1 = mSendButton;
                    boolean flag1;
                    if(et.getText().length() > 0)
                        flag1 = true;
                    else
                        flag1 = false;
                    button1.setEnabled(flag1);
                }

                final ShowTextPublisherHandler this$1;
                final EditText val$et;

                
                {
                    this$1 = ShowTextPublisherHandler.this;
                    et = edittext;
                    super();
                }
            }
);
            mSendButton.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    EditText edittext = (EditText)findViewById(0x7f0e0076);
                    if(edittext.getText().toString().trim().length() > 0)
                        submitText(wv, edittext);
                }

                final ShowTextPublisherHandler this$1;
                final FacebookWebView val$wv;

                
                {
                    this$1 = ShowTextPublisherHandler.this;
                    wv = facebookwebview;
                    super();
                }
            }
);
        }

        protected void submitText(FacebookWebView facebookwebview, TextView textview)
        {
            ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(textview.getWindowToken(), 0);
        }

        private final Button mSendButton;
        final FacewebChromeActivity this$0;


        public ShowTextPublisherHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
            mSendButton = (Button)findViewById(0x7f0e0077);
        }
    }

    protected class ShareHandler
        implements android.view.View.OnClickListener, com.facebook.katana.webview.FacebookWebView.JsReturnHandler
    {

        public void handle(FacebookWebView facebookwebview, String s, boolean flag, String s1)
        {
            removeDialog(2);
            if(!flag) goto _L2; else goto _L1
_L1:
            Toaster.toast(FacewebChromeActivity.this, 0x7f0a01c4);
_L4:
            return;
_L2:
            if(!mIsInElder)
                mHandler.post(new Runnable() {

                    public void run()
                    {
                        EditText edittext = (EditText)findViewById(0x7f0e0128);
                        edittext.setText(null);
                        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                    }

                    final ShareHandler this$1;

                
                {
                    this$1 = ShareHandler.this;
                    super();
                }
                }
);
            if(true) goto _L4; else goto _L3
_L3:
        }

        public void onClick(View view)
        {
            String s = Utils.convertMentionTags(((EditText)findViewById(0x7f0e0128)).getText());
            if(s.length() > 0)
            {
                JSONObject jsonobject = new JSONObject();
                ArrayList arraylist;
                try
                {
                    jsonobject.put("action", "didPostStatus");
                    jsonobject.put("text", s);
                }
                catch(JSONException jsonexception)
                {
                    Log.e(getTag(), (new StringBuilder()).append("inconceivable exception ").append(jsonexception.toString()).toString());
                }
                arraylist = new ArrayList();
                arraylist.add(jsonobject);
                showDialog(2);
                mWebview.executeJsFunction(mPublisherCallback, arraylist, this);
            }
        }

        protected FacebookWebView mWebview;
        final FacewebChromeActivity this$0;

        public ShareHandler()
        {
            this$0 = FacewebChromeActivity.this;
            super();
        }

        public ShareHandler(FacebookWebView facebookwebview)
        {
            this$0 = FacewebChromeActivity.this;
            super();
            mWebview = facebookwebview;
        }
    }

    protected class ShowPublisherHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mPublisherCallback = facebookrpccall.getParameterByName("callback");
            String s = facebookrpccall.getParameterByName("target");
            Location location;
            try
            {
                mProfileId = Long.parseLong(s);
            }
            catch(NumberFormatException numberformatexception) { }
            catch(NullPointerException nullpointerexception) { }
            ComposerUserSettings.get(FacewebChromeActivity.this, "composer_share_location");
            ComposerUserSettings.get(FacewebChromeActivity.this, "composer_tour_completed");
            AudienceSettings.get(FacewebChromeActivity.this, com.facebook.katana.model.PrivacySetting.Category.composer_sticky);
            location = FBLocationManager.getRecentLocation(0x493e0);
            onLocationChanged(location);
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            if(mPublisher != null)
            {
                mPublisher.setVisibility(0);
            } else
            {
                LinearLayout linearlayout = (LinearLayout)findViewById(0x7f0e0069);
                LayoutInflater layoutinflater = (LayoutInflater)getSystemService("layout_inflater");
                if(mIsInElder)
                {
                    android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-1, -2);
                    mPublisher = layoutinflater.inflate(0x7f03006b, null);
                    linearlayout.addView(mPublisher, layoutparams);
                    setupPublisher(facebookrpccall);
                } else
                {
                    linearlayout.addView(layoutinflater.inflate(0x7f030074, null));
                    findViewById(0x7f0e0129).setOnClickListener(new ShareHandler(facebookwebview));
                    findViewById(0x7f0e0127).setOnClickListener(new android.view.View.OnClickListener() {

                        public void onClick(View view)
                        {
                            showDialog(0xf36e2d7);
                        }

                        final ShowPublisherHandler this$1;

                
                {
                    this$1 = ShowPublisherHandler.this;
                    super();
                }
                    }
);
                    AppSessionListener appsessionlistener = ((TaggingAutoCompleteTextView)(EditText)findViewById(0x7f0e0128)).configureView(FacewebChromeActivity.this, mAppSession.getUserImagesCache());
                    if(isOnTop())
                        mAppSession.addListener(appsessionlistener);
                    mAppSessionListeners.add(appsessionlistener);
                }
            }
        }

        protected void setupCheckInOnClick()
        {
            findViewById(0x7f0e0113).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("extra_is_checkin", true);
                    launchComposer(null, bundle, Integer.valueOf(10));
                }

                final ShowPublisherHandler this$1;

                
                {
                    this$1 = ShowPublisherHandler.this;
                    super();
                }
            }
);
            if(getIntent().getAction() != null && getIntent().getAction().equals("com.facebook.katana.SHARE"))
                launchComposer(null, null, Integer.valueOf(10));
        }

        protected void setupPhotoOnClick(final boolean photoAndVideo)
        {
            findViewById(0x7f0e010f).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    FacewebChromeActivity facewebchromeactivity = _fld0;
                    int i;
                    if(photoAndVideo)
                        i = 0xf36e2d7;
                    else
                        i = 0xf36e2d8;
                    facewebchromeactivity.showDialog(i);
                }

                final ShowPublisherHandler this$1;
                final boolean val$photoAndVideo;

                
                {
                    this$1 = ShowPublisherHandler.this;
                    photoAndVideo = flag;
                    super();
                }
            }
);
        }

        protected void setupPublisher(FacebookRpcCall facebookrpccall)
        {
            String s;
            String s1;
            long l;
            s = facebookrpccall.getParameterByName("type");
            s1 = facebookrpccall.getParameterByName("target");
            l = -1L;
            long l1 = Long.parseLong(s1);
            l = l1;
_L2:
            if(s.equals("event") || s.equals("user") && l != -1L && mAppSession.getSessionInfo().getProfile().mUserId != l)
            {
                setupPublisherButton(0x7f0e010f, 0x7f0a017a, 0x7f02010b);
                boolean flag;
                if(!s.equals("user") || s1 == null)
                    flag = true;
                else
                    flag = false;
                setupPhotoOnClick(flag);
                setupPublisherButton(0x7f0e0111, 0x7f0a017c, 0x7f02010c);
                setupStatusOnClick();
                findViewById(0x7f0e0113).setVisibility(8);
                findViewById(0x7f0e0112).setVisibility(8);
            } else
            {
                setupPublisherButton(0x7f0e010f, 0x7f0a0132, 0x7f02010b);
                setupPhotoOnClick(true);
                int i;
                if(s.equals("group"))
                    i = 0x7f0a002e;
                else
                    i = 0x7f0a017b;
                setupPublisherButton(0x7f0e0111, i, 0x7f02010c);
                setupStatusOnClick();
                setupPublisherButton(0x7f0e0113, 0x7f0a023e, 0x7f02010a);
                setupCheckInOnClick();
            }
            return;
            NumberFormatException numberformatexception;
            numberformatexception;
            continue; /* Loop/switch isn't completed */
            NullPointerException nullpointerexception;
            nullpointerexception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        protected void setupPublisherButton(int i, int j, int k)
        {
            Button button = (Button)findViewById(i);
            android.graphics.drawable.Drawable drawable = getResources().getDrawable(k);
            button.setText(getString(j));
            button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }

        protected void setupStatusOnClick()
        {
            findViewById(0x7f0e0111).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    Bundle bundle = null;
                    if(mProfileId == -1L)
                    {
                        bundle = new Bundle();
                        bundle.putInt("extra_composer_title", 0x7f0a01fc);
                    }
                    launchComposer(null, bundle, Integer.valueOf(10));
                }

                final ShowPublisherHandler this$1;

                
                {
                    this$1 = ShowPublisherHandler.this;
                    super();
                }
            }
);
        }

        private static final String PUBLISHER_TYPE_EVENT = "event";
        private static final String PUBLISHER_TYPE_FEED = "feed";
        private static final String PUBLISHER_TYPE_GROUP = "group";
        private static final String PUBLISHER_TYPE_USER = "user";
        protected boolean mExecuted;
        final FacewebChromeActivity this$0;

        ShowPublisherHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class ShowMessageComposerHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            Intent intent = new Intent(FacewebChromeActivity.this, com/facebook/katana/activity/messages/MessageComposeActivity);
            startActivity(intent);
        }

        final FacewebChromeActivity this$0;

        public ShowMessageComposerHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }

    protected class SetToolbarSegmentsHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            JSONArray jsonarray = new JSONArray(facebookrpccall.getParameterByName("segments"));
            RadioGroup radiogroup = (RadioGroup)findViewById(0x7f0e006c);
            callbacks = new String[jsonarray.length()];
            radiogroup.removeAllViews();
            radiogroup.clearCheck();
            for(int i = 0; i < jsonarray.length(); i++)
            {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String s1 = jsonobject.optString("title");
                String s2 = jsonobject.optString("callback");
                callbacks[i] = s2;
                RadioButton radiobutton = setupAndGetTabView(i, s1);
                radiobutton.setTag(s2);
                radiogroup.addView(radiobutton);
                radiobutton.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, -1, 1F));
            }

            mCurrentTab = 0;
            String s = facebookrpccall.getParameterByName("current_tab");
            if(s != null)
                mCurrentTab = Integer.parseInt(s);
            radiogroup.check(mCurrentTab);
            android.widget.RadioGroup.OnCheckedChangeListener oncheckedchangelistener = new android.widget.RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup radiogroup1, int j)
                {
                    switchTab(j);
                }

                final SetToolbarSegmentsHandler this$1;

                
                {
                    this$1 = SetToolbarSegmentsHandler.this;
                    super();
                }
            }
;
            radiogroup.setOnCheckedChangeListener(oncheckedchangelistener);
_L1:
            return;
            JSONException jsonexception;
            jsonexception;
            Log.e(getTag(), "Data format error", jsonexception);
              goto _L1
        }

        protected RadioButton setupAndGetTabView(int i, String s)
        {
            RadioButton radiobutton = (RadioButton)getLayoutInflater().inflate(0x7f030080, null);
            radiobutton.setButtonDrawable(0x7f020060);
            radiobutton.setId(i);
            radiobutton.setText(s);
            return radiobutton;
        }

        protected void switchTab(int i)
        {
            if(mCurrentTab != i) goto _L2; else goto _L1
_L1:
            return;
_L2:
            mCurrentTab = i;
            if(i >= 0 && i < callbacks.length)
                mWv.executeJs(callbacks[i], null);
            if(true) goto _L1; else goto _L3
_L3:
        }

        private static final String PARAM_CURRENT_TAB = "current_tab";
        private static final String PARAM_SEGMENTS = "segments";
        private static final String TAB_CALLBACK = "callback";
        private static final String TAB_TITLE = "title";
        private String callbacks[];
        private int mCurrentTab;
        final FacewebChromeActivity this$0;

        public SetToolbarSegmentsHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
            mCurrentTab = -1;
        }
    }

    protected class SetActionMenuHandler extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        int getIconForAction(String s)
        {
            int i;
            if(s.equals("mark_unread"))
                i = 0x7f0200ba;
            else
            if(s.equals("mark_spam"))
                i = 0x7f0200b8;
            else
            if(s.equals("archive"))
                i = 0x7f0200b4;
            else
            if(s.equals("unarchive"))
                i = 0x7f0200b9;
            else
            if(s.equals("move"))
                i = 0x7f0200b7;
            else
            if(s.equals("delete"))
                i = 0x7f0200b5;
            else
            if(s.equals("forward"))
                i = 0x7f0200b6;
            else
                i = -1;
            return i;
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            String s = facebookrpccall.getParameterByName("actions");
            JSONArray jsonarray;
            int i;
            jsonarray = new JSONArray(s);
            mActionMenuItems = new JSONObject[jsonarray.length()];
            i = 0;
_L1:
            if(i >= jsonarray.length())
                break MISSING_BLOCK_LABEL_134;
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            mActionMenuItems[i] = jsonobject;
            if(jsonobject.has("type"))
            {
                int j = getIconForAction(jsonobject.optString("type"));
                if(j > 0)
                    jsonobject.put("icon", j);
            }
            i++;
              goto _L1
            JSONException jsonexception;
            jsonexception;
            Log.e(getTag(), "Invalid JSON format", jsonexception);
            mActionMenuItems = null;
        }

        private static final String ACTION_ARCHIVE = "archive";
        private static final String ACTION_DELETE = "delete";
        private static final String ACTION_FORWARD = "forward";
        private static final String ACTION_MARK_SPAM = "mark_spam";
        private static final String ACTION_MARK_UNREAD = "mark_unread";
        private static final String ACTION_MOVE = "move";
        private static final String ACTION_UNARCHIVE = "unarchive";
        final FacewebChromeActivity this$0;

        public SetActionMenuHandler(Handler handler)
        {
            this$0 = FacewebChromeActivity.this;
            super(handler);
        }
    }


    public FacewebChromeActivity()
    {
        mProfileId = -1L;
        mActionMenuItems = null;
        mIsInElder = false;
        mFirstResume = true;
        mPublisher = null;
    }

    protected static String getStringParam(FacebookRpcCall facebookrpccall, String s)
    {
        String s1 = facebookrpccall.getParameterByName(s);
        String s2;
        if(s1 instanceof String)
            s2 = (String)s1;
        else
            s2 = null;
        return s2;
    }

    protected MediaUploader getMediaUploader()
    {
        if(mMediaUploader == null)
            if(mProfileId != -1L && mProfileId != mAppSession.getSessionInfo().userId)
                mMediaUploader = new MediaUploader(this, mProfileId);
            else
                mMediaUploader = new MediaUploader(this, null);
        return mMediaUploader;
    }

    protected void launchComposer(Uri uri, Bundle bundle, Integer integer)
    {
        launchComposer(uri, bundle, integer, mProfileId);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s;
        long al[];
        ArrayList arraylist;
        switch(i)
        {
        default:
            continue; /* Loop/switch isn't completed */

        case 10: // '\n'
            s = intent.getStringExtra("extra_status_text");
            al = intent.getLongArrayExtra("extra_tagged_ids");
            arraylist = new ArrayList();
            int k = al.length;
            for(int l = 0; l < k; l++)
                arraylist.add(Long.valueOf(al[l]));

            break;

        case 133701: 
        case 133702: 
            if(mIsInElder)
            {
                Uri uri;
                Bundle bundle;
                Integer integer;
                if(intent != null)
                    uri = intent.getData();
                else
                    uri = null;
                bundle = new Bundle();
                bundle.putInt("extra_photo_request_code", i);
                integer = Integer.valueOf(10);
                launchComposer(uri, bundle, integer);
                continue; /* Loop/switch isn't completed */
            }
            // fall through

        case 133703: 
        case 133704: 
            if(intent == null)
                intent = new Intent();
            getMediaUploader().onActivityResult(i, j, intent);
            continue; /* Loop/switch isn't completed */
        }
        String s1 = intent.getStringExtra("extra_tagged_place_id");
        String s2 = intent.getStringExtra("extra_xed_location");
        FacebookPlace facebookplace = (FacebookPlace)intent.getParcelableExtra("extra_place");
        boolean flag = intent.getBooleanExtra("extra_is_checkin", false);
        Location location = (Location)intent.getParcelableExtra("extra_tagged_location");
        String s3 = intent.getStringExtra("extra_status_privacy");
        Long long1 = Long.valueOf(intent.getLongExtra("extra_status_target_id", -1L));
        String s4;
        if(facebookplace != null)
            s4 = String.valueOf(facebookplace.mPageId);
        else
            s4 = s1;
        if(intent.getBooleanExtra("extra_photo_upload_started", false))
            Toaster.toast(this, 0x7f0a0218);
        else
        if(flag)
            try
            {
                mCheckinReqId = mAppSession.checkin(this, facebookplace, location, s, IntentUtils.primitiveToSet(al), Long.valueOf(mProfileId), s3);
            }
            catch(JSONException jsonexception1)
            {
                Toaster.toast(this, 0x7f0a0240);
            }
        else
        if(s != null)
        {
            JSONObject jsonobject = new JSONObject();
            ArrayList arraylist1;
            FacewebWebView facewebwebview;
            String s5;
            ShareHandler sharehandler;
            try
            {
                jsonobject.put("action", "didPostStatus");
                jsonobject.put("text", s);
                if(arraylist != null)
                    jsonobject.put("users_with", new JSONArray(IntentUtils.primitiveToSet(al)));
                if(s4 != null)
                    jsonobject.put("at", s4);
                if(s3 != null)
                    jsonobject.put("privacy", new JSONObject(s3));
                if(s2 != null)
                    jsonobject.put("disable_location", s2);
                if(long1.longValue() != -1L)
                    jsonobject.put("target", long1);
            }
            catch(JSONException jsonexception)
            {
                Log.e(getTag(), "inconceivable exception", jsonexception);
            }
            arraylist1 = new ArrayList();
            arraylist1.add(jsonobject);
            showDialog(2);
            facewebwebview = mWv;
            s5 = mPublisherCallback;
            sharehandler = new ShareHandler();
            facewebwebview.executeJsFunction(s5, arraylist1, sharehandler);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if(bundle != null)
            mProfileId = bundle.getLong("PROFILE_ID", -1L);
        mHref = getIntent().getStringExtra("mobile_page");
        if(mHref == null)
            mHref = "/home.php";
        PerfLogging.logStep(this, com.facebook.katana.service.method.PerfLogging.Step.ONCREATE, getTag(), getActivityId(), mHref);
        if(bundle != null && bundle.getBoolean("save_active_state"))
            ApiLogging.incrementKillCount();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, getIntent());
_L4:
        return;
_L2:
        mHandler = new Handler();
        setContentView(0x7f03001f);
        hideSearchButton();
        boolean flag;
        UpdateNativeLoadingIndicator updatenativeloadingindicator;
        Button button;
        Boolean boolean1;
        if(getParent() != null)
            flag = true;
        else
            flag = false;
        mWithinTab = flag;
        mWv = ((RefreshableFacewebWebViewContainer)findViewById(0x7f0e006a)).getWebView();
        mWv.loadMobilePage(mHref);
        if(!mWithinTab)
        {
            mWv.registerNativeCallHandler("setNavBarTitle", new SetNavBarTitleHandler(this, mHandler));
            mWv.registerNativeCallHandler("setToolbarSegments", new SetToolbarSegmentsHandler(mHandler));
        } else
        {
            findViewById(0x7f0e0016).setVisibility(8);
        }
        mWv.registerNativeCallHandler("showCommentPublisher", new ShowCommentPublisherHandler(mHandler));
        mWv.registerNativeCallHandler("showPublisher", new ShowPublisherHandler(mHandler));
        mWv.registerNativeCallHandler("removePublisher", new com.facebook.katana.webview.FacebookWebView.NativeUICallHandler(mHandler) {

            public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                if((LinearLayout)findViewById(0x7f0e0069) != null && mPublisher != null)
                    mPublisher.setVisibility(8);
            }

            final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super(handler);
            }
        }
);
        mWv.registerNativeCallHandler("showMsgComposer", new ShowMessageComposerHandler(mHandler));
        mWv.registerNativeCallHandler("showMsgReplyPublisher", new ShowReplyPublisherHandler(mHandler));
        updatenativeloadingindicator = new UpdateNativeLoadingIndicator(mHandler);
        mWv.registerNativeCallHandler("pageLoading", updatenativeloadingindicator);
        mWv.registerNativeCallHandler("pageLoaded", updatenativeloadingindicator);
        mWv.registerNativeCallHandler("appLog", new AppLogHandler(getTag()));
        mWv.registerNativeCallHandler("setNavBarButton", new SetNavBarButton(mHandler));
        mWv.registerNativeCallHandler("setActionMenu", new SetActionMenuHandler(mHandler));
        mPickerView = new ShowPickerView(mHandler);
        mWv.registerNativeCallHandler("showPickerView", mPickerView);
        mRefreshable = false;
        mWv.registerNativeCallHandler("enablePullToRefresh", new com.facebook.katana.webview.FacebookWebView.NativeCallHandler() {

            public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                mRefreshable = true;
            }

            final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super();
            }
        }
);
        mWv.registerNativeCallHandler("close", new com.facebook.katana.webview.FacebookWebView.NativeUICallHandler(mHandler) {

            public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                finish();
            }

            final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super(handler);
            }
        }
);
        mWv.setScrollBarStyle(0x2000000);
        mWv.registerNativeCallHandler("setNavBarHidden", new SetNavBarHiddenHandler(mHandler));
        mWv.registerNativeCallHandler("dismissModalDialog", new DismissModalDialog(mHandler));
        mWv.registerNativeCallHandler("perf.cachedResponseLoaded", new com.facebook.katana.webview.FacebookWebView.NativeCallHandler() {

            public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                PerfLogging.logStep(FacewebChromeActivity.this, com.facebook.katana.service.method.PerfLogging.Step.UI_DRAWN_STALE, getTag(), getActivityId(), mHref);
            }

            final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super();
            }
        }
);
        mWv.registerNativeCallHandler("addFriend", new com.facebook.katana.webview.FacebookWebView.NativeUICallHandler(mHandler) {

            public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                long l = Long.parseLong(facebookrpccall.getParameterByName("userId"));
                AppSessionListener appsessionlistener = new AppSessionListener() {

                    public void onFriendsAddFriendComplete(AppSession appsession, String s, int i, String s1, Exception exception1, List list)
                    {
                        if(i == 200)
                            mWv.refresh();
                    }

                    final _cls5 this$1;

                    
                    {
                        this$1 = _cls5.this;
                        super();
                    }
                }
;
                mAppSessionListeners.add(appsessionlistener);
                mAppSession.addListener(appsessionlistener);
                Dialog dialog = Dialogs.addFriend(FacewebChromeActivity.this, l, new com.facebook.katana.dialog.Dialogs.AddFriendListener() {

                    public void onAddFriendStart(String s)
                    {
                    }

                    final _cls5 this$1;

                    
                    {
                        this$1 = _cls5.this;
                        super();
                    }
                }
);
                dialog.setOwnerActivity(FacewebChromeActivity.this);
                dialog.show();
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super(handler);
            }
        }
);
        button = (Button)findViewById(0x7f0e0067);
        if(button != null)
            button.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    titleBarClickHandler(view);
                }

                final FacewebChromeActivity this$0;

            
            {
                this$0 = FacewebChromeActivity.this;
                super();
            }
            }
);
        mAppSessionListeners = new ArrayList();
        mAppSessionListeners.add(new FacewebAppSessionListener());
        mWv.updateScrollingEnabled();
        mLastPausedTime = System.currentTimeMillis();
        boolean1 = Gatekeeper.get(this, "meta_composer");
        if(boolean1 != null && boolean1.booleanValue())
            mIsInElder = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR lookupswitch 6: default 60
    //                   2: 64
    //                   3: 64
    //                   4: 64
    //                   255255255: 150
    //                   255255256: 161
    //                   255255257: 172;
           goto _L1 _L2 _L2 _L2 _L3 _L4 _L5
_L1:
        Object obj = null;
_L10:
        return ((Dialog) (obj));
_L2:
        int j = -1;
        if(i != 2) goto _L7; else goto _L6
_L6:
        j = 0x7f0a01c5;
_L8:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        if(j != -1)
            progressdialog.setMessage(getText(j));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L7:
        if(i == 3)
            j = 0x7f0a01ae;
        else
        if(i == 4)
            j = 0x7f0a00bd;
        if(true) goto _L8; else goto _L3
_L3:
        obj = getMediaUploader().createDialog();
        continue; /* Loop/switch isn't completed */
_L4:
        obj = getMediaUploader().createPhotoDialog();
        continue; /* Loop/switch isn't completed */
_L5:
        obj = getMediaUploader().createVideoDialog();
        if(true) goto _L10; else goto _L9
_L9:
    }

    protected void onDestroy()
    {
        RefreshableFacewebWebViewContainer refreshablefacewebwebviewcontainer = (RefreshableFacewebWebViewContainer)findViewById(0x7f0e006a);
        if(refreshablefacewebwebviewcontainer != null)
            refreshablefacewebwebviewcontainer.removeAllViews();
        if(mWv != null)
        {
            final FacewebWebView webViewToKill = mWv;
            mHandler.postDelayed(new Runnable() {

                public void run()
                {
                    if(webViewToKill != null)
                        webViewToKill.destroy();
                }

                final FacewebChromeActivity this$0;
                final FacewebWebView val$webViewToKill;

            
            {
                this$0 = FacewebChromeActivity.this;
                webViewToKill = facewebwebview;
                super();
            }
            }
, 30000L);
            mWv = null;
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(i != 4 || keyevent.getRepeatCount() != 0) goto _L2; else goto _L1
_L1:
        View view = findViewById(0x7f0e006d);
        if(view.getVisibility() != 0) goto _L2; else goto _L3
_L3:
        boolean flag;
        view.setVisibility(8);
        mWv.executeJs(mPickerView.dismissScript, null);
        flag = true;
_L5:
        return flag;
_L2:
        flag = super.onKeyDown(i, keyevent);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void onLocationChanged(Location location)
    {
        if(location != null)
        {
            if(PlacesNearby.get(this, new com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType(location)) != null)
                FBLocationManager.removeLocationListener(mLocationListener);
        } else
        {
            FBLocationManager.addLocationListener(this, this);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 100 100: default 24
    //                   100 92;
           goto _L1 _L2
_L1:
        if(mActionMenuItems != null && mActionMenuItems.length > 0)
        {
            for(int i = 0; i < mActionMenuItems.length; i++)
            {
                int j = i + 1000;
                if(menuitem.getItemId() == j)
                    mWv.executeJs(mActionMenuItems[i].optString("callback"), null);
            }

        }
        break; /* Loop/switch isn't completed */
_L2:
        mWv.refresh();
        if(true) goto _L1; else goto _L3
_L3:
        return super.onOptionsItemSelected(menuitem);
    }

    protected void onPause()
    {
        if(mAppSession != null)
        {
            AppSessionListener appsessionlistener;
            for(Iterator iterator = mAppSessionListeners.iterator(); iterator.hasNext(); mAppSession.removeListener(appsessionlistener))
                appsessionlistener = (AppSessionListener)iterator.next();

        }
        mLastPausedTime = System.currentTimeMillis();
        FBLocationManager.removeLocationListener(this);
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
        if(mRefreshable)
        {
            menu.add(0, 100, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
            MenuItem menuitem1 = menu.findItem(100);
            int i;
            int j;
            JSONObject jsonobject;
            MenuItem menuitem;
            boolean flag;
            if(!mShowingProgress)
                flag = true;
            else
                flag = false;
            menuitem1.setEnabled(flag);
        }
        if(mActionMenuItems != null)
            for(i = 0; i < mActionMenuItems.length; i++)
            {
                j = i + 1000;
                jsonobject = mActionMenuItems[i];
                menuitem = menu.add(0, j, 0, jsonobject.optString("title"));
                if(jsonobject.has("icon"))
                    menuitem.setIcon(jsonobject.optInt("icon"));
            }

        return true;
    }

    public void onResume()
    {
        super.onResume();
        PerfLogging.logStep(this, com.facebook.katana.service.method.PerfLogging.Step.ONRESUME, getTag(), getActivityId(), mHref);
        PerfLogging.logPageView(this, getTag(), getActivityId());
        if(mFirstResume)
            mFirstResume = false;
        else
            ApiLogging.incrementResumeCount();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            AppSessionListener appsessionlistener;
            for(Iterator iterator = mAppSessionListeners.iterator(); iterator.hasNext(); mAppSession.addListener(appsessionlistener))
                appsessionlistener = (AppSessionListener)iterator.next();

            mWv.ensureReadiness();
            long l = (System.currentTimeMillis() - mLastPausedTime) / 1000L;
            Object aobj[] = new Object[2];
            aobj[0] = Long.valueOf(l);
            aobj[1] = "true";
            String s = String.format("(function() { if (window.fwDidEnterForeground) { fwDidEnterForeground(%d, %s); } })()", aobj);
            mWv.executeJs(s, null);
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putLong("PROFILE_ID", mProfileId);
        bundle.putBoolean("save_active_state", true);
    }

    public void onTimeOut()
    {
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    protected static final int ACTION_MENU_BASE_ID = 1000;
    private static final long DESTROY_WEBVIEW_DELAY_TIME_MS = 30000L;
    public static final String EXTRA_MOBILE_PAGE = "mobile_page";
    public static final String EXTRA_RAW_PARAMETERS = "raw_parameters";
    private static final String PROFILE_ID = "PROFILE_ID";
    protected static final int PROGRESS_COMMENTING_DIALOG = 3;
    protected static final int PROGRESS_PUBLISHING_DIALOG = 2;
    protected static final int PROGRESS_REPLYING_DIALOG = 4;
    protected static final int REFRESH_ID = 100;
    protected static final String SAVE_ACTIVE_STATE = "save_active_state";
    protected static final int STRUCTURED_COMPOSER = 10;
    protected JSONObject mActionMenuItems[];
    private AppSession mAppSession;
    private List mAppSessionListeners;
    private String mCheckinReqId;
    private boolean mFirstResume;
    protected Handler mHandler;
    private String mHref;
    private boolean mIsInElder;
    private long mLastPausedTime;
    private boolean mLoading;
    private com.facebook.katana.util.FBLocationManager.FBLocationListener mLocationListener;
    private MediaUploader mMediaUploader;
    protected String mMsgComposerCallback;
    protected String mNavButtonCallback;
    protected ShowPickerView mPickerView;
    protected String mPickerViewCallback;
    protected long mProfileId;
    private TabProgressListener mProgressListener;
    protected View mPublisher;
    protected String mPublisherCallback;
    private boolean mRefreshable;
    private boolean mShowingProgress;
    private boolean mWithinTab;
    protected FacewebWebView mWv;


/*
    static boolean access$002(FacewebChromeActivity facewebchromeactivity, boolean flag)
    {
        facewebchromeactivity.mRefreshable = flag;
        return flag;
    }

*/






/*
    static boolean access$1202(FacewebChromeActivity facewebchromeactivity, boolean flag)
    {
        facewebchromeactivity.mLoading = flag;
        return flag;
    }

*/



/*
    static boolean access$1402(FacewebChromeActivity facewebchromeactivity, boolean flag)
    {
        facewebchromeactivity.mShowingProgress = flag;
        return flag;
    }

*/










/*
    static String access$2302(FacewebChromeActivity facewebchromeactivity, String s)
    {
        facewebchromeactivity.mCheckinReqId = s;
        return s;
    }

*/








/*
    static boolean access$702(FacewebChromeActivity facewebchromeactivity, boolean flag)
    {
        facewebchromeactivity.mIsInElder = flag;
        return flag;
    }

*/

}
