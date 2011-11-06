// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VideoUploader.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.facebook.katana.util.Log;

// Referenced classes of package com.facebook.katana.activity.media:
//            UploadVideoActivity

public class VideoUploader
{

    public VideoUploader(Activity activity, long l, int i, int j)
    {
        mActivity = activity;
        mProfileId = l;
        mTakeCameraVideoRequestCode = i;
        mPickExistingVideoRequestCode = j;
    }

    public Dialog createDialog()
    {
        CharSequence acharsequence[] = new CharSequence[2];
        acharsequence[0] = mActivity.getText(0x7f0a021b);
        acharsequence[1] = mActivity.getText(0x7f0a021a);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getString(0x7f0a021d));
        builder.setItems(acharsequence, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                i;
                JVM INSTR tableswitch 0 1: default 24
            //                           0 31
            //                           1 72;
                   goto _L1 _L2 _L3
_L1:
                dialoginterface.dismiss();
                return;
_L2:
                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("video/*");
                mActivity.startActivityForResult(intent1, mPickExistingVideoRequestCode);
                continue; /* Loop/switch isn't completed */
_L3:
                Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
                mActivity.startActivityForResult(intent, mTakeCameraVideoRequestCode);
                if(true) goto _L1; else goto _L4
_L4:
            }

            final VideoUploader this$0;

            
            {
                this$0 = VideoUploader.this;
                super();
            }
        }
);
        return builder.create();
    }

    public void onActivityResult(int i, int j, Intent intent)
    {
        if(i == mTakeCameraVideoRequestCode || i == mPickExistingVideoRequestCode)
        {
            Intent intent1 = new Intent(mActivity, com/facebook/katana/activity/media/UploadVideoActivity);
            intent1.setAction("com.facebook.katana.upload.uri");
            intent1.putExtra("extra_profile_id", mProfileId);
            intent1.putExtra("android.intent.extra.STREAM", intent.getData());
            mActivity.startActivity(intent1);
        } else
        {
            Log.e("VideoUploader", "illegal requestcode");
        }
    }

    private static final String TAG = "VideoUploader";
    private final Activity mActivity;
    private final int mPickExistingVideoRequestCode;
    private final long mProfileId;
    private final int mTakeCameraVideoRequestCode;



}
