// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViewPhotoActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.view.animation.*;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.katana.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.ui.ImageViewTouchBase;
import com.facebook.katana.ui.PhotoGallery;
import com.facebook.katana.util.*;
import custom.android.AdapterView;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.media:
//            PhotoGalleryAdapter, PhotoFeedbackActivity, PhotoInfoDialog, PhotosActivity

public class ViewPhotoActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener, custom.android.AdapterView.OnItemSelectedListener
{
    private class ControlGestureListener extends android.view.GestureDetector.SimpleOnGestureListener
    {

        public boolean onSingleTapConfirmed(MotionEvent motionevent)
        {
            if(!mControlVisible)
                showOnScreenControls();
            else
                hideOnScreenControls();
            return true;
        }

        final ViewPhotoActivity this$0;

        private ControlGestureListener()
        {
            this$0 = ViewPhotoActivity.this;
            super();
        }

    }

    private class IntentUserTask extends UserTask
    {

        protected void doInBackground()
        {
            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
            ImageUtils.decodeFile(mFilename, options);
            mMimeType = options.outMimeType;
            if(mMimeType == null)
                Log.e("doInBackground", "Unknown MIME type");
            else
                mPhotoUri = TempFileManager.addImage(mFilename);
        }

        protected void onPostExecute()
        {
            if(mUserTaskProgress != null)
            {
                mUserTaskProgress.dismiss();
                mUserTaskProgress = null;
            }
            if(mMimeType != null) goto _L2; else goto _L1
_L1:
            Toaster.toast(ViewPhotoActivity.this, 0x7f0a022b);
_L4:
            return;
_L2:
            if(mPhotoUri == null)
                Toaster.toast(ViewPhotoActivity.this, 0x7f0a0225);
            else
            if(mAction.equals("android.intent.action.SEND"))
            {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(mMimeType);
                intent.putExtra("android.intent.extra.STREAM", mPhotoUri);
                try
                {
                    startActivity(Intent.createChooser(intent, getText(0x7f0a022a)));
                }
                catch(ActivityNotFoundException activitynotfoundexception)
                {
                    Toaster.toast(ViewPhotoActivity.this, 0x7f0a0228);
                }
            } else
            if(mAction.equals("android.intent.action.ATTACH_DATA"))
            {
                Intent intent1 = new Intent("android.intent.action.ATTACH_DATA");
                intent1.setDataAndType(mPhotoUri, mMimeType);
                intent1.putExtra("mimeType", mMimeType);
                startActivity(Intent.createChooser(intent1, getString(0x7f0a0229)));
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        protected void onPreExecute()
        {
            mUserTaskProgress = new ProgressDialog(ViewPhotoActivity.this);
            mUserTaskProgress.setProgressStyle(0);
            mUserTaskProgress.setMessage(getText(0x7f0a0227));
            mUserTaskProgress.setIndeterminate(true);
            mUserTaskProgress.setCancelable(false);
            mUserTaskProgress.show();
        }

        private final String mAction;
        private final String mFilename;
        private String mMimeType;
        private Uri mPhotoUri;
        final ViewPhotoActivity this$0;

        public IntentUserTask(Handler handler, String s, String s1)
        {
            this$0 = ViewPhotoActivity.this;
            super(handler);
            mAction = s;
            mFilename = s1;
        }
    }

    private final class QueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(!isFinishing())
            {
                startManagingCursor(cursor);
                mPhotosCursor = cursor;
                refreshUI();
                String s = getIntent().getStringExtra("action");
                if(s == null)
                    s = getIntent().getAction();
                if(StringUtils.saneStringEquals(s, "android.intent.action.EDIT"))
                {
                    String s1 = cursor.getString(2);
                    ((TextView)findViewById(0x7f0e015d)).setText(s1);
                    enterEditMode();
                }
            } else
            {
                cursor.close();
            }
        }

