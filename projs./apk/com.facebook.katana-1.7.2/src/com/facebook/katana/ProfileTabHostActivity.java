// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileTabHostActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.*;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.activity.media.AlbumsActivity;
import com.facebook.katana.activity.places.PlacesInfoActivity;
import com.facebook.katana.activity.profilelist.GroupMemberListActivity;
import com.facebook.katana.activity.stream.StreamActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.FqlGetPageFanStatus;
import com.facebook.katana.service.method.PagesAddFan;
import com.facebook.katana.service.method.PlacesFlag;
import com.facebook.katana.service.method.UserSetContactInfo;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.GrowthUtils;
import com.facebook.katana.util.StringUtils;
import org.json.JSONException;

// Referenced classes of package com.facebook.katana:
//            IntentUriHandler, LoginActivity, MyTabHost, TabProgressSource, 
//            UserInfoActivity, PageInfoActivity, TabProgressListener

public class ProfileTabHostActivity extends BaseFacebookTabActivity
    implements MyTabHost.OnTabChangeListener
{
    private class ProfileAppSessionListener extends AppSessionListener
    {

        public void onGetPageFanStatusComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                boolean flag)
        {
            if(l == mProfileId)
                if(!flag)
                {
                    mCanLike = true;
                    setPrimaryActionFace(-1, getString(0x7f0a01b7));
                } else
                {
                    mCanLike = false;
                    setPrimaryActionFace(-1, null);
                }
        }

        public void onPagesAddFanComplete(AppSession appsession, String s, int i, String s1, Exception exception, boolean flag)
        {
            if(i == 200 && s.equals(mPendingLikeReqId) && isOnTop())
            {
                ApplicationUtils.OpenPageProfile(ProfileTabHostActivity.this, mProfileId, null);
                findViewById(0x7f0e00f1).setVisibility(8);
                finish();
            }
        }

        public void onUserSetContactInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            if(i != 200)
                showDialog(3);
        }

        public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                FacebookUserFull facebookuserfull, boolean flag)
        {
            if(i == 200 && mProfileId == mMyUserId && mMyUserId == facebookuserfull.mUserId && StringUtils.isBlank(facebookuserfull.mCellPhone) && GrowthUtils.showPhoneNumberDialog(ProfileTabHostActivity.this))
            {
                GrowthUtils.stopPhoneNumberDialog(ProfileTabHostActivity.this);
                showDialog(2);
            }
        }

        final ProfileTabHostActivity this$0;

        private ProfileAppSessionListener()
        {
            this$0 = ProfileTabHostActivity.this;
            super();
        }

    }


    public ProfileTabHostActivity()
    {
    }

    public static Intent intentForProfile(Context context, long l)
    {
        return IntentUriHandler.getIntentForUri(context, (new StringBuilder()).append("fb://profile/").append(l).toString());
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, getIntent());
_L5:
        return;
_L2:
        Uri uri;
        String s;
        mMyUserId = mAppSession.getSessionInfo().userId;
        mProfileId = mMyUserId;
        uri = getIntent().getData();
        s = null;
        if(uri == null) goto _L4; else goto _L3
_L3:
        Cursor cursor;
        if(!uri.getScheme().equals("content") || !"com.android.contacts".equals(uri.getAuthority()))
            break MISSING_BLOCK_LABEL_177;
        ContentResolver contentresolver = getContentResolver();
        if(!"vnd.android.cursor.item/vnd.facebook.profile".equals(getIntent().resolveType(contentresolver)))
            break MISSING_BLOCK_LABEL_177;
        String as[] = new String[1];
        as[0] = "data1";
        cursor = contentresolver.query(uri, as, null, null, null);
        if(cursor == null)
            break MISSING_BLOCK_LABEL_165;
        if(cursor.moveToFirst())
            mProfileId = cursor.getLong(0);
        if(cursor != null)
            cursor.close();
