// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploadPhotoActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.service.UploadManager;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.ui.*;
import com.facebook.katana.util.*;
import java.io.*;

// Referenced classes of package com.facebook.katana.activity.media:
//            DropdownTagUsersAdapter, AlbumsActivity, ViewPhotoActivity, UserHolder

public class UploadPhotoActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    private class FaceDetectTask extends Thread
    {

        public void run()
        {
            final android.media.FaceDetector.Face faces[] = new android.media.FaceDetector.Face[10];
            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
            if(mBitmapWidth % 2 != 0)
                mBitmapWidth = mBitmapWidth - 1;
            if(mBitmapHeight % 2 != 0)
                mBitmapHeight = mBitmapHeight - 1;
            Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmapWidth, mBitmapHeight).copy(android.graphics.Bitmap.Config.RGB_565, true);
            (new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 10)).findFaces(bitmap, faces);
            if(bitmap != mBitmap)
                bitmap.recycle();
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(mActivityInForeground)
                        onFaceSuggestionReady(faces, mBitmapWidth, mBitmapHeight);
                }

                final FaceDetectTask this$1;
                final android.media.FaceDetector.Face val$faces[];

                
                {
                    this$1 = FaceDetectTask.this;
                    faces = aface;
                    super();
                }
            }
);
_L2:
            return;
            OutOfMemoryError outofmemoryerror;
            outofmemoryerror;
            continue; /* Loop/switch isn't completed */
            Exception exception;
            exception;
            if(true) goto _L2; else goto _L1
_L1:
        }

        private Bitmap mBitmap;
        private int mBitmapHeight;
        private int mBitmapWidth;
        private final Handler mHandler;
        final UploadPhotoActivity this$0;



        public FaceDetectTask(Handler handler, Bitmap bitmap)
        {
            this$0 = UploadPhotoActivity.this;
            super();
            mHandler = handler;
            mBitmap = bitmap;
        }
    }

    private class LoadImageTask extends Thread
    {

        public void run()
        {
            try
            {
                Bitmap bitmap = ImageUtils.resizeBitmapAndFrame(mInFilename, 960, 960, 1);
                mOutBitmap = bitmap;
                if(bitmap == null)
                    throw new FileNotFoundException("Cannot load bitmap");
            }
            catch(Exception exception)
            {
                Log.e("LoadImageTask.run", (new StringBuilder()).append("Exception: ").append(exception).toString());
                mOutEx = exception;
            }
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(mActivityInForeground)
                    {
                        if(mOutEx != null)
                            showDialog(1);
                        else
                            onBitmapReady(mOutBitmap, mInFilename);
                    } else
                    {
                        if(mOutBitmap != null)
                        {
                            mOutBitmap.recycle();
                            mOutBitmap = null;
                        }
                        (new File(mInFilename)).delete();
                    }
                }

                final LoadImageTask this$1;

                
                {
                    this$1 = LoadImageTask.this;
                    super();
                }
            }
);
        }

        private final Handler mHandler;
        private final String mInFilename;
        private Bitmap mOutBitmap;
        private Exception mOutEx;
        final UploadPhotoActivity this$0;




