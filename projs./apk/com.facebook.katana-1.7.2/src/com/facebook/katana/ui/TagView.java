package com.facebook.katana.ui;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.Button;

public class TagView extends Button {

   public long userId;
   public float x;
   public float y;


   public TagView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public int getFullHeight() {
      float var1 = -this.getPaint().ascent();
      float var2 = this.getPaint().descent();
      float var3 = (float)this.getCompoundPaddingTop();
      float var4 = (float)this.getCompoundPaddingBottom();
      return (int)(var1 + var2 + var3 + var4);
   }

   public int getFullWidth() {
      TextPaint var1 = this.getPaint();
      String var2 = this.getText().toString();
      float var3 = var1.measureText(var2);
      int var4 = this.getCompoundPaddingLeft();
      int var5 = this.getCompoundPaddingRight();
      float var6 = (float)var4 + var3;
      float var7 = (float)var5;
      return (int)(var6 + var7);
   }
}
