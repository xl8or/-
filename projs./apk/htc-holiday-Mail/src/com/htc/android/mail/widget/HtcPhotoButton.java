package com.htc.android.mail.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class HtcPhotoButton extends RelativeLayout {

   private static final String TAG = "HtcPhotoButton";
   private Context mContext;
   private RelativeLayout mLayout;
   private RelativeLayout mLeft;
   OnClickListener mOnClickListener = null;
   OnLongClickListener mOnLongClickListener = null;
   private int mPaddingBottom;
   private int mPaddingLeft;
   private int mPaddingRight;
   private int mPaddingTop;
   private ImageView mPhoto;
   private TextView mText;


   public HtcPhotoButton(Context var1) {
      super(var1);
      this.mContext = var1;

      try {
         this.setLayout();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   public HtcPhotoButton(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;

      try {
         this.setLayout();
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   private void initView() {
      Resources var1 = this.getResources();
      RelativeLayout var2 = (RelativeLayout)this.findViewById(2131296523);
      this.mLayout = var2;
      RelativeLayout var3 = (RelativeLayout)this.findViewById(2131296524);
      this.mLeft = var3;
      ImageView var4 = (ImageView)this.findViewById(2131296525);
      this.mPhoto = var4;
      TextView var5 = (TextView)this.findViewById(2131296526);
      this.mText = var5;
      int var6 = this.mText.getPaddingLeft();
      this.mPaddingLeft = var6;
      int var7 = this.mText.getPaddingRight();
      this.mPaddingRight = var7;
      int var8 = this.mText.getPaddingTop();
      this.mPaddingTop = var8;
      int var9 = this.mText.getPaddingBottom();
      this.mPaddingBottom = var9;
      this.setImageResource(-1);
   }

   private void setLayout() throws Exception {
      this.removeAllViews();
      ViewGroup var1 = (ViewGroup)LayoutInflater.from(this.mContext).inflate(2130903078, (ViewGroup)null);
      if(var1 != null) {
         this.addView(var1);
         this.initView();
      } else {
         throw new Exception("no theme resource");
      }
   }

   private void setLayout(int var1) throws Exception {
      this.removeAllViews();
      ViewGroup var2 = (ViewGroup)LayoutInflater.from(this.mContext).inflate(2130903078, (ViewGroup)null);
      if(var2 != null) {
         this.addView(var2);
         this.initView();
      } else {
         throw new Exception("no theme resource");
      }
   }

   public int getImageWidth() {
      return this.mPhoto.getMeasuredWidth();
   }

   public Object getTag() {
      Object var1;
      if(this.mLayout.getTag() != null) {
         var1 = this.mLayout.getTag();
      } else {
         var1 = super.getTag();
      }

      return var1;
   }

   public CharSequence getText() {
      return this.mText.getText();
   }

   public TextView getTextView() {
      return this.mText;
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      switch(var1.getAction()) {
      case 0:
         if(this.mPhoto != null) {
            this.mPhoto.setPressed((boolean)1);
            this.mPhoto.invalidate();
         }
         break;
      case 1:
      case 3:
         if(this.mPhoto != null) {
            this.mPhoto.setPressed((boolean)0);
            this.mPhoto.invalidate();
         }
      case 2:
      }

      return super.onInterceptTouchEvent(var1);
   }

   public void setBtnBackgroundResource(Drawable var1) {
      ((RelativeLayout)this.findViewById(2131296523)).setBackgroundDrawable(var1);
      TextView var2 = this.mText;
      int var3 = this.mPaddingTop + 6;
      int var4 = this.mPaddingBottom;
      var2.setPadding(0, var3, 0, var4);
   }

   public void setImageBitmap(Bitmap var1) {
      if(this.mPhoto != null && var1 != null) {
         this.mPhoto.setImageBitmap(var1);
         this.mLeft.setVisibility(0);
         TextView var2 = this.mText;
         int var3 = this.mPaddingTop;
         int var4 = this.mPaddingRight;
         int var5 = this.mPaddingBottom;
         var2.setPadding(0, var3, var4, var5);
      } else {
         this.mLeft.setVisibility(8);
         TextView var6 = this.mText;
         int var7 = this.mPaddingLeft;
         int var8 = this.mPaddingTop;
         int var9 = this.mPaddingRight;
         int var10 = this.mPaddingBottom;
         var6.setPadding(var7, var8, var9, var10);
      }
   }

   public void setImageDrawable(Drawable var1) {
      if(this.mPhoto != null && var1 != null) {
         this.mPhoto.setImageDrawable(var1);
         this.mLeft.setVisibility(0);
         TextView var2 = this.mText;
         int var3 = this.mPaddingTop;
         int var4 = this.mPaddingRight;
         int var5 = this.mPaddingBottom;
         var2.setPadding(0, var3, var4, var5);
      } else {
         this.mLeft.setVisibility(8);
         TextView var6 = this.mText;
         int var7 = this.mPaddingLeft;
         int var8 = this.mPaddingTop;
         int var9 = this.mPaddingRight;
         int var10 = this.mPaddingBottom;
         var6.setPadding(var7, var8, var9, var10);
      }
   }

   public void setImageResource(int var1) {
      if(this.mPhoto != null && var1 >= 0) {
         this.mPhoto.setImageResource(var1);
         this.mLeft.setVisibility(0);
         TextView var2 = this.mText;
         int var3 = this.mPaddingTop;
         int var4 = this.mPaddingRight;
         int var5 = this.mPaddingBottom;
         var2.setPadding(0, var3, var4, var5);
      } else {
         this.mLeft.setVisibility(8);
         TextView var6 = this.mText;
         int var7 = this.mPaddingLeft;
         int var8 = this.mPaddingTop;
         int var9 = this.mPaddingRight;
         int var10 = this.mPaddingBottom;
         var6.setPadding(var7, var8, var9, var10);
      }
   }

   public void setImageURI(Uri var1) {
      if(this.mPhoto != null && var1 != null) {
         this.mPhoto.setImageURI(var1);
         this.mLeft.setVisibility(0);
         TextView var2 = this.mText;
         int var3 = this.mPaddingTop;
         int var4 = this.mPaddingRight;
         int var5 = this.mPaddingBottom;
         var2.setPadding(0, var3, var4, var5);
      } else {
         this.mLeft.setVisibility(8);
         TextView var6 = this.mText;
         int var7 = this.mPaddingLeft;
         int var8 = this.mPaddingTop;
         int var9 = this.mPaddingRight;
         int var10 = this.mPaddingBottom;
         var6.setPadding(var7, var8, var9, var10);
      }
   }

   public void setOnClickListener(OnClickListener var1) {
      this.mOnClickListener = var1;
      this.mLayout.setOnClickListener(var1);
   }

   public void setOnLongClickListener(OnLongClickListener var1) {
      this.mOnLongClickListener = var1;
      this.mLayout.setOnLongClickListener(var1);
   }

   public void setTag(Object var1) {
      this.mLayout.setTag(var1);
      super.setTag(var1);
   }

   public void setText(int var1) {
      if(this.mText != null) {
         this.mText.setText(var1);
      }
   }

   public void setText(CharSequence var1) {
      if(this.mText != null) {
         this.mText.setText(var1);
      }
   }

   public void setWidth(int var1) {
      LayoutParams var2 = (LayoutParams)this.mLayout.getLayoutParams();
      var2.width = var1;
      this.mLayout.setLayoutParams(var2);
   }
}
