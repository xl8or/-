// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploadManager.java

package com.facebook.katana.service;

import android.app.Service;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.IBinder;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoTagBase;
import com.facebook.katana.util.Utils;
import java.io.File;
import java.util.List;
import java.util.Random;

public class UploadManager extends Service
{
    private static class OpenHelper extends SQLiteOpenHelper
    {

        public void onCreate(SQLiteDatabase sqlitedatabase)
        {
            sqlitedatabase.execSQL("CREATE TABLE pendingphotos(id INTEGER PRIMARY KEY, numTries INTEGER, albumId TEXT, caption TEXT, filename TEXT, profileId INTEGER, checkinId INTEGER, publish INTEGER, tags TEXT, place INTEGER, targetId INTEGER, privacy TEXT, localUploadId INTEGER)");
        }

        public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
        {
            sqlitedatabase.execSQL("DROP TABLE IF EXISTS pendingphotos");
            onCreate(sqlitedatabase);
        }

        OpenHelper(Context context)
        {
            super(context, "uploadmanager.db", null, 7);
        }
    }

    public static class Extras
    {

        public static final String LOCAL_ID = "upload_manager_extras_local_id";

        public Extras()
        {
        }
    }


    public UploadManager()
    {
        mIsUploading = false;
        mHasNew = false;
        mConnected = false;
    }

    private void cancelAllUploads()
    {
        if(mAppSession != null && mPendingUploadReqId != null)
        {
            mCancelled = true;
            mAppSession.cancelServiceOp(mContext, mPendingUploadReqId);
            mPendingUploadReqId = null;
        }
    }

