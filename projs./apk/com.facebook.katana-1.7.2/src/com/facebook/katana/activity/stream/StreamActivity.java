// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamActivity.java

package com.facebook.katana.activity.stream;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.*;
import com.facebook.katana.*;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.activity.feedback.FeedbackActivity;
import com.facebook.katana.activity.findfriends.FindFriendsActivity;
import com.facebook.katana.activity.findfriends.LegalDisclaimerActivity;
import com.facebook.katana.activity.media.MediaUploader;
import com.facebook.katana.activity.places.PlacesOptInActivity;
import com.facebook.katana.activity.profilelist.FriendMultiSelectorActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.places.PlacesUtils;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.method.*;
import com.facebook.katana.ui.TaggingAutoCompleteTextView;
import com.facebook.katana.util.*;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewContainer;
import java.util.*;
import org.json.JSONException;

// Referenced classes of package com.facebook.katana.activity.stream:
//            StreamAdapter

public class StreamActivity extends ProfileFacebookListActivity
    implements TabProgressSource, com.facebook.katana.util.FBLocationManager.FBLocationListener
{
    protected class StreamAppSessionListener extends AppSessionListener
    {

        public void onCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
        {
            if(s.equals(mPublishReqId))
            {
                removeDialog(5);
                mPublishReqId = null;
                if(i == 200)
                {
                    ApplicationUtils.OpenPlaceProfile(StreamActivity.this, mPlace, Integer.valueOf(0x4000000));
                } else
                {
                    String s2 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a0240), i, s1, exception);
                    Toaster.toast(StreamActivity.this, s2);
                }
            }
        }

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            mAdapter.updatePhoto(bitmap, s2);
        }

        public void onGkSettingsGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, boolean flag)
        {
            if(i == 200 && "places".equals(s2) && flag && mType == FacebookStreamType.PLACE_ACTIVITY_STREAM)
            {
                mPlacesEnabled = true;
                FBLocationManager.addLocationListener(StreamActivity.this, StreamActivity.this);
            }
        }

        public void onHomeStreamUpdated(AppSession appsession)
        {
            if(mType == FacebookStreamType.NEWSFEED_STREAM)
                mAdapter.refreshStream();
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mAdapter.updatePhoto(bitmap, s);
        }

        public void onPhotoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, FacebookPhoto facebookphoto, 
                String s2, long l, long l1, long l2)
        {
            if(mLastCheckin != null && l == mLastCheckin.mCheckinId || l1 == mUserId)
                refresh();
        }

        public void onPlacesEditCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                String s2, Set set)
        {
            if(i == 200)
            {
                refreshLastCheckin();
                updateTagButton();
                refresh();
            } else
            {
                String s3 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a0240), i, s1, exception);
                Toaster.toast(StreamActivity.this, s3);
            }
        }

        public void onPlacesRemoveTagComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost, long l)
        {
            if(i == 200)
                refresh();
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        public void onSetTaggingOptInStatusComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            if(i == 200)
                doCheckin();
        }

        public void onStreamGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                int j, FacebookStreamContainer facebookstreamcontainer, List list)
        {
            if(mType == FacebookStreamType.PLACE_ACTIVITY_STREAM && l == mUserId) goto _L2; else goto _L1
_L1:
            return;
_L2:
            showProgress(false);
            if(j == 2)
                mAdapter.loadingMore(false);
            if(i == 200)
            {
                logStepDataReceived();
                mAdapter.refreshStream(facebookstreamcontainer);
                if(facebookstreamcontainer.getPostCount() == 0)
                {
                    TextView textview = (TextView)findViewById(0x7f0e012f);
                    textview.setHeight(0);
                    textview.invalidate();
                }
            } else
            {
                String s2 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a01b4), i, s1, exception);
                Toaster.toast(StreamActivity.this, s2);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onStreamGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                long al[], long l1, long l2, int j, int k, 
                List list, FacebookStreamContainer facebookstreamcontainer)
        {
            class _cls15
            {

                static final int $SwitchMap$com$facebook$katana$model$FacebookStreamType[];

                static 
                {
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType = new int[FacebookStreamType.values().length];
                    NoSuchFieldError nosuchfielderror4;
                    try
                    {
                        $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.NEWSFEED_STREAM.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.GROUP_WALL_STREAM.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PROFILE_WALL_STREAM.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    try
                    {
                        $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PAGE_WALL_STREAM.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PLACE_ACTIVITY_STREAM.ordinal()] = 5;
_L2:
                    return;
                    nosuchfielderror4;
                    if(true) goto _L2; else goto _L1
_L1:
                }
            }

            _cls15..SwitchMap.com.facebook.katana.model.FacebookStreamType[mType.ordinal()];
            JVM INSTR tableswitch 1 5: default 48
        //                       1 141
        //                       2 159
        //                       3 159
        //                       4 159
        //                       5 140;
               goto _L1 _L2 _L3 _L3 _L3 _L4
_L1:
            showProgress(false);
            if(k == 2)
                mAdapter.loadingMore(false);
            if(i == 200)
            {
                logStepDataReceived();
                mAdapter.refreshStream(facebookstreamcontainer);
                if(facebookstreamcontainer.getPostCount() > 0)
                {
                    Calendar calendar = Calendar.getInstance();
                    if(mType == FacebookStreamType.NEWSFEED_STREAM)
                        listViewContainer.setLastLoadedTime(calendar.getTimeInMillis());
                } else
                {
                    handleStreamError(facebookstreamcontainer);
                }
            } else
            {
                String s2 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a01b4), i, s1, exception);
                Toaster.toast(StreamActivity.this, s2);
            }
