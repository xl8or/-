package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import com.google.android.finsky.utils.Maps;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DiskBasedCache implements Cache {

   private static final int CACHE_VERSION = 1;
   private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
   private static final float HYSTERESIS_FACTOR = 0.9F;
   private final Map<String, DiskBasedCache.CacheHeader> mEntries;
   private final int mMaxCacheSizeInBytes;
   private final File mRootDirectory;
   private long mTotalSize;


   public DiskBasedCache(File var1) {
      this(var1, 5242880);
   }

   public DiskBasedCache(File var1, int var2) {
      LinkedHashMap var3 = Maps.newLinkedHashMap((boolean)1);
      this.mEntries = var3;
      this.mTotalSize = 0L;
      this.mRootDirectory = var1;
      this.mMaxCacheSizeInBytes = var2;
   }

   private File getFileForKey(String var1) {
      File var2 = this.mRootDirectory;
      String var3 = this.getFilenameForKey(var1);
      return new File(var2, var3);
   }

   private String getFilenameForKey(String var1) {
      int var2 = var1.length() / 2;
      String var3 = String.valueOf(var1.substring(0, var2).hashCode());
      StringBuilder var4 = (new StringBuilder()).append(var3);
      String var5 = String.valueOf(var1.substring(var2).hashCode());
      return var4.append(var5).toString();
   }

   private void pruneIfNeeded(int var1) {
      long var2 = this.mTotalSize;
      long var4 = (long)var1;
      long var6 = var2 + var4;
      long var8 = (long)this.mMaxCacheSizeInBytes;
      if(var6 >= var8) {
         if(VolleyLog.DEBUG) {
            Object[] var10 = new Object[0];
            VolleyLog.v("Pruning old cache entries.", var10);
         }

         long var11 = this.mTotalSize;
         int var13 = 0;
         long var14 = System.currentTimeMillis();
         Iterator var16 = this.mEntries.entrySet().iterator();

         while(var16.hasNext()) {
            DiskBasedCache.CacheHeader var17 = (DiskBasedCache.CacheHeader)((Entry)var16.next()).getValue();
            String var18 = var17.key;
            if(this.getFileForKey(var18).delete()) {
               long var19 = this.mTotalSize;
               long var21 = var17.size;
               long var23 = var19 - var21;
               this.mTotalSize = var23;
            } else {
               Object[] var35 = new Object[2];
               String var36 = var17.key;
               var35[0] = var36;
               String var37 = var17.key;
               String var38 = this.getFilenameForKey(var37);
               var35[1] = var38;
               VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", var35);
            }

            var16.remove();
            ++var13;
            long var25 = this.mTotalSize;
            long var27 = (long)var1;
            float var29 = (float)(var25 + var27);
            float var30 = (float)this.mMaxCacheSizeInBytes * 0.9F;
            if(var29 < var30) {
               break;
            }
         }

         if(VolleyLog.DEBUG) {
            Object[] var31 = new Object[3];
            Integer var32 = Integer.valueOf(var13);
            var31[0] = var32;
            Long var33 = Long.valueOf(this.mTotalSize - var11);
            var31[1] = var33;
            Long var34 = Long.valueOf(System.currentTimeMillis() - var14);
            var31[2] = var34;
            VolleyLog.v("pruned %d files, %d bytes, %d ms", var31);
         }
      }
   }

   private void putEntry(String var1, DiskBasedCache.CacheHeader var2) {
      if(!this.mEntries.containsKey(var1)) {
         long var3 = this.mTotalSize;
         long var5 = var2.size;
         long var7 = var3 + var5;
         this.mTotalSize = var7;
      } else {
         DiskBasedCache.CacheHeader var10 = (DiskBasedCache.CacheHeader)this.mEntries.get(var1);
         long var11 = this.mTotalSize;
         long var13 = var2.size;
         long var15 = var10.size;
         long var17 = var13 - var15;
         long var19 = var11 + var17;
         this.mTotalSize = var19;
      }

      this.mEntries.put(var1, var2);
   }

   private void removeEntry(String var1) {
      DiskBasedCache.CacheHeader var2 = (DiskBasedCache.CacheHeader)this.mEntries.get(var1);
      if(var2 != null) {
         long var3 = this.mTotalSize;
         long var5 = var2.size;
         long var7 = var3 - var5;
         this.mTotalSize = var7;
         this.mEntries.remove(var1);
      }
   }

   private static byte[] streamToBytes(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[1024];

      while(true) {
         int var3 = var0.read(var2);
         if(var3 == -1) {
            byte[] var4 = var1.toByteArray();
            var1.close();
            return var4;
         }

         var1.write(var2, 0, var3);
      }
   }

   public void clear() {
      synchronized(this){}

      try {
         File[] var1 = this.mRootDirectory.listFiles();
         if(var1 != null) {
            File[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               boolean var5 = var2[var4].delete();
            }
         }

         this.mEntries.clear();
         this.mTotalSize = 0L;
         Object[] var6 = new Object[0];
         VolleyLog.d("Cache cleared.", var6);
      } finally {
         ;
      }

   }

   public Cache.Entry get(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void initialize() {
      // $FF: Couldn't be decompiled
   }

   public void invalidate(String var1, boolean var2) {
      synchronized(this){}

      try {
         Cache.Entry var3 = this.get(var1);
         if(var3 != null) {
            var3.softTtl = 0L;
            if(var2) {
               var3.ttl = 0L;
            }

            this.put(var1, var3);
         }
      } finally {
         ;
      }

   }

   public void put(String param1, Cache.Entry param2) {
      // $FF: Couldn't be decompiled
   }

   public void remove(String var1) {
      synchronized(this){}

      try {
         boolean var2 = this.getFileForKey(var1).delete();
         this.removeEntry(var1);
         if(!var2) {
            Object[] var3 = new Object[]{var1, null};
            String var4 = this.getFilenameForKey(var1);
            var3[1] = var4;
            VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", var3);
         }
      } finally {
         ;
      }

   }

   private static class CacheHeader {

      public String etag;
      public String key;
      public long serverDate;
      public long size;
      public long softTtl;
      public long ttl;


      private CacheHeader() {}

      public CacheHeader(String var1, Cache.Entry var2) {
         this.key = var1;
         long var3 = (long)var2.data.length;
         this.size = var3;
         String var5 = var2.etag;
         this.etag = var5;
         long var6 = var2.serverDate;
         this.serverDate = var6;
         long var8 = var2.ttl;
         this.ttl = var8;
         long var10 = var2.softTtl;
         this.softTtl = var10;
      }

      public static DiskBasedCache.CacheHeader readHeader(InputStream var0) throws IOException {
         DiskBasedCache.CacheHeader var1 = new DiskBasedCache.CacheHeader();
         ObjectInputStream var2 = new ObjectInputStream(var0);
         if(var2.readByte() != 1) {
            throw new IOException();
         } else {
            String var3 = var2.readUTF();
            var1.key = var3;
            String var4 = var2.readUTF();
            var1.etag = var4;
            if(var1.etag.equals("")) {
               var1.etag = null;
            }

            long var5 = var2.readLong();
            var1.serverDate = var5;
            long var7 = var2.readLong();
            var1.ttl = var7;
            long var9 = var2.readLong();
            var1.softTtl = var9;
            return var1;
         }
      }

      public Cache.Entry toCacheEntry(byte[] var1) {
         Cache.Entry var2 = new Cache.Entry();
         var2.data = var1;
         String var3 = this.etag;
         var2.etag = var3;
         long var4 = this.serverDate;
         var2.serverDate = var4;
         long var6 = this.ttl;
         var2.ttl = var6;
         long var8 = this.softTtl;
         var2.softTtl = var8;
         return var2;
      }

      public boolean writeHeader(OutputStream param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
