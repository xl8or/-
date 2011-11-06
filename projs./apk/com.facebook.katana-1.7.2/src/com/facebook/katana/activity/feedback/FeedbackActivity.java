// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FeedbackActivity.java

package com.facebook.katana.activity.feedback;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.places.PlacesOptInActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.features.places.PlacesUtils;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.PlacesSetTaggingOptInStatus;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.feedback:
//            FeedbackAdapter

public class FeedbackActivity extends BaseFacebookListActivity
{

    public FeedbackActivity()
    {
    }

    private void onAfterPostLoaded()
    {
        mAdapter = new FeedbackAdapter(this, getListView(), mPost, mAppSession.getUserImagesCache(), mAppSession.getPhotosCache(), mAppSession.isStreamGetCommentsPending(mWallUserId, mPost.postId), new FeedbackAdapter.CommentsListener() {

            public void onLike(boolean flag)
            {
                if(flag)
                    mAppSession.streamAddLike(FeedbackActivity.this, mWallUserId, mPost.postId);
                else
                    mAppSession.streamRemoveLike(FeedbackActivity.this, mWallUserId, mPost.postId);
            }

            public void onMediaItemClicked(com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem)
            {
                mAppSession.openMediaItem(FeedbackActivity.this, mediaitem);
            }

            final FeedbackActivity this$0;

            
            {
                this$0 = FeedbackActivity.this;
                super();
            }
        }
);
        getListView().setAdapter(mAdapter);
        EditText edittext;
        if(mPost != null && mPost.getComments() != null)
            setFeedbackBarVisibility(mPost.getComments().canPost);
        else
            setFeedbackBarVisibility(false);
        if(mPost.getComments().getCount() < mPost.getComments().getTotalCount())
            requestMoreComments();
        getListView().setOnCreateContextMenuListener(this);
        getListView().setItemsCanFocus(true);
        edittext = (EditText)findViewById(0x7f0e0076);
        edittext.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
            {
                if(i == 101)
                {
                    String s = textview.getText().toString().trim();
                    if(s.length() > 0)
                    {
                        mAddCommentReqId = mAppSession.streamAddComment(FeedbackActivity.this, mWallUserId, mPost.postId, s, mCommentActor);
                        showDialog(1);
                    }
                }
                return false;
            }

            final FeedbackActivity this$0;

            
            {
                this$0 = FeedbackActivity.this;
                super();
            }
        }
);
        edittext.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean flag)
            {
                View view1 = findViewById(0x7f0e0077);
                int i;
                if(flag)
                    i = 0;
                else
                    i = 8;
                view1.setVisibility(i);
            }

            final FeedbackActivity this$0;

            
            {
                this$0 = FeedbackActivity.this;
                super();
            }
        }
);
        ((Button)findViewById(0x7f0e0077)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                EditText edittext1 = (EditText)findViewById(0x7f0e0076);
                String s = edittext1.getText().toString().trim();
                if(s.length() > 0)
                {
                    mAddCommentReqId = mAppSession.streamAddComment(FeedbackActivity.this, mWallUserId, mPost.postId, s, mCommentActor);
                    showDialog(1);
                    ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
                }
            }

            final FeedbackActivity this$0;

            
            {
                this$0 = FeedbackActivity.this;
                super();
            }
        }
);
        if(mPost.getPostType() == 4 && mPost.mTaggedIds.contains(Long.valueOf(mAppSession.getSessionInfo().userId)) && Boolean.valueOf(PlacesUtils.checkOptInStatus(mAppSession, this)).booleanValue())
        {
            FacebookPlace facebookplace = mPost.getAttachment().mCheckinDetails.getPlaceInfo();
            Intent intent = new Intent(this, com/facebook/katana/activity/places/PlacesOptInActivity);
            intent.putExtra("place_name", facebookplace.mName);
            intent.putExtra("optin_origin", "checkin_tag");
            intent.putExtra("user_profile", mPost.getProfile());
            startActivityForResult(intent, 14);
        } else
        {
            mAppSession.addListener(mAppSessionListener);
        }
    }

    private static boolean responseIsPositive(int i, Exception exception)
    {
        boolean flag;
        if(200 == i && exception == null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void setFeedbackBarVisibility(boolean flag)
    {
        int i;
        if(flag)
            i = 0;
        else
            i = 8;
        findViewById(0x7f0e0075).setVisibility(i);
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
        case 3: // '\003'
            if(intent != null && intent.getBooleanExtra("extra_ptf", false))
            {
                setResult(-1, intent);
                finish();
            }
            break;

        case 14: // '\016'
            if(j == -1)
                PlacesSetTaggingOptInStatus.SetStatus(this, 1);
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        if(mPost != null) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L7:
        return flag;
_L2:
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
        menuitem.getItemId();
        JVM INSTR lookupswitch 5: default 80
    //                   2: 132
    //                   3: 91
    //                   11: 188
    //                   12: 188
    //                   13: 188;
           goto _L3 _L4 _L5 _L6 _L6 _L6
_L3:
        break; /* Loop/switch isn't completed */
_L6:
        break MISSING_BLOCK_LABEL_188;
_L8:
        flag = true;
          goto _L7
        ClassCastException classcastexception;
        classcastexception;
        flag = false;
          goto _L7
_L5:
        FeedbackAdapter.CommentItem commentitem1 = (FeedbackAdapter.CommentItem)mAdapter.getItemByPosition(adaptercontextmenuinfo.position);
        ApplicationUtils.OpenProfile(this, 0, commentitem1.getComment().fromId, commentitem1.getComment().getProfile());
          goto _L8
_L4:
        FeedbackAdapter.CommentItem commentitem = (FeedbackAdapter.CommentItem)mAdapter.getItemByPosition(adaptercontextmenuinfo.position);
        mRemoveCommentReqId = mAppSession.streamRemoveComment(this, mWallUserId, mPost.postId, commentitem.getComment().id);
        showDialog(2);
          goto _L8
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(menuitem.getTitle().toString())));
          goto _L8
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030023);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            Intent intent = getIntent();
            mWallUserId = intent.getLongExtra("extra_uid", 0L);
            final String postId = intent.getStringExtra("extra_post_id");
            if(intent.hasExtra("comment_actor"))
                mCommentActor = (FacebookProfile)intent.getParcelableExtra("comment_actor");
            mAppSessionListener = new AppSessionListener() {

                public void onDownloadStreamPhotoComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3, Bitmap bitmap)
                {
                    if(mAdapter != null)
                        mAdapter.updatePhoto(bitmap, s3);
                    else
                        Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onDownloadStreamPhotoComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                }

                public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s1)
                {
                    if(mAdapter != null)
                        mAdapter.updatePhoto(bitmap, s1);
                    else
                        Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onPhotoDecodeComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                }

                public void onPlacesRemoveTagComplete(AppSession appsession, String s1, int i, String s2, Exception exception, FacebookPost facebookpost, long l1)
                {
                    if(i != 200);
                }

                public void onProfileImageDownloaded(AppSession appsession, String s1, int i, String s2, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
                {
                    if(mAdapter != null && FeedbackActivity.responseIsPositive(i, exception))
                        mAdapter.updateUserImage(profileimage);
                    else
                        Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onProfileImageDownloaded: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                }

                public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
                {
                    if(mAdapter != null)
                        mAdapter.updateUserImage(profileimage);
                    else
                        Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onProfileImageLoaded: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                }

                public void onStreamAddCommentComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3, com.facebook.katana.model.FacebookPost.Comment comment)
                {
                    if(s3.equals(postId))
                    {
                        removeDialog(1);
                        mAddCommentReqId = null;
                        if(FeedbackActivity.responseIsPositive(i, exception))
                        {
                            ((EditText)findViewById(0x7f0e0076)).setText(null);
                            if(mAdapter != null)
                                mAdapter.addCommentComplete();
                            else
                                Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onStreamAddCommentComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                        } else
                        {
                            String s4 = StringUtils.getErrorString(FeedbackActivity.this, getString(0x7f0a01ac), i, s2, exception);
                            Toaster.toast(FeedbackActivity.this, s4);
                        }
                    }
                }

                public void onStreamAddLikeComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3)
                {
                    if(s3.equals(postId))
                    {
                        if(mAdapter != null)
                            mAdapter.addLikeComplete(i);
                        else
                            Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onStreamAddLikeComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                        if(!FeedbackActivity.responseIsPositive(i, exception))
                        {
                            String s4 = StringUtils.getErrorString(FeedbackActivity.this, getString(0x7f0a01ad), i, s2, exception);
                            Toaster.toast(FeedbackActivity.this, s4);
                        }
                    }
                }

                public void onStreamGetCommentsComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3)
                {
                    if(s3.equals(postId))
                    {
                        findViewById(0x7f0e00f1).setVisibility(8);
                        if(mAdapter != null)
                            mAdapter.requestCommentsComplete(i);
                        else
                            Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onStreamGetCommentsComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                        if(!FeedbackActivity.responseIsPositive(i, exception))
                        {
                            String s4 = StringUtils.getErrorString(FeedbackActivity.this, getString(0x7f0a01b4), i, s2, exception);
                            Toaster.toast(FeedbackActivity.this, s4);
                        }
                    }
                }

                public void onStreamRemoveCommentComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3)
                {
                    if(s3.equals(postId))
                    {
                        removeDialog(2);
                        mRemoveCommentReqId = null;
                        mAdapter.removeCommentComplete();
                        if(!FeedbackActivity.responseIsPositive(i, exception))
                        {
                            String s4 = StringUtils.getErrorString(FeedbackActivity.this, getString(0x7f0a01c9), i, s2, exception);
                            Toaster.toast(FeedbackActivity.this, s4);
                        }
                    }
                }

                public void onStreamRemoveLikeComplete(AppSession appsession, String s1, int i, String s2, Exception exception, String s3)
                {
                    if(s3.equals(postId))
                    {
                        mAdapter.removeLikeComplete(i);
                        if(!FeedbackActivity.responseIsPositive(i, exception))
                        {
                            String s4 = StringUtils.getErrorString(FeedbackActivity.this, getString(0x7f0a01ca), i, s2, exception);
                            Toaster.toast(FeedbackActivity.this, s4);
                        }
                    }
                }

                public void onUsersGetInfoComplete(AppSession appsession, String s1, int i, String s2, Exception exception, long l1, 
                        FacebookUser facebookuser, boolean flag)
                {
                    if(FeedbackActivity.responseIsPositive(i, exception) && facebookuser != null)
                        if(mAdapter != null)
                            mAdapter.updateLikeUserName(l1, facebookuser.getDisplayName());
                        else
                            Utils.reportSoftError("feedback_listener_error", (new StringBuilder()).append("onUsersGetInfoComplete: null mAdapter. postid=").append(postId).append(" post=").append(mPost).append(" running=").append(isOnTop()).toString());
                }

                final FeedbackActivity this$0;
                final String val$postId;

            
            {
                this$0 = FeedbackActivity.this;
                postId = s;
                super();
            }
            }