_L4:
            return;
_L2:
            listViewContainer.notifyLoadingFinished();
            if(al == null) goto _L5; else goto _L4
_L5:
            break; /* Loop/switch isn't completed */
_L3:
            if(al != null && al[0] == mUserId) goto _L1; else goto _L4
        }

        public void onStreamPublishComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
        {
            removeDialog(2);
            mPublishReqId = null;
            if(i == 200)
            {
                ((EditText)findViewById(0x7f0e0128)).setText(null);
                mAdapter.refreshStream();
            } else
            {
                String s2 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a01c4), i, s1, exception);
                Toaster.toast(StreamActivity.this, s2);
            }
        }

        public void onStreamRemovePostComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            removeDialog(3);
            mRemoveReqId = null;
            if(i == 200)
            {
                mAdapter.refreshStream();
            } else
            {
                String s2 = StringUtils.getErrorString(StreamActivity.this, getString(0x7f0a01cc), i, s1, exception);
                Toaster.toast(StreamActivity.this, s2);
            }
        }

        public void onVideoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookVideoUploadResponse facebookvideouploadresponse, String s2)
        {
            refresh();
        }

        final StreamActivity this$0;

        protected StreamAppSessionListener()
        {
            this$0 = StreamActivity.this;
            super();
        }
    }


    public StreamActivity()
    {
    }

    private long[] getSourceIds()
    {
        long al[];
        if(mAppSession.getSessionInfo().userId == mUserId)
        {
            if(mType == FacebookStreamType.PROFILE_WALL_STREAM)
            {
                al = new long[1];
                al[0] = mUserId;
            } else
            {
                al = null;
            }
        } else
        {
            al = new long[1];
            al[0] = mUserId;
        }
        return al;
    }

    private void goToProfile(FacebookProfile facebookprofile)
    {
        Intent intent = ProfileTabHostActivity.intentForProfile(this, facebookprofile.mId);
        intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
        intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
        intent.putExtra("extra_user_type", facebookprofile.mType);
        startActivityForResult(intent, 3);
    }

    private boolean handleStreamError(FacebookStreamContainer facebookstreamcontainer)
    {
        boolean flag = false;
        if(facebookstreamcontainer.getPostCount() != 0 || mUserId == mAppSession.getSessionInfo().userId || mType == FacebookStreamType.PLACE_ACTIVITY_STREAM) goto _L2; else goto _L1
_L1:
        if(mType != FacebookStreamType.PROFILE_WALL_STREAM) goto _L4; else goto _L3
_L3:
        Intent intent = new Intent(this, com/facebook/katana/UserInfoActivity);
        intent.putExtra("com.facebook.katana.profile.id", mUserId);
        intent.putExtra("com.facebook.katana.profile.show_photo", true);
        intent.putExtra("com.facebook.katana.profile.is.limited", true);
        startActivity(intent);
        finish();
        flag = true;
_L2:
        return flag;
_L4:
        if(mType == FacebookStreamType.PAGE_WALL_STREAM)
        {
            Intent intent1 = new Intent(this, com/facebook/katana/PageInfoActivity);
            intent1.putExtra("com.facebook.katana.profile.id", mUserId);
            intent1.putExtra("com.facebook.katana.profile.show_photo", true);
            intent1.putExtra("com.facebook.katana.profile.connected", false);
            startActivity(intent1);
            finish();
            flag = true;
        }
        if(true) goto _L2; else goto _L5
_L5:
    }

    private void openMediaItem(com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem)
    {
        mAppSession.openMediaItem(this, mediaitem);
    }

    private void refresh()
    {
        streamGet(null);
        showProgress(true);
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
            listViewContainer.notifyLoading();
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a01bf);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a01b9);
    }

    private void showProgress(boolean flag)
    {
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag);
        mShowingProgress = flag;
        View view = findViewById(0x7f0e00f1);
        if(flag)
        {
            if(view != null)
                view.setVisibility(0);
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            if(view != null)
                view.setVisibility(8);
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    protected void doCheckin()
    {
        if(Boolean.valueOf(PlacesUtils.checkOptInStatus(mAppSession, this)).booleanValue())
        {
            Intent intent = new Intent(this, com/facebook/katana/activity/places/PlacesOptInActivity);
            intent.putExtra("place_name", mPlace.mName);
            intent.putExtra("user_profile", new FacebookProfile(mAppSession.getSessionInfo().getProfile()));
            intent.putExtra("optin_origin", "checkin");
            startActivityForResult(intent, 16);
        } else
        {
            String s = ((EditText)findViewById(0x7f0e0128)).getText().toString().trim();
            showDialog(5);
            try
            {
                mPublishReqId = mAppSession.checkin(this, mPlace, mLastKnownLocation, s, mTaggedUids, null, null);
            }
            catch(JSONException jsonexception)
            {
                removeDialog(5);
                Toaster.toast(this, 0x7f0a0240);
            }
        }
    }

    protected void findFriendsActivityConsentCheck()
    {
        if(!GrowthUtils.shouldShowLegalScreen(this))
            startActivity(new Intent(this, com/facebook/katana/activity/findfriends/FindFriendsActivity));
        else
            startActivityForResult(new Intent(this, com/facebook/katana/activity/findfriends/LegalDisclaimerActivity), 21);
    }

    public void hideCheckinInterface()
    {
        View view = findViewById(0x7f0e012b);
        View view1 = findViewById(0x7f0e0128);
        View view2 = findViewById(0x7f0e012c);
        View view3 = findViewById(0x7f0e012d);
        View view4 = findViewById(0x7f0e0129);
        view.setVisibility(8);
        view1.setVisibility(8);
        view2.setVisibility(8);
        view3.setVisibility(8);
        view4.setVisibility(8);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        switch(i)
        {
        case 2: // '\002'
        case 3: // '\003'
            if(intent != null)
                if(mType == FacebookStreamType.PROFILE_WALL_STREAM || mType == FacebookStreamType.GROUP_WALL_STREAM)
                {
                    if(intent.getBooleanExtra("extra_ptf", false))
                    {
                        setResult(-1, intent);
                        finish();
                    }
                } else
                if(intent.getBooleanExtra("extra_logout", false))
                    LoginActivity.toLogin(this);
            break;

        case 133701: 
        case 133702: 
        case 133703: 
        case 133704: 
            if(intent == null)
                intent = new Intent();
            if(mRecentCheckin && mLastCheckin != null)
                intent.putExtra("checkin_id", mLastCheckin.mCheckinId);
            mMediaUploader.onActivityResult(i, j, intent);
            break;

        case 101: // 'e'
            if(j == -1)
            {
                if(intent.hasExtra("profiles"))
                    mTaggedUids = IntentUtils.primitiveToSet(intent.getLongArrayExtra("profiles"));
                if(mRecentCheckin && mLastCheckin != null)
                    try
                    {
                        PlacesEditCheckin.EditCheckin(AppSession.getActiveSession(this, false), this, mLastCheckin.mCheckinId, null, mTaggedUids);
                    }
                    catch(JSONException jsonexception) { }
                updateTagButton();
            }
            break;

        case 16: // '\020'
            if(j == -1)
                PlacesSetTaggingOptInStatus.SetStatus(this, 1);
            break;

        case 21: // '\025'
            if(j == -1)
            {
                GrowthUtils.setFindFriendsConsentApproved(this);
                GrowthUtils.stopFindFriendsDialog(this);
                startActivity(new Intent(this, com/facebook/katana/activity/findfriends/FindFriendsActivity));
            }
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
        int i = adaptercontextmenuinfo.position - mHeaderViewCount;
        menuitem.getItemId();
        JVM INSTR tableswitch 2 13: default 92
    //                   2 102
    //                   3 194
    //                   4 213
    //                   5 308
    //                   6 264
    //                   7 383
    //                   8 435
    //                   9 92
    //                   10 92
    //                   11 354
    //                   12 354
    //                   13 354;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L1 _L1 _L9 _L9 _L9
_L1:
        boolean flag = true;
_L10:
        return flag;
        ClassCastException classcastexception;
        classcastexception;
        flag = false;
        if(true) goto _L10; else goto _L2
_L2:
        FacebookPost facebookpost3 = mAdapter.getItemByPosition(i);
        Intent intent = new Intent(this, com/facebook/katana/activity/feedback/FeedbackActivity);
        intent.putExtra("extra_post_id", facebookpost3.postId);
        intent.putExtra("extra_uid", mUserId);
        if(mProfile != null && ConnectionsProvider.getAdminProfile(this, mProfileId) != null)
            intent.putExtra("comment_actor", mProfile);
        startActivityForResult(intent, 2);
        continue; /* Loop/switch isn't completed */
_L3:
        goToProfile(mAdapter.getItemByPosition(i).getProfile());
        continue; /* Loop/switch isn't completed */
_L4:
        FacebookPost facebookpost2 = mAdapter.getItemByPosition(i);
        if(facebookpost2.getTargetProfile().mId == mUserId)
            goToProfile(facebookpost2.getProfile());
        else
            goToProfile(facebookpost2.getTargetProfile());
        continue; /* Loop/switch isn't completed */
_L6:
        com.facebook.katana.model.FacebookPost.Attachment attachment1 = mAdapter.getItemByPosition(i).getAttachment();
        if(attachment1 != null && attachment1.href != null)
            mAppSession.openURL(this, attachment1.href);
        continue; /* Loop/switch isn't completed */
_L5:
        FacebookPost facebookpost1 = mAdapter.getItemByPosition(i);
        mRemoveReqId = mAppSession.streamRemovePost(this, mAppSession.getSessionInfo().userId, facebookpost1.postId);
        showDialog(3);
        continue; /* Loop/switch isn't completed */
_L9:
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(menuitem.getTitle().toString())));
        continue; /* Loop/switch isn't completed */
_L7:
        FacebookPost facebookpost = mAdapter.getItemByPosition(i);
        adaptercontextmenuinfo.targetView.findViewById(0x7f0e008d).setVisibility(4);
        PlacesRemoveTag.RemoveTag(AppSession.getActiveSession(this, false), this, facebookpost, AppSession.getActiveSession(this, false).getSessionInfo().userId);
        continue; /* Loop/switch isn't completed */
_L8:
        com.facebook.katana.model.FacebookPost.Attachment attachment = mAdapter.getItemByPosition(i).getAttachment();
        if(attachment != null && attachment.getMediaItems().size() > 0)
            openMediaItem((com.facebook.katana.model.FacebookPost.Attachment.MediaItem)attachment.getMediaItems().get(0));
        if(true) goto _L1; else goto _L11
_L11:
    }

    public void onCreate(Bundle bundle)
    {
        if(getIntent().getBooleanExtra("within_tab", false))
            mHasFatTitleHeader = true;
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, getIntent());
_L4:
        return;
