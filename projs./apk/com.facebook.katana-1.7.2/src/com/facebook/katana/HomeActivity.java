// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.faceweb.FacewebChromeActivity;
import com.facebook.katana.activity.feedback.FeedbackActivity;
import com.facebook.katana.activity.stream.StreamActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.features.places.PlacesUserSettings;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookNotification;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.service.method.PerfLogging;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.TempFileManager;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Utils;
import com.facebook.katana.webview.FacebookRpcCall;
import com.facebook.katana.webview.FacebookWebView;
import com.facebook.katana.webview.FacewebWebView;
import com.facebook.katana.webview.RefreshableFacewebWebViewContainer;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            NotificationsAdapter, IntentUriHandler, LoginActivity, ComposerActivity, 
//            AlertDialogs, SettingsActivity, HtmlAboutActivity

public class HomeActivity extends BaseFacebookListActivity
    implements android.view.View.OnClickListener, android.view.View.OnFocusChangeListener, android.view.View.OnTouchListener, android.view.View.OnLongClickListener
{
    protected class ReelAdapter extends BaseAdapter
    {

        private void calculateGalleryHeight()
        {
            int i = mGalleryView.getMeasuredHeight();
            if(i != 0)
                mGalleryHeight = i - (int)(4F * (float)mImagePadding);
        }

        private void clearViews()
        {
            mPhotoUrls.clear();
        }

        private float getImageMultiplier(Bitmap bitmap, float f)
        {
            if(mGalleryHeight == -1)
                calculateGalleryHeight();
            float f1 = 1F;
            if(mGalleryHeight != -1 && bitmap != null && bitmap.getHeight() != 0)
                f1 = Math.min(f, (float)mGalleryHeight / (float)bitmap.getHeight());
            return f1;
        }

        private void removePost(FacebookPost facebookpost)
        {
            String s = ((com.facebook.katana.model.FacebookPost.Attachment.MediaItem)facebookpost.getAttachment().getMediaItems().get(0)).src;
            mPostIds.remove(facebookpost.postId);
            mPhotoUrls.remove(s);
        }

        public boolean addMediaContent(List list)
        {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2;
            if(list == null || list.size() == 0)
            {
                flag2 = false;
            } else
            {
                FacebookPost facebookpost = getLastPost();
                if(facebookpost != null && FacebookPost.timeComparator.compare(list.get(0), facebookpost) < 0)
                    flag1 = true;
                Iterator iterator = list.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    FacebookPost facebookpost1 = (FacebookPost)iterator.next();
                    if(!mPostIds.contains(facebookpost1.postId) && facebookpost1.canInteractWithFeedback() && ((com.facebook.katana.model.FacebookPost.Attachment.MediaItem)facebookpost1.getAttachment().getMediaItems().get(0)).src != null)
                    {
                        mMediaContent.add(facebookpost1);
                        mPostIds.add(facebookpost1.postId);
                        flag = true;
                    }
                } while(true);
                if(flag && flag1)
                    Collections.sort(mMediaContent, FacebookPost.timeComparator);
                for(; mMediaContent.size() > 50; removePost((FacebookPost)mMediaContent.remove(50)));
                if(flag)
                {
                    if(mIsFirstLoad)
                    {
                        if(getCount() > 1)
                            mGalleryView.setSelection(1);
                        mIsFirstLoad = false;
                    }
                    notifyDataSetChanged();
                }
                flag2 = flag;
            }
            return flag2;
        }

        public int getCount()
        {
            return mMediaContent.size();
        }

        public Object getItem(int i)
        {
            return Integer.valueOf(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public FacebookPost getLastPost()
        {
            FacebookPost facebookpost;
            if(mMediaContent.size() == 0)
                facebookpost = null;
            else
                facebookpost = (FacebookPost)mMediaContent.get(mMediaContent.size() - 1);
            return facebookpost;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            LinearLayout linearlayout = new LinearLayout(mContext);
            ImageView imageview = new ImageView(mContext);
            String s = ((com.facebook.katana.model.FacebookPost.Attachment.MediaItem)((FacebookPost)mMediaContent.get(i)).getAttachment().getMediaItems().get(0)).src;
            Bitmap bitmap = null;
            if(s != null)
            {
                bitmap = mPhotosContainer.get(mContext, s);
                if(bitmap == null)
                    bitmap = mPhotoDownloadingBitmap;
            }
            imageview.setImageBitmap(bitmap);
            Object obj = (List)mPhotoUrls.get(s);
            if(obj == null)
            {
                obj = new ArrayList();
                mPhotoUrls.put(s, obj);
            }
            ((List) (obj)).add(imageview);
            imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(-1, -1));
            imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            linearlayout.addView(imageview);
            linearlayout.setBackgroundResource(0x7f020101);
            linearlayout.setPadding(mImagePadding, mImagePadding, mImagePadding, mImagePadding);
            linearlayout.setGravity(17);
            linearlayout.setLayoutParams(new android.widget.Gallery.LayoutParams(-2, -2));
            if(bitmap != null)
            {
                float f;
                float f1;
                if(bitmap == mPhotoDownloadingBitmap)
                    f = 1F;
                else
                    f = 1.4F;
                f1 = getImageMultiplier(bitmap, f);
                linearlayout.setLayoutParams(new android.widget.Gallery.LayoutParams((int)(f1 * (float)bitmap.getWidth()) + 2 * mImagePadding, (int)(f1 * (float)bitmap.getHeight()) + 2 * mImagePadding));
            }
            return linearlayout;
        }

        public List pullMediaContentFromPosts(List list)
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = list.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                FacebookPost facebookpost = (FacebookPost)iterator.next();
                if(facebookpost.getPostType() == 2 || facebookpost.getPostType() == 3)
                    arraylist.add(facebookpost);
            } while(true);
            return arraylist;
        }

        public void updatePhoto(Bitmap bitmap, String s)
        {
            List list = (List)mPhotoUrls.get(s);
            if(list != null)
            {
                if(bitmap == null)
                {
                    Iterator iterator1 = mMediaContent.iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        FacebookPost facebookpost = (FacebookPost)iterator1.next();
                        if(s.equals(((com.facebook.katana.model.FacebookPost.Attachment.MediaItem)facebookpost.getAttachment().getMediaItems().get(0)).src))
                        {
                            iterator1.remove();
                            removePost(facebookpost);
                        }
                    } while(true);
                } else
                {
                    float f = getImageMultiplier(bitmap, 1.4F);
                    ImageView imageview;
                    for(Iterator iterator = list.iterator(); iterator.hasNext(); imageview.setLayoutParams(new android.widget.Gallery.LayoutParams((int)(f * (float)bitmap.getWidth()), (int)(f * (float)bitmap.getHeight()))))
                    {
                        imageview = (ImageView)iterator.next();
                        imageview.setImageBitmap(bitmap);
                    }

                }
                notifyDataSetChanged();
            }
        }

        private final float mClippingMultiplier = 4F;
        private final Context mContext;
        private int mGalleryHeight;
        private final int mImagePadding;
        private boolean mIsFirstLoad;
        private final float mMaxMultiplier = 1.4F;
        private final List mMediaContent = new LinkedList();
        private final Bitmap mPhotoDownloadingBitmap;
        private final Map mPhotoUrls = new HashMap();
        private final StreamPhotosCache mPhotosContainer;
        private final Set mPostIds = new HashSet();
        final HomeActivity this$0;



        public ReelAdapter(Context context, StreamPhotosCache streamphotoscache)
        {
            this$0 = HomeActivity.this;
            super();
            mContext = context;
            mPhotosContainer = streamphotoscache;
            mPhotoDownloadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f020100);
            mGalleryHeight = -1;
            mImagePadding = (int)TypedValue.applyDimension(1, 7F, mContext.getResources().getDisplayMetrics());
            mIsFirstLoad = true;
        }
    }

    protected class ReelListener
        implements android.widget.AdapterView.OnItemSelectedListener, android.widget.AdapterView.OnItemClickListener
    {

        public void onItemClick(AdapterView adapterview, View view, int i, long l)
        {
            FacebookPost facebookpost = (FacebookPost)mReelAdapter.mMediaContent.get(i);
            Intent intent = new Intent(HomeActivity.this, com/facebook/katana/activity/feedback/FeedbackActivity);
            intent.putExtra("extra_post_id", facebookpost.postId);
            intent.putExtra("extra_uid", mAppSession.getSessionInfo().userId);
            startActivity(intent);
        }

        public void onItemSelected(AdapterView adapterview, View view, int i, long l)
        {
            if(mReelAdapter.mMediaContent.size() > 0 && mReelAdapter.mMediaContent.size() - i < 6 && mReelAdapter.mMediaContent.size() < 50)
                streamGet(mReelAdapter.getLastPost());
        }

        public void onNothingSelected(AdapterView adapterview)
        {
        }

        final HomeActivity this$0;

        protected ReelListener()
        {
            this$0 = HomeActivity.this;
            super();
        }
    }

    private class PhotoReelAppSessionListener extends AppSessionListener
    {

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            mReelAdapter.updatePhoto(bitmap, s2);
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mReelAdapter.updatePhoto(bitmap, s);
        }

        public void onStreamGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                long al[], long l1, long l2, int j, int k, 
                List list, FacebookStreamContainer facebookstreamcontainer)
        {
            List list1;
            ArrayList arraylist;
            FacebookPost facebookpost;
            list1 = null;
            if(facebookstreamcontainer != null)
                list1 = facebookstreamcontainer.getPosts();
            arraylist = new ArrayList();
            facebookpost = null;
            if(list1 != null && list1.size() > 0)
            {
                Iterator iterator = list1.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    FacebookPost facebookpost1 = (FacebookPost)iterator.next();
                    if(facebookpost1.getPostType() == 2 || facebookpost1.getPostType() == 3)
                        arraylist.add(facebookpost1);
                } while(true);
                facebookpost = (FacebookPost)list1.get(list1.size() - 1);
            }
            if(list1 == null || list1.size() != 0) goto _L2; else goto _L1
