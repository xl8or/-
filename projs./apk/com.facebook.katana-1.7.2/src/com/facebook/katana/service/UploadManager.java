package com.facebook.katana.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.IBinder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.UploadManagerConnectivity;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoTagBase;
import com.facebook.katana.util.Utils;
import java.io.File;
import java.util.List;

public class UploadManager extends Service {

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
   private boolean mConnected = 0;
   private Context mContext;
   private Cursor mCursor;
   private SQLiteDatabase mDb;
   private boolean mHasNew = 0;
   private SQLiteStatement mInsertStmt;
   private boolean mIsUploading = 0;
   private String mPendingUploadReqId;
   private final AppSessionListener mSessionListener;


   public UploadManager() {
      UploadManager.1 var1 = new UploadManager.1();
      this.mSessionListener = var1;
   }

   private void cancelAllUploads() {
      if(this.mAppSession != null) {
         if(this.mPendingUploadReqId != null) {
            this.mCancelled = (boolean)1;
            AppSession var1 = this.mAppSession;
            Context var2 = this.mContext;
            String var3 = this.mPendingUploadReqId;
            var1.cancelServiceOp(var2, var3);
            this.mPendingUploadReqId = null;
         }
      }
   }

   public static Intent createUploadIntent(Context var0, String var1, String var2, String var3, long var4, long var6, boolean var8, List<? extends FacebookPhotoTagBase> var9, long var10, String var12, long var13) {
      Intent var15 = new Intent(var0, UploadManager.class);
      Intent var16 = var15.putExtra("type", 3);
      if(var2 != null) {
         var15.putExtra("aid", var2);
      }

      if(var3 != null) {
         var15.putExtra("caption", var3);
      }

      var15.putExtra("profile_id", var4);
      var15.putExtra("checkin_id", var6);
      var15.putExtra("uri", var1);
      var15.putExtra("extra_photo_publish", var8);
      if(var9 != null && !var9.isEmpty()) {
         String var23 = FacebookPhotoTagBase.encode(var9);
         var15.putExtra("tags", var23);
      }

      String var26 = "extra_place";
      var15.putExtra(var26, var10);
      if(var13 != 65535L) {
         String var30 = "extra_status_target_id";
         var15.putExtra(var30, var13);
      }

      if(var12 != null) {
         String var35 = "extra_status_privacy";
         var15.putExtra(var35, var12);
      }

      long var38 = Utils.RNG.nextLong();
      var15.putExtra("upload_manager_extras_local_id", var38);
      return var15;
   }