_L2:
        long l = mAppSession.getSessionInfo().userId;
        mUserId = getIntent().getLongExtra("extra_user_id", l);
        String s = getIntent().getStringExtra("extra_type");
        if(s != null)
            mType = (FacebookStreamType)Enum.valueOf(com/facebook/katana/model/FacebookStreamType, s);
        else
            mType = FacebookStreamType.NEWSFEED_STREAM;
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
        {
            setContentView(0x7f03007b);
            listViewContainer = (RefreshableListViewContainer)findViewById(0x7f0e0132);
            listViewContainer.setOnRefreshListener(new com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewContainer.OnRefreshListener() {

                public void onRefresh()
                {
                    refresh();
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
        } else
        {
            setContentView(0x7f030079);
        }
        if(mType == FacebookStreamType.NEWSFEED_STREAM && !$assertionsDisabled && mUserId != mAppSession.getSessionInfo().userId)
            throw new AssertionError();
        mAppSessionListeners = new ArrayList();
        mAppSessionListeners.add(new StreamAppSessionListener());
        if(getParent() != null)
            findViewById(0x7f0e0016).setVisibility(8);
        else
            setPrimaryActionIcon(0x7f0200f9);
        setupEmptyView();
        getListView().setOnCreateContextMenuListener(this);
        sStackSize = 1 + sStackSize;
        if(mUserId != mAppSession.getSessionInfo().userId)
            mMediaUploader = new MediaUploader(this, mUserId);
        else
            mMediaUploader = new MediaUploader(this, null);
        if(mType != FacebookStreamType.PLACE_ACTIVITY_STREAM)
            mListHeaders.add(Integer.valueOf(0x7f030074));
        else
            mListHeaders.add(Integer.valueOf(0x7f030075));
        if(mType == FacebookStreamType.GROUP_WALL_STREAM)
            MarkGroupRead.Request(this, mUserId);
        setupListHeaders();
        mHeaderViewCount = mListHeaders.size();
        setupFatTitleHeader();
        setupShareBar();
        if(!mAppSession.isStreamGetPending(mUserId, mType))
            streamGet(null);
        showProgress(true);
        if(mType == FacebookStreamType.NEWSFEED_STREAM && GrowthUtils.showFindFriendsDialog(this))
            showDialog(6);
        if(mType == FacebookStreamType.PAGE_WALL_STREAM)
        {
            final String fqlPageRequestId = FqlGetPages.RequestPageInfo(this, (new StringBuilder()).append("page_id=").append(mProfileId).toString(), com/facebook/katana/model/FacebookPage);
            mAppSession.addListener(new AppSessionListener() );
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo;
        if(adaptercontextmenuinfo.position >= mHeaderViewCount) goto _L2; else goto _L1
_L1:
        return;
        ClassCastException classcastexception;
        classcastexception;
        FacebookPost facebookpost;
        if(true) goto _L1; else goto _L2
_L2:
        if((facebookpost = mAdapter.getItemByPosition(adaptercontextmenuinfo.position - mHeaderViewCount)) == null) goto _L1; else goto _L3
_L3:
        switch(facebookpost.getPostType())
        {
        default:
            break;

        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            contextmenu.setHeaderTitle(facebookpost.getProfile().mDisplayName);
            break; /* Loop/switch isn't completed */
        }
        if(true) goto _L1; else goto _L4
_L4:
        String s;
        if(facebookpost.canInteractWithFeedback())
            contextmenu.add(0, 2, 0, 0x7f0a01b3);
        if(facebookpost.getPostType() == 1)
            contextmenu.add(0, 6, 0, 0x7f0a01b5);
        if(facebookpost.getPostType() == 4 && facebookpost.mTaggedIds.contains(Long.valueOf(mAppSession.getSessionInfo().userId)))
            contextmenu.add(0, 7, 0, 0x7f0a0263);
        if(facebookpost.getPostType() == 3)
            contextmenu.add(0, 8, 0, 0x7f0a01d9);
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
            contextmenu.add(0, 3, 0, 0x7f0a01d7);
        if(facebookpost.getTargetProfile() != null)
        {
            ArrayList arraylist;
            int i;
            Iterator iterator;
            String s1;
            Object aobj[];
            if(facebookpost.getTargetProfile().mId == mUserId)
                s1 = facebookpost.getProfile().mDisplayName;
            else
                s1 = facebookpost.getTargetProfile().mDisplayName;
            aobj = new Object[1];
            aobj[0] = s1;
            contextmenu.add(0, 4, 0, getString(0x7f0a01d8, aobj));
        }
        if(mType == FacebookStreamType.PROFILE_WALL_STREAM && mUserId == mAppSession.getSessionInfo().userId && facebookpost.appId == 0xa67c8e50L)
            contextmenu.add(0, 5, 0, 0x7f0a01cb);
        arraylist = new ArrayList();
        StringUtils.parseExpression(facebookpost.message, "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*", null, arraylist, 3);
        i = 0;
        iterator = arraylist.iterator();
_L10:
        if(!iterator.hasNext()) goto _L1; else goto _L5
_L5:
        s = (String)iterator.next();
        i;
        JVM INSTR tableswitch 0 2: default 448
    //                   0 467
    //                   1 483
    //                   2 499;
           goto _L6 _L7 _L8 _L9
_L6:
        break; /* Loop/switch isn't completed */
_L9:
        break MISSING_BLOCK_LABEL_499;
_L11:
        i++;
          goto _L10
_L7:
        contextmenu.add(0, 11, 0, s);
          goto _L11
_L8:
        contextmenu.add(0, 12, 0, s);
          goto _L11
        contextmenu.add(0, 13, 0, s);
          goto _L11
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR lookupswitch 7: default 68
    //                   2: 72
    //                   3: 164
    //                   5: 118
    //                   6: 237
    //                   255255255: 204
    //                   255255256: 215
    //                   255255257: 226;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
        Object obj = null;
_L10:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog2 = new ProgressDialog(this);
        progressdialog2.setProgressStyle(0);
        progressdialog2.setMessage(getText(0x7f0a01c5));
        progressdialog2.setIndeterminate(true);
        progressdialog2.setCancelable(false);
        obj = progressdialog2;
        continue; /* Loop/switch isn't completed */
_L4:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a023c));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a01ce));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L6:
        obj = mMediaUploader.createDialog();
        continue; /* Loop/switch isn't completed */