_L1:
            return;
_L2:
            boolean flag = false;
            if(arraylist.size() > 0 && mReelAdapter.addMediaContent(arraylist))
                flag = true;
            if(!flag || mReelAdapter.getCount() < 10)
                streamGet(facebookpost);
            if(true) goto _L1; else goto _L3
_L3:
        }

        final HomeActivity this$0;

        private PhotoReelAppSessionListener()
        {
            this$0 = HomeActivity.this;
            super();
        }

    }

    private class UpdateNativeLoadingIndicator extends com.facebook.katana.webview.FacebookWebView.NativeUICallHandler
    {

        public void handleNonUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            mIsFacewebNotificationSync = facebookrpccall.method.equals("pageLoading");
            if(!mIsFacewebNotificationSync)
                mNotifLastRefreshedTime = System.currentTimeMillis();
        }

        public void handleUI(FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            updateProgress();
            HomeActivity homeactivity = HomeActivity.this;
            com.facebook.katana.service.method.PerfLogging.Step step;
            if(mIsFacewebNotificationSync)
                step = com.facebook.katana.service.method.PerfLogging.Step.DATA_REQUESTED;
            else
                step = com.facebook.katana.service.method.PerfLogging.Step.DATA_RECEIVED;
            PerfLogging.logStep(homeactivity, step, getTag(), getActivityId());
        }

        final HomeActivity this$0;

        public UpdateNativeLoadingIndicator(Handler handler)
        {
            this$0 = HomeActivity.this;
            super(handler);
        }
    }

    private class NotificationsAppSessionListener extends AppSessionListener
    {

        public void onGetNotificationHistoryComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            updateProgress();
            if(i != 200)
            {
                String s2 = StringUtils.getErrorString(HomeActivity.this, getString(0x7f0a00d6), i, s1, exception);
                Toaster.toast(HomeActivity.this, s2);
            } else
            {
                updateUnreadNotificationCount(getUnreadNotifications().size());
            }
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mAdapter.updatePhoto(bitmap, s);
        }

        final HomeActivity this$0;

        private NotificationsAppSessionListener()
        {
            this$0 = HomeActivity.this;
            super();
        }

    }


    public HomeActivity()
    {
    }

    private List getUnreadNotifications()
    {
        ArrayList arraylist;
        Cursor cursor;
        arraylist = new ArrayList();
        cursor = mAdapter.getCursor();
        if(cursor != null) goto _L2; else goto _L1
_L1:
        return arraylist;
_L2:
        boolean flag;
        boolean flag1;
        if((cursor instanceof AbstractWindowedCursor) && ((AbstractWindowedCursor)cursor).getWindow() == null)
            flag = true;
        else
            flag = false;
        flag1 = false;
        if(cursor.isClosed() || flag)
        {
            Utils.reportSoftError("STALE_CURSOR", (new StringBuilder()).append("Reopened temporary cursor to prevent StaleDataException. isclosed=").append(cursor.isClosed()).append(" windowClosed=").append(flag).toString());
            cursor = getContentResolver().query(NotificationsProvider.CONTENT_URI, NotificationsAdapter.NotificationsQuery.PROJECTION, null, null, null);
            flag1 = true;
        }
        if(cursor.moveToFirst())
            do
                if(cursor.getInt(10) == 0)
                    arraylist.add(Long.valueOf(cursor.getLong(1)));
            while(cursor.moveToNext());
        if(flag1)
            cursor.close();
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void hideNotificationPanelProgressIndicator()
    {
        if(mNotificationPanelProgressIndicatorShown)
        {
            mNotificationPanelProgressIndicatorShown = false;
            findViewById(0x7f0e00dc).setVisibility(4);
        }
    }

    private void hideUnreadNotificationsBadge()
    {
        mNotificationsPanel.findViewById(0x7f0e00da).setVisibility(8);
        ((ImageView)mNotificationsPanel.findViewById(0x7f0e00d9)).setVisibility(0);
    }

    private void initView()
    {
        setContentView(0x7f030032);
        setPrimaryActionIcon(0x7f0200f9);
        ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0144);
        imagebutton.setFocusable(false);
        imagebutton.setImageResource(0x7f020070);
        setupIcons();
        mGalleryView = (Gallery)findViewById(0x7f0e009d);
        if(isMediaReelSupported())
        {
            ReelListener reellistener = new ReelListener();
            mGalleryView.setHorizontalFadingEdgeEnabled(false);
            mGalleryView.setAdapter(mReelAdapter);
            mGalleryView.setOnItemClickListener(reellistener);
            mGalleryView.setOnItemSelectedListener(reellistener);
        }
        if(!shouldMediaReelBeVisible())
            findViewById(0x7f0e009c).setVisibility(8);
        setupFacewebNotification();
        if(mFacewebNotificationsView == null)
        {
            setupNotificationsEmptyView();
            ListView listview = getListView();
            listview.setAdapter(mAdapter);
            listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView adapterview, View view, int i, long l)
                {
                    Cursor cursor = mAdapter.getCursor();
                    cursor.moveToPosition(i);
                    String s = cursor.getString(13);
                    String s1 = cursor.getString(12);
                    String s2 = cursor.getString(9);
                    Intent intent = FacebookNotification.getIntentForNotification(s, s1, mUserId, s2, HomeActivity.this);
                    long al[];
                    if(intent == null)
                        mAppSession.openURL(HomeActivity.this, s2);
                    else
                        startActivityForResult(intent, 4);
                    al = new long[1];
                    al[0] = cursor.getLong(1);
                    markNotificationsAsRead(al);
                }

                final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
            }
);
        }
        mNotificationsPanel = (SlidingDrawer)findViewById(0x7f0e009e);
        mNotificationsPanel.setOnDrawerOpenListener(new android.widget.SlidingDrawer.OnDrawerOpenListener() {

            public void onDrawerOpened()
            {
                mIsNotificationsOpen = true;
                hideUnreadNotificationsBadge();
            }

            final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
        }
);
        mNotificationsPanel.setOnDrawerCloseListener(new android.widget.SlidingDrawer.OnDrawerCloseListener() {

            public void onDrawerClosed()
            {
                mIsNotificationsOpen = false;
                markAllNotificationsAsRead();
                hideNotificationPanelProgressIndicator();
            }

            final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
        }
);
        updateUnreadNotificationCount(mNumUnreadNotifications);
        if(mIsNotificationsOpen)
            mNotificationsPanel.open();
    }

    private void markAllNotificationsAsRead()
    {
        updateUnreadNotificationCount(0);
        hideUnreadNotificationsBadge();
        List list = getUnreadNotifications();
        if(list.size() > 0)
        {
            long al[] = new long[list.size()];
            for(int i = 0; i < al.length; i++)
                al[i] = ((Long)list.get(i)).longValue();

            markNotificationsAsRead(al);
        }
    }

    private void markNotificationsAsRead(long al[])
    {
        mAppSession.notificationsMarkAsRead(this, al);
    }

    private void setupIcon(int i, int j, int k)
    {
        ViewGroup viewgroup = (ViewGroup)findViewById(i);
        viewgroup.setOnClickListener(this);
        viewgroup.setOnLongClickListener(this);
        viewgroup.setOnFocusChangeListener(this);
        viewgroup.setOnTouchListener(this);
        ((ImageView)((LinearLayout)viewgroup.getChildAt(0)).getChildAt(0)).setImageResource(j);
        ((TextView)viewgroup.getChildAt(1)).setText(k);
    }

    private void setupIcons()
    {
        setupIcon(0x7f0e0093, 0x7f020086, 0x7f0a0084);
        setupIcon(0x7f0e0094, 0x7f020089, 0x7f0a0087);
        setupIcon(0x7f0e0095, 0x7f020083, 0x7f0a007f);
        setupIcon(0x7f0e0096, 0x7f020085, 0x7f0a0082);
        setupIcon(0x7f0e0097, 0x7f020088, 0x7f0a0083);
        setupIcon(0x7f0e0098, 0x7f020084, 0x7f0a0080);
        setupIcon(0x7f0e0099, 0x7f020082, 0x7f0a007e);
        setupIcon(0x7f0e009a, 0x7f020087, 0x7f0a0086);
        setupIcon(0x7f0e009b, 0x7f020081, 0x7f0a007d);
    }

    private void setupNotificationsEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a00d8);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a00d7);
    }

    private void showNotificationPanelProgressIndicator()
    {
        if(mNotificationsPanel.isOpened())
        {
            mNotificationPanelProgressIndicatorShown = true;
            findViewById(0x7f0e00dc).setVisibility(0);
        }
    }

    private void showUnreadNotificationsBadge(int i)
    {
        mNotificationsPanel.findViewById(0x7f0e00da).setVisibility(0);
        ((TextView)mNotificationsPanel.findViewById(0x7f0e00db)).setText(Integer.toString(i));
        ((ImageView)mNotificationsPanel.findViewById(0x7f0e00d9)).setVisibility(8);
    }

    private void updateProgress()
    {
        if(mIsFacewebNotificationSync || mAppSession.isNotificationsSyncPending())
        {
            findViewById(0x7f0e00f1).setVisibility(0);
            showNotificationPanelProgressIndicator();
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e00f1).setVisibility(8);
            hideNotificationPanelProgressIndicator();
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    public boolean facebookOnBackPressed()
    {
        setTransitioningToBackground(true);
        finish();
        return true;
    }

    public boolean isMediaReelSupported()
    {
        boolean flag;
        if(Math.max(getWindowManager().getDefaultDisplay().getHeight(), getWindowManager().getDefaultDisplay().getWidth()) > 350)
            flag = true;
        else
            flag = false;
        return flag;
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
            if(mAppSession != null)
                mAppSession.settingsChanged(this);
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131624083 2131624091: default 56
    //                   2131624083 57
    //                   2131624084 101
    //                   2131624085 68
    //                   2131624086 90
    //                   2131624087 123
    //                   2131624088 134
    //                   2131624089 112
    //                   2131624090 79
    //                   2131624091 145;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10
_L1:
        return;
_L2:
        IntentUriHandler.handleUri(this, "fb://feed");
        continue; /* Loop/switch isn't completed */
_L4:
        IntentUriHandler.handleUri(this, "fb://friends");
        continue; /* Loop/switch isn't completed */
_L9:
        IntentUriHandler.handleUri(this, "fb://albums");
        continue; /* Loop/switch isn't completed */
_L5:
        IntentUriHandler.handleUri(this, "fb://messaging");
        continue; /* Loop/switch isn't completed */
_L3:
        IntentUriHandler.handleUri(this, "fb://profile");
        continue; /* Loop/switch isn't completed */
_L8:
        IntentUriHandler.handleUri(this, "fb://events");
        continue; /* Loop/switch isn't completed */
_L6:
        IntentUriHandler.handleUri(this, "fb://places");
        continue; /* Loop/switch isn't completed */
_L7:
        IntentUriHandler.handleUri(this, "fb://groups");
        continue; /* Loop/switch isn't completed */
_L10:
        IntentUriHandler.handleUri(this, "fb://online");
        if(true) goto _L1; else goto _L11
_L11:
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        mGalleryView.setAdapter(null);
        mReelAdapter.clearViews();
        getListView().setAdapter(null);
        mAdapter.clearViews();
        if(mFacewebNotificationsViewContainer != null)
            ((ViewGroup)findViewById(0x7f0e00a0)).removeView(mFacewebNotificationsViewContainer);
        initView();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mUserId = mAppSession.getSessionInfo().userId;
            mAppSessionListener = new AppSessionListener() {

                public void onLogoutComplete(AppSession appsession, String s, int i, String s1, Exception exception)
                {
                    removeDialog(2);
                    LoginActivity.toLogin(HomeActivity.this);
                }

                final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
            }
;
            mAppSession.getConfig(this);
            mNotificationsAppSessionListener = new NotificationsAppSessionListener();
            mPhotoReelAppSessionListener = new PhotoReelAppSessionListener();
            mAdapter = new NotificationsAdapter(this, null, mAppSession.getPhotosCache());
            mReelAdapter = new ReelAdapter(this, mAppSession.getPhotosCache());
            mAdapter.changeCursor(managedQuery(NotificationsProvider.CONTENT_URI, NotificationsAdapter.NotificationsQuery.PROJECTION, null, null, null));
            ComposerActivity.cleanup(this);
            initView();
            if(getIntent().getBooleanExtra(EXTRA_SHOW_NOTIFICATIONS, false))
                mNotificationsPanel.open();
            mLastPausedTime = System.currentTimeMillis();
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 74
    //                   2 28;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a01bd));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L2:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                if(mAppSession != null)
                {
                    mAppSession.authLogout(HomeActivity.this);
                    showDialog(2);
                }
            }

            final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0081), 0x108009b, getString(0x7f0a01be), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a0089).setIcon(0x1080049);
        menu.add(0, 3, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        menu.add(0, 4, 0, 0x7f0a008e).setIcon(0x1080041);
        menu.add(0, 5, 0, 0x7f0a0081).setIcon(0x7f0200b1);
        if(FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone())
            menu.add(0, 6, 0, "Crash").setIcon(0x1080038);
        return true;
    }

    protected void onDestroy()
    {
        if(mFacewebNotificationsView != null)
        {
            mFacewebNotificationsView.destroy();
            mFacewebNotificationsView = null;
        }
        super.onDestroy();
    }

    public void onFocusChange(View view, boolean flag)
    {
        view.getId();
        JVM INSTR tableswitch 2131624083 2131624097: default 80
    //                   2131624083 81
    //                   2131624084 81
    //                   2131624085 81
    //                   2131624086 81
    //                   2131624087 81
    //                   2131624088 81
    //                   2131624089 81
    //                   2131624090 81
    //                   2131624091 81
    //                   2131624092 80
    //                   2131624093 80
    //                   2131624094 80
    //                   2131624095 80
    //                   2131624096 80
    //                   2131624097 81;
           goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L1 _L1 _L1 _L1 _L1 _L2
_L1:
        return;
_L2:
        ImageView imageview = (ImageView)((LinearLayout)((ViewGroup)view).getChildAt(0)).getChildAt(0);
        if(flag)
            imageview.setColorFilter(getResources().getColor(0x7f070001), android.graphics.PorterDuff.Mode.SRC_ATOP);
        else
            imageview.setColorFilter(getResources().getColor(0x7f070000), android.graphics.PorterDuff.Mode.SRC_ATOP);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(i == 4 && keyevent.getRepeatCount() == 0 && mNotificationsPanel.isOpened())
        {
            mNotificationsPanel.animateClose();
            flag = true;
        } else
        {
            flag = super.onKeyDown(i, keyevent);
        }
        return flag;
    }

    public boolean onLongClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131624083 2131624083: default 24
    //                   2131624083 28;
           goto _L1 _L2
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        if(!FacebookAffiliation.isCurrentUserEmployee())
            continue; /* Loop/switch isn't completed */
        ((ImageView)((LinearLayout)((ViewGroup)view).getChildAt(0)).getChildAt(0)).setColorFilter(getResources().getColor(0x7f070000), android.graphics.PorterDuff.Mode.SRC_ATOP);
        startActivity(new Intent(this, com/facebook/katana/activity/stream/StreamActivity));
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L1; else goto _L5
_L5:
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 6: default 40
    //                   2 46
    //                   3 65
    //                   4 95
    //                   5 166
    //                   6 113;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        startActivityForResult(new Intent(this, com/facebook/katana/SettingsActivity), 3);
        continue; /* Loop/switch isn't completed */
