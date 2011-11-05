package com.google.android.finsky.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.remoting.protos.Doc;
import com.google.android.finsky.utils.IntentUtils;
import java.util.Iterator;
import java.util.List;

public class DetailsVideoViewBinder extends DetailsViewBinder {

   private List<Doc.Image> mVideoPreviews;


   public DetailsVideoViewBinder() {}

   private void populateContent() {
      ViewGroup var1 = (ViewGroup)this.mLayout.findViewById(2131755141);
      var1.removeAllViews();
      Iterator var2 = this.mVideoPreviews.iterator();

      while(var2.hasNext()) {
         Doc.Image var3 = (Doc.Image)var2.next();
         ViewGroup var4 = (ViewGroup)this.mInflater.inflate(2130968717, var1, (boolean)0);
         DetailsVideoViewBinder.1 var5 = new DetailsVideoViewBinder.1(var3);
         var4.setOnClickListener(var5);
         var1.addView(var4);
      }

   }

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131231081);
      List var3;
      if(var2.hasVideos()) {
         var3 = var2.getImages(3);
      } else {
         var3 = null;
      }

      this.mVideoPreviews = var3;
      if(this.mVideoPreviews == null) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         this.populateContent();
      }
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final Doc.Image val$image;


      1(Doc.Image var2) {
         this.val$image = var2;
      }

      public void onClick(View var1) {
         PackageManager var2 = DetailsVideoViewBinder.this.mContext.getPackageManager();
         Uri var3 = Uri.parse(this.val$image.getImageUrl());
         String var4 = FinskyApp.get().getCurrentAccountName();
         Intent var5 = IntentUtils.createYouTubeIntentForUrl(var2, var3, var4);
         DetailsVideoViewBinder.this.mContext.startActivity(var5);
      }
   }
}