_L7:
        obj = mMediaUploader.createPhotoDialog();
        continue; /* Loop/switch isn't completed */
_L8:
        obj = mMediaUploader.createVideoDialog();
        continue; /* Loop/switch isn't completed */
_L5:
        GrowthUtils.stopFindFriendsDialog(this);
        obj = (new Builder(this)).setCancelable(true).setTitle(0x7f0a0283).setMessage(0x7f0a0284).setPositiveButton(0x7f0a0285, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                findFriendsActivityConsentCheck();
            }

            final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
        }
).setNegativeButton(0x7f0a025c, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
        }
).create();
        if(true) goto _L10; else goto _L9
_L9:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        onCreateOptionsMenu(menu);
        menu.add(0, 12, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        return true;
    }

    protected void onDestroy()
    {
        onDestroy();
        sStackSize--;
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
        {
            android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt("stream_scroll_pos", getListView().getFirstVisiblePosition());
            editor.commit();
        }
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        FacebookPost facebookpost;
        int j = i - mHeaderViewCount;
        facebookpost = mAdapter.getItemByPosition(j);
        if(facebookpost != null) goto _L2; else goto _L1
_L1:
        if(!mAdapter.isLoadingMore())
        {
            FacebookStreamContainer facebookstreamcontainer = mAppSession.getStreamContainer(mUserId, mType);
            if(facebookstreamcontainer != null)
            {
                int k = facebookstreamcontainer.getPostCount();
                if(k > 0)
                {
                    FacebookPost facebookpost1 = facebookstreamcontainer.getPost(k - 1);
                    mAdapter.loadingMore(true);
                    streamGet(facebookpost1);
                    showProgress(true);
                }
            }
        }
_L4:
        return;
_L2:
        switch(facebookpost.getPostType())
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            if(facebookpost.canInteractWithFeedback())
            {
                Intent intent = new Intent(this, com/facebook/katana/activity/feedback/FeedbackActivity);
                intent.putExtra("extra_post_id", facebookpost.postId);
                intent.putExtra("extra_uid", mUserId);
                if(mProfile != null && ConnectionsProvider.getAdminProfile(this, mProfileId) != null)
                    intent.putExtra("comment_actor", mProfile);
                startActivityForResult(intent, 2);
            }
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onLocationChanged(Location location)
    {
        if(!$assertionsDisabled && mType != FacebookStreamType.PLACE_ACTIVITY_STREAM)
            throw new AssertionError();
        boolean flag = false;
        float f = -1F;
        if(location != null)
        {
            float af[] = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), mPlace.mLatitude, mPlace.mLongitude, af);
            if(af[0] <= 3000F)
                flag = true;
            f = af[0];
            mLastKnownLocation = location;
        }
        TextView textview = (TextView)findViewById(0x7f0e012e);
        if(flag)
        {
            showCheckinInterface();
            textview.setVisibility(8);
        } else
        {
            hideCheckinInterface();
            if(f < 0F)
            {
                textview.setVisibility(8);
            } else
            {
                textview.setVisibility(0);
                textview.setText(StringUtils.formatDistance(this, f));
                if(!$assertionsDisabled && mPlace == null)
                    throw new AssertionError();
                textview.setOnClickListener(new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        String s = LocationUtils.generateGeoIntentURI(mPlace.mName, mPlace.mLatitude, mPlace.mLongitude);
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s)));
_L2:
                        return;
                        ActivityNotFoundException activitynotfoundexception;
                        activitynotfoundexception;
                        if(true) goto _L2; else goto _L1