_L3:
        mAppSession.syncNotifications(this);
        if(mFacewebNotificationsView != null)
            mFacewebNotificationsView.refresh();
        updateProgress();
        continue; /* Loop/switch isn't completed */
_L4:
        startActivity(new Intent(this, com/facebook/katana/HtmlAboutActivity));
        continue; /* Loop/switch isn't completed */
_L6:
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("How do you want to fail?").setPositiveButton("Hard crash", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                throw new Error("Intentional user-triggered crash");
            }

            final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
        }
).setNegativeButton("Soft error", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                Utils.reportSoftError("Intentional user-triggered soft error", "message accompanying user-triggered soft errorwith embedded new\nline.");
                dialoginterface.cancel();
            }

            final HomeActivity this$0;

            
            {
                this$0 = HomeActivity.this;
                super();
            }
        }
);
        builder.show();
        continue; /* Loop/switch isn't completed */
_L5:
        showDialog(1);
        if(true) goto _L1; else goto _L7
_L7:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
        {
            mAppSession.removeListener(mAppSessionListener);
            mAppSession.removeListener(mNotificationsAppSessionListener);
            mAppSession.removeListener(mPhotoReelAppSessionListener);
        }
        mLastPausedTime = System.currentTimeMillis();
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        boolean flag = mAppSession.isNotificationsSyncPending();
        MenuItem menuitem = menu.findItem(3);
        boolean flag1;
        if(!flag)
            flag1 = true;
        else
            flag1 = false;
        menuitem.setEnabled(flag1);
        return true;
    }

    protected void onResume()
    {
        super.onResume();
        updateProgress();
        TempFileManager.cleanup();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(isMediaReelSupported())
            {
                FacebookStreamContainer facebookstreamcontainer = mAppSession.getStreamContainer(mUserId, FacebookStreamType.NEWSFEED_STREAM);
                long l;
                Object aobj[];
                String s;
                if(facebookstreamcontainer == null)
                {
                    if(!mAppSession.isStreamGetPending(mUserId, FacebookStreamType.NEWSFEED_STREAM))
                        streamGet(null);
                } else
                {
                    mReelAdapter.addMediaContent(mReelAdapter.pullMediaContentFromPosts(facebookstreamcontainer.getPosts()));
                }
                mAppSession.addListener(mPhotoReelAppSessionListener);
            }
            mAppSession.addListener(mAppSessionListener);
            mAppSession.addListener(mNotificationsAppSessionListener);
            if(!mAppSession.isNotificationsSyncPending())
            {
                mAppSession.syncNotifications(this);
                updateProgress();
            }
            if(mFacewebNotificationsView != null)
            {
                l = (System.currentTimeMillis() - mLastPausedTime) / 1000L;
                aobj = new Object[2];
                aobj[0] = Long.valueOf(l);
                aobj[1] = "true";
                s = String.format("(function() { if (window.fwDidEnterForeground) { fwDidEnterForeground(%d, %s); } })()", aobj);
                mFacewebNotificationsView.executeJs(s, null);
            }
            PlacesUserSettings.get(this, "places_opt_in");
        }
    }

    public boolean onTouch(View view, MotionEvent motionevent)
    {
        view.getId();
        JVM INSTR tableswitch 2131624083 2131624097: default 80
    //                   2131624083 82
    //                   2131624084 82
    //                   2131624085 82
    //                   2131624086 82
    //                   2131624087 82
    //                   2131624088 82
    //                   2131624089 82
    //                   2131624090 82
    //                   2131624091 82
    //                   2131624092 80
    //                   2131624093 80
    //                   2131624094 80
    //                   2131624095 80
    //                   2131624096 80
    //                   2131624097 82;
           goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L1 _L1 _L1 _L1 _L1 _L2
_L1:
        return false;
_L2:
        ImageView imageview = (ImageView)((LinearLayout)((ViewGroup)view).getChildAt(0)).getChildAt(0);
        if(motionevent.getAction() == 0)
            imageview.setColorFilter(getResources().getColor(0x7f070002), android.graphics.PorterDuff.Mode.SRC_ATOP);
        else
        if(motionevent.getAction() == 1)
            imageview.setColorFilter(getResources().getColor(0x7f070000), android.graphics.PorterDuff.Mode.SRC_ATOP);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void setupFacewebNotification()
    {
        if(mFacewebNotificationsView == null)
        {
            Intent intent = IntentUriHandler.getIntentForUri(this, "fb://notifications");
            if(intent != null && intent.getComponent().getClassName().equals(com/facebook/katana/activity/faceweb/FacewebChromeActivity.getCanonicalName()))
            {
                mFacewebNotificationsViewContainer = new RefreshableFacewebWebViewContainer(this);
                mFacewebNotificationsView = mFacewebNotificationsViewContainer.getWebView();
                String s = intent.getStringExtra("mobile_page");
                mFacewebNotificationsView.loadMobilePage(s);
                mHandler = new Handler();
                UpdateNativeLoadingIndicator updatenativeloadingindicator = new UpdateNativeLoadingIndicator(mHandler);
                mFacewebNotificationsView.registerNativeCallHandler("pageLoading", updatenativeloadingindicator);
                mFacewebNotificationsView.registerNativeCallHandler("pageLoaded", updatenativeloadingindicator);
            }
        }
        if(mFacewebNotificationsView != null)
        {
            getListView().setVisibility(8);
            ((ViewGroup)findViewById(0x7f0e00a0)).addView(mFacewebNotificationsViewContainer);
        }
    }

    public boolean shouldMediaReelBeVisible()
    {
        return isMediaReelSupported();
    }

    public void streamGet(FacebookPost facebookpost)
    {
        if(!mAppSession.isStreamGetPending(mAppSession.getSessionInfo().userId, FacebookStreamType.NEWSFEED_STREAM))
        {
            byte byte0 = 0;
            long l = -1L;
            if(facebookpost != null)
            {
                byte0 = 2;
                l = facebookpost.createdTime;
            }
            mAppSession.streamGet(this, mAppSession.getSessionInfo().userId, null, -1L, l, 20, byte0, FacebookStreamType.NEWSFEED_STREAM);
        }
    }

    public void titleBarClickHandler(View view)
    {
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        Intent intent = IntentUriHandler.getIntentForUri(this, "fb://feed");
        intent.setAction("com.facebook.katana.SHARE");
        startActivity(intent);
    }

    public void updateUnreadNotificationCount(int i)
    {
        mNumUnreadNotifications = i;
        if(i > 0 && !mNotificationsPanel.isOpened())
        {
            showUnreadNotificationsBadge(mNumUnreadNotifications);
            long l = System.currentTimeMillis() - mNotifLastRefreshedTime;
            if(mFacewebNotificationsView != null && !mIsFacewebNotificationSync && l > 30000L)
                mFacewebNotificationsView.refresh();
        }
    }

    private static final int ABOUT_ID = 4;
    private static final int CRASH_ID = 6;
    public static String EXTRA_SHOW_NOTIFICATIONS = "notifications";
    private static final int LOGOUT_DIALOG_ID = 1;
    private static final int LOGOUT_ID = 5;
    private static final int MAX_REEL_ITEMS = 50;
    private static final int MEDIAREEL_MIN_SCREEN_HEIGHT = 350;
    private static final int NOTIFICATIONS_RESULT_CODE = 4;
    private static final String NOTIFICATIONS_URI = "fb://notifications";
    private static final int PROGRESS_LOGGING_OUT_DIALOG_ID = 2;
    private static final int REFRESH_MENU_ID = 3;
    private static final long REFRESH_NOTIFICATION_MS = 30000L;
    private static final int SETTINGS_ID = 2;
    private static final int SETTINGS_RESULT_CODE = 3;
    private NotificationsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private FacewebWebView mFacewebNotificationsView;
    RefreshableFacewebWebViewContainer mFacewebNotificationsViewContainer;
    private Gallery mGalleryView;
    private Handler mHandler;
    private boolean mIsFacewebNotificationSync;
    private boolean mIsNotificationsOpen;
    private long mLastPausedTime;
    private long mNotifLastRefreshedTime;
    private boolean mNotificationPanelProgressIndicatorShown;
    private AppSessionListener mNotificationsAppSessionListener;
    private SlidingDrawer mNotificationsPanel;
    private int mNumUnreadNotifications;
    private AppSessionListener mPhotoReelAppSessionListener;
    private ReelAdapter mReelAdapter;
    private long mUserId;






/*
    static boolean access$1402(HomeActivity homeactivity, boolean flag)
    {
        homeactivity.mIsNotificationsOpen = flag;
        return flag;
    }

*/










/*
    static boolean access$602(HomeActivity homeactivity, boolean flag)
    {
        homeactivity.mIsFacewebNotificationSync = flag;
        return flag;
    }

*/


/*
    static long access$702(HomeActivity homeactivity, long l)
    {
        homeactivity.mNotifLastRefreshedTime = l;
        return l;
    }

*/


}
