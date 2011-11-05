package com.android.email;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.email.R;
import com.sec.android.touchwiz.widget.TwSoftkeyItem;

public class EmailTwSoftkeyItem extends TwSoftkeyItem {

   public static final float FONT_SIZE = 24.0F;
   public static final int IMAGE_LEFT = 1;
   public static final int IMAGE_NONE = 0;
   public static final int IMAGE_RIGHT = 2;
   private Drawable mItemIcon;
   private int mItemIconAlign = 1;
   private String mItemText = "";
   private TextView mTextImageButton;


   public EmailTwSoftkeyItem(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public EmailTwSoftkeyItem(Context var1, AttributeSet var2) {
      super(var1, var2);
      int[] var3 = R.styleable.EmailTwSoftkeyItem;
      TypedArray var4 = var1.obtainStyledAttributes(var2, var3);
      String var5 = var4.getString(1);
      this.mItemText = var5;
      Drawable var6 = var4.getDrawable(2);
      this.mItemIcon = var6;
      int var7 = var4.getInt(0, 0);
      this.mItemIconAlign = var7;
      this.init(var1, var2);
      var4.recycle();
   }

   private void init(Context var1, AttributeSet var2) {
      this.setClickable((boolean)1);
      this.setBackgroundResource(33685607);
      Resources var3 = this.getResources();
      TextView var4 = new TextView(var1, (AttributeSet)null, 33619971);
      this.mTextImageButton = var4;
      TextView var5 = this.mTextImageButton;
      String var6 = this.mItemText;
      var5.setText(var6);
      TextView var7 = this.mTextImageButton;
      int[] var8 = new int[5];
      int[] var9 = new int[]{16842919};
      var8[0] = (int)var9;
      int[] var10 = new int[]{16842908};
      var8[1] = (int)var10;
      int[] var11 = new int[]{16842913, 16842908};
      var8[2] = (int)var11;
      int[] var12 = new int[]{16842910};
      var8[3] = (int)var12;
      int[] var13 = new int[1];
      var8[4] = (int)var13;
      int[] var14 = new int[]{-1, -1, -1, -16777216, 0};
      int var15 = var3.getColor(2131427559);
      var14[4] = var15;
      ColorStateList var16 = new ColorStateList((int[][])var8, var14);
      var7.setTextColor(var16);
      this.mTextImageButton.setCompoundDrawablePadding(0);
      this.mTextImageButton.setClickable((boolean)0);
      this.mTextImageButton.setDuplicateParentStateEnabled((boolean)1);
      TextView var17 = this.mTextImageButton;
      float var18 = (float)var3.getInteger(2131230872);
      var17.setTextSize(0, var18);
      this.mTextImageButton.setGravity(17);
      if(this.mItemIconAlign == 1) {
         TextView var19 = this.mTextImageButton;
         Drawable var20 = this.mItemIcon;
         var19.setCompoundDrawablesWithIntrinsicBounds(var20, (Drawable)null, (Drawable)null, (Drawable)null);
         this.mTextImageButton.setPadding(1, 0, 1, 0);
      } else if(this.mItemIconAlign == 2) {
         TextView var22 = this.mTextImageButton;
         Drawable var23 = this.mItemIcon;
         var22.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var23, (Drawable)null);
         this.mTextImageButton.setPadding(1, 0, 1, 0);
      }

      TextView var21 = this.mTextImageButton;
      this.addView(var21);
   }

   public void setCompoundDrawables(boolean var1) {
      Drawable var2 = this.getResources().getDrawable(2130837923);
      Drawable var3 = this.getResources().getDrawable(2130837924);
      if(var1) {
         this.mTextImageButton.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var2, (Drawable)null);
      } else {
         this.mTextImageButton.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var3, (Drawable)null);
      }
   }

   public void setEmailTwSoftkeyITemTextMaxLine(int var1) {
      if(this.mTextImageButton != null) {
         this.mTextImageButton.setMaxLines(var1);
      }
   }

   public void setEmailTwSoftkeyItemImage(Drawable var1) {
      if(this.mItemIconAlign == 1) {
         this.mTextImageButton.setCompoundDrawablesWithIntrinsicBounds(var1, (Drawable)null, (Drawable)null, (Drawable)null);
         this.mTextImageButton.setPadding(1, 0, 1, 0);
      } else if(this.mItemIconAlign == 2) {
         this.mTextImageButton.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var1, (Drawable)null);
         this.mTextImageButton.setPadding(1, 0, 1, 0);
      }
   }

   public void setEmailTwSoftkeyItemText(String var1) {
      this.mTextImageButton.setText(var1);
   }

   public void setEmailTwSoftkeyItemTextSize(int var1) {
      TextView var2 = this.mTextImageButton;
      float var3 = (float)var1;
      var2.setTextSize(var3);
   }
}