    public static Intent createUploadIntent(Context context, String s, String s1, String s2, long l, long l1, 
            boolean flag, List list, long l2, String s3, long l3)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/UploadManager);
        intent.putExtra("type", 3);
        if(s1 != null)
            intent.putExtra("aid", s1);
        if(s2 != null)
            intent.putExtra("caption", s2);
        intent.putExtra("profile_id", l);
        intent.putExtra("checkin_id", l1);
        intent.putExtra("uri", s);
        intent.putExtra("extra_photo_publish", flag);
        if(list != null && !list.isEmpty())
            intent.putExtra("tags", FacebookPhotoTagBase.encode(list));
        intent.putExtra("extra_place", l2);
        if(l3 != -1L)
            intent.putExtra("extra_status_target_id", l3);
        if(s3 != null)
            intent.putExtra("extra_status_privacy", s3);
        intent.putExtra("upload_manager_extras_local_id", Utils.RNG.nextLong());
        return intent;
    }

    private void endSession()
    {
        endUpload();
        if(mAppSession != null)
            mAppSession.removeListener(mSessionListener);
    }

    private void endUpload()
    {
        mIsUploading = false;
        if(mCursor != null)
            mCursor.close();
    }

    public static long extractLocalUploadId(Intent intent)
    {
        return intent.getLongExtra("upload_manager_extras_local_id", -1L);
    }

    private void handleCommand(Intent intent)
    {
        if(intent == null) goto _L2; else goto _L1
_L1:
        intent.getIntExtra("type", 0);
        JVM INSTR tableswitch 1 3: default 36
    //                   1 37
    //                   2 44
    //                   3 51;
           goto _L3 _L4 _L5 _L6
_L3:
        return;
_L4:
        resumeUploads();
        continue; /* Loop/switch isn't completed */
_L5:
        clearUploads();
        continue; /* Loop/switch isn't completed */
_L6:
        resumeUploads();
        addNewImage(intent.getStringExtra("aid"), intent.getStringExtra("caption"), intent.getStringExtra("uri"), intent.getLongExtra("profile_id", -1L), intent.getLongExtra("checkin_id", -1L), intent.getBooleanExtra("extra_photo_publish", false), intent.getStringExtra("tags"), intent.getLongExtra("extra_place", -1L), intent.getLongExtra("extra_status_target_id", -1L), intent.getStringExtra("extra_status_privacy"), intent.getLongExtra("upload_manager_extras_local_id", -1L));
        continue; /* Loop/switch isn't completed */
_L2:
        resumeUploads();
        if(true) goto _L3; else goto _L7
_L7:
    }

    private void startNextUpload()
    {
        do
        {
            int i = mCursor.getInt(0);
            int j = mCursor.getInt(1);
            String s = mCursor.getString(2);
            String s1 = mCursor.getString(3);
            String s2 = mCursor.getString(4);
            long l = mCursor.getLong(5);
            long l1 = mCursor.getLong(6);
            String s3 = mCursor.getString(8);
            long l2 = mCursor.getLong(9);
            long l3 = mCursor.getLong(10);
            String s4 = mCursor.getString(11);
            boolean flag;
            long l4;
            if(mCursor.getInt(7) == 1)
                flag = true;
            else
                flag = false;
            l4 = mCursor.getLong(12);
            if(j >= 5)
                mDb.delete("pendingphotos", (new StringBuilder()).append("id = ").append(i).toString(), null);
        } while(j >= 5 && mCursor.moveToNext());
        mPendingUploadReqId = mAppSession.photoUpload(mContext, i, s, s1, s2, l, l1, flag, l2, s3, l3, s4, l4);
    }

    protected void addNewImage(String s, String s1, String s2, long l, long l1, 
            boolean flag, String s3, long l2, long l3, String s4, 
            long l4)
    {
        mInsertStmt.clearBindings();
        mInsertStmt.bindLong(1, 0L);
        if(s != null)
            mInsertStmt.bindString(2, s);
        if(s1 != null)
            mInsertStmt.bindString(3, s1);
        mInsertStmt.bindString(4, s2);
        mInsertStmt.bindLong(5, l);
        mInsertStmt.bindLong(6, l1);
        mInsertStmt.bindLong(9, l2);
        SQLiteStatement sqlitestatement = mInsertStmt;
        long l5;
        if(flag)
            l5 = 1L;
        else
            l5 = 0L;
        sqlitestatement.bindLong(7, l5);
        mInsertStmt.bindLong(12, l4);
        if(s3 != null)
            mInsertStmt.bindString(8, s3);
        mInsertStmt.bindLong(10, l3);
        if(s4 != null)
            mInsertStmt.bindString(11, s4);
        mInsertStmt.executeInsert();
        mHasNew = true;
        if(!mIsUploading && mAppSession != null)
            startUploadProcess();
    }

    protected void clearUploads()
    {
        if(mDb != null && mDb.isOpen())
        {
            mDb.delete("pendingphotos", null, null);
            mDb.close();
        }
        endSession();
    }

    public void moveToNextUpload()
    {
        if(mCursor.moveToNext())
            startNextUpload();
        else
        if(mHasNew)
            startUploadProcess();
        else
            endUpload();
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public void onDestroy()
    {
        cancelAllUploads();
        mDb.close();
    }

    public void onStart(Intent intent, int i)
    {
        handleCommand(intent);
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        handleCommand(intent);
        return 1;
    }

    protected void resumeUploads()
    {
        if(mDb == null || !mDb.isOpen())
        {
            mDb = (new OpenHelper(mContext)).getWritableDatabase();
            mInsertStmt = mDb.compileStatement("INSERT INTO pendingphotos(numTries, albumId, caption, filename, profileId, checkinId, publish, tags, place, targetId, privacy, localUploadId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        if(mAppSession != null && mAppSession.getStatus().equals(com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_OUT))
        {
            mAppSession.removeListener(mSessionListener);
            mAppSession = null;
        }
        if(mAppSession == null)
        {
            mAppSession = AppSession.getActiveSession(mContext, false);
            if(mAppSession != null)
                mAppSession.addListener(mSessionListener);
            else
                mDb.delete("pendingphotos", null, null);
        }
        mCancelled = false;
        mConnected = UploadManagerConnectivity.isConnected(mContext);
        if(mAppSession != null && mConnected && !mIsUploading)
            startUploadProcess();
    }

    protected void startUploadProcess()
    {
        mIsUploading = true;
        mHasNew = false;
        SQLiteDatabase sqlitedatabase = mDb;
        String as[] = new String[13];
        as[0] = "id";
        as[1] = "numTries";
        as[2] = "albumId";
        as[3] = "caption";
        as[4] = "filename";
        as[5] = "profileId";
        as[6] = "checkinId";
        as[7] = "publish";
        as[8] = "tags";
        as[9] = "place";
        as[10] = "targetId";
        as[11] = "privacy";
        as[12] = "localUploadId";
        mCursor = sqlitedatabase.query("pendingphotos", as, null, null, null, null, null, null);
        if(mCursor.moveToFirst())
            startNextUpload();
        else
            endUpload();
    }

    private static final String DATABASE_NAME = "uploadmanager.db";
    private static final int DATABASE_VERSION = 7;
    private static final String INSERT = "INSERT INTO pendingphotos(numTries, albumId, caption, filename, profileId, checkinId, publish, tags, place, targetId, privacy, localUploadId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final int MAX_TRIES = 5;
    public static final int REQ_TYPE_CLEAR = 2;
    public static final int REQ_TYPE_RESUME = 1;
    public static final int REQ_TYPE_UPLOAD = 3;
    private static final String TABLE_NAME = "pendingphotos";
    private static final int UPLOAD_LOCAL_ID = 12;
    private static final int UPLOAD_PHOTO_ALBUM_ID = 2;
    private static final int UPLOAD_PHOTO_CAPTION = 3;
    private static final int UPLOAD_PHOTO_CHECKIN_ID = 6;
    private static final int UPLOAD_PHOTO_FILENAME = 4;
    private static final int UPLOAD_PHOTO_ID = 0;
    private static final int UPLOAD_PHOTO_NUMTRIES = 1;
    private static final int UPLOAD_PHOTO_PLACE = 9;
    private static final int UPLOAD_PHOTO_PRIVACY = 11;
    private static final int UPLOAD_PHOTO_PROFILE_ID = 5;
    private static final int UPLOAD_PHOTO_PUBLISH = 7;
    private static final int UPLOAD_PHOTO_TAGS = 8;
    private static final int UPLOAD_PHOTO_TARGET_ID = 10;
    private AppSession mAppSession;
    private boolean mCancelled;
    private boolean mConnected;
    private Context mContext;
    private Cursor mCursor;
    private SQLiteDatabase mDb;
    private boolean mHasNew;
    private SQLiteStatement mInsertStmt;
    private boolean mIsUploading;
    private String mPendingUploadReqId;
    private final AppSessionListener mSessionListener = new AppSessionListener() {

        public void onPhotoUploadComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, FacebookPhoto facebookphoto, 
                String s2, long l, long l1, long l2)
        {
            if(i != 200) goto _L2; else goto _L1
_L1:
            mDb.delete("pendingphotos", (new StringBuilder()).append("id = ").append(j).toString(), null);
            File file1 = new File(s2);
            file1.delete();
            moveToNextUpload();
_L4:
            if(mPendingUploadReqId == s)
                mPendingUploadReqId = null;
            return;
_L2:
            if(!mCancelled)
            {
                SQLiteDatabase sqlitedatabase = mDb;
                String as[] = new String[2];
                as[0] = "id";
                as[1] = "numTries";
                Cursor cursor = sqlitedatabase.query("pendingphotos", as, (new StringBuilder()).append("id = ").append(j).toString(), null, null, null, null);
                if(cursor.moveToFirst())
                {
                    int k = cursor.getInt(1);
                    ContentValues contentvalues = new ContentValues();
                    contentvalues.put("numTries", Integer.valueOf(k + 1));
                    mDb.update("pendingphotos", contentvalues, (new StringBuilder()).append("id = ").append(j).toString(), null);
                }
                mConnected = UploadManagerConnectivity.isConnected(mContext);
                if(mConnected)
                {
                    mDb.delete("pendingphotos", (new StringBuilder()).append("id = ").append(j).toString(), null);
                    File file = new File(s2);
                    file.delete();
                    moveToNextUpload();
                } else
                {
                    endUpload();
                }
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final UploadManager this$0;

            
            {
                this$0 = UploadManager.this;
                super();
            }
    }
;





/*
    static boolean access$202(UploadManager uploadmanager, boolean flag)
    {
        uploadmanager.mConnected = flag;
        return flag;
    }

*/





/*
    static String access$502(UploadManager uploadmanager, String s)
    {
        uploadmanager.mPendingUploadReqId = s;
        return s;
    }

*/
}
