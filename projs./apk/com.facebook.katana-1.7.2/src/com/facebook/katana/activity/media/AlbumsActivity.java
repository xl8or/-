// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbumsActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.facebook.katana.*;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.media:
//            MediaUploader, AlbumsAdapter, CreateEditAlbumActivity, AlbumInfoDialog, 
//            PhotosActivity

public class AlbumsActivity extends ProfileFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener, TabProgressSource
{
    private class AlbumsAppSessionListener extends AppSessionListener
    {

        public void onPhotoDeleteAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
        {
            removeDialog(3);
            mDeleteAlbumReqId = null;
            if(i == 200)
            {
                if(mAlbum != null)
                {
                    mAlbum = null;
                    removeDialog(1);
                    removeDialog(2);
                }
                updateFatTitleHeader();
                updateEmptyView();
            } else
            {
                String s3 = StringUtils.getErrorString(AlbumsActivity.this, getString(0x7f0a000c), i, s1, exception);
                Toaster.toast(AlbumsActivity.this, s3);
            }
        }

        public void onPhotoGetAlbumsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String as[], long l)
        {
            if(l == mUserId)
            {
                showProgress(false);
                if(i == 200)
                {
                    logStepDataReceived();
                    updateFatTitleHeader();
                    updateEmptyView();
                } else
                {
                    String s2 = StringUtils.getErrorString(AlbumsActivity.this, getString(0x7f0a0011), i, s1, exception);
                    Toaster.toast(AlbumsActivity.this, s2);
                }
            }
        }

        final AlbumsActivity this$0;

        private AlbumsAppSessionListener()
        {
            this$0 = AlbumsActivity.this;
            super();
        }

    }


    public AlbumsActivity()
    {
    }

    public static Intent createIntentForAlbumSelector(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        Intent intent1;
        if(appsession == null)
        {
            intent1 = null;
        } else
        {
            Intent intent = new Intent(context, com/facebook/katana/activity/media/AlbumsActivity);
            intent.setData(ContentUris.withAppendedId(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, appsession.getSessionInfo().userId));
            intent.putExtra("extra_exclude_read_only", true);
            intent.setAction("android.intent.action.PICK");
            intent1 = intent;
        }
        return intent1;
    }

    private void refresh()
    {
        mAppSession.photoGetAlbums(this, mUserId, null);
        logStepDataRequested();
        showProgress(true);
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a0010);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a0012);
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

    private void updateEmptyView()
    {
        if(getListView().getAdapter().isEmpty())
            findViewById(0x7f0e000d).setVisibility(0);
        else
            findViewById(0x7f0e000d).setVisibility(8);
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
        String s;
        Cursor cursor = (Cursor)mAdapter.getItem(getCursorPosition(adaptercontextmenuinfo.position));
        s = mAdapter.getAlbumId(cursor);
        menuitem.getItemId();
        JVM INSTR tableswitch 4 6: default 76
    //                   4 147
    //                   5 86
    //                   6 129;
           goto _L1 _L2 _L3 _L4
_L1:
        boolean flag = true;
_L5:
        return flag;
        ClassCastException classcastexception;
        classcastexception;
        flag = false;
        if(true) goto _L5; else goto _L3
_L3:
        Intent intent = new Intent(this, com/facebook/katana/activity/media/CreateEditAlbumActivity);
        intent.setAction("android.intent.action.EDIT");
        intent.setData(Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, s));
        startActivity(intent);
        continue; /* Loop/switch isn't completed */
_L4:
        mAlbum = FacebookAlbum.readFromContentProvider(this, s);
        showDialog(2);
        continue; /* Loop/switch isn't completed */
_L2:
        mAlbum = FacebookAlbum.readFromContentProvider(this, s);
        showDialog(1);
        if(true) goto _L1; else goto _L6
