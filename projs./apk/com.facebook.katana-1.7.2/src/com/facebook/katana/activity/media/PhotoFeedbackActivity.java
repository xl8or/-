// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoFeedbackActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.FatTitleHeader;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.*;
import java.util.*;

public class PhotoFeedbackActivity extends BaseFacebookListActivity
    implements FatTitleHeader
{
    private static class PhotoFeedbackAdapter extends BaseAdapter
    {

        public void addComment(FacebookPhotoComment facebookphotocomment)
        {
            mComments.add(facebookphotocomment);
            notifyDataSetChanged();
        }

        public int getCount()
        {
            return mComments.size();
        }

        public Object getItem(int i)
        {
            return mComments.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            View view1;
            ViewHolder viewholder;
            FacebookPhotoComment facebookphotocomment;
            long l;
            FacebookProfile facebookprofile;
            String s;
            if(view == null)
            {
                view1 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030011, null);
                viewholder = new ViewHolder(view1, 0x7f0e0036);
                view1.setTag(viewholder);
                mViewHolders.add(viewholder);
            } else
            {
                view1 = view;
                viewholder = (ViewHolder)view1.getTag();
            }
            facebookphotocomment = (FacebookPhotoComment)mComments.get(i);
            l = facebookphotocomment.getFromProfileId();
            viewholder.setItemId(Long.valueOf(l));
            facebookprofile = facebookphotocomment.getFromProfile();
            s = facebookprofile.mImageUrl;
            if(s != null)
            {
                android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, l, s);
                String s1;
                if(bitmap != null)
                    viewholder.mImageView.setImageBitmap(bitmap);
                else
                    viewholder.mImageView.setImageResource(0x7f0200f3);
            } else
            {
                viewholder.mImageView.setImageResource(0x7f0200f3);
            }
            ((TextView)view1.findViewById(0x7f0e0037)).setText(facebookprofile.mDisplayName);
            ((TextView)view1.findViewById(0x7f0e0039)).setText(facebookphotocomment.getBody());
            s1 = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * facebookphotocomment.getTime());
            ((TextView)view1.findViewById(0x7f0e0038)).setText(s1);
            return view1;
        }

        public void setComments(List list)
        {
            mComments.clear();
            mComments.addAll(list);
            notifyDataSetChanged();
        }

        public void updateUserImage(ProfileImage profileimage)
        {
            Iterator iterator = mViewHolders.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                ViewHolder viewholder = (ViewHolder)iterator.next();
                Long long1 = (Long)viewholder.getItemId();
                if(long1 != null && long1.equals(Long.valueOf(profileimage.id)))
                    viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
            } while(true);
        }

        private final List mComments = new ArrayList();
        private final Context mContext;
        private final ProfileImagesCache mUserImagesCache;
        private final List mViewHolders = new ArrayList();

        public PhotoFeedbackAdapter(Context context, ProfileImagesCache profileimagescache)
        {
            mContext = context;
            mUserImagesCache = profileimagescache;
        }
    }

    private class PhotoFeedbackAppSessionListener extends AppSessionListener
    {

        public void onDownloadPhotoThumbnailComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String s3)
        {
            if(mPhotoId.equals(s3)) goto _L2; else goto _L1
_L1:
            return;
_L2:
            ImageView imageview = (ImageView)findViewById(0x7f0e0070);
            if(i == 200)
            {
                mPhoto = FacebookPhoto.readFromContentProvider(PhotoFeedbackActivity.this, s3);
                byte abyte0[] = mPhoto.getImageBytes();
                if(abyte0 != null)
                    imageview.setImageBitmap(ImageUtils.decodeByteArray(abyte0, 0, abyte0.length));
            } else
            {
                imageview.setImageResource(0x7f0200ff);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onPhotoAddCommentComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, FacebookPhotoComment facebookphotocomment)
        {
            if(s2.equals(mPhotoId))
            {
                removeDialog(1);
                if(i == 200)
                {
                    ((EditText)findViewById(0x7f0e0076)).setText(null);
                    FacebookProfile facebookprofile = ConnectionsProvider.getAdminProfile(PhotoFeedbackActivity.this, mPhoto.getOwnerId());
                    if(facebookprofile != null)
                        facebookphotocomment.setFromProfile(facebookprofile);
                    mAdapter.addComment(facebookphotocomment);
                } else
                {
                    String s3 = StringUtils.getErrorString(PhotoFeedbackActivity.this, getString(0x7f0a0118), i, s1, exception);
                    Toaster.toast(PhotoFeedbackActivity.this, s3);
                }
            }
        }

        public void onPhotoGetCommentsComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, List list, 
                boolean flag)
        {
            if(s2.equals(mPhotoId)) goto _L2; else goto _L1
_L1:
            return;
_L2:
            showProgress(false);
            if(i == 200)
            {
                mAdapter.setComments(list);
                if(flag)
                    findViewById(0x7f0e0075).setVisibility(0);
            } else
            {
                String s3 = StringUtils.getErrorString(PhotoFeedbackActivity.this, getString(0x7f0a011c), i, s1, exception);
                Toaster.toast(PhotoFeedbackActivity.this, s3);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        public void onPhotoGetPhotosComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, String as[], 
                long l)
        {
            if(i == 200 && !isFinishing())
            {
                mPhoto = FacebookPhoto.readFromContentProvider(PhotoFeedbackActivity.this, getIntent().getData());
                if(mPhoto != null)
                {
                    mAppSession.downloadPhotoThumbail(PhotoFeedbackActivity.this, mPhoto.getAlbumId(), mPhoto.getPhotoId(), mPhoto.getSrc());
                    setupFatTitleHeader();
                }
            }
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

        final PhotoFeedbackActivity this$0;

        private PhotoFeedbackAppSessionListener()
        {
            this$0 = PhotoFeedbackActivity.this;
            super();
        }

    }


    public PhotoFeedbackActivity()
    {
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a011f);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a011e);
        findViewById(0x1020004).setBackgroundDrawable(getResources().getDrawable(0x7f02013b));
    }

    private void showProgress(boolean flag)
    {
        if(flag)
        {
            findViewById(0x7f0e00f1).setVisibility(0);
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e00f1).setVisibility(8);
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030050);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mPhotoId = getIntent().getData().getLastPathSegment();
            mPhoto = FacebookPhoto.readFromContentProvider(this, getIntent().getData());
            if(mPhoto == null)
            {
                String as[] = new String[1];
                as[0] = mPhotoId;
                List list = Arrays.asList(as);
                if(!mAppSession.isPhotosGetPending(list, -1L))
                    mAppSession.photoGetPhotos(this, null, list, -1L);
            }
            mAppSessionListener = new PhotoFeedbackAppSessionListener();
            setupFatTitleHeader();
            mAdapter = new PhotoFeedbackAdapter(this, mAppSession.getUserImagesCache());
            getListView().setAdapter(mAdapter);
            findViewById(0x7f0e0077).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    EditText edittext1 = (EditText)findViewById(0x7f0e0076);
                    String s = edittext1.getText().toString().trim();
                    if(s.length() > 0)
                    {
                        showDialog(1);
                        mAddCommentReqId = mAppSession.photoAddComment(PhotoFeedbackActivity.this, mPhotoId, s);
                        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
                    }
                }

                final PhotoFeedbackActivity this$0;

            
            {
                this$0 = PhotoFeedbackActivity.this;
                super();
            }
            }
);
            EditText edittext = (EditText)findViewById(0x7f0e0076);
            edittext.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
                {
                    if(i == 101)
                    {
                        String s = ((TextView)findViewById(0x7f0e0076)).getText().toString().trim();
                        if(s.length() > 0)
                        {
                            showDialog(1);
                            mAddCommentReqId = mAppSession.photoAddComment(PhotoFeedbackActivity.this, mPhotoId, s);
                        }
                    }
                    return false;
                }

                final PhotoFeedbackActivity this$0;

            
            {
                this$0 = PhotoFeedbackActivity.this;
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

                final PhotoFeedbackActivity this$0;

            
            {
                this$0 = PhotoFeedbackActivity.this;
                super();
            }
            }
);
            setupEmptyView();
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 1: default 20
    //                   1 24;
           goto _L1 _L2
