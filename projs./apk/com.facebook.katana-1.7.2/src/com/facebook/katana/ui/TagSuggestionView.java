package com.facebook.katana.ui;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class TagSuggestionView extends LinearLayout implements OnTouchListener {

   private static final float FACE_HEIGHT_FACTOR = 2.5F;
   private static final float FACE_WIDTH_FACTOR = 1.8F;
   private float mDistance;
   private int mImageSize;
   private Button mSuggestionBtn;
   private Button mTextBtn;
   private float x;
   private float y;


   public TagSuggestionView(Context var1, AttributeSet var2) {
      super(var1, var2);
      View var3 = ((LayoutInflater)var1.getSystemService("layout_inflater")).inflate(2130903171, this);
      this.setPadding(0, 0, 0, 0);
      this.setBackgroundColor(0);
      Button var4 = (Button)this.findViewById(2131624259);
      this.mTextBtn = var4;
      Button var5 = (Button)this.findViewById(2131624258);
      this.mSuggestionBtn = var5;
      this.mTextBtn.setOnTouchListener(this);
      this.mSuggestionBtn.setOnTouchListener(this);
   }

   public int getFaceBoxHeight() {
      float var1 = (float)this.mImageSize;
      float var2 = this.mDistance;
      return (int)(var1 * var2 * 2.5F);
   }

   public int getFaceBoxWidth() {
      float var1 = (float)this.mImageSize;
      float var2 = this.mDistance;
      return (int)(var1 * var2 * 1.8F);
   }

   public int getFullHeight() {
      float var1 = -this.mTextBtn.getPaint().ascent();
      float var2 = this.mTextBtn.getPaint().descent();
      float var3 = (float)this.mTextBtn.getCompoundPaddingTop();
      float var4 = (float)this.mTextBtn.getCompoundPaddingBottom();
      float var5 = var1 + var2 + var3 + var4;
      float var6 = (float)this.getFaceBoxHeight();
      return (int)(var5 + var6);
   }

   public int getFullWidth() {
      TextPaint var1 = this.mTextBtn.getPaint();
      String var2 = this.mTextBtn.getText().toString();
      float var3 = var1.measureText(var2);
      int var4 = this.mTextBtn.getCompoundPaddingLeft();
      int var5 = this.mTextBtn.getCompoundPaddingRight();
      int var6 = (int)var3 + var4 + var5;
      int var7 = this.getFaceBoxWidth();
      return Math.max(var6, var7);
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      switch(var2.getAction()) {
      case 0:
         this.mTextBtn.setPressed((boolean)1);
         this.mSuggestionBtn.setPressed((boolean)1);
         break;
      case 1:
      case 3:
         this.mTextBtn.setPressed((boolean)0);
         this.mSuggestionBtn.setPressed((boolean)0);
      case 2:
      }

      return this.onTouchEvent(var2);
   }

   public void setEyeDistance(float var1) {
      this.mDistance = var1;
      this.updateSize();
   }

   public void setImageSize(int var1) {
      this.mImageSize = var1;
      this.updateSize();
   }

   public void setX(float var1) {
      this.x = var1;
   }

   public void setY(float var1) {
      this.y = var1;
   }

   public void updateSize() {
      Button var1 = this.mSuggestionBtn;
      int var2 = this.getFaceBoxWidth();
      var1.setWidth(var2);
      Button var3 = this.mSuggestionBtn;
      int var4 = this.getFaceBoxHeight();
      var3.setHeight(var4);
   }
}
