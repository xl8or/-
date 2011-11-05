package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.finsky.layout.ScrollableViewGroup;
import com.google.android.finsky.layout.SlidingPanel.SlidingPanelHeader;

public class SlidingPanel extends ScrollableViewGroup {

   private int mCenterPanel = -1;
   private View[] mContentPanels;
   private float mFirstTabPartialWidth = 1.0F;
   private int mFirstVisiblePanel = -1;
   private int mGutterWidth;
   private View[] mGutters;
   private SlidingPanelHeader mHeader;
   private int mLastVisiblePanel = -1;
   private SlidingPanel.OnPanelSelectedListener mOnPanelSelectedListener;
   private int mPanelHeight;
   private int mPanelWidth;
   private int mSelectedPanel = -1;
   private int mStripHeight;
   private Runnable mVisibilityUpdater;


   public SlidingPanel(Context var1, AttributeSet var2) {
      super(var1, var2);
      Resources var3 = var1.getResources();
      int var4 = var3.getDimensionPixelSize(2131427390);
      this.mStripHeight = var4;
      int var5 = var3.getDimensionPixelSize(2131427392);
      this.mGutterWidth = var5;
      SlidingPanel.1 var6 = new SlidingPanel.1();
      this.mVisibilityUpdater = var6;
      SlidingPanelHeader var7 = new SlidingPanelHeader(this, var1);
      this.mHeader = var7;
      SlidingPanelHeader var8 = this.mHeader;
      this.addView(var8);
   }

   // $FF: synthetic method
   static void access$1000(SlidingPanel var0, int var1) {
      var0.updateSelectedPanel(var1);
   }

   // $FF: synthetic method
   static SlidingPanelHeader access$1100(SlidingPanel var0) {
      return var0.mHeader;
   }

   // $FF: synthetic method
   static View[] access$200(SlidingPanel var0) {
      return var0.mContentPanels;
   }

   // $FF: synthetic method
   static int access$300(SlidingPanel var0) {
      return var0.mPanelWidth;
   }

   // $FF: synthetic method
   static int access$400(SlidingPanel var0) {
      return var0.mStripHeight;
   }

   // $FF: synthetic method
   static int access$600(SlidingPanel var0, int var1) {
      return var0.tabIndexToPixel(var1);
   }

   // $FF: synthetic method
   static int access$700(SlidingPanel var0, int var1) {
      return var0.getTabWidth(var1);
   }

   // $FF: synthetic method
   static int access$800(SlidingPanel var0) {
      return var0.mGutterWidth;
   }

   // $FF: synthetic method
   static int access$900(SlidingPanel var0) {
      return var0.mSelectedPanel;
   }

   private int getFirstTabMissingWidth() {
      float var1 = (float)this.mPanelWidth;
      float var2 = this.mFirstTabPartialWidth;
      float var3 = 1.0F - var2;
      return (int)(var1 * var3);
   }

   private int getTabWidth(int var1) {
      float var2 = (float)this.mPanelWidth;
      return this.getTabWidth(var1, var2);
   }

   private int getTabWidth(int var1, float var2) {
      int var3;
      if(var1 == 0) {
         var3 = (int)(this.mFirstTabPartialWidth * var2);
      } else {
         var3 = (int)var2;
      }

      return var3;
   }

   private int pixelToTabIndex(int var1) {
      float var2 = (float)this.mPanelWidth;
      float var3 = this.mFirstTabPartialWidth;
      int var4 = (int)(var2 * var3);
      int var5 = this.mGutterWidth + var4;
      int var6;
      if(var1 <= var5) {
         var6 = 0;
      } else {
         int var7 = this.mGutterWidth + var4;
         int var8 = var1 - var7;
         int var9 = this.mPanelWidth;
         int var10 = this.mGutterWidth;
         int var11 = var9 + var10;
         var6 = var8 / var11 + 1;
      }

      return var6;
   }

   private int tabIndexToPixel(int var1) {
      int var2;
      if(var1 == 0) {
         var2 = 0;
      } else {
         float var3 = (float)this.mPanelWidth;
         float var4 = this.mFirstTabPartialWidth;
         int var5 = (int)(var3 * var4);
         int var6 = this.mPanelWidth;
         int var7 = this.mGutterWidth;
         int var8 = var6 + var7;
         int var9 = this.mGutterWidth + var5;
         int var10 = (var1 + -1) * var8;
         var2 = var9 + var10;
      }

      return var2;
   }

   private void update(int var1) {
      label15: {
         int var2 = this.pixelToTabIndex(var1);
         int var3 = this.mPanelWidth + var1;
         int var4 = this.pixelToTabIndex(var3);
         int var5 = this.mFirstVisiblePanel;
         if(var2 == var5) {
            int var6 = this.mLastVisiblePanel;
            if(var4 == var6) {
               break label15;
            }
         }

         this.mFirstVisiblePanel = var2;
         this.mLastVisiblePanel = var4;
      }

      int var7 = this.mPanelWidth / 2 + var1;
      int var8 = this.pixelToTabIndex(var7);
      int var9 = this.mCenterPanel;
      if(var8 != var9) {
         this.mCenterPanel = var8;
      }
   }

