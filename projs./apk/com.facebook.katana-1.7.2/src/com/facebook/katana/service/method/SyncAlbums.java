package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.service.method.AlbumSyncModel;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetAlbums;
import com.facebook.katana.util.Factory;
import java.util.ArrayList;
import java.util.List;

public class SyncAlbums extends ApiMethod {

   private static final long ALBUM_BATCH_SIZE = 20L;
   private static final long FIRST_BATCH_SIZE = 10L;
   private static final long MAX_ALBUM_BEFORE_USING_BATCH = 70L;
   private final String[] mAlbumIds;
   private List<FacebookAlbum> mAlbums;
   private boolean mAllQueriesSuccess;
   private final Context mContext;
   private final Intent mIntent;
   private final ApiMethodListener mListener;
   private final String mSessionKey;
   private final long mUserId;


   public SyncAlbums(Context var1, Intent var2, String var3, long var4, String[] var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      Object var12 = null;
      super(var1, var2, "GET", (String)null, var8, (ApiMethodListener)var12);
      ArrayList var13 = new ArrayList();
      this.mAlbums = var13;
      this.mContext = var1;
      this.mIntent = var2;
      this.mSessionKey = var3;
      this.mUserId = var4;
      this.mAlbumIds = var6;
      this.mListener = var7;
      this.mAllQueriesSuccess = (boolean)1;
   }

   protected static Factory<Cursor> localAlbumsCursorFactory(ContentResolver var0, long var1, List<FacebookAlbum> var3) {
      return new SyncAlbums.1(var1, var0, var3);
   }

   protected static boolean shouldUseBatch(long var0, String[] var2) {
      boolean var3;
      if(var2 != false && var2.length != 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected static void syncAlbums(ContentResolver var0, long var1, List<FacebookAlbum> var3, boolean var4) {
      Factory var5 = localAlbumsCursorFactory(var0, var1, var3);
      byte var8 = 1;
      AlbumSyncModel.doSync(var0, var3, var5, (boolean)1, (boolean)var8, var4, (boolean)0, var1);
   }

   private static boolean updatingSpecificAlbums(long var0) {
      boolean var2;
      if(65535L == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void start() {
      long var1 = 65535L;
      long var3 = this.mUserId;
      String[] var5 = this.mAlbumIds;
      if(shouldUseBatch(var3, var5)) {
         var1 = 10L;
      }

      Context var6 = this.mContext;
      Intent var7 = this.mReqIntent;
      String var8 = this.mSessionKey;
      long var9 = this.mUserId;
      String[] var11 = this.mAlbumIds;
      SyncAlbums.SyncAlbumsListener var12 = new SyncAlbums.SyncAlbumsListener();
      (new FqlGetAlbums(var6, var7, var8, var9, var11, var12, 0L, var1)).start();
   }

   protected class SyncAlbumsListener implements ApiMethodListener {

      private boolean mLastBatch;


      protected SyncAlbumsListener() {}

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         if(this.mLastBatch) {
            SyncAlbums.this.mListener.onOperationComplete(var1, var2, var3, var4);
         }
      }

      public void onOperationProgress(ApiMethod var1, long var2, long var4) {
         ApiMethodListener var6 = SyncAlbums.this.mListener;
         var6.onOperationProgress(var1, var2, var4);
      }

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         SyncAlbums var5;
         byte var8;
         label30: {
            var5 = SyncAlbums.this;
            if(SyncAlbums.this.mAllQueriesSuccess) {
               short var7 = 200;
               if(var2 == var7) {
                  var8 = 1;
                  break label30;
               }
            }

            var8 = 0;
         }

         var5.mAllQueriesSuccess = (boolean)var8;
         List var10 = ((FqlGetAlbums)var1).mAlbums;
         long var11 = (long)SyncAlbums.this.mAlbums.size();
         if(var10 != null && var10.size() != 0 && var11 < 70L && SyncAlbums.this.mAllQueriesSuccess) {
            ContentResolver var23 = SyncAlbums.this.mContext.getContentResolver();
            long var24 = SyncAlbums.this.mUserId;
            byte var30 = 0;
            SyncAlbums.syncAlbums(var23, var24, var10, (boolean)var30);
            List var31 = SyncAlbums.this.mAlbums;
            var31.addAll(var10);
            long var34 = 65535L;
            if((long)SyncAlbums.this.mAlbums.size() < 70L) {
               var34 = 20L;
            }

            Context var36 = SyncAlbums.this.mContext;
            Intent var37 = SyncAlbums.this.mIntent;
            String var38 = SyncAlbums.this.mSessionKey;
            long var39 = SyncAlbums.this.mUserId;
            String[] var41 = SyncAlbums.this.mAlbumIds;
            long var42 = (long)SyncAlbums.this.mAlbums.size();
            (new FqlGetAlbums(var36, var37, var38, var39, var41, this, var42, var34)).start();
         } else {
            byte var13 = 1;
            this.mLastBatch = (boolean)var13;
            if(SyncAlbums.this.mAllQueriesSuccess) {
               ContentResolver var14 = SyncAlbums.this.mContext.getContentResolver();
               long var15 = SyncAlbums.this.mUserId;
               List var17 = SyncAlbums.this.mAlbums;
               SyncAlbums.syncAlbums(var14, var15, var17, (boolean)1);
            }

            ApiMethodListener var18 = SyncAlbums.this.mListener;
            var18.onProcessComplete(var1, var2, var3, var4);
         }
      }
   }

   static class 1 implements Factory<Cursor> {

      // $FF: synthetic field
      final List val$albums;
      // $FF: synthetic field
      final ContentResolver val$resolver;
      // $FF: synthetic field
      final long val$userId;


      1(long var1, ContentResolver var3, List var4) {
         this.val$userId = var1;
         this.val$resolver = var3;
         this.val$albums = var4;
      }

      public Cursor make() {
         Cursor var3;
         if(SyncAlbums.updatingSpecificAlbums(this.val$userId)) {
            ContentResolver var1 = this.val$resolver;
            List var2 = this.val$albums;
            var3 = AlbumSyncModel.cursorForAlbums(var1, var2);
         } else {
            ContentResolver var4 = this.val$resolver;
            long var5 = this.val$userId;
            var3 = AlbumSyncModel.cursorForAlbumsForUser(var4, var5);
         }

         return var3;
      }
   }
}
