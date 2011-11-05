package com.facebook.katana.binding;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.WorkerThread;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProfileImagesCache {

   private static final long MAX_CACHE_ENTRIES = 75L;
   private final List<ProfileImage> mCacheList;
   private final ProfileImagesCache.ProfileImagesContainerListener mListener;
   private final Map<Long, ProfileImage> mLoadPendingMap;
   private WorkerThread mLoaderThread;
   private final Map<Long, String> mPendingDownload;
   private final Map<Long, ProfileImage> mProfileImages;


   protected ProfileImagesCache(ProfileImagesCache.ProfileImagesContainerListener var1) {
      HashMap var2 = new HashMap();
      this.mProfileImages = var2;
      ArrayList var3 = new ArrayList();
      this.mCacheList = var3;
      HashMap var4 = new HashMap();
      this.mPendingDownload = var4;
      HashMap var5 = new HashMap();
      this.mLoadPendingMap = var5;
      this.mListener = var1;
   }

   private static Bitmap getProfileImageBitmap(Context var0, long var1) {
      Bitmap var3 = null;
      Uri var4 = ConnectionsProvider.CONNECTION_ID_CONTENT_URI;
      String var5 = String.valueOf(var1);
      Uri var6 = Uri.withAppendedPath(var4, var5);
      ContentResolver var7 = var0.getContentResolver();
      String[] var8 = ProfileImagesCache.ImagesQuery.PROJECTION;
      Object var9 = null;
      Object var10 = null;
      Cursor var11 = var7.query(var6, var8, (String)null, (String[])var9, (String)var10);
      if(var11 != null) {
         if(var11.moveToFirst()) {
            byte[] var12 = var11.getBlob(0);
            if(var12 != null) {
               int var13 = var12.length;
               var3 = ImageUtils.decodeByteArray(var12, 0, var13);
            }
         }

         var11.close();
      }

      return var3;
   }

   private static String getProfileImageURL(Context var0, long var1) {
      String var3 = null;
      Uri var4 = ConnectionsProvider.CONNECTION_ID_CONTENT_URI;
      String var5 = String.valueOf(var1);
      Uri var6 = Uri.withAppendedPath(var4, var5);
      ContentResolver var7 = var0.getContentResolver();
      String[] var8 = ProfileImagesCache.ImagesURLQuery.PROJECTION_URL;
      Object var9 = null;
      Object var10 = null;
      Cursor var11 = var7.query(var6, var8, (String)null, (String[])var9, (String)var10);
      if(var11 != null) {
         if(var11.moveToFirst()) {
            var3 = var11.getString(0);
         }

         var11.close();
      }

      return var3;
   }

   private void load(Context var1, ProfileImage var2, ProfileImagesCache.ProfileImagesContainerListener var3) {
      long var4 = var2.id;
      Map var6 = this.mLoadPendingMap;
      Long var7 = Long.valueOf(var4);
      if(!var6.containsKey(var7)) {
         Map var8 = this.mLoadPendingMap;
         Long var9 = Long.valueOf(var4);
         var8.put(var9, var2);
         Handler var11 = this.mLoaderThread.getThreadHandler();
         ProfileImagesCache.3 var16 = new ProfileImagesCache.3(var1, var4, var2, var3);
         var11.post(var16);
      }
   }

   private void loadKnownURL(Context var1, long var2, ProfileImagesCache.ProfileImagesContainerListener var4) {
      Handler var5 = this.mLoaderThread.getThreadHandler();
      ProfileImagesCache.2 var6 = new ProfileImagesCache.2(var1, var2);
      var5.post(var6);
   }

   private void makeRoom() {
      List var1 = this.mCacheList;
      Collection var2 = this.mProfileImages.values();
      var1.addAll(var2);
      List var4 = this.mCacheList;
      ProfileImagesCache.1 var5 = new ProfileImagesCache.1();
      Collections.sort(var4, var5);
      int var6 = Math.min(this.mCacheList.size(), 5);

      for(int var7 = 0; var7 < var6; ++var7) {
         ProfileImage var8 = (ProfileImage)this.mCacheList.get(var7);
         Map var9 = this.mProfileImages;
         Long var10 = Long.valueOf(var8.id);
         var9.remove(var10);
      }

      this.mCacheList.clear();
   }

   protected void close() {
      this.mProfileImages.clear();
      this.mPendingDownload.clear();
      this.mLoadPendingMap.clear();
   }

   public Bitmap get(Context var1, long var2, String var4) {
      Bitmap var5 = null;
      Map var6 = this.mProfileImages;
      Long var7 = Long.valueOf(var2);
      ProfileImage var8 = (ProfileImage)var6.get(var7);
      if(var4 != null && var4.length() > 0) {
         if(var8 != null) {
            if(var8.url.equals(var4)) {
               var5 = var8.getBitmap();
               if(var5 == null) {
                  ProfileImagesCache.ProfileImagesContainerListener var9 = this.mListener;
                  this.load(var1, var8, var9);
               } else {
                  var8.incrUsageCount();
               }
            } else {
               Map var10 = this.mPendingDownload;
               Long var11 = Long.valueOf(var2);
               if(!var10.containsKey(var11)) {
                  this.mListener.onProfileImageDownload(var1, var2, var4);
                  Map var12 = this.mPendingDownload;
                  Long var13 = Long.valueOf(var2);
                  var12.put(var13, var4);
               }
            }
         } else {
            ProfileImage var15 = new ProfileImage(var2, var4, (Bitmap)null);
            ProfileImagesCache.ProfileImagesContainerListener var16 = this.mListener;
            this.load(var1, var15, var16);
         }
      } else if(var8 != null) {
         Map var17 = this.mProfileImages;
         Long var18 = Long.valueOf(var2);
         var17.remove(var18);
      }

      return var5;
   }

   public void get(Context var1, Map<Long, String> var2) {
      this.mPendingDownload.putAll(var2);
      Iterator var3 = var2.keySet().iterator();

      while(var3.hasNext()) {
         Long var4 = (Long)var3.next();
         ProfileImagesCache.ProfileImagesContainerListener var5 = this.mListener;
         long var6 = var4.longValue();
         String var8 = (String)var2.get(var4);
         var5.onProfileImageDownload(var1, var6, var8);
      }

   }

   public Bitmap getWithoutURL(Context var1, long var2) {
      Bitmap var4 = null;
      Map var5 = this.mProfileImages;
      Long var6 = Long.valueOf(var2);
      ProfileImage var7 = (ProfileImage)var5.get(var6);
      if(var7 != null) {
         var4 = var7.getBitmap();
         if(var4 == null) {
            ProfileImagesCache.ProfileImagesContainerListener var8 = this.mListener;
            this.load(var1, var7, var8);
         } else {
            var7.incrUsageCount();
         }
      } else {
         ProfileImagesCache.ProfileImagesContainerListener var9 = this.mListener;
         this.loadKnownURL(var1, var2, var9);
      }

      return var4;
   }

   protected ProfileImage onDownloadComplete(Context var1, int var2, long var3, ProfileImage var5) {
      if(var2 == 200) {
         if((long)this.mProfileImages.size() >= 75L) {
            this.makeRoom();
         }

         Map var6 = this.mProfileImages;
         Long var7 = Long.valueOf(var3);
         var6.put(var7, var5);
      }

      Map var9 = this.mPendingDownload;
      Long var10 = Long.valueOf(var3);
      var9.remove(var10);
      return var5;
   }

   protected void open(Context var1, WorkerThread var2) {
      this.mLoaderThread = var2;
   }

   private interface ImagesURLQuery {

      int INDEX_URL;
      String[] PROJECTION_URL;


      static {
         String[] var0 = new String[]{"user_image_url"};
         PROJECTION_URL = var0;
      }
   }

   class 1 implements Comparator<ProfileImage> {

      1() {}

      public int compare(ProfileImage var1, ProfileImage var2) {
         int var3 = var1.getUsageCount();
         int var4 = var2.getUsageCount();
         byte var5;
         if(var3 > var4) {
            var5 = 1;
         } else {
            int var6 = var1.getUsageCount();
            int var7 = var2.getUsageCount();
            if(var6 == var7) {
               var5 = 0;
            } else {
               var5 = -1;
            }
         }

         return var5;
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final long val$uid;


      2(Context var2, long var3) {
         this.val$context = var2;
         this.val$uid = var3;
      }

      public void run() {
         Context var1 = this.val$context;
         long var2 = this.val$uid;
         String var4 = ProfileImagesCache.getProfileImageURL(var1, var2);
         if(var4 == null) {
            StringBuilder var5 = (new StringBuilder()).append("loadKnownURL: did not get url for uid=");
            long var6 = this.val$uid;
            String var8 = var5.append(var6).toString();
            Log.i("ProfileImageCache", var8);
         } else {
            ProfileImagesCache var9 = ProfileImagesCache.this;
            Context var10 = this.val$context;
            long var11 = this.val$uid;
            ProfileImage var13 = new ProfileImage(var11, var4, (Bitmap)null);
            ProfileImagesCache.ProfileImagesContainerListener var14 = ProfileImagesCache.this.mListener;
            var9.load(var10, var13, var14);
         }
      }
   }

   protected interface ProfileImagesContainerListener {

      void onProfileImageDownload(Context var1, long var2, String var4);

      void onProfileImageLoaded(Context var1, ProfileImage var2);
   }

   private interface ImagesQuery {

      int INDEX_IMAGE;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"user_image"};
         PROJECTION = var0;
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final ProfileImagesCache.ProfileImagesContainerListener val$listener;
      // $FF: synthetic field
      final long val$profileId;
      // $FF: synthetic field
      final ProfileImage val$profileImage;


      3(Context var2, long var3, ProfileImage var5, ProfileImagesCache.ProfileImagesContainerListener var6) {
         this.val$context = var2;
         this.val$profileId = var3;
         this.val$profileImage = var5;
         this.val$listener = var6;
      }

      public void run() {
         Context var1 = this.val$context;
         long var2 = this.val$profileId;
         Bitmap var4 = ProfileImagesCache.getProfileImageBitmap(var1, var2);
         this.val$profileImage.setBitmap(var4);
         Handler var5 = ProfileImagesCache.this.mLoaderThread.getHandler();
         ProfileImagesCache.3.1 var6 = new ProfileImagesCache.3.1();
         var5.post(var6);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Map var1 = ProfileImagesCache.this.mLoadPendingMap;
            Long var2 = Long.valueOf(3.this.val$profileId);
            var1.remove(var2);
            if(3.this.val$profileImage.getBitmap() != null) {
               3.this.val$profileImage.incrUsageCount();
               if((long)ProfileImagesCache.this.mProfileImages.size() >= 75L) {
                  ProfileImagesCache.this.makeRoom();
               }

               Map var4 = ProfileImagesCache.this.mProfileImages;
               Long var5 = Long.valueOf(3.this.val$profileId);
               ProfileImage var6 = 3.this.val$profileImage;
               var4.put(var5, var6);
               ProfileImagesCache.ProfileImagesContainerListener var8 = 3.this.val$listener;
               Context var9 = 3.this.val$context;
               ProfileImage var10 = 3.this.val$profileImage;
               var8.onProfileImageLoaded(var9, var10);
            } else {
               Map var11 = ProfileImagesCache.this.mPendingDownload;
               Long var12 = Long.valueOf(3.this.val$profileId);
               if(!var11.containsKey(var12)) {
                  ProfileImagesCache.ProfileImagesContainerListener var13 = ProfileImagesCache.this.mListener;
                  Context var14 = 3.this.val$context;
                  long var15 = 3.this.val$profileId;
                  String var17 = 3.this.val$profileImage.url;
                  var13.onProfileImageDownload(var14, var15, var17);
                  Map var18 = ProfileImagesCache.this.mPendingDownload;
                  Long var19 = Long.valueOf(3.this.val$profileId);
                  String var20 = 3.this.val$profileImage.url;
                  var18.put(var19, var20);
               }
            }
         }
      }
   }
}
