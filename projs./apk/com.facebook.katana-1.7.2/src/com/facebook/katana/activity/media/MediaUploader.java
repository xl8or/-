// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MediaUploader.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.facebook.katana.util.Log;

// Referenced classes of package com.facebook.katana.activity.media:
//            PhotoUploader, VideoUploader

public class MediaUploader
{

    public MediaUploader(Activity activity, long l)
    {
        mActivity = activity;
        mAlbumId = null;
        mProfileId = l;
        mPhotoUploader = new PhotoUploader(mActivity, mProfileId, 0x20a45, 0x20a46);
        mVideoUploader = new VideoUploader(mActivity, mProfileId, 0x20a47, 0x20a48);
    }

    public MediaUploader(Activity activity, String s)
    {
        mActivity = activity;
        mAlbumId = s;
        mProfileId = -1L;
        mPhotoUploader = new PhotoUploader(mActivity, mAlbumId, 0x20a45, 0x20a46);
        mVideoUploader = new VideoUploader(mActivity, mProfileId, 0x20a47, 0x20a48);
    }

    public Dialog createDialog()
    {
        CharSequence acharsequence[] = new CharSequence[2];
        acharsequence[0] = mActivity.getText(0x7f0a0211);
        acharsequence[1] = mActivity.getText(0x7f0a021c);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getText(0x7f0a0200));
        builder.setItems(acharsequence, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                i;
                JVM INSTR tableswitch 0 1: default 24
            //                           0 31
            //                           1 46;
                   goto _L1 _L2 _L3
_L1:
                dialoginterface.dismiss();
                return;
_L2:
                mActivity.showDialog(0xf36e2d8);
                continue; /* Loop/switch isn't completed */
_L3:
                mActivity.showDialog(0xf36e2d9);
                if(true) goto _L1; else goto _L4
_L4:
            }

            final MediaUploader this$0;

            
            {
                this$0 = MediaUploader.this;
                super();
            }
        }
);
        return builder.create();
    }

    public Dialog createPhotoDialog()
    {
        return mPhotoUploader.createDialog();
    }

    public Dialog createVideoDialog()
    {
        return mVideoUploader.createDialog();
    }

    public void onActivityResult(int i, int j, Intent intent)
    {
        if(i == 0x20a45 || i == 0x20a46)
            mPhotoUploader.onActivityResult(i, j, intent);
        else
        if(i == 0x20a47 || i == 0x20a48)
            mVideoUploader.onActivityResult(i, j, intent);
        else
            Log.e("MediaUploader", "illegal requestcode");
    }

    public static final int MEDIA_SOURCE_CHOOSER_DIALOG_ID = 0xf36e2d7;
    public static final int PHOTO_SOURCE_CHOOSER_DIALOG_ID = 0xf36e2d8;
    public static final int PICK_EXISTING_PHOTO_REQUEST_CODE = 0x20a46;
    public static final int PICK_EXISTING_VIDEO_REQUEST_CODE = 0x20a48;
    private static final String TAG = "MediaUploader";
    public static final int TAKE_CAMERA_PHOTO_REQUEST_CODE = 0x20a45;
    public static final int TAKE_CAMERA_VIDEO_REQUEST_CODE = 0x20a47;
    public static final int VIDEO_SOURCE_CHOOSER_DIALOG_ID = 0xf36e2d9;
    private final Activity mActivity;
    private final String mAlbumId;
    private PhotoUploader mPhotoUploader;
    private final long mProfileId;
    private VideoUploader mVideoUploader;

}
