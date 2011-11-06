// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoUploader.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;
import java.io.File;

// Referenced classes of package com.facebook.katana.activity.media:
//            UploadPhotoActivity

public class PhotoUploader
{

    public PhotoUploader(Activity activity, long l, int i, int j)
    {
        mActivity = activity;
        mAlbumId = null;
        mProfileId = l;
        mTakeCameraPhotoRequestCode = i;
        mPickExistingPhotoRequestCode = j;
    }

    public PhotoUploader(Activity activity, String s, int i, int j)
    {
        mActivity = activity;
        mAlbumId = s;
        mProfileId = -1L;
        mTakeCameraPhotoRequestCode = i;
        mPickExistingPhotoRequestCode = j;
    }

    public Dialog createDialog()
    {
        CharSequence acharsequence[] = new CharSequence[2];
        acharsequence[0] = mActivity.getText(0x7f0a0210);
        acharsequence[1] = mActivity.getText(0x7f0a020f);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
        builder.setTitle(0x7f0a0212);
        builder.setItems(acharsequence, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                i;
                JVM INSTR tableswitch 0 1: default 24
            //                           0 31
            //                           1 75;
                   goto _L1 _L2 _L3
_L1:
                dialoginterface.dismiss();
                return;
_L2:
                Intent intent1 = new Intent("android.intent.action.PICK", android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("image/*");
                mActivity.startActivityForResult(intent1, mPickExistingPhotoRequestCode);
                continue; /* Loop/switch isn't completed */
_L3:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", PhotoUploader.TEMP_PHOTO_URI);
                mActivity.startActivityForResult(intent, mTakeCameraPhotoRequestCode);
                if(true) goto _L1; else goto _L4
_L4:
            }

            final PhotoUploader this$0;

            
            {
                this$0 = PhotoUploader.this;
                super();
            }
        }
);
        return builder.create();
    }

    public void onActivityResult(int i, int j, Intent intent)
    {
        if(i != mTakeCameraPhotoRequestCode) goto _L2; else goto _L1
_L1:
        if(TEMP_PHOTO_FILE.exists()) goto _L4; else goto _L3
_L3:
        Toaster.toast(mActivity, 0x7f0a0205);
_L8:
        return;
_L4:
        Intent intent1;
        intent1 = new Intent(mActivity, com/facebook/katana/activity/media/UploadPhotoActivity);
        intent1.setAction("com.facebook.katana.upload.bitmap");
        intent1.putExtra("android.intent.extra.STREAM", TEMP_PHOTO_URI);
_L6:
        intent1.putExtra("extra_album_id", mAlbumId);
        intent1.putExtra("extra_profile_id", mProfileId);
        if(intent != null)
        {
            intent1.putExtra("extra_checkin_id", intent.getLongExtra("checkin_id", -1L));
            intent1.putExtra("extra_photo_publish", intent.getBooleanExtra("extra_photo_publish", true));
        }
        mActivity.startActivity(intent1);
        continue; /* Loop/switch isn't completed */
_L2:
        if(i != mPickExistingPhotoRequestCode)
            break; /* Loop/switch isn't completed */
        intent1 = new Intent(mActivity, com/facebook/katana/activity/media/UploadPhotoActivity);
        intent1.setAction("com.facebook.katana.upload.uri");
        intent1.putExtra("android.intent.extra.STREAM", intent.getData());
        if(true) goto _L6; else goto _L5
_L5:
        Log.e("PhotoUploader", "illegal requestcode");
        if(true) goto _L8; else goto _L7
_L7:
    }

    private static final String PHOTO_FILENAME = "Facebook-photo.jpg";
    private static final String TAG = "PhotoUploader";
    public static final File TEMP_PHOTO_FILE;
    public static final Uri TEMP_PHOTO_URI;
    private final Activity mActivity;
    private final String mAlbumId;
    private final int mPickExistingPhotoRequestCode;
    private final long mProfileId;
    private final int mTakeCameraPhotoRequestCode;

    static 
    {
        TEMP_PHOTO_FILE = new File(Environment.getExternalStorageDirectory(), "Facebook-photo.jpg");
        TEMP_PHOTO_URI = Uri.fromFile(TEMP_PHOTO_FILE);
    }



}
