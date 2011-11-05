package com.facebook.katana.activity.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.ui.ImageViewTouchBase;
import java.util.ArrayList;
import java.util.Map;

public class PhotoGalleryAdapter extends CursorAdapter {

   protected static final String PHOTO_GALLERY_OOM_ERROR = "photo_gallery_oom_error";
   private String mAlbumId;
   private AppSession mAppSession;
   private Context mContext;
   private LayoutInflater mInflater;
   private Map<String, String> mPendingDownloadMap;
   private Bitmap mPhotoDownloadingBitmap;
   private ArrayList<PhotoGalleryAdapter.ViewHolder> mViewHolders;


   public PhotoGalleryAdapter(Context var1, Cursor var2, AppSession var3, Map<String, String> var4, String var5) {
      super(var1, var2);
      this.mContext = var1;
      this.mPendingDownloadMap = var4;
      this.mAlbumId = var5;
      this.mAppSession = var3;
      LayoutInflater var6 = LayoutInflater.from(var1);
      this.mInflater = var6;
      ArrayList var7 = new ArrayList();
      this.mViewHolders = var7;
      Bitmap var8 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837760);
      this.mPhotoDownloadingBitmap = var8;
   }

   private void setPhotoCaption(TextView var1, String var2) {
      if(var2 != null && var2.length() != 0) {
         var1.setVisibility(0);
         var1.setText(var2);
      } else {
         var1.setVisibility(4);
      }
   }

   public void bindView(View param1, Context param2, Cursor param3) {
      // $FF: Couldn't be decompiled
   }

   public Object getItem(int var1) {
      Cursor var2 = this.getCursor();
      var2.moveToPosition(var1);
      return var2.getString(1);
   }

   public View newView(Context var1, Cursor var2, ViewGroup var3) {
      View var4 = this.mInflater.inflate(2130903122, (ViewGroup)null);
      PhotoGalleryAdapter.ViewHolder var5 = new PhotoGalleryAdapter.ViewHolder((PhotoGalleryAdapter.1)null);
      ImageViewTouchBase var6 = (ImageViewTouchBase)var4.findViewById(2131623981);
      var5.mImageView = var6;
      ProgressBar var7 = (ProgressBar)var4.findViewById(2131624163);
      var5.mProgressBar = var7;
      TextView var8 = (TextView)var4.findViewById(2131624164);
      var5.mCaption = var8;
      var4.setTag(var5);
      this.mViewHolders.add(var5);
      return var4;
   }

   public void requery() {
      Cursor var1 = this.getCursor();
      if(var1 != null) {
         boolean var2 = var1.requery();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class ViewHolder {

      TextView mCaption;
      ImageViewTouchBase mImageView;
      ProgressBar mProgressBar;


      private ViewHolder() {}

      // $FF: synthetic method
      ViewHolder(PhotoGalleryAdapter.1 var1) {
         this();
      }
   }
}
