// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import com.facebook.katana.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.*;
import java.util.Arrays;

// Referenced classes of package com.facebook.katana.activity.media:
//            ViewPhotoActivity, MediaUploader, PhotosAdapter, AlbumInfoDialog, 
//            PhotoInfoDialog, CreateEditAlbumActivity

public class PhotosActivity extends BaseFacebookActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class PhotosAppSessionListener extends AppSessionListener
    {

        public void onDownloadPhotoThumbnailComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            if(s2.equals(mAlbumId)) goto _L2; else goto _L1
_L1:
            return;
_L2:
            if(mAppSession != null && mAppSession.isAlbumGetThumbnailsPending(mAlbumId))
                findViewById(0x7f0e00f1).setVisibility(0);
            else
                findViewById(0x7f0e00f1).setVisibility(8);
            if(i != 200)
                mAdapter.onDownloadPhotoError(s3);
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onPhotoDeleteAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
        {
            removeDialog(5);
            mDeleteAlbumReqId = null;
            if(i == 200)
            {
                Toaster.toast(PhotosActivity.this, 0x7f0a0127);
                finish();
            } else
            {
                String s3 = StringUtils.getErrorString(PhotosActivity.this, getString(0x7f0a000c), i, s1, exception);
                Toaster.toast(PhotosActivity.this, s3);
            }
        }

        public void onPhotoDeletePhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            removeDialog(6);
            mDeletePhotoReqId = null;
            if(i == 200)
            {
                if(mPhotoId != null)
                {
                    mPhotoId = null;
                    removeDialog(2);
                    removeDialog(4);
                }
            } else
            {
                String s4 = StringUtils.getErrorString(PhotosActivity.this, getString(0x7f0a012a), i, s1, exception);
                Toaster.toast(PhotosActivity.this, s4);
            }
        }

        public void onPhotoGetAlbumsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String as[], long l)
        {
            if(as != null && Arrays.binarySearch(as, mAlbumId) >= 0)
            {
                mAlbumTask.receivedResponse = true;
                if(200 != i)
                {
                    String s2 = StringUtils.getErrorString(PhotosActivity.this, getString(0x7f0a0011), i, s1, exception);
                    Toaster.toast(PhotosActivity.this, s2);
                }
            }
        }

        public void onPhotoGetPhotosComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String as[], 
                long l)
        {
            if(s2 != null && s2.equals(mAlbumId))
            {
                mPhotosTask.receivedResponse = true;
                if(i != 200)
                {
                    String s3 = StringUtils.getErrorString(PhotosActivity.this, getString(0x7f0a012f), i, s1, exception);
                    Toaster.toast(PhotosActivity.this, s3);
                    if(gridView().getCount() == 0)
                        finish();
                }
                if(mFirstDownloadBatch)
                {
                    mFirstDownloadBatch = false;
                    mPhotosTask.clear();
                    loadPhotos();
                }
                updateContentViews();
            }
        }

        final PhotosActivity this$0;

        private PhotosAppSessionListener()
        {
            this$0 = PhotosActivity.this;
            super();
        }

    }

    private class AlbumsContentObserver extends ContentObserver
    {

        public void onChange(boolean flag)
        {
            mAlbum = FacebookAlbum.readFromContentProvider(PhotosActivity.this, mAlbumId);
            if(mAlbum != null)
                updateFatTitleBar();
        }

        final PhotosActivity this$0;

        public AlbumsContentObserver()
        {
            this$0 = PhotosActivity.this;
            super(new Handler());
        }
    }


    public PhotosActivity()
    {
    }

    private static void D(String s)
    {
    }

    private GridView gridView()
    {
        return (GridView)findViewById(0x102000a);
    }

    private void loadAlbum()
    {
        if(mAlbumId != null)
        {
            if(mAlbum == null)
                mAlbum = FacebookAlbum.readFromContentProvider(this, mAlbumId);
            if(mAlbum == null && !mAlbumTask.sentRequest && mAppSession != null)
            {
                D("sending request for album");
                mAlbumTask.sentRequest = true;
                if(!mAppSession.isAlbumsGetPending(mOwnerId, mAlbumId))
                {
                    AppSession appsession = mAppSession;
                    long l = mOwnerId;
                    String as[] = new String[1];
                    as[0] = mAlbumId;
                    appsession.photoGetAlbums(this, l, Arrays.asList(as));
                }
            }
        }
    }

    private void refresh()
    {
        if(!mAppSession.isPhotosGetPending(mAlbumId, mOwnerId))
        {
            mPhotosTask.clear();
            mFirstDownloadBatch = true;
            loadPhotos();
            updateContentViews();
        }
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a0133);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a0131);
    }

    public static void showPhotos(Context context, String s, long l)
    {
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Long.valueOf(l);
        IntentUriHandler.handleUri(context, String.format("fb://album/%s?owner=%d", aobj));
    }

    private void updateContentViews()
    {
        GridView gridview = gridView();
        View view = findViewById(0x1020004);
        if(gridview.getCount() > 0 && !mFirstDownloadBatch)
        {
            gridview.setVisibility(0);
            view.setVisibility(8);
        } else
        {
            gridview.setVisibility(8);
            view.setVisibility(0);
            if(!mPhotosTask.receivedResponse)
            {
                findViewById(0x7f0e0056).setVisibility(8);
                findViewById(0x7f0e0057).setVisibility(0);
            } else
            {
                findViewById(0x7f0e0056).setVisibility(0);
                findViewById(0x7f0e0057).setVisibility(8);
            }
        }
    }

    private void updateFatTitleBar()
    {
        if(mAlbum != null)
        {
            String s = mAlbum.getName();
            if(s != null)
                ((TextView)findViewById(0x7f0e0071)).setText(s);
            int i = mAlbum.getSize();
            String s1;
            if(i == 0)
                s1 = getString(0x7f0a0014);
            else
            if(i == 1)
            {
                s1 = getString(0x7f0a0015);
            } else
            {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(i);
                s1 = getString(0x7f0a0016, aobj);
            }
            ((TextView)findViewById(0x7f0e0072)).setText(s1);
            if(mAlbum.getOwner() == mAppSession.getSessionInfo().userId && mAlbum.getType() != "profile")
                setPrimaryActionIcon(0x7f0200fd);
            else
                setPrimaryActionIcon(-1);
        } else
        {
            setPrimaryActionIcon(-1);
        }
    }

    private void viewPhoto(String s, String s1)
    {
        startActivity(ViewPhotoActivity.photoIntent(this, mOwnerId, mAlbumId, s, s1));
    }

    void loadPhotos()
    {
        if(mAppSession != null && !mPhotosTask.sentRequest)
        {
            mPhotosTask.sentRequest = true;
            byte byte0 = 0;
            byte byte1 = 30;
            if(!mFirstDownloadBatch)
            {
                byte0 = 30;
                byte1 = -1;
            }
            if(!mAppSession.isPhotosGetPending(mAlbumId, mOwnerId))
                mAppSession.photoGetPhotos(this, mAlbumId, null, mOwnerId, byte0, byte1);
        }
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
        case 133701: 
        case 133702: 
        case 133703: 
        case 133704: 
            mMediaUploader.onActivityResult(i, j, intent);
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
        String s = ((Cursor)mAdapter.getItem(adaptercontextmenuinfo.position)).getString(1);
        menuitem.getItemId();
        JVM INSTR tableswitch 11 14: default 72
    //                   11 82
    //                   12 94
    //                   13 106
    //                   14 120;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        boolean flag = true;
_L6:
        return flag;
        ClassCastException classcastexception;
        classcastexception;
        flag = false;
        if(true) goto _L6; else goto _L2
_L2:
        viewPhoto(s, "android.intent.action.VIEW");
        continue; /* Loop/switch isn't completed */
_L3:
        viewPhoto(s, "android.intent.action.EDIT");
        continue; /* Loop/switch isn't completed */
_L4:
        mPhotoId = s;
        showDialog(4);
        continue; /* Loop/switch isn't completed */
_L5:
        mPhotoId = s;
        showDialog(2);
        if(true) goto _L1; else goto _L7
_L7:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030055);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            Intent intent = getIntent();
            mOwnerId = intent.getLongExtra("owner", -1L);
            mAlbumId = intent.getStringExtra("album");
            if(bundle != null)
            {
                mAlbumTask = (TaskContext)bundle.getParcelable("state_album_task");
                mPhotosTask = (TaskContext)bundle.getParcelable("state_photos_task");
            }
            if(mAlbumTask == null)
                mAlbumTask = new TaskContext();
            if(mPhotosTask == null)
                mPhotosTask = new TaskContext();
            loadAlbum();
            if(bundle != null)
                mPhotoId = bundle.getString("state_photo_id");
            GridView gridview = gridView();
            mAdapter = new PhotosAdapter(this, mAlbumId, mOwnerId, mAppSession);
            gridview.setAdapter(mAdapter);
            setupEmptyView();
            mAppSessionListener = new PhotosAppSessionListener();
            mAlbumContentObserver = new AlbumsContentObserver();
            gridview.setOnCreateContextMenuListener(this);
            gridview.setOnItemClickListener(this);
            mMediaUploader = new MediaUploader(this, mAlbumId);
        }
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        contextmenu.setHeaderTitle(getString(0x7f0a0132));
        contextmenu.add(0, 11, 0, 0x7f0a0135);
        if(mOwnerId == mAppSession.getSessionInfo().userId)
        {
            contextmenu.add(0, 12, 0, 0x7f0a012e);
            contextmenu.add(0, 13, 0, 0x7f0a0129);
        }
        contextmenu.add(0, 14, 0, 0x7f0a012d);
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR lookupswitch 9: default 84
    //                   1: 88
    //                   2: 100
    //                   3: 116
    //                   4: 170
    //                   5: 224
    //                   6: 270
    //                   255255255: 310
    //                   255255256: 321
    //                   255255257: 332;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10