/*
        static Bitmap access$1202(LoadImageTask loadimagetask, Bitmap bitmap)
        {
            loadimagetask.mOutBitmap = bitmap;
            return bitmap;
        }

*/


        public LoadImageTask(Handler handler, String s)
        {
            this$0 = UploadPhotoActivity.this;
            super();
            mHandler = handler;
            mInFilename = s;
        }
    }

    private class SaveImageTask extends Thread
    {

        public void run()
        {
            mOutFilename = (new StringBuilder()).append("upload_").append(StringUtils.randomString(4)).toString();
            try
            {
                FileOutputStream fileoutputstream = openFileOutput(mOutFilename, 0);
                mInBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, fileoutputstream);
                fileoutputstream.flush();
                fileoutputstream.close();
                mOutFilename = (new StringBuilder()).append(getFilesDir().getAbsolutePath()).append("/").append(mOutFilename).toString();
            }
            catch(Exception exception)
            {
                Log.e("SaveImageTask.run", (new StringBuilder()).append("Exception: ").append(exception).toString());
                mOutEx = exception;
            }
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(!mActivityInForeground) goto _L2; else goto _L1
_L1:
                    if(mOutEx == null) goto _L4; else goto _L3
_L3:
                    showDialog(2);
_L6:
                    return;
_L4:
                    onBitmapReady(mInBitmap, mOutFilename);
                    if(mUriToDeleteWhenDone != null)
                        (new File(mUriToDeleteWhenDone.getPath())).delete();
                    continue; /* Loop/switch isn't completed */
_L2:
                    if(mInBitmap != null)
                    {
                        mInBitmap.recycle();
                        mInBitmap = null;
                    }
                    if(mOutFilename != null)
                        (new File(mOutFilename)).delete();
                    if(true) goto _L6; else goto _L5
_L5:
                }

                final SaveImageTask this$1;

                
                {
                    this$1 = SaveImageTask.this;
                    super();
                }
            }
);
        }

        private final Handler mHandler;
        private Bitmap mInBitmap;
        private Exception mOutEx;
        private String mOutFilename;
        private final Uri mUriToDeleteWhenDone;
        final UploadPhotoActivity this$0;





/*
        static Bitmap access$702(SaveImageTask saveimagetask, Bitmap bitmap)
        {
            saveimagetask.mInBitmap = bitmap;
            return bitmap;
        }

*/


        public SaveImageTask(Handler handler, Bitmap bitmap, boolean flag, Uri uri)
        {
            this$0 = UploadPhotoActivity.this;
            super();
            mHandler = handler;
            mInBitmap = bitmap;
            mUriToDeleteWhenDone = uri;
        }
    }

    private class ScaleImageTask extends Thread
    {

        public void run()
        {
            try
            {
                mOutBitmap = ImageUtils.scaleImage(mContext, mPhotoUri, 960, 960);
            }
            catch(Exception exception)
            {
                Log.e("ScaleImageTask.run", (new StringBuilder()).append("Exception: ").append(exception).toString());
                mOpex = exception;
            }
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(!mActivityInForeground) goto _L2; else goto _L1
_L1:
                    if(mOpex != null)
                    {
                        showDialog(1);
                    } else
                    {
                        Uri uri;
                        if(mDeleteInputWhenDone)
                            uri = mPhotoUri;
                        else
                            uri = null;
                        (new SaveImageTask(mHandler, mOutBitmap, true, uri)).start();
                    }
_L4:
                    return;
_L2:
                    if(mOutBitmap != null)
                    {
                        mOutBitmap.recycle();
                        mOutBitmap = null;
                    }
                    if(true) goto _L4; else goto _L3
_L3:
                }

                final ScaleImageTask this$1;

                
                {
                    this$1 = ScaleImageTask.this;
                    super();
                }
            }
);
        }

        private final Context mContext;
        private final boolean mDeleteInputWhenDone;
        private final Handler mHandler;
        private Exception mOpex;
        private Bitmap mOutBitmap;
        private final Uri mPhotoUri;
        final UploadPhotoActivity this$0;







