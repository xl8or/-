package com.android.email;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Shader.TileMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.android.email.ToolTipItem;
import java.util.Vector;

class ToolTip extends View {

   static final boolean DEBUG = false;
   static final int INVALID_ITEM = 255;
   static final int ITEM_PADDING = 15;
   static final String LOGTAG = "ToolTip";
   static final float ROUND_DIAMETER = 10.0F;
   static final int SEPARATOR_MARGIN = 3;
   static final int STROKE_WIDTH = 1;
   Path mBgPath;
   LinearGradient mBgShader;
   int mBodyHeight;
   int mBodyWidth;
   final Context mContext;
   int mDownItem;
   boolean mIsDown;
   Vector mItems;
   Path mSeparatorB;
   Path mSeparatorW;
   View mSrcView;
   int mTailHeight;
   int mTailWidth;
   Paint mTextPaint;
   int mViewHeight;
   int mViewWidth;
   WindowManager mWindowManager;
   boolean mbDrawUpsideDown;
   boolean mbShow;


   ToolTip(View var1) {
      Context var2 = var1.getContext();
      super(var2);
      Vector var3 = new Vector();
      this.mItems = var3;
      this.mbShow = (boolean)0;
      this.mTailWidth = 20;
      this.mTailHeight = 15;
      this.mIsDown = (boolean)0;
      this.mDownItem = -1;
      this.mbDrawUpsideDown = (boolean)0;
      this.mSrcView = var1;
      Context var4 = this.mSrcView.getContext();
      this.mContext = var4;
      this.setPadding(0, 0, 0, 0);
      WindowManager var5 = (WindowManager)this.mContext.getSystemService("window");
      this.mWindowManager = var5;
      Path var6 = new Path();
      this.mBgPath = var6;
      Path var7 = new Path();
      this.mSeparatorB = var7;
      Path var8 = new Path();
      this.mSeparatorW = var8;
      Paint var9 = new Paint();
      this.mTextPaint = var9;
      this.mTextPaint.setTextSize(30.0F);
      this.mTextPaint.setColor(-1);
      this.mTextPaint.setAntiAlias((boolean)1);
   }

   void addItem(ToolTipItem var1) {
      this.mItems.add(var1);
   }

   int findItem(int var1, int var2) {
      int var3 = 0;
      Rect var4 = new Rect();
      var4.left = 0;
      var4.top = 0;
      int var5 = this.mViewHeight;
      var4.bottom = var5;
      int var6 = 0;

      int var10;
      while(true) {
         int var7 = this.mItems.size();
         if(var6 >= var7) {
            var10 = -1;
            break;
         }

         ToolTipItem var8 = (ToolTipItem)this.mItems.get(var6);
         int var9 = var8.bounds.width() + var3;
         var4.right = var9;
         if(var4.contains(var1, var2)) {
            var10 = var6;
            break;
         }

         int var11 = var8.bounds.width();
         var3 += var11;
         ++var6;
      }

      return var10;
   }

   int getTooltipHeight() {
      this.layout(0, 0);
      return this.mBodyHeight;
   }

   void hide() {
      if(this.mbShow) {
         this.mWindowManager.removeView(this);
         this.mbShow = (boolean)0;
      }
   }

   void layout(int var1, int var2) {
      this.mBodyWidth = 0;
      this.mBodyHeight = 0;
      int var3 = 0;

      while(true) {
         int var4 = this.mItems.size();
         if(var3 >= var4) {
            int var16 = this.mBodyWidth;
            this.mViewWidth = var16;
            int var17 = this.mBodyHeight;
            int var18 = this.mTailHeight;
            int var19 = var17 + var18;
            this.mViewHeight = var19;
            float var20 = (float)this.mViewHeight;
            int[] var21 = new int[]{-7566196, -11579569, -14737633};
            float[] var22 = new float[]{(float)false, (float)1056964608, (float)1065353216};
            TileMode var23 = TileMode.REPEAT;
            float var24 = 0.0F;
            float var25 = 0.0F;
            LinearGradient var26 = new LinearGradient(0.0F, var24, var25, var20, var21, var22, var23);
            this.mBgShader = var26;
            return;
         }

         ToolTipItem var5 = (ToolTipItem)this.mItems.get(var3);
         Paint var6 = this.mTextPaint;
         String var7 = var5.mstrText;
         int var8 = var5.mstrText.length();
         Rect var9 = var5.bounds;
         var6.getTextBounds(var7, 0, var8, var9);
         var5.bounds.inset('\ufff1', '\ufff1');
         int var10 = this.mBodyHeight;
         int var11 = var5.bounds.height();
         if(var10 < var11) {
            int var12 = var5.bounds.height();
            this.mBodyHeight = var12;
         }

         int var13 = this.mBodyWidth;
         int var14 = var5.bounds.width();
         int var15 = var13 + var14;
         this.mBodyWidth = var15;
         ++var3;
      }
   }

