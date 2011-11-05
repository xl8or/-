package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.model.Track;
import java.util.List;

public class TrackListAdapter extends ArrayAdapter<Track> {

   private OnClickListener mOnClickListener;
   private Drawable mPauseIcon;
   private Drawable mPlayIcon;


   public TrackListAdapter(Context var1, int var2, List<Track> var3, OnClickListener var4) {
      super(var1, var2, 0, var3);
      this.mOnClickListener = var4;
      Resources var5 = var1.getResources();
      Drawable var6 = var5.getDrawable(2130837647);
      this.mPlayIcon = var6;
      Drawable var7 = var5.getDrawable(2130837640);
      this.mPauseIcon = var7;
   }

   private void setTrackView(View var1, Track.TrackMode var2) {
      if(var1 != null) {
         TrackListAdapter.TrackViewHolder var3 = (TrackListAdapter.TrackViewHolder)var1.getTag();
         int[] var4 = TrackListAdapter.2.$SwitchMap$com$google$android$finsky$model$Track$TrackMode;
         int var5 = var2.ordinal();
         switch(var4[var5]) {
         case 1:
            var3.loadingSpinner.setVisibility(8);
            var3.playIcon.setVisibility(0);
            ImageView var7 = var3.playIcon;
            Drawable var8 = this.mPlayIcon;
            var7.setImageDrawable(var8);
            var1.setBackgroundColor(0);
            return;
         case 2:
            var3.loadingSpinner.setVisibility(8);
            var3.playIcon.setVisibility(0);
            ImageView var9 = var3.playIcon;
            Drawable var10 = this.mPlayIcon;
            var9.setImageDrawable(var10);
            var1.setBackgroundColor(-7829368);
            return;
         case 3:
            var3.loadingSpinner.setVisibility(0);
            var3.playIcon.setVisibility(8);
            var1.setBackgroundColor(-7829368);
            return;
         case 4:
            var3.loadingSpinner.setVisibility(8);
            var3.playIcon.setVisibility(0);
            ImageView var11 = var3.playIcon;
            Drawable var12 = this.mPauseIcon;
            var11.setImageDrawable(var12);
            var1.setBackgroundColor(-7829368);
            return;
         default:
            String var6 = "Cannot have TrackDisplayMode: " + var2;
            throw new IllegalStateException(var6);
         }
      }
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4 = var2;
      if(var2 == null) {
         var4 = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2130968663, var3, (boolean)0);
      }

      Track var5 = (Track)this.getItem(var1);
      TrackListAdapter.TrackViewHolder var6 = (TrackListAdapter.TrackViewHolder)var4.getTag();
      if(var6 == null) {
         var6 = new TrackListAdapter.TrackViewHolder(var4);
         var4.setTag(var6);
      }

      Track.TrackMode var7 = var5.mode;
      this.setTrackView(var4, var7);
      String var8 = var5.url;
      var6.url = var8;
      TextView var9 = var6.trackNumber;
      String var10 = Integer.toString(var5.trackNo);
      var9.setText(var10);
      TextView var11 = var6.title;
      String var12 = var5.title;
      var11.setText(var12);
      TextView var13 = var6.album;
      String var14 = var5.album;
      var13.setText(var14);
      TextView var15 = var6.year;
      String var16 = Integer.toString(var5.year);
      var15.setText(var16);
      TextView var17 = var6.artist;
      String var18 = var5.artist;
      var17.setText(var18);
      TextView var19 = var6.length;
      String var20 = var5.length;
      var19.setText(var20);
      TextView var21 = var6.priceButton;
      String var22 = var5.price;
      var21.setText(var22);
      TextView var23 = var6.priceButton;
      TrackListAdapter.1 var24 = new TrackListAdapter.1();
      var23.setOnClickListener(var24);
      FrameLayout var25 = var6.playButtonLayout;
      OnClickListener var26 = this.mOnClickListener;
      var25.setOnClickListener(var26);
      TextView var27 = var6.trackNumber;
      OnClickListener var28 = this.mOnClickListener;
      var27.setOnClickListener(var28);
      TextView var29 = var6.title;
      OnClickListener var30 = this.mOnClickListener;
      var29.setOnClickListener(var30);
      TextView var31 = var6.album;
      OnClickListener var32 = this.mOnClickListener;
      var31.setOnClickListener(var32);
      TextView var33 = var6.year;
      OnClickListener var34 = this.mOnClickListener;
      var33.setOnClickListener(var34);
      TextView var35 = var6.artist;
      OnClickListener var36 = this.mOnClickListener;
      var35.setOnClickListener(var36);
      TextView var37 = var6.length;
      OnClickListener var38 = this.mOnClickListener;
      var37.setOnClickListener(var38);
      TextView var39 = var6.album;
      OnClickListener var40 = this.mOnClickListener;
      var39.setOnClickListener(var40);
      return var4;
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$model$Track$TrackMode = new int[Track.TrackMode.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$model$Track$TrackMode;
            int var1 = Track.TrackMode.READY.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$model$Track$TrackMode;
            int var3 = Track.TrackMode.PAUSE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$model$Track$TrackMode;
            int var5 = Track.TrackMode.LOADING.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$model$Track$TrackMode;
            int var7 = Track.TrackMode.PLAYING.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {}
   }

   protected static final class TrackViewHolder {

      public final TextView album;
      public final TextView artist;
      public final TextView length;
      public final ProgressBar loadingSpinner;
      public final FrameLayout playButtonLayout;
      public final ImageView playIcon;
      public final TextView priceButton;
      public final TextView title;
      public final TextView trackNumber;
      public String url;
      public final TextView year;


      protected TrackViewHolder(View var1) {
         FrameLayout var2 = (FrameLayout)var1.findViewById(2131755229);
         this.playButtonLayout = var2;
         ProgressBar var3 = (ProgressBar)var1.findViewById(2131755230);
         this.loadingSpinner = var3;
         ImageView var4 = (ImageView)var1.findViewById(2131755231);
         this.playIcon = var4;
         TextView var5 = (TextView)var1.findViewById(2131755232);
         this.trackNumber = var5;
         TextView var6 = (TextView)var1.findViewById(2131755233);
         this.title = var6;
         TextView var7 = (TextView)var1.findViewById(2131755234);
         this.album = var7;
         TextView var8 = (TextView)var1.findViewById(2131755235);
         this.year = var8;
         TextView var9 = (TextView)var1.findViewById(2131755236);
         this.artist = var9;
         TextView var10 = (TextView)var1.findViewById(2131755237);
         this.length = var10;
         TextView var11 = (TextView)var1.findViewById(2131755238);
         this.priceButton = var11;
      }
   }
}