_L1:
        Object obj = null;
_L12:
        return ((Dialog) (obj));
_L2:
        obj = AlbumInfoDialog.create(this, mAlbum);
        continue; /* Loop/switch isn't completed */
_L3:
        obj = PhotoInfoDialog.create(this, FacebookPhoto.readFromContentProvider(this, mPhotoId));
        continue; /* Loop/switch isn't completed */
_L4:
        android.content.DialogInterface.OnClickListener onclicklistener1 = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                mDeleteAlbumReqId = mAppSession.photoDeleteAlbum(PhotosActivity.this, mAlbumId);
                removeDialog(3);
                showDialog(5);
            }

            final PhotosActivity this$0;

            
            {
                this$0 = PhotosActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a000a), 0x1080027, getString(0x7f0a000b), getString(0x7f0a0233), onclicklistener1, getString(0x7f0a00d4), null, null, true);
        continue; /* Loop/switch isn't completed */
_L5:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                mDeletePhotoReqId = mAppSession.photoDeletePhoto(PhotosActivity.this, mAlbumId, mPhotoId);
                removeDialog(4);
                showDialog(6);
            }

            final PhotosActivity this$0;

            
            {
                this$0 = PhotosActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, getString(0x7f0a0129), 0x1080027, getString(0x7f0a012b), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        continue; /* Loop/switch isn't completed */
_L6:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a000d));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L7:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a012c));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L8:
        obj = mMediaUploader.createDialog();
        continue; /* Loop/switch isn't completed */