   private void endSession() {
      this.endUpload();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mSessionListener;
         var1.removeListener(var2);
      }
   }

   private void endUpload() {
      this.mIsUploading = (boolean)0;
      if(this.mCursor != null) {
         this.mCursor.close();
      }
   }

   public static long extractLocalUploadId(Intent var0) {
      return var0.getLongExtra("upload_manager_extras_local_id", 65535L);
   }

   private void handleCommand(Intent var1) {
      if(var1 != null) {
         String var3 = "type";
         byte var4 = 0;
         switch(var1.getIntExtra(var3, var4)) {
         case 1:
            this.resumeUploads();
            return;
         case 2:
            this.clearUploads();
            return;
         case 3:
            this.resumeUploads();
            String var6 = "aid";
            String var7 = var1.getStringExtra(var6);
            String var9 = "caption";
            String var10 = var1.getStringExtra(var9);
            String var12 = "uri";
            String var13 = var1.getStringExtra(var12);
            String var15 = "profile_id";
            long var16 = 65535L;
            long var18 = var1.getLongExtra(var15, var16);
            String var21 = "checkin_id";
            long var22 = 65535L;
            long var24 = var1.getLongExtra(var21, var22);
            String var27 = "extra_photo_publish";
            byte var28 = 0;
            boolean var29 = var1.getBooleanExtra(var27, (boolean)var28);
            String var31 = "tags";
            String var32 = var1.getStringExtra(var31);
            String var34 = "extra_place";
            long var35 = 65535L;
            long var37 = var1.getLongExtra(var34, var35);
            String var40 = "extra_status_target_id";
            long var41 = 65535L;
            long var43 = var1.getLongExtra(var40, var41);
            String var46 = "extra_status_privacy";
            String var47 = var1.getStringExtra(var46);
            String var49 = "upload_manager_extras_local_id";
            long var50 = 65535L;
            long var52 = var1.getLongExtra(var49, var50);
            this.addNewImage(var7, var10, var13, var18, var24, var29, var32, var37, var43, var47, var52);
            return;
         default:
         }
      } else {
         this.resumeUploads();
      }
   }

   private void startNextUpload() {
      int var1;
      int var2;
      String var3;
      String var4;
      String var5;
      long var6;
      long var8;
      String var10;
      long var11;
      long var13;
      String var15;
      long var17;
      byte var16;
      byte var31;
      do {
         var1 = this.mCursor.getInt(0);
         var2 = this.mCursor.getInt(1);
         var3 = this.mCursor.getString(2);
         var4 = this.mCursor.getString(3);
         var5 = this.mCursor.getString(4);
         var6 = this.mCursor.getLong(5);
         var8 = this.mCursor.getLong(6);
         var10 = this.mCursor.getString(8);
         var11 = this.mCursor.getLong(9);
         var13 = this.mCursor.getLong(10);
         var15 = this.mCursor.getString(11);
         if(this.mCursor.getInt(7) == 1) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         var17 = this.mCursor.getLong(12);
         byte var20 = 5;
         if(var2 >= var20) {
            SQLiteDatabase var21 = this.mDb;
            StringBuilder var22 = (new StringBuilder()).append("id = ");
            String var24 = var22.append(var1).toString();
            String var26 = "pendingphotos";
            Object var28 = null;
            var21.delete(var26, var24, (String[])var28);
         }

         var31 = 5;
      } while(var2 >= var31 && this.mCursor.moveToNext());

      AppSession var32 = this.mAppSession;
      Context var33 = this.mContext;
      String var34 = var32.photoUpload(var33, var1, var3, var4, var5, var6, var8, (boolean)var16, var11, var10, var13, var15, var17);
      this.mPendingUploadReqId = var34;
   }

   protected void addNewImage(String param1, String param2, String param3, long param4, long param6, boolean param8, String param9, long param10, long param12, String param14, long param15) {
      // $FF: Couldn't be decompiled
   }

   protected void clearUploads() {
      if(this.mDb != null && this.mDb.isOpen()) {
         int var1 = this.mDb.delete("pendingphotos", (String)null, (String[])null);
         this.mDb.close();
      }

      this.endSession();
   }

   public void moveToNextUpload() {
      if(this.mCursor.moveToNext()) {
         this.startNextUpload();
      } else if(this.mHasNew) {
         this.startUploadProcess();
      } else {
         this.endUpload();
      }
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate() {
      super.onCreate();
      Context var1 = this.getApplicationContext();
      this.mContext = var1;
   }

   public void onDestroy() {
      this.cancelAllUploads();
      this.mDb.close();
   }

   public void onStart(Intent var1, int var2) {
      this.handleCommand(var1);
   }

   public int onStartCommand(Intent var1, int var2, int var3) {
      this.handleCommand(var1);
      return 1;
   }

   protected void resumeUploads() {
      if(this.mDb == null || !this.mDb.isOpen()) {
         Context var1 = this.mContext;
         SQLiteDatabase var2 = (new UploadManager.OpenHelper(var1)).getWritableDatabase();
         this.mDb = var2;
         SQLiteStatement var3 = this.mDb.compileStatement("INSERT INTO pendingphotos(numTries, albumId, caption, filename, profileId, checkinId, publish, tags, place, targetId, privacy, localUploadId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
         this.mInsertStmt = var3;
      }

      if(this.mAppSession != null) {
         AppSession.LoginStatus var4 = this.mAppSession.getStatus();
         AppSession.LoginStatus var5 = AppSession.LoginStatus.STATUS_LOGGED_OUT;
         if(var4.equals(var5)) {
            AppSession var6 = this.mAppSession;
            AppSessionListener var7 = this.mSessionListener;
            var6.removeListener(var7);
            this.mAppSession = null;
         }
      }

      if(this.mAppSession == null) {
         AppSession var8 = AppSession.getActiveSession(this.mContext, (boolean)0);
         this.mAppSession = var8;
         if(this.mAppSession != null) {
            AppSession var9 = this.mAppSession;
            AppSessionListener var10 = this.mSessionListener;
            var9.addListener(var10);
         } else {
            int var12 = this.mDb.delete("pendingphotos", (String)null, (String[])null);
         }
      }

      this.mCancelled = (boolean)0;
      boolean var11 = UploadManagerConnectivity.isConnected(this.mContext);
      this.mConnected = var11;
      if(this.mAppSession != null) {
         if(this.mConnected) {
            if(!this.mIsUploading) {
               this.startUploadProcess();
            }
         }
      }
   }

   protected void startUploadProcess() {
      this.mIsUploading = (boolean)1;
      this.mHasNew = (boolean)0;
      SQLiteDatabase var1 = this.mDb;
      String[] var2 = new String[]{"id", "numTries", "albumId", "caption", "filename", "profileId", "checkinId", "publish", "tags", "place", "targetId", "privacy", "localUploadId"};
      Object var3 = null;
      Object var4 = null;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var1.query("pendingphotos", var2, (String)null, (String[])var3, (String)var4, (String)var5, (String)var6, (String)var7);
      this.mCursor = var8;
      if(this.mCursor.moveToFirst()) {
         this.startNextUpload();
      } else {
         this.endUpload();
      }
   }

   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context var1) {
         super(var1, "uploadmanager.db", (CursorFactory)null, 7);
      }

      public void onCreate(SQLiteDatabase var1) {
         var1.execSQL("CREATE TABLE pendingphotos(id INTEGER PRIMARY KEY, numTries INTEGER, albumId TEXT, caption TEXT, filename TEXT, profileId INTEGER, checkinId INTEGER, publish INTEGER, tags TEXT, place INTEGER, targetId INTEGER, privacy TEXT, localUploadId INTEGER)");
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         var1.execSQL("DROP TABLE IF EXISTS pendingphotos");
         this.onCreate(var1);
      }
   }

   public static class Extras {

      public static final String LOCAL_ID = "upload_manager_extras_local_id";


      public Extras() {}
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onPhotoUploadComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, FacebookPhoto var7, String var8, long var9, long var11, long var13) {
         short var16 = 200;
         if(var3 == var16) {
            SQLiteDatabase var17 = UploadManager.this.mDb;
            StringBuilder var18 = (new StringBuilder()).append("id = ");
            String var20 = var18.append(var6).toString();
            var17.delete("pendingphotos", var20, (String[])null);
            File var22 = new File(var8);
            boolean var25 = var22.delete();
            UploadManager.this.moveToNextUpload();
         } else if(!UploadManager.this.mCancelled) {
            SQLiteDatabase var27 = UploadManager.this.mDb;
            String[] var28 = new String[]{"id", "numTries"};
            StringBuilder var29 = (new StringBuilder()).append("id = ");
            String var31 = var29.append(var6).toString();
            Cursor var32 = var27.query("pendingphotos", var28, var31, (String[])null, (String)null, (String)null, (String)null);
            if(var32.moveToFirst()) {
               int var33 = var32.getInt(1);
               ContentValues var34 = new ContentValues();
               Integer var35 = Integer.valueOf(var33 + 1);
               var34.put("numTries", var35);
               SQLiteDatabase var36 = UploadManager.this.mDb;
               StringBuilder var37 = (new StringBuilder()).append("id = ");
               String var39 = var37.append(var6).toString();
               var36.update("pendingphotos", var34, var39, (String[])null);
            }

            UploadManager var41 = UploadManager.this;
            boolean var42 = UploadManagerConnectivity.isConnected(UploadManager.this.mContext);
            var41.mConnected = var42;
            if(UploadManager.this.mConnected) {
               SQLiteDatabase var44 = UploadManager.this.mDb;
               StringBuilder var45 = (new StringBuilder()).append("id = ");
               String var47 = var45.append(var6).toString();
               var44.delete("pendingphotos", var47, (String[])null);
               File var49 = new File(var8);
               boolean var52 = var49.delete();
               UploadManager.this.moveToNextUpload();
            } else {
               UploadManager.this.endUpload();
            }
         }

         if(UploadManager.this.mPendingUploadReqId == var2) {
            String var26 = UploadManager.this.mPendingUploadReqId = null;
         }
      }
   }
}
