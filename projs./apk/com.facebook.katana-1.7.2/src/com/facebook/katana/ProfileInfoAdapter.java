package com.facebook.katana;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class ProfileInfoAdapter extends BaseAdapter {

   protected final Context mContext;
   protected final List<ProfileInfoAdapter.Item> mItems;
   private Bitmap mNoAvatarBitmap;
   protected final StreamPhotosCache mPhotosContainer;
   protected final boolean mShowProfilePhoto;


   public ProfileInfoAdapter(Context var1, StreamPhotosCache var2, boolean var3) {
      this.mContext = var1;
      this.mPhotosContainer = var2;
      ArrayList var4 = new ArrayList();
      this.mItems = var4;
      this.mShowProfilePhoto = var3;
   }

   private Bitmap getNoAvatarImage() {
      if(this.mNoAvatarBitmap == null) {
         Bitmap var1 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837747);
         this.mNoAvatarBitmap = var1;
      }

      return this.mNoAvatarBitmap;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public Object getItem(int var1) {
      return Integer.valueOf(var1);
   }

   public ProfileInfoAdapter.Item getItemByPosition(int var1) {
      return (ProfileInfoAdapter.Item)this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      ProfileInfoAdapter.Item var4 = (ProfileInfoAdapter.Item)this.mItems.get(var1);
      View var6;
      if(var2 == null) {
         LayoutInflater var5 = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
         switch(var4.mType) {
         case 0:
            var6 = var5.inflate(2130903140, (ViewGroup)null);
            break;
         case 1:
         default:
            var6 = var5.inflate(2130903096, (ViewGroup)null);
            break;
         case 2:
         case 3:
         case 4:
            var6 = var5.inflate(2130903095, (ViewGroup)null);
         }
      } else {
         var6 = var2;
      }

      LinearLayout var7;
      Bitmap var11;
      switch(var4.mType) {
      case 0:
         var7 = (LinearLayout)var6.findViewById(2131624197);
         if(var4.getUrl() != null) {
            StreamPhotosCache var8 = this.mPhotosContainer;
            Context var9 = this.mContext;
            String var10 = var4.getUrl();
            var11 = var8.get(var9, var10);
            if(var11 != null) {
               ImageUtils.formatPhotoStreamImageView(this.mContext, var7, var11);
            } else {
               Context var16 = this.mContext;
               Bitmap var17 = this.getNoAvatarImage();
               ImageUtils.formatPhotoStreamImageView(var16, var7, var17);
            }
         } else {
            Context var18 = this.mContext;
            Bitmap var19 = this.getNoAvatarImage();
            ImageUtils.formatPhotoStreamImageView(var18, var7, var19);
         }

         TextView var12 = (TextView)var6.findViewById(2131624192);
         String var13 = var4.getTitle();
         var12.setText(var13);
         TextView var14 = (TextView)var6.findViewById(2131624198);
         if(var4.getSubTitle() != null) {
            var14.setVisibility(0);
            String var15 = var4.getSubTitle();
            var14.setText(var15);
         } else {
            var14.setVisibility(8);
         }
         break;
      case 1:
      case 5:
         TextView var34 = (TextView)var6.findViewById(2131624101);
         String var35 = var4.getTitle();
         var34.setText(var35);
         TextView var36 = (TextView)var6.findViewById(2131624102);
         String var37 = var4.getSubTitle();
         var36.setText(var37);
         var36.setAutoLinkMask(1);
         MovementMethod var38 = LinkMovementMethod.getInstance();
         var36.setMovementMethod(var38);
         break;
      case 2:
      case 3:
         TextView var20 = (TextView)var6.findViewById(2131624102);
         ImageView var21 = (ImageView)var6.findViewById(2131624104);
         var21.setVisibility(0);
         TextView var22 = (TextView)var6.findViewById(2131624101);
         String var23 = var4.getTitle();
         var22.setText(var23);
         String var24 = var4.getSubTitle();
         var20.setText(var24);
         if(var4.mType == 3) {
            var21.setImageResource(2130837798);
         } else {
            var21.setImageResource(2130837797);
         }
         break;
      case 4:
         TextView var25 = (TextView)var6.findViewById(2131624102);
         TextView var26 = (TextView)var6.findViewById(2131624101);
         String var27 = var4.getTitle();
         var26.setText(var27);
         String var28 = var4.getSubTitle();
         var25.setText(var28);
         if(var4.getUrl() != null) {
            StreamPhotosCache var29 = this.mPhotosContainer;
            Context var30 = this.mContext;
            String var31 = var4.getUrl();
            var11 = var29.get(var30, var31);
            var7 = (LinearLayout)var6.findViewById(2131624103);
            if(var11 != null) {
               ImageUtils.formatPhotoStreamImageView(this.mContext, var7, var11);
            } else {
               Context var32 = this.mContext;
               Bitmap var33 = this.getNoAvatarImage();
               ImageUtils.formatPhotoStreamImageView(var32, var7, var33);
            }
         }
      }

      return var6;
   }

   public void updatePhoto() {
      this.notifyDataSetChanged();
   }

   protected static class Item {

      public static final int TYPE_EMAIL = 3;
      public static final int TYPE_INFO = 1;
      public static final int TYPE_PHONE = 2;
      public static final int TYPE_PROFILE_PHOTO = 0;
      public static final int TYPE_SIGNIFICANT_OTHER = 4;
      public static final int TYPE_URL = 5;
      final String mSubTitle;
      final long mTargetId;
      final String mTitle;
      final int mType;
      final String mUrl;


      public Item(int var1) {
         this.mType = var1;
         this.mTitle = null;
         this.mSubTitle = null;
         this.mUrl = null;
         this.mTargetId = 65535L;
      }

      public Item(int var1, String var2, String var3) {
         this.mType = var1;
         this.mTitle = var2;
         this.mSubTitle = var3;
         this.mUrl = null;
         this.mTargetId = 65535L;
      }

      public Item(int var1, String var2, String var3, String var4) {
         this.mType = var1;
         this.mTitle = var2;
         this.mSubTitle = var3;
         this.mUrl = var4;
         this.mTargetId = 65535L;
      }

      public Item(int var1, String var2, String var3, String var4, long var5) {
         this.mType = var1;
         this.mTitle = var2;
         this.mSubTitle = var3;
         this.mUrl = var4;
         this.mTargetId = var5;
      }

      public String getSubTitle() {
         return this.mSubTitle;
      }

      public long getTargetId() {
         return this.mTargetId;
      }

      public String getTitle() {
         return this.mTitle;
      }

      public int getType() {
         return this.mType;
      }

      public String getUrl() {
         return this.mUrl;
      }
   }
}