_L9:
        obj = mMediaUploader.createPhotoDialog();
        continue; /* Loop/switch isn't completed */
_L10:
        obj = mMediaUploader.createVideoDialog();
        if(true) goto _L12; else goto _L11
_L11:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        if(mOwnerId == mAppSession.getSessionInfo().userId)
        {
            menu.add(0, 5, 0, 0x7f0a000f).setIcon(0x7f02009b);
            menu.add(0, 6, 0, 0x7f0a000a).setIcon(0x7f020097);
        }
        menu.add(0, 7, 0, 0x7f0a000e).setIcon(0x7f02008c);
        return true;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        viewPhoto(((Cursor)mAdapter.getItem(i)).getString(1), "android.intent.action.VIEW");
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 7: default 44
    //                   2 50
    //                   3 44
    //                   4 44
    //                   5 57
    //                   6 100
    //                   7 108;
           goto _L1 _L2 _L1 _L1 _L3 _L4 _L5
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        refresh();
        continue; /* Loop/switch isn't completed */
_L3:
        Intent intent = new Intent(this, com/facebook/katana/activity/media/CreateEditAlbumActivity);
        intent.setAction("android.intent.action.EDIT");
        intent.setData(Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, mAlbumId));
        startActivity(intent);
        continue; /* Loop/switch isn't completed */
_L4:
        showDialog(3);
        continue; /* Loop/switch isn't completed */
_L5:
        showDialog(1);
        if(true) goto _L1; else goto _L6
_L6:
    }

    protected void onPause()
    {
        super.onPause();
        mAlbumTask.reset();
        mPhotosTask.reset();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
        getContentResolver().unregisterContentObserver(mAlbumContentObserver);
    }

    protected void onPrepareDialog(int i, Dialog dialog)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 25
    //                   2 36;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        AlbumInfoDialog.update(dialog, mAlbum);
        continue; /* Loop/switch isn't completed */
_L3:
        PhotoInfoDialog.update(dialog, FacebookPhoto.readFromContentProvider(this, mPhotoId));
        if(true) goto _L1; else goto _L4