_L1:
        Object obj = null;
_L4:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a0119));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        return true;
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = ((FacebookPhotoComment)mAdapter.getItem(i)).getFromProfile();
        ApplicationUtils.OpenUserProfile(this, facebookprofile.mId, facebookprofile);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 1 1: default 24
    //                   1 30;
           goto _L1 _L2
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        mAppSession.photoGetComments(this, mPhotoId);
        showProgress(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        boolean flag = mAppSession.isPhotoGetCommentPending(mPhotoId);
        MenuItem menuitem = menu.findItem(1);
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
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        boolean flag = false;
        mAppSession.addListener(mAppSessionListener);
        if(mAddCommentReqId != null && !mAppSession.isRequestPending(mAddCommentReqId))
        {
            removeDialog(1);
            mAddCommentReqId = null;
            flag = true;
            ((EditText)findViewById(0x7f0e0076)).setText(null);
        }
        if(!mAppSession.isPhotoGetCommentPending(mPhotoId))
        {
            if(mAdapter.getCount() == 0 || flag)
            {
                mAppSession.photoGetComments(this, mPhotoId);
                showProgress(true);
            }
        } else
        {
            showProgress(true);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setupFatTitleHeader()
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e0070);
        imageview.setVisibility(0);
        byte abyte0[];
        String s;
        com.facebook.katana.util.StringUtils.TimeFormatStyle timeformatstyle;
        long l;
        String s1;
        Object aobj[];
        String s2;
        if(mPhoto == null)
            abyte0 = null;
        else
            abyte0 = mPhoto.getImageBytes();
        if(abyte0 != null)
        {
            android.graphics.Bitmap bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
            if(bitmap != null)
                imageview.setImageBitmap(bitmap);
        } else
        if(mPhoto != null)
        {
            imageview.setImageResource(0x7f020100);
            mAppSession.downloadPhotoThumbail(this, mPhoto.getAlbumId(), mPhoto.getPhotoId(), mPhoto.getSrc());
        } else
        {
            imageview.setImageResource(0x7f020100);
        }
        if(mPhoto == null)
            s = null;
        else
            s = mPhoto.getCaption();
        if(s == null)
            s = getString(0x7f0a01ff);
        ((TextView)findViewById(0x7f0e0071)).setText(s);
        timeformatstyle = com.facebook.katana.util.StringUtils.TimeFormatStyle.MONTH_DAY_YEAR_STYLE;
        if(mPhoto == null)
            l = System.currentTimeMillis();
        else
            l = mPhoto.getCreatedMs();
        s1 = StringUtils.getTimeAsString(this, timeformatstyle, l);
        aobj = new Object[1];
        aobj[0] = s1;
        s2 = getString(0x7f0a011b, aobj);
        ((TextView)findViewById(0x7f0e0072)).setText(s2);
    }

    public void updateFatTitleHeader()
    {
    }

    private static final int PROGRESS_ADD_COMMENT_DIALOG_ID = 1;
    private static final int REFRESH_MENU_ID = 1;
    private PhotoFeedbackAdapter mAdapter;
    private String mAddCommentReqId;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private FacebookPhoto mPhoto;
    private String mPhotoId;



/*
    static FacebookPhoto access$002(PhotoFeedbackActivity photofeedbackactivity, FacebookPhoto facebookphoto)
    {
        photofeedbackactivity.mPhoto = facebookphoto;
        return facebookphoto;
    }

*/






/*
    static String access$602(PhotoFeedbackActivity photofeedbackactivity, String s)
    {
        photofeedbackactivity.mAddCommentReqId = s;
        return s;
    }

*/
}
