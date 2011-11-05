package com.facebook.katana.activity.media;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends CursorAdapter {

   private final AppSession mAppSession;
   private final Context mContext;
   private final long mOwner;
   private final List<ViewHolder<String>> mViewHolders;


   public AlbumsAdapter(Context var1, Uri var2, String var3, AppSession var4) {
      Activity var5 = (Activity)var1;
      String[] var6 = AlbumsAdapter.AlbumsQuery.PROJECTION;
      Object var9 = null;
      Cursor var10 = var5.managedQuery(var2, var6, var3, (String[])null, (String)var9);
      super(var1, var10, (boolean)1);
      this.mContext = var1;
      this.mAppSession = var4;
      long var11 = Long.valueOf((String)var2.getPathSegments().get(2)).longValue();
      this.mOwner = var11;
      ArrayList var13 = new ArrayList();
      this.mViewHolders = var13;
   }

   public void bindView(View var1, Context var2, Cursor var3) {
      byte var5 = 1;
      String var6 = var3.getString(var5);
      ViewHolder var7 = (ViewHolder)var1.getTag();
      var7.setItemId(var6);
      byte var9 = 4;
      byte[] var10 = var3.getBlob(var9);
      if(var10 != null) {
         int var11 = var10.length;
         Bitmap var12 = ImageUtils.decodeByteArray(var10, 0, var11);
         if(var12 != null) {
            var7.mImageView.setImageBitmap(var12);
         }
      } else {
         byte var27 = 3;
         String var28 = var3.getString(var27);
         if(var28 != null) {
            var7.mImageView.setImageResource(2130837760);
            byte var30 = 2;
            String var31 = var3.getString(var30);
            AppSession var32 = this.mAppSession;
            Context var33 = this.mContext;
            long var34 = this.mOwner;
            var32.downloadAlbumThumbail(var33, var34, var6, var31, var28);
         } else {
            var7.mImageView.setImageResource(2130837760);
         }
      }

      int var14 = 2131623941;
      TextView var15 = (TextView)var1.findViewById(var14);
      byte var17 = 6;
      String var18 = var3.getString(var17);
      var15.setText(var18);
      int var20 = 2131623948;
      TextView var21 = (TextView)var1.findViewById(var20);
      byte var23 = 5;
      int var24 = var3.getInt(var23);
      String var25;
      if(var24 == 0) {
         var25 = this.mContext.getString(2131361812);
      } else if(var24 == 1) {
         var25 = this.mContext.getString(2131361813);
      } else {
         Context var37 = this.mContext;
         Object[] var38 = new Object[1];
         Integer var39 = Integer.valueOf(var24);
         var38[0] = var39;
         var25 = var37.getString(2131361814, var38);
      }

      var21.setText(var25);
   }

   public String getAlbumId(Cursor var1) {
      return var1.getString(1);
   }

   public String getAlbumName(Cursor var1) {
      return var1.getString(6);
   }

   public int getAlbumSize(Cursor var1) {
      return var1.getInt(5);
   }

   public View newView(Context var1, Cursor var2, ViewGroup var3) {
      View var4 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903042, (ViewGroup)null);
      ViewHolder var5 = new ViewHolder(var4, 2131623940);
      var4.setTag(var5);
      this.mViewHolders.add(var5);
      return var4;
   }

   private interface AlbumsQuery {

      int INDEX_ALBUM_ID = 1;
      int INDEX_COVER_PHOTO_ID = 2;
      int INDEX_COVER_PHOTO_URL = 3;
      int INDEX_COVER_THUMBNAIL = 4;
      int INDEX_NAME = 6;
      int INDEX_SIZE = 5;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "aid", "cover_pid", "cover_url", "thumbnail", "size", "name"};
         PROJECTION = var0;
      }
   }
}