_L4:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        boolean flag = mAppSession.isPhotosGetPending(mAlbumId, mOwnerId);
        MenuItem menuitem = menu.findItem(2);
        boolean flag1;
        if(!flag)
            flag1 = true;
        else
            flag1 = false;
        menuitem.setEnabled(flag1);
        if(mOwnerId == mAppSession.getSessionInfo().userId && mAlbum != null)
        {
            MenuItem menuitem1 = menu.findItem(5);
            boolean flag2;
            MenuItem menuitem2;
            boolean flag3;
            if(!flag)
                flag2 = true;
            else
                flag2 = false;
            menuitem1.setEnabled(flag2);
            menuitem2 = menu.findItem(6);
            if(!flag)
                flag3 = true;
            else
                flag3 = false;
            menuitem2.setEnabled(flag3);
        }
        return true;
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
            if(mDeleteAlbumReqId != null && !mAppSession.isRequestPending(mDeleteAlbumReqId))
            {
                removeDialog(5);
                mDeleteAlbumReqId = null;
            }
            if(mDeletePhotoReqId != null && !mAppSession.isRequestPending(mDeletePhotoReqId))
            {
                removeDialog(6);
                mDeletePhotoReqId = null;
            }
            mAppSession.addListener(mAppSessionListener);
            mAlbum = FacebookAlbum.readFromContentProvider(this, mAlbumId);
            loadAlbum();
            Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, mAlbumId);
            getContentResolver().registerContentObserver(uri, false, mAlbumContentObserver);
            if(!mPhotosTask.sentRequest)
            {
                mFirstDownloadBatch = true;
                loadPhotos();
            }
            updateFatTitleBar();
            updateContentViews();
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(mPhotoId != null)
            bundle.putString("state_photo_id", mPhotoId);
        bundle.putParcelable("state_album_task", mAlbumTask);
        bundle.putParcelable("state_photos_task", mPhotosTask);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        showDialog(0xf36e2d7);
    }

    private static final int ALBUM_INFO_DIALOG_ID = 1;
    private static final int ALBUM_INFO_MENU_ID = 7;
    private static final boolean DEBUG = false;
    private static final int DELETE_ALBUM_MENU_ID = 6;
    private static final int DELETE_ALBUM_QUESTION_DIALOG_ID = 3;
    private static final int DELETE_PHOTO_QUESTION_DIALOG_ID = 4;
    private static final int EDIT_ALBUM_MENU_ID = 5;
    public static final String EXTRA_ALBUM_ID = "album";
    public static final String EXTRA_OWNER_ID = "owner";
    private static final int PHOTO_DELETE_MENU_ID = 13;
    private static final int PHOTO_DOWNLOAD_BATCH_SIZE = 30;
    private static final int PHOTO_EDIT_MENU_ID = 12;
    private static final int PHOTO_INFO_DIALOG_ID = 2;
    private static final int PHOTO_INFO_MENU_ID = 14;
    private static final int PHOTO_VIEW_MENU_ID = 11;
    private static final int PROGRESS_DELETE_ALBUM_DIALOG_ID = 5;
    private static final int PROGRESS_DELETE_PHOTO_DIALOG_ID = 6;
    private static final int REFRESH_MENU_ID = 2;
    private static final String STATE_ALBUM_TASK = "state_album_task";
    private static final String STATE_PHOTOS_TASK = "state_photos_task";
    private static final String STATE_PHOTO_ID = "state_photo_id";
    private PhotosAdapter mAdapter;
    private FacebookAlbum mAlbum;
    private ContentObserver mAlbumContentObserver;
    private String mAlbumId;
    private TaskContext mAlbumTask;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mDeleteAlbumReqId;
    private String mDeletePhotoReqId;
    private boolean mFirstDownloadBatch;
    private MediaUploader mMediaUploader;
    private long mOwnerId;
    private String mPhotoId;
    private TaskContext mPhotosTask;



/*
    static FacebookAlbum access$002(PhotosActivity photosactivity, FacebookAlbum facebookalbum)
    {
        photosactivity.mAlbum = facebookalbum;
        return facebookalbum;
    }

*/




/*
    static String access$1002(PhotosActivity photosactivity, String s)
    {
        photosactivity.mPhotoId = s;
        return s;
    }

*/









/*
    static boolean access$602(PhotosActivity photosactivity, boolean flag)
    {
        photosactivity.mFirstDownloadBatch = flag;
        return flag;
    }

*/



/*
    static String access$802(PhotosActivity photosactivity, String s)
    {
        photosactivity.mDeleteAlbumReqId = s;
        return s;
    }

*/


/*
    static String access$902(PhotosActivity photosactivity, String s)
    {
        photosactivity.mDeletePhotoReqId = s;
        return s;
    }

*/
}