_L6:
        if(s == null)
            s = "wall";
        Intent intent = getIntent();
        mProfileType = intent.getIntExtra("extra_user_type", 0);
        setContentView(0x7f030069);
        MyTabHost mytabhost = (MyTabHost)getTabHost();
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean("within_tab", true);
        bundle1.putString("extra_parent_tag", getTag());
        bundle1.putInt("extra_user_type", mProfileType);
        bundle1.putLong("extra_user_id", mProfileId);
        if(mProfileType == 2)
            bundle1.putParcelable("extra_place", intent.getParcelableExtra("extra_place"));
        Intent intent1;
        int i;
        android.widget.RadioButton radiobutton;
        MyTabHost.TabSpec tabspec;
        Intent intent2;
        TabProgressListener tabprogresslistener;
        ProfileAppSessionListener profileappsessionlistener;
        Exception exception;
        if(mProfileType == 0)
            intent1 = IntentUriHandler.getIntentForUri(this, (new StringBuilder()).append("fb://profile/").append(mProfileId).append("/wall/inner").toString());
        else
        if(mProfileType == 3)
            intent1 = IntentUriHandler.getIntentForUri(this, (new StringBuilder()).append("fb://group/").append(mProfileId).append("/wall/inner").toString());
        else
        if(mProfileType == 4)
        {
            intent1 = IntentUriHandler.getIntentForUri(this, (new StringBuilder()).append("fb://event/").append(mProfileId).append("/wall/inner").toString());
        } else
        {
            intent1 = new Intent(this, com/facebook/katana/activity/stream/StreamActivity);
            intent1.putExtra("extra_user_id", mProfileId);
            if(mProfileType == 2)
                intent1.putExtra("extra_type", FacebookStreamType.PLACE_ACTIVITY_STREAM.toString());
            else
            if(mProfileType == 3)
                intent1.putExtra("extra_type", FacebookStreamType.GROUP_WALL_STREAM.toString());
            else
            if(mProfileType == 1)
                intent1.putExtra("extra_type", FacebookStreamType.PAGE_WALL_STREAM.toString());
            else
                intent1.putExtra("extra_type", FacebookStreamType.PROFILE_WALL_STREAM.toString());
            intent1.putExtra("extra_user_display_name", intent.getStringExtra("extra_user_display_name"));
            intent1.putExtra("extra_image_url", intent.getStringExtra("extra_image_url"));
        }
        if(mProfileType == 2)
            i = 0x7f0a0145;
        else
            i = 0x7f0a0178;
        radiobutton = setupAndGetTabView("wall", i);
        intent1.putExtras(bundle1);
        tabspec = mytabhost.myNewTabSpec("wall", radiobutton);
        tabspec.setContent(intent1);
        mytabhost.addTab(tabspec);
        intent2 = null;
        if(mProfileType == 2)
            intent2 = new Intent(this, com/facebook/katana/activity/places/PlacesInfoActivity);
        else
        if(mProfileType == 0)
        {
            intent2 = new Intent(this, com/facebook/katana/UserInfoActivity);
            intent2.putExtra("com.facebook.katana.profile.id", mProfileId);
            intent2.putExtra("com.facebook.katana.profile.show_photo", false);
        } else
        if(mProfileType == 1)
        {
            intent2 = new Intent(this, com/facebook/katana/PageInfoActivity);
            intent2.putExtra("com.facebook.katana.profile.id", mProfileId);
            intent2.putExtra("com.facebook.katana.profile.show_photo", false);
        }
        if(intent2 != null)
        {
            intent2.putExtras(bundle1);
            MyTabHost.TabSpec tabspec3 = mytabhost.myNewTabSpec("info", setupAndGetTabView("info", 0x7f0a0152));
            tabspec3.setContent(intent2);
            mytabhost.addTab(tabspec3);
        }
        if(mProfileType == 3)
        {
            Intent intent3 = new Intent(this, com/facebook/katana/activity/profilelist/GroupMemberListActivity);
            intent3.putExtra("group_id", mProfileId);
            intent3.putExtras(bundle1);
            MyTabHost.TabSpec tabspec1 = mytabhost.myNewTabSpec("group_members", setupAndGetTabView("group_members", 0x7f0a0150));
            tabspec1.setContent(intent3);
            mytabhost.addTab(tabspec1);
        } else
        {
            Intent intent4 = new Intent(this, com/facebook/katana/activity/media/AlbumsActivity);
            intent4.setAction("android.intent.action.VIEW");
            intent4.putExtras(bundle1);
            if(mProfileId != mMyUserId)
                intent4.putExtra("extra_exclude_empty", true);
            intent4.setData(Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, String.valueOf(mProfileId)));
            MyTabHost.TabSpec tabspec2 = mytabhost.myNewTabSpec("photos", setupAndGetTabView("photos", 0x7f0a015d));
            tabspec2.setContent(intent4);
            mytabhost.addTab(tabspec2);
        }
        setupTabs();
        mytabhost.setCurrentTabByTag(s);
        onTabChanged(s);
        mytabhost.setOnTabChangedListener(this);
        tabprogresslistener = new TabProgressListener() {

            public void onShowProgress(boolean flag)
            {
                View view = findViewById(0x7f0e00f1);
                int j;
                if(flag)
                    j = 0;
                else
                    j = 8;
                view.setVisibility(j);
            }

            final ProfileTabHostActivity this$0;

            
            {
                this$0 = ProfileTabHostActivity.this;
                super();
            }
        }