        public static final int ALBUM_PHOTOS_TOKEN = 1;
        final ViewPhotoActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = ViewPhotoActivity.this;
            super(context.getContentResolver());
        }
    }

    private class ViewPhotoAppSessionListener extends AppSessionListener
    {

        public void onDownloadPhotoFullComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            if(s2 == null || mAlbumId == null || s2.equals(mAlbumId)) goto _L2; else goto _L1
_L1:
            return;
_L2:
            if(i != 200)
            {
                Log.e(ViewPhotoActivity.TAG, "Error when download photo", exception);
                String s5 = StringUtils.getErrorString(ViewPhotoActivity.this, getString(0x7f0a0130), i, s1, exception);
                Toaster.toast(ViewPhotoActivity.this, s5);
            } else
            {
                mPendingDownloadMap.remove(s3);
                mDownloadedPhotos.add(s3);
                mGalleryAdapter.requery();
                if(mDownloadedPhotos.size() > 5)
                {
                    String s4 = (String)mDownloadedPhotos.get(0);
                    if(!s4.equals(mPhotoId))
                    {
                        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, s4);
                        PhotosProvider.clearPhotoFiles(ViewPhotoActivity.this, uri);
                        mGalleryAdapter.requery();
                        mDownloadedPhotos.remove(0);
                    }
                }
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onPhotoDeletePhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            removeDialog(4);
            mDeletePhotoReqId = null;
            if(i == 200)
            {
                mGalleryAdapter.requery();
            } else
            {
                String s4 = StringUtils.getErrorString(ViewPhotoActivity.this, getString(0x7f0a012a), i, s1, exception);
                Toaster.toast(ViewPhotoActivity.this, s4);
            }
        }

        public void onPhotoEditPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            dismissDialog(1);
            if(i == 200)
            {
                mGalleryAdapter.requery();
                exitEditMode();
                Toaster.toast(ViewPhotoActivity.this, 0x7f0a0224);
            } else
            {
                Toaster.toast(ViewPhotoActivity.this, 0x7f0a0223);
            }
        }

        public void onPhotoGetAlbumsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String as[], long l)
        {
            removeDialog(5);
            if(200 == i)
            {
                ViewPhotoActivity.D("get album query succeeded");
                loadAlbum();
            } else
            {
                String s2 = StringUtils.getErrorString(ViewPhotoActivity.this, getString(0x7f0a0011), i, s1, exception);
                Toaster.toast(ViewPhotoActivity.this, s2);
            }
        }

        public void onPhotoGetPhotosComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String as[], 
                long l)
        {
            boolean flag;
            boolean flag1;
            if(200 == i)
                flag = true;
            else
                flag = false;
            flag1 = false;
            if(as == null || Arrays.binarySearch(as, mPhotoId) < 0) goto _L2; else goto _L1
_L1:
            mCurrentPhotoMetadataTask.receivedResponse = true;
            flag1 = true;
            if(flag)
                ViewPhotoActivity.D("get photo query succeeded");
_L4:
            if(flag1 && !isFinishing() && mPhotosCursor != null)
            {
                removeDialog(5);
                mPhotosCursor.requery();
                refreshUI();
            }
            return;
_L2:
            if(s2 != null && s2.equals(mAlbumId))
            {
                mAlbumPhotosMetadataTask.receivedResponse = true;
                flag1 = true;
                if(flag)
                    ViewPhotoActivity.D("get album photos query succeeded");
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final ViewPhotoActivity this$0;

        private ViewPhotoAppSessionListener()
        {
            this$0 = ViewPhotoActivity.this;
            super();
        }

    }

    public static interface PhotoQuery
    {

        public static final int INDEX_ALBUM_ID = 0;
        public static final int INDEX_CAPTION = 2;
        public static final int INDEX_FILENAME = 5;
        public static final int INDEX_PHOTO_ID = 1;
        public static final int INDEX_SRC_BIG = 3;
        public static final int INDEX_THUMBNAIL = 4;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[7];
            as[0] = "aid";
            as[1] = "pid";
            as[2] = "caption";
            as[3] = "src_big";
            as[4] = "thumbnail";
            as[5] = "filename";
            as[6] = "_id";
        }
    }


    public ViewPhotoActivity()
    {
        mPaused = true;
    }

    private static void D(String s)
    {
        Log.d("ViewPhotoActivity", s);
    }

    private void editPhoto()
    {
        String s = ((TextView)findViewById(0x7f0e015d)).getText().toString().trim();
        if(s.length() > 0)
        {
            showDialog(1);
            mAppSession.photoEditPhoto(this, mAlbumId, mPhotoId, s);
        }
    }

    private void enterEditMode()
    {
        if(mEditBarView.getVisibility() != 0)
        {
            mEditBarView.setAnimation(mPullUpAnimation);
            mEditBarView.setVisibility(0);
            hideOnScreenControls();
        }
    }

    private void exitEditMode()
    {
        if(mEditBarView.getVisibility() == 0)
        {
            mEditBarView.setAnimation(mPullDownAnimation);
            mEditBarView.setVisibility(8);
        }
    }

    private void hideOnScreenControls()
    {
        mControlVisible = false;
        if(mActionIconPanelView.getVisibility() == 0)
        {
            AlphaAnimation alphaanimation = new AlphaAnimation(1F, 0F);
            alphaanimation.setDuration(500L);
            mActionIconPanelView.startAnimation(alphaanimation);
            mActionIconPanelView.setVisibility(4);
        }
    }

    private void loadAlbum()
    {
        if(mAlbumId == null) goto _L2; else goto _L1
_L1:
        if(mAlbum == null)
            mAlbum = FacebookAlbum.readFromContentProvider(this, mAlbumId);
        if(mAppSession == null) goto _L2; else goto _L3
_L3:
        if(mAlbum != null) goto _L5; else goto _L4
_L4:
        if(!mAlbumMetadataTask.sentRequest)
        {
            D("sending request for album");
            mAlbumMetadataTask.sentRequest = true;
            if(!mAppSession.isAlbumsGetPending(mOwnerId, mAlbumId))
            {
                AppSession appsession = mAppSession;
                long l = mOwnerId;
                String as[] = new String[1];
                as[0] = mAlbumId;
                appsession.photoGetAlbums(this, l, Arrays.asList(as));
            }
        }
_L2:
        return;
_L5:
        removeDialog(5);
        if(!mAlbumPhotosMetadataTask.sentRequest)
        {
            D("sending request for all photos in album");
            mAlbumPhotosMetadataTask.sentRequest = true;
            if(!mAppSession.isPhotosGetPending(mAlbumId, mOwnerId))
                mAppSession.photoGetPhotos(this, mAlbumId, null, mOwnerId);
        }
        if(true) goto _L2; else goto _L6
_L6:
    }

    public static Intent photoIntent(Context context, long l, String s, String s1, String s2)
    {
        Object aobj[] = new Object[4];
        aobj[0] = Long.valueOf(l);
        aobj[1] = s;
        aobj[2] = s1;
        aobj[3] = s2;
        return IntentUriHandler.getIntentForUri(context, String.format("fb://photo/%1$d/%2$s/%3$s?action=%4$s", aobj));
    }

    private void refreshUI()
    {
        if(mPhotosCursor != null) goto _L2; else goto _L1
_L1:
        finish();
_L8:
        return;
_L2:
        boolean flag;
        int i;
        flag = false;
        i = 0;
        if(!mPhotosCursor.moveToFirst()) goto _L4; else goto _L3
_L3:
        String s = mPhotosCursor.getString(1);
        if(!mPhotoId.equals(s)) goto _L6; else goto _L5
_L5:
        flag = true;
_L4:
        if(flag)
        {
            mGalleryAdapter.changeCursor(mPhotosCursor);
            mGallery.setSelection(i);
            mInfoLoaded = true;
        } else
        if(mPhotoId != null)
        {
            if(!mCurrentPhotoMetadataTask.sentRequest)
            {
                mCurrentPhotoMetadataTask.sentRequest = true;
                String as[] = new String[1];
                as[0] = mPhotoId;
                List list = Arrays.asList(as);
                if(!mAppSession.isPhotosGetPending(list, mOwnerId))
                    mAppSession.photoGetPhotos(this, null, list, mOwnerId);
            } else
            if(mCurrentPhotoMetadataTask.receivedResponse)
            {
                Toaster.toast(getApplication(), 0x7f0a0130);
                finish();
            }
        } else
        {
            finish();
        }
        continue; /* Loop/switch isn't completed */
_L6:
        i++;
        if(mPhotosCursor.moveToNext()) goto _L3; else goto _L4
        if(true) goto _L8; else goto _L7
_L7:
    }

    private void setupOnScreenControls()
    {
        mGestureDetector = new GestureDetector(this, new ControlGestureListener());
        findViewById(0x7f0e0159).setOnClickListener(this);
        findViewById(0x7f0e015a).setOnClickListener(this);
        findViewById(0x7f0e015b).setOnClickListener(this);
    }

    private void showOnScreenControls()
    {
        if(!mPaused) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(mActionIconPanelView.getWindowToken() == null)
        {
            mHandler.post(new Runnable() {

                public void run()
                {
                    showOnScreenControls();
                }

                final ViewPhotoActivity this$0;

            
            {
                this$0 = ViewPhotoActivity.this;
                super();
            }
            }
);
        } else
        {
            mControlVisible = true;
            if(mActionIconPanelView.getVisibility() != 0)
            {
                AlphaAnimation alphaanimation = new AlphaAnimation(0F, 1F);
                alphaanimation.setDuration(500L);
                mActionIconPanelView.startAnimation(alphaanimation);
                mActionIconPanelView.setVisibility(0);
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private Uri uriForPhotoQuery()
    {
        Uri uri;
        if(mAlbumId != null)
            uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, mAlbumId);
        else
        if(mPhotoId != null)
            uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, mPhotoId);
        else
            uri = null;
        return uri;
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        boolean flag = true;
        if(!mPaused)
            if(mEditBarView.getVisibility() == 0)
            {
                motionevent.offsetLocation(0F, -mEditBarView.getTop());
                if(!mEditBarView.dispatchTouchEvent(motionevent))
                    motionevent.offsetLocation(0F, mEditBarView.getTop());
            } else
            {
                super.dispatchTouchEvent(motionevent);
                flag = mGestureDetector.onTouchEvent(motionevent);
            }
        return flag;
    }

    public boolean facebookOnBackPressed()
    {
        if(mEditBarView.getVisibility() == 0)
            exitEditMode();
        else
        if(mImageView != null && mImageView.getScale() > 1F)
            mImageView.zoomTo(1F);
        else
            finish();
        return true;
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR lookupswitch 5: default 56
    //                   2131624130: 179
    //                   2131624281: 57
    //                   2131624282: 97
    //                   2131624283: 137
    //                   2131624287: 172;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        return;
_L3:
        String s1 = mPhotosCursor.getString(5);
        if(s1 != null)
            (new IntentUserTask(mHandler, "android.intent.action.ATTACH_DATA", s1)).execute();
        continue; /* Loop/switch isn't completed */
_L4:
        String s = mPhotosCursor.getString(5);
        if(s != null)
            (new IntentUserTask(mHandler, "android.intent.action.SEND", s)).execute();
        continue; /* Loop/switch isn't completed */
_L5:
        Intent intent = new Intent(this, com/facebook/katana/activity/media/PhotoFeedbackActivity);
        intent.setData(Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, mPhotoId));
        startActivity(intent);
        continue; /* Loop/switch isn't completed */
_L6:
        editPhoto();
        continue; /* Loop/switch isn't completed */
_L2:
        ((TextView)findViewById(0x7f0e015d)).setText(null);
        if(true) goto _L1; else goto _L7
_L7:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        Intent intent;
        getWindow().addFlags(1024);
        setContentView(0x7f03008e);
        intent = getIntent();
        if(bundle == null)
            break; /* Loop/switch isn't completed */
        mPhotoId = bundle.getString("state_current_photo_id");
        mAlbumId = bundle.getString("state_current_album_id");
        mOwnerId = bundle.getLong("state_current_owner_id", -1L);
        mCurrentPhotoMetadataTask = (TaskContext)bundle.getParcelable("current_photo_metadata");
        mAlbumMetadataTask = (TaskContext)bundle.getParcelable("album_metadata");
        mAlbumPhotosMetadataTask = (TaskContext)bundle.getParcelable("album_photos_metadata");
_L5:
        showDialog(5);
        loadAlbum();
        mActionIconPanelView = findViewById(0x7f0e0158);
        mGalleryAdapter = new PhotoGalleryAdapter(this, null, mAppSession, mPendingDownloadMap, mAlbumId);
        mGallery = (PhotoGallery)findViewById(0x7f0e009d);
        mGallery.setAdapter(mGalleryAdapter);
        mGallery.setOnItemSelectedListener(this);
        mEditBarView = findViewById(0x7f0e015c);
        mPullUpAnimation = AnimationUtils.loadAnimation(this, 0x7f040005);
        mPullDownAnimation = AnimationUtils.loadAnimation(this, 0x7f040004);
        setupOnScreenControls();
        ((EditText)findViewById(0x7f0e015d)).setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
            {
                if(i == 101)
                    editPhoto();
                return false;
            }

            final ViewPhotoActivity this$0;

            
            {
                this$0 = ViewPhotoActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e015f).setOnClickListener(this);
        findViewById(0x7f0e00c2).setOnClickListener(this);
        mQueryHandler = new QueryHandler(this);
        mAppSessionListener = new ViewPhotoAppSessionListener();
        if(true) goto _L4; else goto _L3
_L3:
        Uri uri = intent.getData();
        String s;
        if(uri != null)
        {
            mPhotoId = uri.getQueryParameter("photo");
            mAlbumId = uri.getQueryParameter("album");
            s = uri.getQueryParameter("user");
        } else
        {
            s = intent.getStringExtra("owner");
            mAlbumId = intent.getStringExtra("album");
            mPhotoId = intent.getStringExtra("photo");
        }
        if(s == null)
            mOwnerId = -1L;
        else
            mOwnerId = Long.valueOf(s).longValue();
        mCurrentPhotoMetadataTask = new TaskContext();
        mAlbumPhotosMetadataTask = new TaskContext();
        mAlbumMetadataTask = new TaskContext();
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 5: default 36
    //                   1 94
    //                   2 40
    //                   3 186
    //                   4 140
    //                   5 202;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        Object obj = null;
_L8:
        return ((Dialog) (obj));
_L3:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                mDeletePhotoReqId = mAppSession.photoDeletePhoto(ViewPhotoActivity.this, mAlbumId, mPhotoId);
                removeDialog(2);
                showDialog(4);
            }

            final ViewPhotoActivity this$0;

            
            {
                this$0 = ViewPhotoActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0129), 0x1080027, getString(0x7f0a012b), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        continue; /* Loop/switch isn't completed */