/*
        static Bitmap access$502(ScaleImageTask scaleimagetask, Bitmap bitmap)
        {
            scaleimagetask.mOutBitmap = bitmap;
            return bitmap;
        }

*/

        public ScaleImageTask(Context context, Handler handler, Uri uri, boolean flag)
        {
            this$0 = UploadPhotoActivity.this;
            super();
            mContext = context;
            mHandler = handler;
            mPhotoUri = uri;
            mDeleteInputWhenDone = flag;
        }
    }


    public UploadPhotoActivity()
    {
        mDeleteFileOnDestroy = true;
    }

    private void initializeTaggableView()
    {
        mTaggableListener = new com.facebook.katana.ui.TaggableView.TaggableViewListener() {

            public void onClicked(float f, float f1)
            {
                mLastClickedX = f;
                mLastClickedY = f1;
                updateTagControlVisibility(true);
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
;
        final TagUsersAutoCompleteTextView tagAutoCompleteView = (TagUsersAutoCompleteTextView)findViewById(0x7f0e014a);
        mDropdownTagUsersAdapter = new DropdownTagUsersAdapter(this, mAppSession.getUserImagesCache());
        tagAutoCompleteView.setAdapter(mDropdownTagUsersAdapter);
        tagAutoCompleteView.setThreshold(0);
        tagAutoCompleteView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                if(view != null)
                {
                    UserHolder userholder = (UserHolder)adapterview.getItemAtPosition(i);
                    long l1 = userholder.getUid();
                    String s = userholder.getDisplayName();
                    tagAutoCompleteView.setText(null);
                    updateTagControlVisibility(false);
                    final TagView tagBtn = ((TaggableView)findViewById(0x7f0e014c)).addTag(l1, mLastClickedX, mLastClickedY, s);
                    tagBtn.setOnLongClickListener(new android.view.View.OnLongClickListener() {

                        public boolean onLongClick(View view1)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putLong("photo_tag_user_id", tagBtn.userId);
                            showDialog(3, bundle);
                            return true;
                        }

                        final _cls3 this$1;
                        final TagView val$tagBtn;

                    
                    {
                        this$1 = _cls3.this;
                        tagBtn = tagview;
                        super();
                    }
                    }
);
                } else
                {
                    tagAutoCompleteView.setText(null);
                    updateTagControlVisibility(false);
                }
            }

            final UploadPhotoActivity this$0;
            final TagUsersAutoCompleteTextView val$tagAutoCompleteView;

            
            {
                this$0 = UploadPhotoActivity.this;
                tagAutoCompleteView = tagusersautocompletetextview;
                super();
            }
        }
);
        ((ImageView)findViewById(0x7f0e014b)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                tagAutoCompleteView.setText(null);
                updateTagControlVisibility(false);
            }

            final UploadPhotoActivity this$0;
            final TagUsersAutoCompleteTextView val$tagAutoCompleteView;

            
            {
                this$0 = UploadPhotoActivity.this;
                tagAutoCompleteView = tagusersautocompletetextview;
                super();
            }
        }
);
        Toaster.toast(this, 0x7f0a0126);
    }

    private void onBitmapReady(Bitmap bitmap, String s)
    {
        mFilename = s;
        if(mViewBitmap != null)
        {
            mViewBitmap.recycle();
            mViewBitmap = null;
        }
        mViewBitmap = bitmap;
        TaggableView taggableview = (TaggableView)findViewById(0x7f0e014c);
        taggableview.setImage(bitmap);
        if(!mFacesSuggested)
        {
            (new FaceDetectTask(new Handler(), bitmap)).start();
            mFacesSuggested = true;
        }
        taggableview.setListener(mTaggableListener);
        findViewById(0x7f0e0149).setVisibility(8);
        findViewById(0x7f0e014c).setVisibility(0);
        ((ImageView)findViewById(0x7f0e00e5)).setImageBitmap(bitmap);
        findViewById(0x7f0e00e5).setVisibility(0);
        findViewById(0x7f0e014d).setVisibility(8);
        findViewById(0x7f0e00f1).setVisibility(8);
        findViewById(0x7f0e014f).setEnabled(true);
    }

    private void onFaceSuggestionReady(android.media.FaceDetector.Face aface[], int i, int j)
    {
        TaggableView taggableview = (TaggableView)findViewById(0x7f0e014c);
        int k = aface.length;
        int l = 0;
        do
        {
            if(l >= k)
                break;
            android.media.FaceDetector.Face face = aface[l];
            if(face != null && face.confidence() >= 0.5F)
            {
                PointF pointf = new PointF();
                face.getMidPoint(pointf);
                taggableview.addSuggestion(pointf.x / (float)i, pointf.y / (float)j, face.eyesDistance() / (float)i);
            }
            l++;
        } while(true);
    }

    private void runUI()
    {
        updateFatTitleBar();
        mActivityInForeground = true;
        if(mFilename == null) goto _L2; else goto _L1
_L1:
        findViewById(0x7f0e00f1).setVisibility(0);
        (new LoadImageTask(new Handler(), mFilename)).start();
_L6:
        mAppSession.addListener(mListener);
_L4:
        return;
_L2:
        Uri uri;
        String s = getIntent().getStringExtra("photo_uri");
        if(s != null)
            uri = Uri.parse(s);
        else
            uri = (Uri)getIntent().getExtras().getParcelable("android.intent.extra.STREAM");
        if(uri != null)
            break; /* Loop/switch isn't completed */
        finish();
        if(true) goto _L4; else goto _L3
_L3:
        String s1 = uri.getScheme();
        if(s1 != null && s1.equals("content"))
        {
            findViewById(0x7f0e00f1).setVisibility(0);
            (new ScaleImageTask(this, new Handler(), uri, false)).start();
        } else
        if(getIntent().getBooleanExtra("extra_photo_is_scaled", false))
        {
            findViewById(0x7f0e00f1).setVisibility(0);
            (new LoadImageTask(new Handler(), uri.toString())).start();
        } else
        {
            (new ScaleImageTask(this, new Handler(), uri, true)).start();
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    private void setupFatTitleBar()
    {
        if(mProfileId == -1L)
        {
            ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0073);
            imagebutton.setVisibility(0);
            imagebutton.setImageResource(0x7f0200ce);
            imagebutton.setOnClickListener(this);
        }
    }

    private void updateFatTitleBar()
    {
        ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a0212);
        String s;
        if(mProfileId != -1L && mCheckinId == -1L)
        {
            if(mProfile == null)
            {
                FqlGetProfile.RequestSingleProfile(this, mProfileId);
                s = getString(0x7f0a0215);
            } else
            {
                Object aobj2[] = new Object[1];
                aobj2[0] = mProfile.mDisplayName;
                s = getString(0x7f0a0216, aobj2);
            }
        } else
        if(mAlbum == null)
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = getString(0x7f0a0204);
            s = getString(0x7f0a0214, aobj1);
        } else
        {
            Object aobj[] = new Object[1];
            aobj[0] = mAlbum.getName();
            s = getString(0x7f0a0214, aobj);
        }
        ((TextView)findViewById(0x7f0e0072)).setText(s);
    }

    private void updateTagControlVisibility(boolean flag)
    {
        TagUsersAutoCompleteTextView tagusersautocompletetextview = (TagUsersAutoCompleteTextView)findViewById(0x7f0e014a);
        InputMethodManager inputmethodmanager = (InputMethodManager)getSystemService("input_method");
        View view = findViewById(0x7f0e0074);
        View view1 = findViewById(0x7f0e0149);
        if(flag)
        {
            view.setVisibility(8);
            view1.setVisibility(0);
            tagusersautocompletetextview.requestFocus();
            inputmethodmanager.showSoftInput(tagusersautocompletetextview, 1);
        } else
        {
            view.setVisibility(0);
            view1.setVisibility(8);
            tagusersautocompletetextview.clearFocus();
            inputmethodmanager.hideSoftInputFromWindow(tagusersautocompletetextview.getWindowToken(), 1);
        }
    }

    private void upload()
    {
        java.util.List list = ((TaggableView)findViewById(0x7f0e014c)).getTags();
        String s = ((EditText)findViewById(0x7f0e014e)).getText().toString().trim();
        if(s.length() == 0)
            s = null;
        String s1 = null;
        if(mAlbum != null)
            s1 = mAlbum.getAlbumId();
        startService(UploadManager.createUploadIntent(this, mFilename, s1, s, mProfileId, mCheckinId, mPublish, list, -1L, null, -1L));
        mDeleteFileOnDestroy = false;
        Toaster.toast(this, 0x7f0a0218);
        finish();
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
        case 2210: 
            runUI();
            break;

        case 100000: 
            mAlbum = FacebookAlbum.readFromContentProvider(this, intent.getData());
            updateFatTitleBar();
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR lookupswitch 3: default 40
    //                   2131624051: 85
    //                   2131624243: 78
    //                   2131624271: 41;
           goto _L1 _L2 _L3 _L4
_L1:
        return;
_L4:
        TextView textview = (TextView)findViewById(0x7f0e014e);
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(textview.getWindowToken(), 0);
        upload();
        continue; /* Loop/switch isn't completed */
_L3:
        finish();
        continue; /* Loop/switch isn't completed */
_L2:
        startActivityForResult(AlbumsActivity.createIntentForAlbumSelector(this), 0x186a0);
        if(true) goto _L1; else goto _L5
_L5:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, getIntent());
_L9:
        return;
