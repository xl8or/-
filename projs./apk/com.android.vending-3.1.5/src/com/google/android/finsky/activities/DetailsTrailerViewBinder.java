package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.layout.ThumbnailListener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.IntentUtils;
import java.util.Iterator;
import java.util.List;

public class DetailsTrailerViewBinder extends DetailsViewBinder {

   private BitmapLoader mBitmapLoader;
   private List<DeviceDoc.Trailer> mMovieTrailers;


   public DetailsTrailerViewBinder() {}

   private void populateContent() {
      ViewGroup var1 = (ViewGroup)this.mLayout.findViewById(2131755141);
      var1.removeAllViews();
      Iterator var2 = this.mMovieTrailers.iterator();

      while(var2.hasNext()) {
         DeviceDoc.Trailer var3 = (DeviceDoc.Trailer)var2.next();
         ViewGroup var4 = (ViewGroup)this.mInflater.inflate(2130968717, var1, (boolean)0);
         TextView var5 = (TextView)var4.findViewById(2131755321);
         String var6 = var3.getTitle();
         var5.setText(var6);
         TextView var7 = (TextView)var4.findViewById(2131755322);
         String var8 = var3.getDuration();
         var7.setText(var8);
         String var9 = var3.getThumbnailUrl();
         if(var9 != null) {
            ImageView var10 = (ImageView)var4.findViewById(2131755319);
            BitmapLoader var11 = this.mBitmapLoader;
            ThumbnailListener var12 = new ThumbnailListener(var10, (boolean)1);
            var11.getOrBindImmediately(var9, var10, var12);
         }

         DetailsTrailerViewBinder.1 var14 = new DetailsTrailerViewBinder.1(var3);
         var4.setOnClickListener(var14);
         var1.addView(var4);
      }

   }

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131231080);
      List var3 = var2.getMovieTrailers();
      this.mMovieTrailers = var3;
      if(this.mMovieTrailers != null && this.mMovieTrailers.size() != 0) {
         this.mLayout.setVisibility(0);
         this.populateContent();
      } else {
         this.mLayout.setVisibility(8);
      }
   }

   public void init(Context var1, DfeApi var2, NavigationManager var3, BitmapLoader var4) {
      super.init(var1, var2, var3);
      this.mBitmapLoader = var4;
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final DeviceDoc.Trailer val$trailer;


      1(DeviceDoc.Trailer var2) {
         this.val$trailer = var2;
      }

      public void onClick(View var1) {
         PackageManager var2 = DetailsTrailerViewBinder.this.mContext.getPackageManager();
         Uri var3 = Uri.parse(this.val$trailer.getWatchUrl());
         String var4 = FinskyApp.get().getCurrentAccountName();
         Intent var5 = IntentUtils.createYouTubeIntentForUrl(var2, var3, var4);
         DetailsTrailerViewBinder.this.mContext.startActivity(var5);
      }
   }
}