;
        mProgressListener = tabprogresslistener;
        mCurrentSource = (TabProgressSource)getCurrentActivity();
        mCurrentSource.setProgressListener(mProgressListener);
        profileappsessionlistener = new ProfileAppSessionListener();
        mAppSessionListener = profileappsessionlistener;
        if(mProfileId == mMyUserId && GrowthUtils.showPhoneNumberDialog(this))
            mAppSession.usersGetInfo(this, mMyUserId);
        if(true) goto _L5; else goto _L4
        exception;
        if(cursor != null)
            cursor.close();
        throw exception;
_L4:
        mProfileId = getIntent().getLongExtra("extra_user_id", mMyUserId);
        s = getIntent().getStringExtra("tab");
          goto _L6
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 3: default 28
    //                   1 32
    //                   2 162
    //                   3 270;
           goto _L1 _L2 _L3 _L4
_L1:
        Object obj = null;
_L6:
        return ((Dialog) (obj));
_L2:
        CharSequence acharsequence[] = new CharSequence[4];
        acharsequence[0] = getText(0x7f0a0248);
        acharsequence[1] = getText(0x7f0a0244);
        acharsequence[2] = getText(0x7f0a0260);
        acharsequence[3] = getText(0x7f0a0247);
        final String itemFlags[] = new String[4];
        itemFlags[0] = PlacesFlag.INFO_INCORRECT;
        itemFlags[1] = PlacesFlag.OFFENSIVE;
        itemFlags[2] = PlacesFlag.CLOSED;
        itemFlags[3] = PlacesFlag.DUPLICATE;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(0x7f0a0243);
        builder.setItems(acharsequence, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                String s1 = itemFlags[j];
                View view;
                Toast toast;
                try
                {
                    FacebookPlace facebookplace = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
                    PlacesFlag.FlagPlace(mAppSession, ProfileTabHostActivity.this, facebookplace, s1);
                }
                catch(JSONException jsonexception) { }
                dialoginterface.dismiss();
                view = getLayoutInflater().inflate(0x7f03000d, (ViewGroup)findViewById(0x7f0e002c));
                ((ImageView)view.findViewById(0x7f0e002d)).setImageResource(0x7f02004c);
                ((TextView)view.findViewById(0x7f0e002e)).setText(0x7f0a0246);
                toast = new Toast(getApplicationContext());
                toast.setView(view);
                toast.setGravity(17, 0, 0);
                toast.show();
            }

            final ProfileTabHostActivity this$0;
            final String val$itemFlags[];

            
            {
                this$0 = ProfileTabHostActivity.this;
                itemFlags = as;
                super();
            }
        }
);
        obj = builder.create();
        continue; /* Loop/switch isn't completed */
