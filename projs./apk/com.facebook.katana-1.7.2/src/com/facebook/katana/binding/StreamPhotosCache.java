package com.facebook.katana.binding;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.binding.StreamPhoto;
import com.facebook.katana.binding.WorkerThread;
import com.facebook.katana.provider.PhotosProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StreamPhotosCache {

   private static final long MAX_CACHE_ENTRIES = 100L;
   private static final long MAX_CACHE_SIZE = 500000L;
   public static final int TYPE_STREAM_PHOTO = 1;
   public static final int TYPE_STREAM_PROFILE_PHOTO = 2;
   private final List<StreamPhoto> mCacheList;
   private long mCacheSize;
   private WorkerThread mDecoderThread;
   private final StreamPhotosCache.PhotosContainerListener mListener;
   private final Map<String, String> mPendingDownload;
   private final Map<String, StreamPhoto> mPhotos;


   protected StreamPhotosCache(StreamPhotosCache.PhotosContainerListener var1) {
      HashMap var2 = new HashMap();
      this.mPhotos = var2;
      ArrayList var3 = new ArrayList();
      this.mCacheList = var3;
      HashMap var4 = new HashMap();
      this.mPendingDownload = var4;
      this.mListener = var1;
   }

   public static Map<String, StreamPhoto> getPhotos(Context var0) {
      HashMap var1 = new HashMap();
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = PhotosProvider.STREAM_PHOTOS_CONTENT_URI;
      String[] var4 = StreamPhotosCache.StreamPhotoQuery.PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var2.query(var3, var4, (String)null, (String[])var5, (String)var6);
      if(var7 != null) {
         if(var7.moveToFirst()) {
            do {
               Uri var8 = PhotosProvider.STREAM_PHOTOS_CONTENT_URI;
               StringBuilder var9 = (new StringBuilder()).append("");
               int var10 = var7.getInt(0);
               String var11 = var9.append(var10).toString();
               Uri var12 = Uri.withAppendedPath(var8, var11);
               String var13 = var7.getString(2);
               File var14 = new File(var13);
               if(var14.exists()) {
                  String var15 = var7.getString(1);
                  long var16 = var14.length();
                  Object var18 = null;
                  StreamPhoto var19 = new StreamPhoto(var12, var15, var13, var16, (Bitmap)var18);
                  var1.put(var15, var19);
               } else {
                  int var21 = var0.getContentResolver().delete(var12, (String)null, (String[])null);
               }
            } while(var7.moveToNext());
         }

         var7.close();
      }

      return var1;
   }

   private void makeRoom(Context var1, long var2) {
      List var4 = this.mCacheList;
      Collection var5 = this.mPhotos.values();
      var4.addAll(var5);
      List var7 = this.mCacheList;
      StreamPhotosCache.1 var8 = new StreamPhotosCache.1();
      Collections.sort(var7, var8);
      Iterator var9 = this.mCacheList.iterator();

      while(var9.hasNext()) {
         StreamPhoto var10 = (StreamPhoto)var9.next();
         Map var11 = this.mPhotos;
         String var12 = var10.getUrl();
         var11.remove(var12);
         ContentResolver var14 = var1.getContentResolver();
         Uri var15 = var10.getContentUri();
         var14.delete(var15, (String)null, (String[])null);
         long var17 = this.mCacheSize;
         long var19 = var10.getLength();
         long var21 = var17 - var19;
         this.mCacheSize = var21;
         if(this.mCacheSize <= var2 && (long)this.mPhotos.size() < 100L) {
            break;
         }
      }

      this.mCacheList.clear();
   }

   protected void close() {
      this.mPhotos.clear();
      this.mPendingDownload.clear();
      this.mDecoderThread = null;
   }

   public void decode(StreamPhoto var1, StreamPhotosCache.PhotosContainerListener var2) {
      Handler var3 = this.mDecoderThread.getThreadHandler();
      StreamPhotosCache.2 var4 = new StreamPhotosCache.2(var1, var2);
      var3.post(var4);
   }

   public Bitmap get(Context var1, String var2) {
      return this.get(var1, var2, 1);
   }

   public Bitmap get(Context var1, String var2, int var3) {
      Bitmap var4 = null;
      StreamPhoto var5 = (StreamPhoto)this.mPhotos.get(var2);
      if(var5 != null) {
         var5.incrUsageCount();
         var4 = var5.getBitmap();
         if(var4 == null) {
            StreamPhotosCache.PhotosContainerListener var6 = this.mListener;
            this.decode(var5, var6);
         }
      } else if(!this.mPendingDownload.containsKey(var2)) {
         this.mListener.onPhotoRequested(var1, var2, var3);
         this.mPendingDownload.put(var2, var2);
      }

      return var4;
   }

   protected StreamPhoto onDownloadComplete(Context var1, int var2, String var3, StreamPhoto var4) {
      if(var2 == 200) {
         long var5 = var4.getLength();
         if(var5 < 500000L) {
            if(this.mCacheSize + var5 > 500000L || (long)this.mPhotos.size() >= 100L) {
               long var7 = 500000L - var5;
               this.makeRoom(var1, var7);
            }

            Map var9 = this.mPhotos;
            String var10 = var4.getUrl();
            var9.put(var10, var4);
            long var12 = this.mCacheSize + var5;
            this.mCacheSize = var12;
         }
      }

      this.mPendingDownload.remove(var3);
      return var4;
   }

   protected void open(Context var1, WorkerThread var2) {
      Map var3 = this.mPhotos;
      Map var4 = getPhotos(var1);
      var3.putAll(var4);

      long var10;
      for(Iterator var5 = this.mPhotos.values().iterator(); var5.hasNext(); this.mCacheSize = var10) {
         long var6 = this.mCacheSize;
         long var8 = ((StreamPhoto)var5.next()).getLength();
         var10 = var6 + var8;
      }

      this.mDecoderThread = var2;
   }

   protected interface PhotosContainerListener {

      void onPhotoDecoded(Bitmap var1, String var2);

      void onPhotoRequested(Context var1, String var2, int var3);
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final StreamPhotosCache.PhotosContainerListener val$listener;
      // $FF: synthetic field
      final StreamPhoto val$photo;


      2(StreamPhoto var2, StreamPhotosCache.PhotosContainerListener var3) {
         this.val$photo = var2;
         this.val$listener = var3;
      }

      public void run() {
         StreamPhoto var1 = this.val$photo;
         Bitmap var2 = BitmapFactory.decodeFile(this.val$photo.getFilename());
         var1.setBitmap(var2);
         if(this.val$photo.getBitmap() != null) {
            Handler var3 = StreamPhotosCache.this.mDecoderThread.getHandler();
            StreamPhotosCache.2.1 var4 = new StreamPhotosCache.2.1();
            var3.post(var4);
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            StreamPhotosCache.PhotosContainerListener var1 = 2.this.val$listener;
            Bitmap var2 = 2.this.val$photo.getBitmap();
            String var3 = 2.this.val$photo.getUrl();
            var1.onPhotoDecoded(var2, var3);
         }
      }
   }

   class 1 implements Comparator<StreamPhoto> {

      1() {}

      public int compare(StreamPhoto var1, StreamPhoto var2) {
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

   private interface StreamPhotoQuery {

      int INDEX_FILENAME = 2;
      int INDEX_ID = 0;
      int INDEX_URL = 1;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "url", "filename"};
         PROJECTION = var0;
      }
   }
}