_L2:
        String s = getIntent().getAction();
        if(s == null) goto _L4; else goto _L3
_L3:
        if(!s.startsWith("com.facebook.katana.upload.notification.error")) goto _L6; else goto _L5
_L5:
        mAppSession.cancelUploadNotification(this, (new StringBuilder()).append("").append(getIntent().getExtras().getInt("android.intent.extra.SUBJECT")).toString());
_L4:
        setContentView(0x7f030089);
        String s1 = getIntent().getStringExtra("extra_album_id");
        if(s1 != null)
        {
            mAlbum = FacebookAlbum.readFromContentProvider(this, s1);
            if(mAlbum == null)
            {
                finish();
                continue; /* Loop/switch isn't completed */
            }
        }
        break MISSING_BLOCK_LABEL_284;
_L6:
        if(!s.startsWith("com.facebook.katana.upload.notification.ok"))
            continue; /* Loop/switch isn't completed */
        mAppSession.cancelUploadNotification(this, (new StringBuilder()).append("").append(getIntent().getExtras().getInt("android.intent.extra.SUBJECT")).toString());
        (new File(((Uri)getIntent().getParcelableExtra("android.intent.extra.STREAM")).getPath())).delete();
        String s3 = getIntent().getStringExtra("extra_photo_id");
        String s4 = getIntent().getStringExtra("extra_album_id");
        startActivity(ViewPhotoActivity.photoIntent(this, mAppSession.getSessionInfo().userId, s4, s3, "android.intent.action.VIEW"));
        finish();
        continue; /* Loop/switch isn't completed */
        if(!s.startsWith("com.facebook.katana.upload.notification.pending")) goto _L4; else goto _L7