_L2:
        ProgressDialog progressdialog2 = new ProgressDialog(this);
        progressdialog2.setProgressStyle(0);
        progressdialog2.setMessage(getText(0x7f0a022d));
        progressdialog2.setIndeterminate(true);
        progressdialog2.setCancelable(false);
        obj = progressdialog2;
        continue; /* Loop/switch isn't completed */
_L5:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a012c));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L4:
        obj = PhotoInfoDialog.create(this, FacebookPhoto.readFromContentProvider(this, mPhotoId));
        continue; /* Loop/switch isn't completed */
_L6:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a008c));
        progressdialog.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface)
            {
                facebookOnBackPressed();
            }

            final ViewPhotoActivity this$0;

            
            {
                this$0 = ViewPhotoActivity.this;
                super();
            }
        }
);
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(true);
        obj = progressdialog;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 3, 0, 0x7f0a0226).setIcon(0x7f0200cb);
        if(!mFromAlbum)
            menu.add(0, 4, 0, 0x7f0a001c).setIcon(0x7f0200ac);
        return true;
    }

    protected void onDestroy()
    {
        mGestureDetector = null;
        mGallery.setOnItemSelectedListener(null);
        mGallery.destroy();
        mGalleryAdapter = null;
        super.onDestroy();
    }

    public void onItemSelected(AdapterView adapterview, View view, int i, long l)
    {
        mImageView = (ImageViewTouchBase)mGallery.getSelectedView().findViewById(0x7f0e002d);
        mPhotoId = (String)mGallery.getSelectedItem();
        hideOnScreenControls();
    }

    public void onNothingSelected(AdapterView adapterview)
    {
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 1 4: default 36
    //                   1 42
    //                   2 73
    //                   3 81
    //                   4 89;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        String s = mPhotosCursor.getString(2);
        ((TextView)findViewById(0x7f0e015d)).setText(s);
        enterEditMode();
        continue; /* Loop/switch isn't completed */
_L3:
        showDialog(2);
        continue; /* Loop/switch isn't completed */
_L4:
        showDialog(3);
        continue; /* Loop/switch isn't completed */
_L5:
        PhotosActivity.showPhotos(this, mAlbumId, mOwnerId);
        if(true) goto _L1; else goto _L6
_L6:
    }

    protected void onPause()
    {
        super.onPause();
        mPaused = true;
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
        mAlbumMetadataTask.reset();
        mAlbumPhotosMetadataTask.reset();
        mCurrentPhotoMetadataTask.reset();
        if(mUserTaskProgress != null)
        {
            mUserTaskProgress.dismiss();
            mUserTaskProgress = null;
        }
        mPendingDownloadMap.clear();
    }

    protected void onPrepareDialog(int i, Dialog dialog)
    {
        i;
        JVM INSTR tableswitch 3 3: default 20
    //                   3 21;
           goto _L1 _L2
_L1:
        return;
_L2:
        PhotoInfoDialog.update(dialog, FacebookPhoto.readFromContentProvider(this, mPhotoId));
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(3).setEnabled(mInfoLoaded);
        boolean flag;
        if(mAlbum == null)
            menu.removeItem(4);
        else
        if(menu.findItem(4) == null)
            menu.add(0, 4, 0, 0x7f0a001c).setIcon(0x7f0200ac);
        if(mAlbum != null && mAlbum.getOwner() == mAppSession.getSessionInfo().userId)
        {
            if(menu.findItem(1) == null)
                menu.add(0, 1, 0, 0x7f0a0222).setIcon(0x7f02009c);
            if(menu.findItem(2) == null)
                menu.add(0, 2, 0, 0x7f0a0221).setIcon(0x7f020099);
        } else
        {
            menu.removeItem(1);
            menu.removeItem(2);
        }
        if(mPaused || mGalleryAdapter.getCursor() == null)
            flag = false;
        else
            flag = true;
        return flag;
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        mPaused = false;
        mAppSession.addListener(mAppSessionListener);
        if(mDeletePhotoReqId != null && !mAppSession.isRequestPending(mDeletePhotoReqId))
        {
            removeDialog(4);
            mDeletePhotoReqId = null;
        }
        loadAlbum();
        if(mGalleryAdapter.getCursor() == null)
        {
            mQueryHandler.startQuery(1, null, uriForPhotoQuery(), PhotoQuery.PROJECTION, null, null, null);
        } else
        {
            refreshUI();
            if(StringUtils.saneStringEquals(getIntent().getAction(), "android.intent.action.EDIT"))
            {
                String s = mPhotosCursor.getString(2);
                ((TextView)findViewById(0x7f0e015d)).setText(s);
                enterEditMode();
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(mPhotoId != null)
            bundle.putString("state_current_photo_id", mPhotoId);
        if(mAlbumId != null)
            bundle.putString("state_current_album_id", mAlbumId);
        if(-1L != mOwnerId)
            bundle.putLong("state_current_owner_id", mOwnerId);
        bundle.putParcelable("current_photo_metadata", mCurrentPhotoMetadataTask);
        bundle.putParcelable("album_metadata", mAlbumMetadataTask);
        bundle.putParcelable("album_photos_metadata", mAlbumPhotosMetadataTask);
    }

    protected void onStop()
    {
        super.onStop();
        if(isFinishing())
        {
            PhotosProvider.clearPhotoFiles(this, Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, mAlbumId));
            TempFileManager.cleanup();
        }
    }

    private static final int ALBUM_DOWNLOAD_DIALOG = 5;
    private static final boolean DEBUG = true;
    private static final int DELETE_PHOTO_MENU_ID = 2;
    private static final int DELETE_PHOTO_QUESTION_DIALOG_ID = 2;
    private static final int EDIT_PHOTO_MENU_ID = 1;
    private static final int INFO_ALBUM_ID = 4;
    private static final int INFO_PHOTO_MENU_ID = 3;
    private static final int MAX_CACHED_PHOTOS = 5;
    private static final int PHOTO_INFO_DIALOG_ID = 3;
    private static final int PROGRESS_DELETE_PHOTO_DIALOG_ID = 4;
    private static final int PROGRESS_EDIT_DIALOG_ID = 1;
    private static final String STATE_ALBUM_METADATA_TASK = "album_metadata";
    private static final String STATE_ALBUM_PHOTOS_METADATA_TASK = "album_photos_metadata";
    private static final String STATE_CURRENT_ALBUM_ID = "state_current_album_id";
    private static final String STATE_CURRENT_OWNER_ID = "state_current_owner_id";
    private static final String STATE_CURRENT_PHOTO_ID = "state_current_photo_id";
    private static final String STATE_CURRENT_PHOTO_METADATA_TASK = "current_photo_metadata";
    private static final String TAG = com/facebook/katana/activity/media/ViewPhotoActivity.toString();
    private View mActionIconPanelView;
    private FacebookAlbum mAlbum;
    private String mAlbumId;
    private TaskContext mAlbumMetadataTask;
    private TaskContext mAlbumPhotosMetadataTask;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private boolean mControlVisible;
    private TaskContext mCurrentPhotoMetadataTask;
    private String mDeletePhotoReqId;
    private final List mDownloadedPhotos = new ArrayList();
    private View mEditBarView;
    private boolean mFromAlbum;
    private PhotoGallery mGallery;
    private PhotoGalleryAdapter mGalleryAdapter;
    private GestureDetector mGestureDetector;
    private final Handler mHandler = new Handler();
    private ImageViewTouchBase mImageView;
    private boolean mInfoLoaded;
    private long mOwnerId;
    private boolean mPaused;
    private final Map mPendingDownloadMap = new HashMap();
    private String mPhotoId;
    private Cursor mPhotosCursor;
    private Animation mPullDownAnimation;
    private Animation mPullUpAnimation;
    private QueryHandler mQueryHandler;
    private ProgressDialog mUserTaskProgress;







/*
    static Cursor access$1102(ViewPhotoActivity viewphotoactivity, Cursor cursor)
    {
        viewphotoactivity.mPhotosCursor = cursor;
        return cursor;
    }

*/






/*
    static ProgressDialog access$1502(ViewPhotoActivity viewphotoactivity, ProgressDialog progressdialog)
    {
        viewphotoactivity.mUserTaskProgress = progressdialog;
        return progressdialog;
    }

*/






/*
    static String access$202(ViewPhotoActivity viewphotoactivity, String s)
    {
        viewphotoactivity.mDeletePhotoReqId = s;
        return s;
    }

*/








}