_L6:
    }

    public void onCreate(Bundle bundle)
    {
        mHasFatTitleHeader = true;
        super.onCreate(bundle);
        setContentView(0x7f030003);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            Uri uri = getIntent().getData();
            Uri uri1 = uri;
            ListView listview;
            ArrayList arraylist;
            Object aobj[];
            if(uri != null)
            {
                if(uri.getScheme().equals("content"))
                {
                    mUserId = Long.parseLong(uri.getLastPathSegment());
                    if(bundle != null)
                    {
                        String s = bundle.getString("state_album_id");
                        if(s != null)
                            mAlbum = FacebookAlbum.readFromContentProvider(this, s);
                    }
                }
            } else
            {
                mUserId = mAppSession.getSessionInfo().userId;
                uri1 = Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, String.valueOf(mUserId));
            }
            if(mUserId == mAppSession.getSessionInfo().userId)
                setPrimaryActionIcon(0x7f0200fd);
            setupListHeaders();
            setupFatTitleHeader();
            listview = getListView();
            arraylist = new ArrayList();
            if(getIntent().getBooleanExtra("extra_exclude_read_only", false))
                arraylist.add((new StringBuilder()).append("type").append("<>'").append("profile").append("'"));
            if(getIntent().getBooleanExtra("extra_exclude_empty", false))
                arraylist.add((new StringBuilder()).append("size").append("> 0"));
            aobj = new Object[1];
            aobj[0] = arraylist;
            mAdapter = new AlbumsAdapter(this, uri1, StringUtils.join(" AND ", aobj), mAppSession);
            if(getParent() != null)
                findViewById(0x7f0e0016).setVisibility(8);
            setupEmptyView();
            mAppSessionListener = new AlbumsAppSessionListener();
            listview.setOnItemClickListener(this);
            listview.setOnCreateContextMenuListener(this);
            mMediaUploader = new MediaUploader(this, null);
        }
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
label0:
        {
            android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo;
            Cursor cursor;
            try
            {
                adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo;
            }
            catch(ClassCastException classcastexception)
            {
                break label0;
            }
            cursor = (Cursor)mAdapter.getItem(getCursorPosition(adaptercontextmenuinfo.position));
            contextmenu.setHeaderTitle(mAdapter.getAlbumName(cursor));
            if("android.intent.action.PICK".equals(getIntent().getAction()))
            {
                contextmenu.add(0, 4, 0, 0x7f0a000e);
            } else
            {
                if(mUserId == mAppSession.getSessionInfo().userId)
                {
                    contextmenu.add(0, 5, 0, 0x7f0a000f);
                    if(mAdapter.getAlbumSize(cursor) == 0)
                        contextmenu.add(0, 6, 0, 0x7f0a000a);
                }
                contextmenu.add(0, 4, 0, 0x7f0a000e);
            }
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR lookupswitch 6: default 60
    //                   1: 64
    //                   2: 76
    //                   3: 130
    //                   255255255: 170
    //                   255255256: 181
    //                   255255257: 192;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        Object obj = null;
_L9:
        return ((Dialog) (obj));
_L2:
        obj = AlbumInfoDialog.create(this, mAlbum);
        continue; /* Loop/switch isn't completed */
_L3:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                mDeleteAlbumReqId = mAppSession.photoDeleteAlbum(AlbumsActivity.this, mAlbum.getAlbumId());
                removeDialog(2);
                showDialog(3);
            }

            final AlbumsActivity this$0;

            
            {
                this$0 = AlbumsActivity.this;
                super();
            }
        }
;
        obj = AlertDialogs.createAlert(this, mAlbum.getName(), 0x1080027, getString(0x7f0a000b), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        continue; /* Loop/switch isn't completed */
_L4:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a000d));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L5:
        obj = mMediaUploader.createDialog();
        continue; /* Loop/switch isn't completed */
_L6:
        obj = mMediaUploader.createPhotoDialog();
        continue; /* Loop/switch isn't completed */
_L7:
        obj = mMediaUploader.createVideoDialog();
        if(true) goto _L9; else goto _L8
