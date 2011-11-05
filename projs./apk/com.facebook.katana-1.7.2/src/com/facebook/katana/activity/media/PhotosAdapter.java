package com.facebook.katana.activity.media;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.ImageUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PhotosAdapter extends CursorAdapter {

   private final String mAlbumId;
   private final AppSession mAppSession;
   private final Context mContext;
   private final Map<String, String> mDownloadPending;
   private final List<ViewHolder<String>> mViewHolders;


   public PhotosAdapter(Context var1, String var2, long var3, AppSession var5) {
      Activity var6 = (Activity)var1;
      Uri var7 = PhotosProvider.PHOTOS_CONTENT_URI;
      String[] var8 = PhotosAdapter.PhotosQuery.PROJECTION;
      StringBuilder var9 = (new StringBuilder()).append("aid=");
      String var10 = DatabaseUtils.sqlEscapeString(var2);
      String var11 = var9.append(var10).append(" AND ").append("owner").append("=").append(var3).toString();
      Object var12 = null;
      Cursor var13 = var6.managedQuery(var7, var8, var11, (String[])null, (String)var12);
      super(var1, var13, (boolean)1);
      this.mContext = var1;
      this.mAlbumId = var2;
      this.mAppSession = var5;
      HashMap var14 = new HashMap();
      this.mDownloadPending = var14;
      ArrayList var15 = new ArrayList();
      this.mViewHolders = var15;
   }

   public void bindView(View var1, Context var2, Cursor var3) {
      String var4 = var3.getString(1);
      ViewHolder var5 = (ViewHolder)var1.getTag();
      var5.setItemId(var4);
      byte[] var6 = var3.getBlob(3);
      if(var6 != null) {
         int var7 = var6.length;
         Bitmap var8 = ImageUtils.decodeByteArray(var6, 0, var7);
         if(var8 != null) {
            var5.mImageView.setImageBitmap(var8);
         }
      } else {
         String var9 = var3.getString(2);
         if(var9 != null) {
            var5.mImageView.setImageResource(2130837760);
            if(!this.mDownloadPending.containsKey(var4)) {
               AppSession var10 = this.mAppSession;
               Context var11 = this.mContext;
               String var12 = this.mAlbumId;
               var10.downloadPhotoThumbail(var11, var12, var4, var9);
               this.mDownloadPending.put(var4, var9);
            }
         } else {
            var5.mImageView.setImageResource(2130837759);
         }
      }
   }

   public View newView(Context var1, Cursor var2, ViewGroup var3) {
      View var4 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903123, (ViewGroup)null);
      ViewHolder var5 = new ViewHolder(var4, 2131624165);
      var4.setTag(var5);
      this.mViewHolders.add(var5);
      return var4;
   }

   protected void onContentChanged() {
      super.onContentChanged();
   }

   public void onDownloadPhotoError(String var1) {
      Iterator var2 = this.mViewHolders.iterator();

      while(var2.hasNext()) {
         ViewHolder var3 = (ViewHolder)var2.next();
         Object var4 = var3.getItemId();
         if(var1.equals(var4)) {
            var3.mImageView.setImageResource(2130837759);
            return;
         }
      }

   }

   public interface PhotosQuery {

      int INDEX_PHOTO_ID = 1;
      int INDEX_SRC = 2;
      int INDEX_THUMBNAIL = 3;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "pid", "src", "thumbnail"};
         PROJECTION = var0;
      }
   }
}