_L3:
        final View dialogView = LayoutInflater.from(this).inflate(0x7f03004f, null);
        String s = ((TelephonyManager)getSystemService("phone")).getLine1Number();
        if(!StringUtils.isBlank(s))
            ((EditText)dialogView.findViewById(0x7f0e00e2)).setText(s);
        obj = (new android.app.AlertDialog.Builder(this)).setCancelable(true).setTitle(0x7f0a0286).setView(dialogView).setPositiveButton(0x7f0a0289, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                String s1 = ((EditText)dialogView.findViewById(0x7f0e00e2)).getText().toString();
                UserSetContactInfo.setCellNumber(mAppSession, ProfileTabHostActivity.this, s1);
                removeDialog(2);
            }

            final ProfileTabHostActivity this$0;
            final View val$dialogView;

            
            {
                this$0 = ProfileTabHostActivity.this;
                dialogView = view;
                super();
            }
        }
).setNegativeButton(0x7f0a025c, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final ProfileTabHostActivity this$0;

            
            {
                this$0 = ProfileTabHostActivity.this;
                super();
            }
        }
).create();
        continue; /* Loop/switch isn't completed */
_L4:
        obj = (new android.app.AlertDialog.Builder(this)).setCancelable(true).setTitle(0x7f0a004d).setMessage(0x7f0a028a).setPositiveButton(0x7f0a01fb, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
                showDialog(2);
            }

            final ProfileTabHostActivity this$0;

            
            {
                this$0 = ProfileTabHostActivity.this;
                super();
            }
        }
).setNegativeButton(0x7f0a025c, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final ProfileTabHostActivity this$0;

            
            {
                this$0 = ProfileTabHostActivity.this;
                super();
            }
        }
).create();
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        if(mProfileType != 2) goto _L2; else goto _L1
_L1:
        menu.add(0, 101, 0x30000, 0x7f0a0245).setIcon(0x7f0200af);
_L4:
        return true;
_L2:
        if(mProfileType == 0 && mProfileId != mMyUserId)
            menu.add(0, 102, 0x30000, 0x7f0a0179).setIcon(0x7f0200ad);
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mCurrentSource != null)
            mCurrentSource.setProgressListener(null);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 101 102: default 28
    //                   101 34
    //                   102 42;
           goto _L1 _L2 _L3
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        showDialog(1);
        continue; /* Loop/switch isn't completed */
_L3:
        IntentUriHandler.handleUri(this, (new StringBuilder()).append("fb://messaging/compose/").append(mProfileId).toString());
        if(true) goto _L1; else goto _L4
_L4:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(mProfileType == 1)
                FqlGetPageFanStatus.RequestPageFanStatus(this, mProfileId);
            mAppSession.addListener(mAppSessionListener);
        }
    }

    public void onTabChanged(String s)
    {
        if(mCurrentSource != null)
            mCurrentSource.setProgressListener(null);
        mCurrentSource = (TabProgressSource)getCurrentActivity();
        mCurrentSource.setProgressListener(mProgressListener);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        if(mCanLike)
        {
            PagesAddFan pagesaddfan = new PagesAddFan(this, null, mAppSession.getSessionInfo().sessionKey, mProfileId, null);
            mPendingLikeReqId = mAppSession.postToService(this, pagesaddfan, 1001, 1020, null);
            setPrimaryActionFace(-1, null);
            findViewById(0x7f0e00f1).setVisibility(0);
        }
    }

    public static final String EXTRA_STARTING_TAB = "tab";
    private static final int FLAG_PLACES_DIALOG = 1;
    private static final int FLAG_PLACES_ID = 101;
    private static final int MESSAGE_ID = 102;
    private static final int PHONE_NUMBER_ENTER = 2;
    private static final int PHONE_NUMBER_FAIL = 3;
    public static final String TAG_GROUP_MEMBERS = "group_members";
    public static final String TAG_INFO = "info";
    public static final String TAG_PHOTOS = "photos";
    public static final String TAG_WALL = "wall";
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private boolean mCanLike;
    private TabProgressSource mCurrentSource;
    private long mMyUserId;
    private String mPendingLikeReqId;
    private long mProfileId;
    private int mProfileType;
    private TabProgressListener mProgressListener;




/*
    static boolean access$202(ProfileTabHostActivity profiletabhostactivity, boolean flag)
    {
        profiletabhostactivity.mCanLike = flag;
        return flag;
    }

*/




}