   private void updatePanelVisibilities() {
      int var1 = 0;

      while(true) {
         int var2 = this.mContentPanels.length;
         if(var1 >= var2) {
            return;
         }

         View var3;
         byte var6;
         label17: {
            var3 = this.mContentPanels[var1];
            int var4 = this.mFirstVisiblePanel;
            if(var1 >= var4) {
               int var5 = this.mLastVisiblePanel;
               if(var1 <= var5) {
                  var6 = 0;
                  break label17;
               }
            }

            var6 = 4;
         }

         this.updateVisibility(var3, var6);
         ++var1;
      }
   }

   private void updatePanelVisibilitiesAsync() {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.mContentPanels.length;
         if(var2 >= var3) {
            break;
         }

         View var4;
         byte var7;
         label25: {
            var4 = this.mContentPanels[var2];
            int var5 = this.mFirstVisiblePanel;
            if(var2 >= var5) {
               int var6 = this.mLastVisiblePanel;
               if(var2 <= var6) {
                  var7 = 0;
                  break label25;
               }
            }

            var7 = 4;
         }

         if(var4.getVisibility() != var7) {
            var1 = true;
            break;
         }

         ++var2;
      }

      SlidingPanelHeader var8 = this.mHeader;
      int var9 = this.mHeader.getWidth();
      int var10 = this.mHeader.getHeight();
      byte var11 = 0;
      var8.onLayout((boolean)1, 0, var11, var9, var10);
      if(var1) {
         Runnable var12 = this.mVisibilityUpdater;
         this.post(var12);
      }
   }

   private void updateSelectedPanel(int var1) {
      if(this.mContentPanels.length != 0) {
         int var2 = this.mContentPanels.length;
         if(var1 >= var2) {
            var1 = this.mContentPanels.length + -1;
         }

         if(this.mSelectedPanel != var1) {
            this.mSelectedPanel = var1;
            if(this.mOnPanelSelectedListener != null) {
               SlidingPanel.OnPanelSelectedListener var3 = this.mOnPanelSelectedListener;
               View var4 = this.mContentPanels[var1];
               var3.onPanelSelected(var4);
            }

            this.mHeader.invalidate();
         }
      }
   }

   private void updateVisibility(View var1, int var2) {
      if(var1.getVisibility() != var2) {
         var1.setVisibility(var2);
      }
   }

   public int getPanelCount() {
      int var1;
      if(this.mContentPanels != null) {
         var1 = this.mContentPanels.length;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public int getSelectedPanel() {
      return this.mSelectedPanel;
   }

   public void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = 0;
      if(this.mContentPanels != null) {
         if(this.mContentPanels.length != 0) {
            int var7 = this.getScrollX();
            if(var1) {
               int var8 = this.mPanelWidth;
               int var9 = var4 - var2;
               this.mPanelWidth = var9;
               int var10 = this.mPanelWidth;
               if(var8 != var10) {
                  SlidingPanelHeader.access$100(this.mHeader);
               }

               int var11 = var5 - var3;
               int var12 = this.mStripHeight;
               int var13 = var11 - var12;
               this.mPanelHeight = var13;
            }

            byte var45 = 0;
            var3 = this.mStripHeight;
            int var14 = this.mPanelHeight;
            var5 = var3 + var14;
            int var15 = 0;

            while(true) {
               int var16 = this.mContentPanels.length;
               if(var15 >= var16) {
                  int var26 = this.getFirstTabMissingWidth();
                  SlidingPanelHeader var27 = this.mHeader;
                  int var28 = this.mPanelWidth;
                  int var29 = this.mContentPanels.length;
                  int var30 = var28 * var29;
                  int var31 = this.mGutterWidth;
                  int var32 = this.mContentPanels.length + -1;
                  int var33 = var31 * var32;
                  int var34 = var30 + var33 - var26;
                  int var35 = this.mStripHeight;
                  var27.layout(0, 0, var34, var35);
                  int var36 = this.mPanelWidth;
                  int var37 = this.mGutterWidth;
                  int var38 = var36 + var37;
                  int var39 = this.mContentPanels.length + -1;
                  int var40 = var38 * var39 - var26;
                  this.setScrollLimits(0, var40);
                  if(var1) {
                     if(this.mSelectedPanel != 0) {
                        int var41 = this.mSelectedPanel;
                        int var42 = this.mPanelWidth;
                        int var43 = this.mGutterWidth;
                        int var44 = var42 + var43;
                        var6 = var41 * var44 - var26;
                     }

                     this.scrollTo(var6);
                     this.update(var6);
                     return;
                  }

                  this.update(var7);
                  return;
               }

               int var17 = this.getTabWidth(var15);
               View var18 = this.mContentPanels[var15];
               int var19 = var45 + var17;
               var18.layout(var45, var3, var19, var5);
               int var20 = var45 + var17;
               int var21 = this.mContentPanels.length + -1;
               if(var15 < var21) {
                  View var22 = this.mGutters[var15];
                  int var23 = this.mGutterWidth + var20;
                  var22.layout(var20, var3, var23, var5);
                  int var24 = this.mGutterWidth;
                  int var10000 = var20 + var24;
               }

               ++var15;
            }
         }
      }
   }

   public void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = MeasureSpec.getSize(var2);
      this.setMeasuredDimension(var3, var4);
      int var5 = this.mStripHeight;
      int var6 = var4 - var5;
      int var7 = MeasureSpec.makeMeasureSpec(this.mGutterWidth, 1073741824);
      int var8 = MeasureSpec.getMode(var2);
      int var9 = MeasureSpec.makeMeasureSpec(var6, var8);
      int var10 = 0;

      while(true) {
         int var11 = this.mContentPanels.length;
         if(var10 >= var11) {
            SlidingPanelHeader var15 = this.mHeader;
            int var16 = MeasureSpec.makeMeasureSpec(var3, 1073741824);
            int var17 = MeasureSpec.makeMeasureSpec(this.mStripHeight, 1073741824);
            var15.measure(var16, var17);
            return;
         }

         float var12 = (float)var3;
         int var13 = MeasureSpec.makeMeasureSpec(this.getTabWidth(var10, var12), 1073741824);
         this.mContentPanels[var10].measure(var13, var9);
         int var14 = this.mContentPanels.length + -1;
         if(var10 < var14) {
            this.mGutters[var10].measure(var7, var9);
         }

         ++var10;
      }
   }

   public void onScrollChanged(int var1, int var2, int var3, int var4) {
      this.update(var1);
      this.updatePanelVisibilitiesAsync();
   }

   protected void onScrollFinished(int var1) {
      if(this.mContentPanels.length != 0) {
         int var2 = this.getScrollX();
         int var3 = this.pixelToTabIndex(var2);
         int var4;
         if(var1 < 0) {
            var4 = var3;
         } else {
            var4 = var3 + 1;
         }

         int var5 = this.tabIndexToPixel(var4);
         this.smoothScrollTo(var5);
         this.updateSelectedPanel(var4);
      }
   }

   public void removeOnPanelSelectedListener() {
      this.mOnPanelSelectedListener = null;
   }

   public void setFirstTabPartialWidth(float var1) {
      this.mFirstTabPartialWidth = var1;
   }

   public void setOnPanelSelectedListener(SlidingPanel.OnPanelSelectedListener var1) {
      this.mOnPanelSelectedListener = var1;
   }

   public void setPanels(String[] var1, View[] var2, int var3) {
      if(var1 != null && var2 != null) {
         int var4 = var1.length;
         int var5 = var2.length;
         if(var4 != var5) {
            throw new IllegalArgumentException("Array sizes do not match");
         } else {
            int var6 = 0;

            while(true) {
               int var7 = var2.length;
               if(var6 >= var7) {
                  this.removeAllViews();
                  SlidingPanelHeader var8 = this.mHeader;
                  this.addView(var8);
                  int var9 = var1.length;
                  this.mContentPanels = var2;
                  int var10 = var9 + -1;
                  View[] var11 = new View[Math.max(0, var10)];
                  this.mGutters = var11;
                  LayoutInflater var12 = LayoutInflater.from(this.getContext());

                  for(int var13 = 0; var13 < var9; ++var13) {
                     this.mContentPanels[var13].setVisibility(4);
                     View var14 = this.mContentPanels[var13];
                     this.addView(var14);
                     int var15 = var9 + -1;
                     if(var13 < var15) {
                        View[] var16 = this.mGutters;
                        View var17 = var12.inflate(2130968707, this, (boolean)0);
                        var16[var13] = var17;
                        View var18 = this.mGutters[var13];
                        this.addView(var18);
                     }
                  }

                  this.mHeader.setPanels(var1, var3);
                  if(var9 <= 0) {
                     return;
                  }

                  this.setSelectedPanel(0);
                  return;
               }

               if(var2[var6] == false) {
                  throw new IllegalArgumentException("A null sub view");
               }

               ++var6;
            }
         }
      } else {
         throw new IllegalArgumentException("Must pass non-null arrays");
      }
   }

   public void setSelectedPanel(int var1) {
      this.updateSelectedPanel(var1);
      int var2 = this.mSelectedPanel;
      this.mFirstVisiblePanel = var2;
      int var3 = this.mSelectedPanel;
      this.mLastVisiblePanel = var3;
      if(this.mSelectedPanel == 0 && (double)this.mFirstTabPartialWidth < 1.0D) {
         int var4 = this.mLastVisiblePanel + 1;
         this.mLastVisiblePanel = var4;
      }

      this.updatePanelVisibilities();
   }

   public interface OnPanelSelectedListener {

      void onPanelSelected(View var1);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         SlidingPanel.this.updatePanelVisibilities();
      }
   }
}