_L7:
        finish();
        continue; /* Loop/switch isn't completed */
        mCheckinId = getIntent().getLongExtra("extra_checkin_id", -1L);
        mProfileId = getIntent().getLongExtra("extra_profile_id", -1L);
        mPublish = getIntent().getBooleanExtra("extra_photo_publish", true);
        Button button = (Button)findViewById(0x7f0e014f);
        button.setEnabled(false);
        button.setOnClickListener(this);
        ((Button)findViewById(0x7f0e0133)).setOnClickListener(this);
        setupFatTitleBar();
        String s2 = getIntent().getStringExtra("android.intent.extra.TITLE");
        if(TextUtils.isEmpty(s2))
            s2 = getIntent().getStringExtra("android.intent.extra.TEXT");
        if(s2 != null)
            ((TextView)findViewById(0x7f0e014e)).setText(s2);
        mListener = new AppSessionListener() {

            public void onGetProfileComplete(AppSession appsession, String s5, int i, String s6, Exception exception, FacebookProfile facebookprofile)
            {
                if(i == 200 && facebookprofile != null && facebookprofile.mId == mProfileId)
                {
                    mProfile = facebookprofile;
                    updateFatTitleBar();
                }
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
;
        initializeTaggableView();
        if(true) goto _L9; else goto _L8
_L8:
    }

    protected Dialog onCreateDialog(int i, final Bundle extra)
    {
        i;
        JVM INSTR tableswitch 1 3: default 28
    //                   1 34
    //                   2 86
    //                   3 138;
           goto _L1 _L2 _L3 _L4
_L1:
        Object obj = null;
_L6:
        return ((Dialog) (obj));
_L2:
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0217), 0x1080027, getString(0x7f0a0205), getString(0x7f0a00dd), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                finish();
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
, null, null, new android.content.DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface)
            {
                finish();
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
, true);
        continue; /* Loop/switch isn't completed */
