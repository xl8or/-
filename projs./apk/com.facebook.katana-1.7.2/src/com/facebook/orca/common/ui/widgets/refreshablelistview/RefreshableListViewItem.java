package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewState;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RefreshableListViewItem extends FrameLayout {

   private View actionContainer;
   private ImageView arrowImage;
   private int direction = -1;
   private RotateAnimation forwardFlipAnimation;
   private View refreshContainer;
   private RotateAnimation reverseFlipAnimation;
   private RefreshableListViewState state;
   private TextView textLastLoadedTime;
   private TextView textViewPull;
   private TextView textViewPush;
   private TextView textViewRelease;
   private List<TextView> textViews;


   public RefreshableListViewItem(Context var1) {
      super(var1);
      this.init();
   }

   public RefreshableListViewItem(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init();
   }

   public RefreshableListViewItem(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init();
   }

   private void init() {
      View var1 = LayoutInflater.from(this.getContext()).inflate(2130903149, this);
      View var2 = this.findViewById(2131624212);
      this.actionContainer = var2;
      View var3 = this.findViewById(2131624220);
      this.refreshContainer = var3;
      TextView var4 = (TextView)this.findViewById(2131624216);
      this.textViewPull = var4;
      this.textViewPull.setText(2131362458);
      TextView var5 = (TextView)this.findViewById(2131624218);
      this.textViewRelease = var5;
      this.textViewRelease.setText(2131362460);
      TextView var6 = (TextView)this.findViewById(2131624217);
      this.textViewPush = var6;
      this.textViewPush.setText(2131362459);
      TextView[] var7 = new TextView[3];
      TextView var8 = this.textViewPull;
      var7[0] = var8;
      TextView var9 = this.textViewRelease;
      var7[1] = var9;
      TextView var10 = this.textViewPush;
      var7[2] = var10;
      List var11 = Arrays.asList(var7);
      this.textViews = var11;
      TextView var12 = (TextView)this.findViewById(2131624219);
      this.textLastLoadedTime = var12;
      this.textLastLoadedTime.setText(2131362462);
      ((TextView)this.findViewById(2131624222)).setText(2131362461);
      ImageView var13 = (ImageView)this.findViewById(2131624213);
      this.arrowImage = var13;
      this.arrowImage.setMinimumHeight(50);
      RotateAnimation var14 = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
      this.forwardFlipAnimation = var14;
      RotateAnimation var15 = this.forwardFlipAnimation;
      LinearInterpolator var16 = new LinearInterpolator();
      var15.setInterpolator(var16);
      this.forwardFlipAnimation.setDuration(250L);
      this.forwardFlipAnimation.setFillAfter((boolean)1);
      RotateAnimation var17 = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
      this.reverseFlipAnimation = var17;
      RotateAnimation var18 = this.reverseFlipAnimation;
      LinearInterpolator var19 = new LinearInterpolator();
      var18.setInterpolator(var19);
      this.reverseFlipAnimation.setDuration(250L);
      this.reverseFlipAnimation.setFillAfter((boolean)1);
   }

   private void makeTextVisible(TextView var1) {
      Iterator var2 = this.textViews.iterator();

      while(var2.hasNext()) {
         TextView var3 = (TextView)var2.next();
         if(var3 == var1) {
            var3.setVisibility(0);
         } else {
            var3.setVisibility(4);
         }
      }

   }

   public void setDirection(int var1) {
      int var2 = this.direction;
      if(var1 != var2) {
         if(var1 == 0) {
            this.setBackgroundResource(2130837773);
         } else {
            this.setBackgroundResource(2130837774);
         }

         this.direction = var1;
      }
   }

   public void setLastLoadedTime(long var1) {
      if(var1 >= 0L) {
         Context var3 = this.getContext();
         Date var4 = new Date(var1);
         String var5 = DateFormat.getDateFormat(var3).format(var4);
         String var6 = DateFormat.getTimeFormat(var3).format(var4);
         Object[] var7 = new Object[]{var5, var6};
         String var8 = var3.getString(2131362462, var7);
         this.textLastLoadedTime.setText(var8);
         this.textLastLoadedTime.setVisibility(0);
      } else {
         this.textLastLoadedTime.setVisibility(8);
      }
   }

   public void setState(RefreshableListViewState var1) {
      label49: {
         RefreshableListViewState var2 = this.state;
         RefreshableListViewState var3 = RefreshableListViewState.PULL_TO_REFRESH;
         if(var2 == var3) {
            RefreshableListViewState var4 = RefreshableListViewState.RELEASE_TO_REFRESH;
            if(var1 == var4) {
               TextView var5 = this.textViewRelease;
               this.makeTextVisible(var5);
               this.arrowImage.setImageResource(2130837718);
               this.arrowImage.clearAnimation();
               ImageView var6 = this.arrowImage;
               RotateAnimation var7 = this.forwardFlipAnimation;
               var6.startAnimation(var7);
               break label49;
            }
         }

         RefreshableListViewState var9 = RefreshableListViewState.PUSH_TO_REFRESH;
         if(var2 == var9) {
            RefreshableListViewState var10 = RefreshableListViewState.RELEASE_TO_REFRESH;
            if(var1 == var10) {
               TextView var11 = this.textViewRelease;
               this.makeTextVisible(var11);
               this.arrowImage.setImageResource(2130837719);
               this.arrowImage.clearAnimation();
               ImageView var12 = this.arrowImage;
               RotateAnimation var13 = this.forwardFlipAnimation;
               var12.startAnimation(var13);
               break label49;
            }
         }

         RefreshableListViewState var14 = RefreshableListViewState.RELEASE_TO_REFRESH;
         if(var2 == var14) {
            RefreshableListViewState var15 = RefreshableListViewState.PULL_TO_REFRESH;
            if(var1 == var15) {
               TextView var16 = this.textViewPull;
               this.makeTextVisible(var16);
               this.arrowImage.setImageResource(2130837718);
               this.arrowImage.clearAnimation();
               ImageView var17 = this.arrowImage;
               RotateAnimation var18 = this.reverseFlipAnimation;
               var17.startAnimation(var18);
               break label49;
            }
         }

         RefreshableListViewState var19 = RefreshableListViewState.RELEASE_TO_REFRESH;
         if(var2 == var19) {
            RefreshableListViewState var20 = RefreshableListViewState.PUSH_TO_REFRESH;
            if(var1 == var20) {
               TextView var21 = this.textViewPush;
               this.makeTextVisible(var21);
               this.arrowImage.setImageResource(2130837719);
               this.arrowImage.clearAnimation();
               ImageView var22 = this.arrowImage;
               RotateAnimation var23 = this.reverseFlipAnimation;
               var22.startAnimation(var23);
               break label49;
            }
         }

         RefreshableListViewState var24 = RefreshableListViewState.PULL_TO_REFRESH;
         if(var1 == var24) {
            this.arrowImage.setImageResource(2130837718);
            TextView var25 = this.textViewPull;
            this.makeTextVisible(var25);
            this.arrowImage.clearAnimation();
         } else {
            RefreshableListViewState var26 = RefreshableListViewState.PUSH_TO_REFRESH;
            if(var1 == var26) {
               this.arrowImage.setImageResource(2130837719);
               TextView var27 = this.textViewPush;
               this.makeTextVisible(var27);
               this.arrowImage.clearAnimation();
            } else {
               RefreshableListViewState var28 = RefreshableListViewState.RELEASE_TO_REFRESH;
               if(var1 == var28) {
                  TextView var29 = this.textViewRelease;
                  this.makeTextVisible(var29);
                  this.arrowImage.setImageResource(2130837718);
                  this.arrowImage.clearAnimation();
                  ImageView var30 = this.arrowImage;
                  RotateAnimation var31 = this.forwardFlipAnimation;
                  var30.startAnimation(var31);
               }
            }
         }
      }

      RefreshableListViewState var8 = RefreshableListViewState.LOADING;
      if(var1 == var8) {
         this.actionContainer.setVisibility(8);
         this.refreshContainer.setVisibility(0);
      } else {
         this.actionContainer.setVisibility(0);
         this.refreshContainer.setVisibility(8);
      }

      this.state = var1;
   }
}