;
            mPost = FacebookStreamContainer.get(postId);
            if(mPost != null)
            {
                onAfterPostLoaded();
            } else
            {
                setListLoading(true);
                setFeedbackBarVisibility(false);
                String s = mAppSession.getSessionInfo().sessionKey;
                long l = mWallUserId;
                String as[] = new String[1];
                as[0] = postId;
                _cls1FqlGetPosts _lcls1fqlgetposts = new _cls1FqlGetPosts(this, intent, s, null, l, as);
                mAppSession.postToService(this, _lcls1fqlgetposts, 1001, 1001, null);
            }
        }
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        if(mPost != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo;
        FeedbackAdapter.Item item = mAdapter.getItemByPosition(adaptercontextmenuinfo.position);
        item.getType();
        JVM INSTR lookupswitch 6: default 92
    //                   0: 95
    //                   1: 95
    //                   2: 95
    //                   3: 95
    //                   4: 95
    //                   31: 267;
           goto _L1 _L3 _L3 _L3 _L3 _L3 _L4
_L3:
        int j;
        Iterator iterator1;
        FacebookPost facebookpost = ((FeedbackAdapter.PostItem)item).getPost();
        contextmenu.setHeaderTitle(facebookpost.getProfile().mDisplayName);
        ArrayList arraylist1 = new ArrayList();
        StringUtils.parseExpression(facebookpost.message, "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*", null, arraylist1, 3);
        j = 0;
        iterator1 = arraylist1.iterator();
_L10:
        if(!iterator1.hasNext()) goto _L1; else goto _L5
_L5:
        String s1 = (String)iterator1.next();
        j;
        JVM INSTR tableswitch 0 2: default 208
    //                   0 219
    //                   1 235
    //                   2 251;
           goto _L6 _L7 _L8 _L9
_L6:
        j++;
          goto _L10
        ClassCastException classcastexception;
        classcastexception;
          goto _L1
_L7:
        contextmenu.add(0, 11, 0, s1);
          goto _L6
_L8:
        contextmenu.add(0, 12, 0, s1);
          goto _L6
_L9:
        contextmenu.add(0, 13, 0, s1);
          goto _L6
_L4:
        int i;
        Iterator iterator;
        contextmenu.setHeaderTitle(0x7f0a01b0);
        contextmenu.add(0, 3, 0, 0x7f0a01d7);
        if(mPost.getComments().canRemove)
            contextmenu.add(0, 2, 0, 0x7f0a01c8);
        FeedbackAdapter.CommentItem commentitem = (FeedbackAdapter.CommentItem)item;
        ArrayList arraylist = new ArrayList();
        StringUtils.parseExpression(commentitem.getComment().text, "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*", null, arraylist, 3);
        i = 0;
        iterator = arraylist.iterator();
_L16:
        if(!iterator.hasNext()) goto _L1; else goto _L11
_L11:
        String s = (String)iterator.next();
        i;
        JVM INSTR tableswitch 0 2: default 412
    //                   0 418
    //                   1 434
    //                   2 450;
           goto _L12 _L13 _L14 _L15
_L12:
        i++;
          goto _L16
_L13:
        contextmenu.add(0, 11, 0, s);
          goto _L12
_L14:
        contextmenu.add(0, 12, 0, s);
          goto _L12
_L15:
        contextmenu.add(0, 13, 0, s);
          goto _L12
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 74;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a01ae));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a01cd));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 3, 0, 0x7f0a01d7).setIcon(0x7f0200c0);
        return true;
    }

    protected void onDestroy()
    {
        super.onDestroy();
        getListView().setAdapter(null);
        if(mAdapter != null)
        {
            mAdapter.close();
            mAdapter = null;
        }
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        if(mPost != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        FeedbackAdapter.Item item = mAdapter.getItemByPosition(i);
        switch(item.getType())
        {
        case 0: // '\0'
        case 1: // '\001'
            FacebookProfile facebookprofile = mPost.getProfile();
            Intent intent = ProfileTabHostActivity.intentForProfile(this, facebookprofile.mId);
            intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
            intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
            intent.putExtra("extra_user_type", facebookprofile.mType);
            startActivityForResult(intent, 3);
            break;

        case 4: // '\004'
            if(!ApplicationUtils.OpenPlaceProfile(this, mPost.getAttachment().mCheckinDetails.getPlaceInfo()))
            {
                com.facebook.katana.model.FacebookPost.Attachment attachment2 = mPost.getAttachment();
                if(attachment2 != null && attachment2.href != null)
                    mAppSession.openURL(this, attachment2.href);
            }
            break;

        case 2: // '\002'
            com.facebook.katana.model.FacebookPost.Attachment attachment1 = ((FeedbackAdapter.PostItem)mAdapter.getItemByPosition(i)).getPost().getAttachment();
            if(attachment1 != null && attachment1.href != null)
                mAppSession.openURL(this, attachment1.href);
            break;

        case 30: // '\036'
            requestMoreComments();
            break;

        case 31: // '\037'
            FeedbackAdapter.CommentItem commentitem = (FeedbackAdapter.CommentItem)item;
            ApplicationUtils.OpenProfile(this, 0, commentitem.getComment().fromId, commentitem.getComment().getProfile());
            break;

        case 3: // '\003'
            com.facebook.katana.model.FacebookPost.Attachment attachment = mPost.getAttachment();
            if(attachment != null && attachment.getMediaItems().size() > 0)
                mAppSession.openMediaItem(this, (com.facebook.katana.model.FacebookPost.Attachment.MediaItem)attachment.getMediaItems().get(0));
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        if(mPost != null) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L7:
        return flag;
_L2:
        menuitem.getItemId();
        JVM INSTR tableswitch 3 3: default 36
    //                   3 45;
           goto _L3 _L4
_L3:
        flag = super.onOptionsItemSelected(menuitem);
          goto _L5
_L4:
        ApplicationUtils.OpenProfile(this, 0, mPost.actorId, mPost.getProfile());
        if(true) goto _L3; else goto _L5
_L5:
        if(true) goto _L7; else goto _L6
_L6:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        if(mPost != null)
        {
            mAppSession.addListener(mAppSessionListener);
            if(mAddCommentReqId != null && !mAppSession.isRequestPending(mAddCommentReqId))
            {
                removeDialog(1);
                mAddCommentReqId = null;
            }
            if(mRemoveCommentReqId != null && !mAppSession.isRequestPending(mRemoveCommentReqId))
            {
                removeDialog(2);
                mRemoveCommentReqId = null;
            }
            if(mAppSession.isStreamGetCommentsPending(mWallUserId, mPost.postId))
                findViewById(0x7f0e00f1).setVisibility(0);
            if(mAdapter.getLikeUserId() != -1L)
                mAppSession.usersGetBriefInfo(this, mAdapter.getLikeUserId());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void requestMoreComments()
    {
        if(!mAppSession.isStreamGetCommentsPending(mWallUserId, mPost.postId))
        {
            mAdapter.requestMoreComments();
            mAppSession.streamGetComments(this, mWallUserId, mPost.postId);
            findViewById(0x7f0e00f1).setVisibility(0);
        }
    }

    public static final String EXTRA_COMMENT_ACTOR = "comment_actor";
    public static final String EXTRA_POST_ID = "extra_post_id";
    public static final String EXTRA_USER_ID = "extra_uid";
    protected static final String FEEDBACK_LISTENER_ERROR = "feedback_listener_error";
    private static final int PLACES_OPT_IN_DIALOG = 14;
    private static final int PROGRESS_ADDING_COMMENT_DIALOG = 1;
    private static final int PROGRESS_REMOVING_COMMENT_DIALOG = 2;
    private static final int REMOVE_COMMENT_ID = 2;
    private static final int USER_PROFILE_ID = 3;
    private static final int VIEW_URL_0_ID = 11;
    private static final int VIEW_URL_1_ID = 12;
    private static final int VIEW_URL_2_ID = 13;
    private FeedbackAdapter mAdapter;
    private String mAddCommentReqId;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private FacebookProfile mCommentActor;
    private FacebookPost mPost;
    private String mRemoveCommentReqId;
    private long mWallUserId;




/*
    static FacebookPost access$102(FeedbackActivity feedbackactivity, FacebookPost facebookpost)
    {
        feedbackactivity.mPost = facebookpost;
        return facebookpost;
    }

*/



/*
    static String access$302(FeedbackActivity feedbackactivity, String s)
    {
        feedbackactivity.mAddCommentReqId = s;
        return s;
    }

*/


/*
    static String access$402(FeedbackActivity feedbackactivity, String s)
    {
        feedbackactivity.mRemoveCommentReqId = s;
        return s;
    }

*/






    private class _cls1FqlGetPosts extends FqlGetStream
        implements ApiMethodCallback
    {

        public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
        {
            if(i == 200 && getPosts().size() > 0)
            {
                mPost = (FacebookPost)getPosts().get(0);
                onAfterPostLoaded();
                setListLoading(false);
            } else
            {
                Toaster.toast(mContext, 0x7f0a01b4);
                finish();
            }
        }

        final FeedbackActivity this$0;

        public _cls1FqlGetPosts(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, 
                String as[])
        {
            this$0 = FeedbackActivity.this;
            super(context, intent, s, apimethodlistener, l, as);
        }
    }

}