   void makeBgPath(int var1, int var2) {
      this.mBgPath.reset();
      int var3 = var1 - var2;
      int var4 = this.mBodyWidth - 1;
      int var5 = this.mBodyHeight - 1;
      float var6 = (float)var3;
      float var7 = (float)(this.mTailWidth / 2);
      float var8 = 10.0F + var7;
      if(var6 < var8) {
         int var9 = this.mTailWidth / 2 + 10;
         var3 += var9;
      }

      int var10 = this.mTailWidth / 2 + 10;
      int var11 = var4 - var10;
      if(var3 > var11) {
         int var12 = this.mTailWidth / 2 + 10;
         var3 -= var12;
      }

      int var13 = 1;
      byte var14 = this.mbDrawUpsideDown;
      if(1 == var14) {
         var13 = this.mTailHeight + 1;
      }

      Path var15 = this.mBgPath;
      float var16 = (float)var13;
      float var17 = (float)var4;
      float var18 = (float)(var5 + var13);
      RectF var19 = new RectF(1.0F, var16, var17, var18);
      Direction var20 = Direction.CCW;
      var15.addRoundRect(var19, 10.0F, 10.0F, var20);
      if(!this.mbDrawUpsideDown) {
         Path var21 = this.mBgPath;
         int var22 = this.mTailWidth / 2;
         float var23 = (float)(var3 - var22);
         float var24 = (float)var5;
         var21.moveTo(var23, var24);
         Path var25 = this.mBgPath;
         float var26 = (float)var3;
         float var27 = (float)(this.mTailHeight + var5);
         var25.lineTo(var26, var27);
         Path var28 = this.mBgPath;
         float var29 = (float)(this.mTailWidth / 2 + var3);
         float var30 = (float)var5;
         var28.lineTo(var29, var30);
      } else {
         Path var58 = this.mBgPath;
         int var59 = this.mTailWidth / 2;
         float var60 = (float)(var3 - var59);
         float var61 = (float)var13;
         var58.moveTo(var60, var61);
         Path var62 = this.mBgPath;
         float var63 = (float)var3;
         var62.lineTo(var63, 0.0F);
         Path var64 = this.mBgPath;
         float var65 = (float)(this.mTailWidth / 2 + var3);
         float var66 = (float)var13;
         var64.lineTo(var65, var66);
      }

      this.mSeparatorB.reset();
      this.mSeparatorW.reset();
      Point var31 = new Point(0, 0);
      Point var32 = new Point(0, 0);
      int var33 = var13 + 3;
      var31.y = var33;
      int var34 = 0;

      while(true) {
         int var35 = this.mItems.size() - 1;
         if(var34 >= var35) {
            return;
         }

         ToolTipItem var36 = (ToolTipItem)this.mItems.get(var34);
         int var37 = var31.x;
         int var38 = var36.bounds.width() - 2;
         int var39 = var37 + var38;
         var31.x = var39;
         int var40 = var31.x;
         var32.x = var40;
         int var41 = var31.y;
         int var42 = this.mBodyHeight;
         int var43 = var41 + var42 - 9;
         var32.y = var43;
         Path var44 = this.mSeparatorB;
         float var45 = (float)var31.x;
         float var46 = (float)var31.y;
         var44.moveTo(var45, var46);
         Path var47 = this.mSeparatorB;
         float var48 = (float)var32.x;
         float var49 = (float)var32.y;
         var47.lineTo(var48, var49);
         int var50 = var31.x + 1;
         var31.x = var50;
         int var51 = var32.x + 1;
         var32.x = var51;
         Path var52 = this.mSeparatorW;
         float var53 = (float)var31.x;
         float var54 = (float)var31.y;
         var52.moveTo(var53, var54);
         Path var55 = this.mSeparatorW;
         float var56 = (float)var32.x;
         float var57 = (float)var32.y;
         var55.lineTo(var56, var57);
         ++var34;
      }
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      int var2 = var1.save();
      Paint var3 = new Paint();
      Style var4 = Style.FILL;
      var3.setStyle(var4);
      var3.setColor(-16777216);
      var3.setAntiAlias((boolean)1);
      LinearGradient var5 = this.mBgShader;
      var3.setShader(var5);
      Path var7 = this.mBgPath;
      var1.drawPath(var7, var3);
      Paint var8 = new Paint();
      Style var9 = Style.STROKE;
      var8.setStyle(var9);
      var8.setColor(-14540254);
      var8.setAntiAlias((boolean)1);
      Paint var10 = new Paint();
      Style var11 = Style.STROKE;
      var10.setStyle(var11);
      var10.setColor(-2236963);
      var10.setAntiAlias((boolean)1);
      Path var12 = this.mSeparatorB;
      var1.drawPath(var12, var8);
      Path var13 = this.mSeparatorW;
      var1.drawPath(var13, var10);
      Rect var14 = new Rect();
      var14.top = 0;
      int var15 = this.mViewHeight;
      var14.bottom = var15;
      int var16 = 0;
      int var17 = 0;

      while(true) {
         int var18 = this.mItems.size();
         if(var17 >= var18) {
            if(this.mIsDown == 1) {
               int var33 = var1.save();
               Path var34 = this.mBgPath;
               var1.clipPath(var34);
               Paint var36 = new Paint();
               Style var37 = Style.FILL;
               var36.setStyle(var37);
               var36.setColor(-936394755);
               var1.drawRect(var14, var36);
               Vector var38 = this.mItems;
               int var39 = this.mDownItem;
               ToolTipItem var40 = (ToolTipItem)var38.get(var39);
               if(!this.mbDrawUpsideDown) {
                  String var41 = var40.mstrText;
                  int var42 = -var40.bounds.left;
                  int var43 = var14.left;
                  float var44 = (float)(var42 + var43);
                  float var45 = (float)(-var40.bounds.top);
                  Paint var46 = this.mTextPaint;
                  var1.drawText(var41, var44, var45, var46);
               } else {
                  String var47 = var40.mstrText;
                  int var48 = -var40.bounds.left;
                  int var49 = var14.left;
                  float var50 = (float)(var48 + var49);
                  int var51 = -var40.bounds.top;
                  int var52 = this.mTailHeight;
                  float var53 = (float)(var51 + var52);
                  Paint var54 = this.mTextPaint;
                  var1.drawText(var47, var50, var53, var54);
               }

               var1.restore();
            }

            var1.restoreToCount(var2);
            return;
         }

         ToolTipItem var19 = (ToolTipItem)this.mItems.get(var17);
         if(!this.mbDrawUpsideDown) {
            String var20 = var19.mstrText;
            float var21 = (float)(-var19.bounds.left + var16);
            float var22 = (float)(-var19.bounds.top);
            Paint var23 = this.mTextPaint;
            var1.drawText(var20, var21, var22, var23);
         } else {
            String var27 = var19.mstrText;
            float var28 = (float)(-var19.bounds.left + var16);
            int var29 = -var19.bounds.top;
            int var30 = this.mTailHeight;
            float var31 = (float)(var29 + var30);
            Paint var32 = this.mTextPaint;
            var1.drawText(var27, var28, var31, var32);
         }

         int var24 = this.mDownItem;
         if(var17 == var24) {
            var14.left = var16;
            int var25 = var19.bounds.width() + var16;
            var14.right = var25;
         }

         int var26 = var19.bounds.width();
         var16 += var26;
         ++var17;
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction();
      int var3 = (int)var1.getX();
      int var4 = (int)var1.getY();
      if(var2 == 0) {
         this.mIsDown = (boolean)1;
         int var5 = this.findItem(var3, var4);
         this.mDownItem = var5;
         this.invalidate();
      } else if(var2 != 2 && var2 == 1) {
         this.mIsDown = (boolean)0;
         this.mDownItem = -1;
         this.invalidate();
         int var6 = this.findItem(var3, var4);
         if(-1 != var6) {
            ToolTipItem var7 = (ToolTipItem)this.mItems.get(var6);
            if(var7 != null) {
               var7.onItemSelected();
            }
         }
      }

      return true;
   }

   int pinViewX(int var1) {
      int var2 = this.mWindowManager.getDefaultDisplay().getWidth();
      int var3 = this.mViewWidth / 2;
      int var4 = var1 + var3;
      int var5 = this.mViewWidth / 2;
      int var6 = var1 - var5;
      int var7 = var6;
      if(var4 > var2) {
         int var8 = this.mViewWidth;
         var7 = var2 - var8;
      } else if(var6 < 0) {
         var7 = 0;
      }

      return var7;
   }

   void setFontSize(float var1) {
      this.mTextPaint.setTextSize(var1);
   }

   void show(int var1, int var2) {
      int[] var3 = new int[2];
      this.mSrcView.getLocationInWindow(var3);
      int var4 = var3[0];
      int var5 = var1 + var4;
      int var6 = var3[1];
      int var7 = var2 + var6;
      String var8 = "show : [x:y = " + var5 + ":" + var7 + "]";
      int var9 = Log.v("ToolTip", var8);
      this.layout(var5, var7);
      int var10 = this.pinViewX(var5);
      int var11 = this.mViewHeight;
      int var12 = var7 - var11;
      this.makeBgPath(var5, var10);
      byte var13 = this.mbDrawUpsideDown;
      if(1 == var13) {
         int var14 = this.mViewHeight;
         var12 += var14;
      }

      if(this.mbShow != 1) {
         int var15 = this.mViewWidth;
         int var16 = this.mViewHeight;
         LayoutParams var17 = new LayoutParams(var15, var16, var10, var12, 1000, 8, -1);
         var17.gravity = 51;
         this.mWindowManager.addView(this, var17);
         this.mbShow = (boolean)1;
      }
   }

   void show(int var1, int var2, boolean var3) {
      this.mbDrawUpsideDown = var3;
      this.show(var1, var2);
   }
}