_L8:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        if(!"android.intent.action.PICK".equals(getIntent().getAction())) goto _L2; else goto _L1
_L1:
        menu.add(0, 2, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
_L4:
        return true;
_L2:
        menu.add(0, 2, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        if(mUserId == mAppSession.getSessionInfo().userId)
            menu.add(0, 3, 0, 0x7f0a0009).setIcon(0x7f02008b);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        Cursor cursor = (Cursor)mAdapter.getItem(getCursorPosition(i));
        if("android.intent.action.PICK".equals(getIntent().getAction()))
        {
            Intent intent = new Intent();
            intent.setData(Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, mAdapter.getAlbumId(cursor)));
            setResult(-1, intent);
            finish();
        } else
        {
            PhotosActivity.showPhotos(this, mAdapter.getAlbumId(cursor), mUserId);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 3: default 28
    //                   2 34
    //                   3 41;
           goto _L1 _L2 _L3
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        refresh();
        continue; /* Loop/switch isn't completed */
_L3:
        Intent intent = new Intent(this, com/facebook/katana/activity/media/CreateEditAlbumActivity);
        intent.setAction("android.intent.action.INSERT");
        startActivity(intent);
        if(true) goto _L1; else goto _L4
_L4:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
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
        dialog.setTitle(mAlbum.getName());
        if(true) goto _L1; else goto _L4
_L4:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean flag;
        super.onPrepareOptionsMenu(menu);
        if(!mAppSession.isAlbumsGetPending(mUserId))
            flag = true;
        else
            flag = false;
        if(!"android.intent.action.PICK".equals(getIntent().getAction())) goto _L2; else goto _L1
_L1:
        menu.findItem(2).setEnabled(flag);
_L4:
        return true;
_L2:
        menu.findItem(2).setEnabled(flag);
        MenuItem menuitem = menu.findItem(3);
        if(menuitem != null)
            menuitem.setEnabled(flag);
        if(true) goto _L4; else goto _L3
_L3:
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
                removeDialog(3);
                mDeleteAlbumReqId = null;
            }
            mAppSession.addListener(mAppSessionListener);
            getListView().setAdapter(mAdapter);
            updateEmptyView();
            if(!mAppSession.isAlbumsGetPending(mUserId))
                refresh();
            else
                showProgress(true);
            updateFatTitleHeader();
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(mAlbum != null)
            bundle.putString("state_album_id", mAlbum.getAlbumId());
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    public void setupFatTitleHeader()
    {
        if(getIntent().getBooleanExtra("within_tab", false))
            super.setupFatTitleHeader();
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        showDialog(0xf36e2d7);
    }

    public void updateFatTitleHeader()
    {
        if(!getIntent().getBooleanExtra("within_tab", false)) goto _L2; else goto _L1
_L1:
        super.updateFatTitleHeader();
_L4:
        return;
_L2:
        int i;
        Object obj;
        TextView textview = (TextView)findViewById(0x7f0e0071);
        if("android.intent.action.PICK".equals(getIntent().getAction()))
            textview.setText(0x7f0a0017);
        else
            textview.setText(0x7f0a0013);
        i = mAdapter.getCount();
        if(i != 0)
            break; /* Loop/switch isn't completed */
        if(mShowingProgress)
            obj = null;
        else
            obj = getString(0x7f0a001a);
_L5:
        ((TextView)findViewById(0x7f0e0072)).setText(((CharSequence) (obj)));
        if(true) goto _L4; else goto _L3
_L3:
        if(i == 1)
        {
            obj = getString(0x7f0a001b);
        } else
        {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            obj = getString(0x7f0a0019, aobj);
        }
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    private static final int ALBUM_DELETE_MENU_ID = 6;
    private static final int ALBUM_EDIT_MENU_ID = 5;
    private static final int ALBUM_INFO_DIALOG_ID = 1;
    private static final int ALBUM_INFO_MENU_ID = 4;
    public static final int ALBUM_SELECTOR_ACTIVITY_ID = 0x186a0;
    private static final int CREATE_ALBUM_ID = 3;
    private static final int DELETE_ALBUM_QUESTION_DIALOG_ID = 2;
    public static final String EXTRA_EXCLUDE_EMPTY_ALBUMS = "extra_exclude_empty";
    public static final String EXTRA_EXCLUDE_READ_ONLY_ALBUMS = "extra_exclude_read_only";
    private static final int PROGRESS_DELETE_ALBUM_DIALOG_ID = 3;
    private static final int REFRESH_ID = 2;
    private static final String SAVE_STATE_ALBUM_ID = "state_album_id";
    private AlbumsAdapter mAdapter;
    private FacebookAlbum mAlbum;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mDeleteAlbumReqId;
    private MediaUploader mMediaUploader;
    private TabProgressListener mProgressListener;
    private boolean mShowingProgress;
    private long mUserId;






/*
    static String access$402(AlbumsActivity albumsactivity, String s)
    {
        albumsactivity.mDeleteAlbumReqId = s;
        return s;
    }

*/



/*
    static FacebookAlbum access$502(AlbumsActivity albumsactivity, FacebookAlbum facebookalbum)
    {
        albumsactivity.mAlbum = facebookalbum;
        return facebookalbum;
    }

*/

}
