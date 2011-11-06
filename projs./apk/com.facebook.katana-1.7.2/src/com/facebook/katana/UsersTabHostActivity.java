// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UsersTabHostActivity.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.activity.findfriends.FindFriendsActivity;
import com.facebook.katana.activity.findfriends.LegalDisclaimerActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.GrowthUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            TabProgressListener, UsersTabProgressSource, MyTabHost, FriendsActivity, 
//            PageSearchResultsActivity, RequestsActivity, LoginActivity

public class UsersTabHostActivity extends BaseFacebookTabActivity
    implements MyTabHost.OnTabChangeListener, TabProgressListener
{

    public UsersTabHostActivity()
    {
        mRequestsActivityStarted = false;
        mRequestsListener = new AppSessionListener() {

            public void onUserGetFriendRequestsComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
            {
                if(map != null)
                {
                    Parcelable aparcelable[] = new Parcelable[map.size()];
                    int j = 0;
                    for(Iterator iterator = map.values().iterator(); iterator.hasNext();)
                    {
                        FacebookUser facebookuser = (FacebookUser)iterator.next();
                        int k = j + 1;
                        aparcelable[j] = facebookuser;
                        j = k;
                    }

                    Iterator iterator1 = ((MyTabHost)getTabHost()).getTabSpecs().iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        MyTabHost.TabSpec tabspec = (MyTabHost.TabSpec)iterator1.next();
                        if(tabspec.tag.equals("requests"))
                        {
                            RadioButton radiobutton = (RadioButton)tabspec.view;
                            if(isOnTop())
                                if(aparcelable.length == 0)
                                {
                                    radiobutton.setText(getString(0x7f0a0088));
                                } else
                                {
                                    UsersTabHostActivity userstabhostactivity = UsersTabHostActivity.this;
                                    Object aobj[] = new Object[1];
                                    aobj[0] = Integer.valueOf(map.size());
                                    radiobutton.setText(userstabhostactivity.getString(0x7f0a017d, aobj));
                                }
                            mNumRequests = Integer.valueOf(map.size());
                            if(!mRequestsActivityStarted)
                            {
                                Intent intent = new Intent(UsersTabHostActivity.this, com/facebook/katana/RequestsActivity);
                                intent.putExtra("within_tab", true);
                                intent.putExtra("extra_frend_requests", aparcelable);
                                tabspec.setContent(intent);
                            }
                        }
                    } while(true);
                }
                appsession.removeListener(this);
            }

            final UsersTabHostActivity this$0;

            
            {
                this$0 = UsersTabHostActivity.this;
                super();
            }
        }
;
    }

    private void setUpProgressListener()
    {
        if(mCurrentTab instanceof UsersTabProgressSource)
            ((UsersTabProgressSource)mCurrentTab).setProgressListener(this);
    }

    private void setUpTextHint(String s)
    {
        if(!s.equals("friends")) goto _L2; else goto _L1
_L1:
        mTextBox.setHint(0x7f0a006c);
        findViewById(0x7f0e0153).setVisibility(0);
_L4:
        return;
_L2:
        if(s.equals("page_search"))
        {
            mTextBox.setHint(0x7f0a0073);
            findViewById(0x7f0e0153).setVisibility(0);
        } else
        if(s.equals("requests"))
        {
            findViewById(0x7f0e0153).setVisibility(8);
            ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(mTextBox.getWindowToken(), 0);
            mRequestsActivityStarted = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void findFriendsActivityConsentCheck()
    {
        if(!GrowthUtils.shouldShowLegalScreen(this))
            startActivity(new Intent(this, com/facebook/katana/activity/findfriends/FindFriendsActivity));
        else
            startActivityForResult(new Intent(this, com/facebook/katana/activity/findfriends/LegalDisclaimerActivity), 0);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        if(i == 0 && j == -1)
        {
            GrowthUtils.setFindFriendsConsentApproved(this);
            GrowthUtils.stopFindFriendsDialog(this);
            startActivity(new Intent(this, com/facebook/katana/activity/findfriends/FindFriendsActivity));
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03008c);
        hideSearchButton();
        setPrimaryActionFace(-1, getString(0x7f0a0285));
        MyTabHost mytabhost = (MyTabHost)getTabHost();
        mytabhost.handleTouchMode(false);
        MyTabHost.TabSpec tabspec = mytabhost.myNewTabSpec("friends", setupAndGetTabView("friends", 0x7f0a006d));
        Intent intent = new Intent(this, com/facebook/katana/FriendsActivity);
        intent.putExtra("within_tab", true);
        intent.putExtra("extra_parent_tag", getTag());
        tabspec.setContent(intent);
        mytabhost.addTab(tabspec);
        MyTabHost.TabSpec tabspec1 = mytabhost.myNewTabSpec("page_search", setupAndGetTabView("page_search", 0x7f0a0115));
        Intent intent1 = new Intent(this, com/facebook/katana/PageSearchResultsActivity);
        intent1.putExtra("within_tab", true);
        intent1.putExtra("extra_parent_tag", getTag());
        tabspec1.setContent(intent1);
        mytabhost.addTab(tabspec1);
        MyTabHost.TabSpec tabspec2 = mytabhost.myNewTabSpec("requests", setupAndGetTabView("requests", 0x7f0a0088));
        Intent intent2 = new Intent(this, com/facebook/katana/RequestsActivity);
        intent2.putExtra("within_tab", true);
        tabspec2.setContent(intent2);
        mytabhost.addTab(tabspec2);
        mTextBox = (TextView)findViewById(0x7f0e0019);
        mTextBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable)
            {
            }

            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
            {
            }

            public void onTextChanged(CharSequence charsequence, int i, int j, int k)
            {
                String s2 = charsequence.toString().trim();
                if(mCurrentTab instanceof UsersTabProgressSource)
                    ((UsersTabProgressSource)mCurrentTab).search(s2);
            }

            final UsersTabHostActivity this$0;

            
            {
                this$0 = UsersTabHostActivity.this;
                super();
            }
        }
);
        mCurrentTab = getCurrentActivity();
        if(mCurrentTab instanceof UsersTabProgressSource)
            ((UsersTabProgressSource)mCurrentTab).setProgressListener(this);
        String s = "friends";
        String s1 = getIntent().getStringExtra("com.facebook.katana.DefaultTab");
        if(s1 != null)
            s = s1;
        mytabhost.setCurrentTabByTag(s);
        mCurrentTab = getCurrentActivity();
        setUpTextHint(s);
        setUpProgressListener();
        mTextBox.requestFocus();
        mytabhost.setOnTabChangedListener(this);
        setupTabs();
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mCurrentTab != null && (mCurrentTab instanceof UsersTabProgressSource))
            ((UsersTabProgressSource)mCurrentTab).setProgressListener(null);
    }

    public void onResume()
    {
        super.onResume();
        AppSession appsession = AppSession.getActiveSession(this, true);
        if(appsession == null)
        {
            LoginActivity.toLogin(this, getIntent());
            finish();
        } else
        {
            appsession.addListener(mRequestsListener);
            appsession.getFriendRequests(this, appsession.getSessionInfo().userId);
        }
        if(mNumRequests != null)
        {
            Iterator iterator = ((MyTabHost)getTabHost()).getTabSpecs().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                MyTabHost.TabSpec tabspec = (MyTabHost.TabSpec)iterator.next();
                if(tabspec.tag.equals("requests"))
                {
                    RadioButton radiobutton = (RadioButton)tabspec.view;
                    if(mNumRequests.intValue() == 0)
                    {
                        radiobutton.setText(getString(0x7f0a0088));
                    } else
                    {
                        Object aobj[] = new Object[1];
                        aobj[0] = mNumRequests;
                        radiobutton.setText(getString(0x7f0a017d, aobj));
                    }
                }
            } while(true);
        }
    }

    public boolean onSearchRequested()
    {
        return true;
    }

    public void onShowProgress(boolean flag)
    {
        View view = findViewById(0x7f0e00f1);
        int i;
        if(flag)
            i = 0;
        else
            i = 8;
        view.setVisibility(i);
    }

    public void onTabChanged(String s)
    {
        if(mCurrentTab != null && (mCurrentTab instanceof UsersTabProgressSource))
            ((UsersTabProgressSource)mCurrentTab).setProgressListener(null);
        mCurrentTab = getCurrentActivity();
        if(mCurrentTab instanceof UsersTabProgressSource)
        {
            ((UsersTabProgressSource)mCurrentTab).setProgressListener(this);
            ((UsersTabProgressSource)mCurrentTab).search(mTextBox.getText().toString().trim());
        }
        setUpTextHint(s);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        findFriendsActivityConsentCheck();
    }

    public static final int IMPORT_CONTACT_CONSENT = 0;
    public static final String INTENT_DEFAULT_TAB_KEY = "com.facebook.katana.DefaultTab";
    public static final String TAG_FRIENDS = "friends";
    public static final String TAG_PAGES_SEARCH = "page_search";
    public static final String TAG_REQUESTS = "requests";
    private Activity mCurrentTab;
    private Integer mNumRequests;
    private boolean mRequestsActivityStarted;
    private AppSessionListener mRequestsListener;
    private TextView mTextBox;


/*
    static Integer access$002(UsersTabHostActivity userstabhostactivity, Integer integer)
    {
        userstabhostactivity.mNumRequests = integer;
        return integer;
    }

*/


}
