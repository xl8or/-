package com.facebook.katana.activity.media;

import com.facebook.katana.ViewHolder;

public class UserHolder {

   private String mDisplayName;
   private String mImageURL;
   private long mUid;
   private ViewHolder<Long> mViewHolder;


   public UserHolder() {}

   public String getDisplayName() {
      return this.mDisplayName;
   }

   public String getImageURL() {
      return this.mImageURL;
   }

   public long getUid() {
      return this.mUid;
   }

   public ViewHolder<Long> getViewHolder() {
      return this.mViewHolder;
   }

   public void setDisplayName(String var1) {
      this.mDisplayName = var1;
   }

   public void setImageURL(String var1) {
      this.mImageURL = var1;
   }

   public void setUid(long var1) {
      this.mUid = var1;
   }

   public void setViewHolder(ViewHolder<Long> var1) {
      this.mViewHolder = var1;
   }
}