_L1:
                    }

                    final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
                }
);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 12 12: default 24
    //                   12 30;
           goto _L1 _L2
_L1:
        return onOptionsItemSelected(menuitem);
_L2:
        refresh();
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void onPause()
    {
        onPause();
        if(mType == FacebookStreamType.PLACE_ACTIVITY_STREAM)
            FBLocationManager.removeLocationListener(this);
        if(mAppSession != null)
        {
            AppSessionListener appsessionlistener;
            for(Iterator iterator = mAppSessionListeners.iterator(); iterator.hasNext(); mAppSession.removeListener(appsessionlistener))
                appsessionlistener = (AppSessionListener)iterator.next();

        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        onPrepareOptionsMenu(menu);
        MenuItem menuitem = menu.findItem(12);
        boolean flag;
        if(!mAppSession.isStreamGetPending(mUserId, mType))
            flag = true;
        else
            flag = false;
        menuitem.setEnabled(flag);
        return true;
    }

    protected void onResume()
    {
        onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L9:
        return;
_L2:
        FacebookStreamContainer facebookstreamcontainer;
        if(mRemoveReqId != null && !mAppSession.isRequestPending(mRemoveReqId))
        {
            removeDialog(3);
            mRemoveReqId = null;
        }
        if(mPublishReqId != null && !mAppSession.isRequestPending(mPublishReqId))
        {
            removeDialog(2);
            removeDialog(5);
            mPublishReqId = null;
        }
        AppSessionListener appsessionlistener;
        for(Iterator iterator = mAppSessionListeners.iterator(); iterator.hasNext(); mAppSession.addListener(appsessionlistener))
            appsessionlistener = (AppSessionListener)iterator.next();

        facebookstreamcontainer = mAppSession.getStreamContainer(mUserId, mType);
        ListView listview;
        ProfileImagesCache profileimagescache;
        com.facebook.katana.binding.StreamPhotosCache streamphotoscache;
        StreamAdapter.StreamAdapterListener streamadapterlistener;
        int i;
        long l1;
        long l2;
        if(facebookstreamcontainer == null || System.currentTimeMillis() - facebookstreamcontainer.getLastGetAllTime() >= 0x927c0L)
        {
            if(!mAppSession.isStreamGetPending(mUserId, mType))
                streamGet(null);
            showProgress(true);
        } else
        if(mAppSession.isStreamGetPending(mUserId, mType))
            showProgress(true);
        else
            showProgress(false);
        if(mType != FacebookStreamType.PLACE_ACTIVITY_STREAM) goto _L4; else goto _L3
_L3:
        l1 = UserValuesManager.getLongValue(this, "places:last_checkin_pageid", -1L);
        l2 = UserValuesManager.getLongValue(this, "places:last_checkin_time", -1L);
        if(l1 != mPlace.mPageId || System.currentTimeMillis() - l2 > 0xa4cb80L) goto _L6; else goto _L5
_L5:
        mRecentCheckin = true;
        mPlacesEnabled = true;
        showCheckinInterface();
        refreshLastCheckin();
        updateTagButton();
_L4:
        if(mAdapter != null)
            break; /* Loop/switch isn't completed */
        long l;
        Boolean boolean1;
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
            l = -1L;
        else
            l = mUserId;
        listview = getListView();
        profileimagescache = mAppSession.getUserImagesCache();
        streamphotoscache = mAppSession.getPhotosCache();
        streamadapterlistener = new StreamAdapter.StreamAdapterListener() {

            public void onOpenMediaItem(com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem)
            {
                openMediaItem(mediaitem);
            }

            public void onUserImageClicked(FacebookPost facebookpost)
            {
                if(facebookpost.actorId != mUserId) goto _L2; else goto _L1
_L1:
                if(mType == FacebookStreamType.NEWSFEED_STREAM)
                    goToProfile(facebookpost.getProfile());
_L4:
                return;
_L2:
                if(facebookpost.actorId != mAppSession.getSessionInfo().userId)
                    goToProfile(facebookpost.getProfile());
                if(true) goto _L4; else goto _L3
_L3:
            }

            final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
        }
;
        mAdapter = new StreamAdapter(this, listview, facebookstreamcontainer, profileimagescache, streamphotoscache, streamadapterlistener, l);
        getListView().setAdapter(mAdapter);
        if(mType == FacebookStreamType.NEWSFEED_STREAM && (getIntent().getAction() == null || !getIntent().getAction().equals("com.facebook.katana.SHARE")))
        {
            i = PreferenceManager.getDefaultSharedPreferences(this).getInt("stream_scroll_pos", -1);
            if(i > 0)
                getListView().setSelection(i);
            mAppSession.releaseStreamContainers();
        }
        continue; /* Loop/switch isn't completed */
_L6:
        mRecentCheckin = false;
        boolean1 = Gatekeeper.get(this, "places");
        if(boolean1 != null && boolean1.booleanValue())
        {
            mPlacesEnabled = true;
            FBLocationManager.addLocationListener(this, this);
        }
        if(true) goto _L4; else goto _L7
_L7:
        if(facebookstreamcontainer != null)
            mAdapter.refreshStream(facebookstreamcontainer);
        if(true) goto _L9; else goto _L8
_L8:
    }

    protected void onStop()
    {
        super.onStop();
    }

    public void onTimeOut()
    {
    }

    protected void publishStatusUpdate()
    {
        android.text.Editable editable = ((EditText)findViewById(0x7f0e0128)).getEditableText();
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(editable);
        FacebookProfile afacebookprofile[] = (FacebookProfile[])spannablestringbuilder.getSpans(0, spannablestringbuilder.length(), com/facebook/katana/model/FacebookProfile);
        HashSet hashset = new HashSet();
        int i = afacebookprofile.length;
        for(int j = 0; j < i; j++)
        {
            FacebookProfile facebookprofile1 = afacebookprofile[j];
            int k = spannablestringbuilder.getSpanStart(facebookprofile1);
            int l = spannablestringbuilder.getSpanEnd(facebookprofile1);
            Object aobj[] = new Object[2];
            aobj[0] = Long.valueOf(facebookprofile1.mId);
            aobj[1] = facebookprofile1.mDisplayName;
            spannablestringbuilder.replace(k, l, String.format("@[%d:%s]", aobj));
            hashset.add(facebookprofile1);
        }

        String s = spannablestringbuilder.toString().trim();
        if(s.length() > 0)
        {
            FacebookProfile facebookprofile = null;
            if(ConnectionsProvider.getAdminProfile(this, mProfileId) != null)
                facebookprofile = mProfile;
            showDialog(2);
            mPublishReqId = StreamPublish.Publish(this, mUserId, s, editable.toString(), hashset, false, facebookprofile);
        }
    }

    protected void refreshLastCheckin()
    {
        String s = UserValuesManager.getValue(this, "places:last_checkin", null);
        if(s != null)
        {
            FacebookCheckin facebookcheckin = (FacebookCheckin)JMCachingDictDestination.jsonDecode(s, com/facebook/katana/model/FacebookCheckin);
            if(mRecentCheckin && facebookcheckin != null)
            {
                mLastCheckin = facebookcheckin;
                mTaggedUids = new HashSet(facebookcheckin.getDetails().mTaggedUids);
            }
        }
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    protected void setupDealsView()
    {
        final String dealsUrl = (new StringBuilder()).append(com.facebook.katana.Constants.URL.getDealsUrl(this)).append("?promotion_id=").append(mPlace.getDealInfo().mDealId).toString();
        final WebView dealsWebview = (WebView)findViewById(0x7f0e012a);
        dealsWebview.setScrollBarStyle(0x2000000);
        dealsWebview.setWebViewClient(new com.facebook.katana.webview.FacebookAuthentication.AuthWebViewClient(this, new com.facebook.katana.webview.FacebookAuthentication.Callback() {

            public void authenticationFailed()
            {
                Toaster.toast(StreamActivity.this, getString(0x7f0a004a));
            }

            public void authenticationNetworkFailed()
            {
                Toaster.toast(StreamActivity.this, getString(0x7f0a004a));
            }

            public void authenticationSuccessful()
            {
                dealsWebview.loadUrl(dealsUrl);
            }

            final StreamActivity this$0;
            final String val$dealsUrl;
            final WebView val$dealsWebview;

            
            {
                this$0 = StreamActivity.this;
                dealsWebview = webview;
                dealsUrl = s;
                super();
            }
        }
));
        dealsWebview.loadUrl(dealsUrl);
    }

    protected void setupListHeaders()
    {
        if(mType == FacebookStreamType.NEWSFEED_STREAM)
        {
            if(!mListHeaders.isEmpty())
            {
                LayoutInflater layoutinflater = (LayoutInflater)getSystemService("layout_inflater");
                Integer integer;
                for(Iterator iterator = mListHeaders.iterator(); iterator.hasNext(); ((LinearLayout)findViewById(0x7f0e0131)).addView(layoutinflater.inflate(integer.intValue(), null)))
                    integer = (Integer)iterator.next();

                mListHeaders = new ArrayList();
            }
        } else
        {
            super.setupListHeaders();
        }
    }

    public void setupShareBar()
    {
        if(mType != FacebookStreamType.PLACE_ACTIVITY_STREAM)
        {
            ((Button)findViewById(0x7f0e0129)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    publishStatusUpdate();
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            findViewById(0x7f0e0127).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    if(mUserId != mAppSession.getSessionInfo().userId)
                        showDialog(0xf36e2d8);
                    else
                        showDialog(0xf36e2d7);
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            TaggingAutoCompleteTextView taggingautocompletetextview = (TaggingAutoCompleteTextView)findViewById(0x7f0e0128);
            if(getIntent().getAction() != null && getIntent().getAction().equals("com.facebook.katana.SHARE"))
            {
                taggingautocompletetextview.requestFocus();
                getWindow().setSoftInputMode(4);
            }
            if(mType == FacebookStreamType.PROFILE_WALL_STREAM || mType == FacebookStreamType.GROUP_WALL_STREAM)
            {
                int i;
                if(mUserId == mAppSession.getSessionInfo().userId)
                    i = 0x7f0a01d0;
                else
                    i = 0x7f0a01da;
                taggingautocompletetextview.setHint(i);
            }
            taggingautocompletetextview.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView textview, int j, KeyEvent keyevent)
                {
                    if(j == textview.getImeActionId())
                        publishStatusUpdate();
                    return false;
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            mAppSessionListeners.add(taggingautocompletetextview.configureView(this, mAppSession.getUserImagesCache()));
            if(mType == FacebookStreamType.PAGE_WALL_STREAM)
            {
                taggingautocompletetextview.setHint(0x7f0a01da);
                showComposerIfCanPost();
            }
        } else
        {
            ((Button)findViewById(0x7f0e0129)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    doCheckin();
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            ((Button)findViewById(0x7f0e012c)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    Intent intent = new Intent(StreamActivity.this, com/facebook/katana/activity/profilelist/FriendMultiSelectorActivity);
                    intent.putExtra("profiles", IntentUtils.setToPrimitive(mTaggedUids));
                    startActivityForResult(intent, 101);
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            EditText edittext = (EditText)findViewById(0x7f0e0128);
            edittext.setHint(0x7f0a0262);
            findViewById(0x7f0e012d).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    if(mUserId != mAppSession.getSessionInfo().userId)
                        showDialog(0xf36e2d8);
                    else
                        showDialog(0xf36e2d7);
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            edittext.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView textview, int j, KeyEvent keyevent)
                {
                    boolean flag;
                    if(j == textview.getImeActionId())
                    {
                        doCheckin();
                        flag = true;
                    } else
                    {
                        flag = false;
                    }
                    return flag;
                }

                final StreamActivity this$0;

            
            {
                this$0 = StreamActivity.this;
                super();
            }
            }
);
            mPlace = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
            if(!$assertionsDisabled && mPlace == null)
                throw new AssertionError();
            if(mPlace.getDealInfo() != null)
                setupDealsView();
            mTaggedUids = new HashSet();
        }
    }

    public void showCheckinInterface()
    {
        View view = findViewById(0x7f0e012b);
        View view1 = findViewById(0x7f0e0128);
        View view2 = findViewById(0x7f0e012c);
        View view3 = findViewById(0x7f0e012d);
        View view4 = findViewById(0x7f0e0129);
        if(mRecentCheckin)
        {
            view1.setVisibility(8);
            view.setVisibility(0);
            view2.setVisibility(0);
            view3.setVisibility(0);
            view4.setVisibility(8);
        } else
        {
            view1.setVisibility(0);
            view.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(8);
            view4.setVisibility(0);
        }
    }

    public void showComposerIfCanPost()
    {
        TaggingAutoCompleteTextView taggingautocompletetextview = (TaggingAutoCompleteTextView)findViewById(0x7f0e0128);
        Button button = (Button)findViewById(0x7f0e0129);
        View view = findViewById(0x7f0e0127);
        int i;
        if(mCanPost)
            i = 0;
        else
            i = 8;
        taggingautocompletetextview.setVisibility(i);
        button.setVisibility(i);
        view.setVisibility(i);
    }

    protected void streamGet(FacebookPost facebookpost)
    {
        byte byte0;
        long l;
        if(facebookpost != null)
        {
            byte0 = 2;
            l = facebookpost.createdTime;
        } else
        {
            byte0 = 0;
            l = -1L;
        }
        if(mType != FacebookStreamType.PLACE_ACTIVITY_STREAM)
        {
            mAppSession.streamGet(this, mAppSession.getSessionInfo().userId, getSourceIds(), -1L, l, 20, byte0, mType);
            logStepDataRequested();
        } else
        {
            FacebookPlace facebookplace = mPlace;
            FqlGetPlaceCheckins.RequestPlaceCheckins(this, -1L, l, facebookplace, 20, byte0);
        }
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        getListView().setSelection(0);
        View view1 = findViewById(0x7f0e0128);
        view1.requestFocus();
        ((InputMethodManager)getSystemService("input_method")).showSoftInput(view1, 0);
    }

    protected void updateTagButton()
    {
        Button button = (Button)findViewById(0x7f0e012c);
        if(mRecentCheckin)
        {
            if(mTaggedUids.size() == 0)
                button.setText(getString(0x7f0a0267));
            else
                button.setText(getString(0x7f0a0269));
        } else
        {
            String s = null;
            if(mTaggedUids.size() > 0)
            {
                FacebookProfile facebookprofile = ConnectionsProvider.getFriendProfileFromId(this, ((Long)(new ArrayList(mTaggedUids)).get(0)).longValue());
                if(facebookprofile != null)
                    s = facebookprofile.mDisplayName;
            }
            if(s == null || mTaggedUids.size() == 0)
                button.setText(getString(0x7f0a0267));
            else
            if(mTaggedUids.size() == 1)
            {
                Object aobj2[] = new Object[1];
                aobj2[0] = s;
                button.setText(getString(0x7f0a0272, aobj2));
            } else
            if(mTaggedUids.size() == 2)
            {
                Object aobj1[] = new Object[1];
                aobj1[0] = s;
                button.setText(getString(0x7f0a0273, aobj1));
            } else
            {
                Object aobj[] = new Object[2];
                aobj[0] = s;
                aobj[1] = Integer.valueOf(mTaggedUids.size() - 1);
                button.setText(getString(0x7f0a0274, aobj));
            }
        }
    }

    static final boolean $assertionsDisabled = false;
    public static final String ACTION_SHARE = "com.facebook.katana.SHARE";
    private static final long AUTO_REFRESH_INTERVAL_MS = 0x927c0L;
    public static final String EXTRA_LOGOUT = "extra_logout";
    public static final String EXTRA_POP_TO_FIRST = "extra_ptf";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_USER_ID = "extra_user_id";
    private static final int FEEDBACK_ID = 2;
    private static final int FIND_FRIENDS_DIALOG = 6;
    private static final int IMPORT_CONTACT_CONSENT = 21;
    private static final int OPEN_LINK_IN_BROWSER_ID = 6;
    private static final int OPEN_VIDEO_ID = 8;
    private static final int PLACES_OPT_IN_DIALOG = 16;
    private static final int PROGRESS_CHECKING_IN_DIALOG = 5;
    private static final int PROGRESS_PUBLISHING_DIALOG = 2;
    private static final int PROGRESS_REMOVING_POST_DIALOG = 3;
    private static final int REFRESH_ID = 12;
    private static final int REMOVE_POST_ID = 5;
    private static final int REMOVE_TAG_ID = 7;
    private static final String STREAM_SCROLL_POS = "stream_scroll_pos";
    private static final int TAGGER_ACTIVITY_ID = 101;
    private static final int VIEW_PROFILE_ID = 3;
    private static final int VIEW_TARGET_WALL_ID = 4;
    private static final int VIEW_URL_0_ID = 11;
    private static final int VIEW_URL_1_ID = 12;
    private static final int VIEW_URL_2_ID = 13;
    private static int sStackSize;
    private RefreshableListViewContainer listViewContainer;
    private StreamAdapter mAdapter;
    private AppSession mAppSession;
    private List mAppSessionListeners;
    private boolean mCanPost;
    private int mHeaderViewCount;
    protected FacebookCheckin mLastCheckin;
    protected Location mLastKnownLocation;
    private MediaUploader mMediaUploader;
    protected FacebookPlace mPlace;
    protected boolean mPlacesEnabled;
    private TabProgressListener mProgressListener;
    private String mPublishReqId;
    protected boolean mRecentCheckin;
    private String mRemoveReqId;
    private boolean mShowingProgress;
    protected Set mTaggedUids;
    private FacebookStreamType mType;
    private long mUserId;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/activity/stream/StreamActivity.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }









/*
    static String access$1402(StreamActivity streamactivity, String s)
    {
        streamactivity.mPublishReqId = s;
        return s;
    }

*/


/*
    static String access$1502(StreamActivity streamactivity, String s)
    {
        streamactivity.mRemoveReqId = s;
        return s;
    }

*/




/*
    static boolean access$402(StreamActivity streamactivity, boolean flag)
    {
        streamactivity.mCanPost = flag;
        return flag;
    }

*/





}