_L3:
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0217), 0x1080027, getString(0x7f0a0213), getString(0x7f0a00dd), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                finish();
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
, null, null, new android.content.DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface)
            {
                finish();
            }

            final UploadPhotoActivity this$0;

            
            {
                this$0 = UploadPhotoActivity.this;
                super();
            }
        }
, true);
        continue; /* Loop/switch isn't completed */
_L4:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                ((TaggableView)findViewById(0x7f0e014c)).deleteTag(extra.getLong("photo_tag_user_id"));
                removeDialog(3);
            }

            final UploadPhotoActivity this$0;
            final Bundle val$extra;

            
            {
                this$0 = UploadPhotoActivity.this;
                extra = bundle;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0122), 0x1080027, getString(0x7f0a0123), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mFilename != null && mDeleteFileOnDestroy)
            (new File(mFilename)).delete();
        if(mDropdownTagUsersAdapter != null)
            mDropdownTagUsersAdapter.cleanUp();
    }

    protected void onPause()
    {
        mActivityInForeground = false;
        if(mAppSession != null && mListener != null)
            mAppSession.removeListener(mListener);
        ((TaggableView)findViewById(0x7f0e014c)).setImage(null);
        if(mViewBitmap != null)
        {
            mViewBitmap.recycle();
            mViewBitmap = null;
        }
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.redirectThroughLogin(this);
        else
            runUI();
    }

    private static final int DELETE_PHOTO_TAG_DIALOG_ID = 3;
    private static final int ERROR_LOAD_PHOTO_DIALOG_ID = 1;
    private static final int ERROR_SAVE_PHOTO_DIALOG_ID = 2;
    public static final String EXTRA_ALBUM_ID = "extra_album_id";
    public static final String EXTRA_CHECKIN_ID = "extra_checkin_id";
    public static final String EXTRA_PHOTO_DATA = "extra_photo_data";
    public static final String EXTRA_PHOTO_ID = "extra_photo_id";
    public static final String EXTRA_PHOTO_IS_SCALED = "extra_photo_is_scaled";
    public static final String EXTRA_PROFILE = "extra_profile";
    public static final String EXTRA_PROFILE_ID = "extra_profile_id";
    private static final float FACE_CONFIDENCE_THRESHOLD = 0.5F;
    private static final int FACE_MAX_SUGGESTIONS = 10;
    public static final int MAX_IMAGE_DIMENSION = 960;
    private static final String PHOTO_TAG_USER_ID = "photo_tag_user_id";
    private boolean mActivityInForeground;
    private FacebookAlbum mAlbum;
    private AppSession mAppSession;
    private long mCheckinId;
    private boolean mDeleteFileOnDestroy;
    private DropdownTagUsersAdapter mDropdownTagUsersAdapter;
    private boolean mFacesSuggested;
    private String mFilename;
    private float mLastClickedX;
    private float mLastClickedY;
    private AppSessionListener mListener;
    private FacebookProfile mProfile;
    private long mProfileId;
    private boolean mPublish;
    private com.facebook.katana.ui.TaggableView.TaggableViewListener mTaggableListener;
    private Bitmap mViewBitmap;





/*
    static FacebookProfile access$1802(UploadPhotoActivity uploadphotoactivity, FacebookProfile facebookprofile)
    {
        uploadphotoactivity.mProfile = facebookprofile;
        return facebookprofile;
    }

*/




/*
    static float access$2002(UploadPhotoActivity uploadphotoactivity, float f)
    {
        uploadphotoactivity.mLastClickedX = f;
        return f;
    }

*/



/*
    static float access$2102(UploadPhotoActivity uploadphotoactivity, float f)
    {
        uploadphotoactivity.mLastClickedY = f;
        return f;
    }

*/


}
