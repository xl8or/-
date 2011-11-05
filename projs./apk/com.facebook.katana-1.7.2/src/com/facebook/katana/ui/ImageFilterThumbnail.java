package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.katana.features.imagefilters.ImageFilter;

public class ImageFilterThumbnail extends RelativeLayout {

   private ImageFilter mFilter;
   private ImageView mImageView;
   private TextView mTextView;


   public ImageFilterThumbnail(Context var1, ImageFilter var2) {
      super(var1);
      this.mFilter = var2;
      View var3 = LayoutInflater.from(var1).inflate(2130903076, this);
      ImageView var4 = (ImageView)this.findViewById(2131624056);
      this.mImageView = var4;
      TextView var5 = (TextView)this.findViewById(2131624057);
      this.mTextView = var5;
      TextView var6 = this.mTextView;
      String var7 = this.mFilter.getName();
      var6.setText(var7);
      this.setBackgroundColor(0);
   }

   public void setThumbnailImage(Bitmap var1) {
      ImageView var2 = this.mImageView;
      Bitmap var3 = this.mFilter.applyFilter(var1);
      var2.setImageBitmap(var3);
   }
}
