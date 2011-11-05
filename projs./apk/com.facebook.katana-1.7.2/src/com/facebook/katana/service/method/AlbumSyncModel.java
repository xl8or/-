package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.Factory;
import com.facebook.katana.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AlbumSyncModel {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private static boolean DEBUG;


   static {
      byte var0;
      if(!AlbumSyncModel.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      DEBUG = (boolean)0;
   }

   public AlbumSyncModel() {}

   private static void D(String var0) {
      if(DEBUG) {
         Log.d("AlbumSyncModel", var0);
      }
   }

   public static void assignCoversToAlbums(Collection<FacebookPhoto> var0, Collection<FacebookAlbum> var1) {
      HashMap var2 = new HashMap();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         FacebookPhoto var4 = (FacebookPhoto)var3.next();
         String var5 = var4.getPhotoId();
         var2.put(var5, var4);
      }

      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         FacebookAlbum var8 = (FacebookAlbum)var7.next();
         String var9 = var8.getCoverPhotoId();
         FacebookPhoto var10 = (FacebookPhoto)var2.get(var9);
         if(var10 != null) {
            String var11 = var10.getSrcSmall();
            var8.setCoverPhotoUrl(var11);
         }
      }

   }

   private static void commit(ContentResolver var0, Collection<FacebookAlbum> var1, Collection<FacebookAlbum> var2, Collection<String> var3, long var4) {
      Uri var8;
      if(65535L != var4) {
         Uri var6 = PhotosProvider.ALBUMS_OWNER_CONTENT_URI;
         String var7 = Long.toString(var4);
         var8 = Uri.withAppendedPath(var6, var7);
      } else {
         var8 = null;
      }

      Iterator var11;
      if(var1.size() > 0) {
         ContentValues[] var9 = new ContentValues[var1.size()];
         Iterator var10 = var1.iterator();
         var11 = null;

         while(var10.hasNext()) {
            FacebookAlbum var12 = (FacebookAlbum)var10.next();
            ContentValues var13 = new ContentValues();
            var9[var11] = var13;
            ++var11;
            String var14 = var12.getAlbumId();
            var13.put("aid", var14);
            String var15 = var12.getCoverPhotoId();
            var13.put("cover_pid", var15);
            String var16 = var12.getCoverPhotoUrl();
            var13.put("cover_url", var16);
            Long var17 = Long.valueOf(var12.getOwner());
            var13.put("owner", var17);
            String var18 = var12.getName();
            var13.put("name", var18);
            Long var19 = Long.valueOf(var12.getCreated());
            var13.put("created", var19);
            Long var20 = Long.valueOf(var12.getModified());
            var13.put("modified", var20);
            String var21 = var12.getDescription();
            var13.put("description", var21);
            String var22 = var12.getLocation();
            var13.put("location", var22);
            Integer var23 = Integer.valueOf(var12.getSize());
            var13.put("size", var23);
            String var24 = var12.getVisibility();
            var13.put("visibility", var24);
            String var25 = var12.getType();
            var13.put("type", var25);
            Long var26 = Long.valueOf(var12.getObjectId());
            var13.put("object_id", var26);
         }

         Uri var27 = PhotosProvider.ALBUMS_CONTENT_URI;
         var0.bulkInsert(var27, var9);
      }

      Iterator var30;
      if(var2.size() > 0) {
         ContentValues var29 = new ContentValues();
         var30 = var2.iterator();

         while(var30.hasNext()) {
            FacebookAlbum var31 = (FacebookAlbum)var30.next();
            var29.clear();
            String var32 = var31.getCoverPhotoId();
            var29.put("cover_pid", var32);
            String var33 = var31.getCoverPhotoUrl();
            var29.put("cover_url", var33);
            String var34 = var31.getName();
            var29.put("name", var34);
            Long var35 = Long.valueOf(var31.getCreated());
            var29.put("created", var35);
            Long var36 = Long.valueOf(var31.getModified());
            var29.put("modified", var36);
            String var37 = var31.getDescription();
            var29.put("description", var37);
            String var38 = var31.getLocation();
            var29.put("location", var38);
            Integer var39 = Integer.valueOf(var31.getSize());
            var29.put("size", var39);
            String var40 = var31.getVisibility();
            var29.put("visibility", var40);
            String var41 = var31.getType();
            var29.put("type", var41);
            if(var31.hasCoverChanged()) {
               byte[] var42 = (byte[])false;
               var29.put("thumbnail", var42);
            }

            if(var8 == null) {
               Uri var43 = PhotosProvider.ALBUMS_OWNER_CONTENT_URI;
               long var44 = var31.getOwner();
               ContentUris.withAppendedId(var43, var44);
            }

            String[] var47 = new String[1];
            String var48 = var31.getAlbumId();
            var47[0] = var48;
            var0.update(var8, var29, "aid IN(?)", var47);
         }
      }

      if(var3.size() > 0) {
         StringBuilder var50 = new StringBuilder(128);
         StringBuilder var51 = var50.append("aid").append(" IN(");
         var11 = var3.iterator();

         StringBuilder var54;
         String var53;
         for(boolean var52 = true; var11.hasNext(); var54 = var50.append('\'').append(var53).append('\'')) {
            var53 = (String)var11.next();
            if(var52) {
               var52 = false;
            } else {
               StringBuilder var55 = var50.append(',');
            }
         }

         StringBuilder var56 = var50.append(')');
         if(!$assertionsDisabled && var8 == null) {
            throw new AssertionError();
         } else {
            String var57 = var50.toString();
            var0.delete(var8, var57, (String[])null);
            StringBuilder var59 = new StringBuilder(128);
            StringBuilder var60 = var59.append("aid").append(" IN(");
            var30 = var3.iterator();

            StringBuilder var63;
            String var62;
            for(boolean var61 = true; var30.hasNext(); var63 = var59.append('\'').append(var62).append('\'')) {
               var62 = (String)var30.next();
               if(var61) {
                  var61 = false;
               } else {
                  StringBuilder var64 = var59.append(',');
               }
            }

            StringBuilder var65 = var59.append(')');
            Uri var66 = PhotosProvider.PHOTOS_CONTENT_URI;
            String var67 = var59.toString();
            var0.delete(var66, var67, (String[])null);
         }
      }
   }

   public static Cursor cursorForAlbums(ContentResolver var0, List<FacebookAlbum> var1) {
      StringBuilder var2 = new StringBuilder(128);
      StringBuilder var3 = var2.append("aid").append(" IN(");
      boolean var4 = true;

      StringBuilder var10;
      for(Iterator var5 = var1.iterator(); var5.hasNext(); var10 = var2.append('\'')) {
         FacebookAlbum var6 = (FacebookAlbum)var5.next();
         if(var4) {
            var4 = false;
         } else {
            StringBuilder var11 = var2.append(',');
         }

         StringBuilder var7 = var2.append('\'');
         String var8 = var6.getAlbumId();
         var2.append(var8);
      }

      StringBuilder var12 = var2.append(')');
      Uri var13 = PhotosProvider.ALBUMS_CONTENT_URI;
      String[] var14 = AlbumSyncModel.AlbumsQuery.PROJECTION;
      String var15 = var2.toString();
      Object var17 = null;
      return var0.query(var13, var14, var15, (String[])null, (String)var17);
   }

   public static Cursor cursorForAlbumsForUser(ContentResolver var0, long var1) {
      Uri var3 = PhotosProvider.ALBUMS_OWNER_CONTENT_URI;
      String var4 = "" + var1;
      Uri var5 = Uri.withAppendedPath(var3, var4);
      String[] var6 = AlbumSyncModel.AlbumsQuery.PROJECTION;
      Object var8 = null;
      Object var9 = null;
      return var0.query(var5, var6, (String)null, (String[])var8, (String)var9);
   }

   public static AlbumSyncModel.Result doSync(ContentResolver var0, List<FacebookAlbum> var1, Factory<Cursor> var2, boolean var3, boolean var4, boolean var5, boolean var6, long var7) {
      synchronized(AlbumSyncModel.class){}

      AlbumSyncModel.Result var80;
      try {
         ArrayList var9 = new ArrayList();
         ArrayList var10 = new ArrayList();
         ArrayList var11 = new ArrayList();
         ArrayList var12 = new ArrayList();
         StringBuilder var13 = new StringBuilder();
         String var14 = "received ";
         StringBuilder var15 = var13.append(var14);
         int var16 = var1.size();
         StringBuilder var19 = var15.append(var16);
         String var20 = " albums";
         D(var19.append(var20).toString());
         HashMap var85 = new HashMap();
         Iterator var21 = var1.iterator();

         while(true) {
            if(!var21.hasNext()) {
               Cursor var29 = (Cursor)var2.make();
               FacebookAlbum var81;
               if(var29.moveToFirst()) {
                  do {
                     byte var31 = 0;
                     String var83 = var29.getString(var31);
                     var81 = (FacebookAlbum)var85.get(var83);
                     if(var81 == null) {
                        if(var5) {
                           boolean var36 = var11.add(var83);
                        }
                     } else {
                        long var53 = var81.getModified();
                        long var55 = var29.getLong(1);
                        if(var53 != var55) {
                           String var86 = var81.getCoverPhotoUrl();
                           String var57 = var29.getString(2);
                           if(var86 == null && var57 == null) {
                              byte var59 = 0;
                              var81.setCoverChanged((boolean)var59);
                           } else if(var86 != null && var57 != null) {
                              if(!var86.equals(var57)) {
                                 var21 = null;
                              } else {
                                 var21 = null;
                              }

                              var81.setCoverChanged((boolean)var21);
                           } else {
                              byte var66 = 1;
                              var81.setCoverChanged((boolean)var66);
                           }

                           boolean var62 = var10.add(var81);
                        }
                     }

                     Object var39 = var85.remove(var83);
                  } while(var29.moveToNext());
               }

               var29.close();
               if(var3) {
                  Collection var40 = var85.values();
                  boolean var43 = var9.addAll(var40);
               }

               StringBuilder var44 = (new StringBuilder()).append("found ");
               int var45 = var9.size();
               StringBuilder var46 = var44.append(var45).append(" albums to add, ");
               int var47 = var10.size();
               StringBuilder var48 = var46.append(var47).append(" albums to update, and ");
               int var49 = var11.size();
               D(var48.append(var49).append(" albums to delete").toString());
               if(var6) {
                  Iterator var84 = var9.iterator();

                  String var82;
                  while(var84.hasNext()) {
                     var82 = ((FacebookAlbum)var84.next()).getCoverPhotoId();
                     if(var82 != null) {
                        boolean var52 = var12.add(var82);
                     }
                  }

                  var84 = var10.iterator();

                  while(var84.hasNext()) {
                     var81 = (FacebookAlbum)var84.next();
                     if(var81.hasCoverChanged()) {
                        var82 = var81.getCoverPhotoId();
                        if(var82 != null) {
                           boolean var69 = var12.add(var82);
                        }
                     }
                  }

                  if(var12.size() == 0) {
                     D("no covers missing");
                     commit(var0, var9, var10, var11, var7);
                     var80 = new AlbumSyncModel.Result((boolean)1);
                  } else {
                     StringBuilder var73 = (new StringBuilder()).append("missing ");
                     int var74 = var12.size();
                     D(var73.append(var74).append(" covers").toString());
                     var80 = new AlbumSyncModel.Result(var12);
                  }
               } else {
                  commit(var0, var9, var10, var11, var7);
                  var80 = new AlbumSyncModel.Result((boolean)1);
               }
               break;
            }

            FacebookAlbum var22 = (FacebookAlbum)var21.next();
            String var23 = var22.getAlbumId();
            Object var27 = var85.put(var23, var22);
         }
      } finally {
         ;
      }

      return var80;
   }

   public static class Result {

      public final boolean done;
      public final Collection<String> missingCovers;


      Result(Collection<String> var1) {
         this.done = (boolean)0;
         this.missingCovers = var1;
      }

      Result(boolean var1) {
         this.done = var1;
         this.missingCovers = null;
      }
   }

   private interface AlbumsQuery {

      int INDEX_ALBUM_ID = 0;
      int INDEX_COVER_PHOTO_URL = 2;
      int INDEX_MODIFIED = 1;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"aid", "modified", "cover_url"};
         PROJECTION = var0;
      }
   }
}
