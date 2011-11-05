package com.google.android.finsky.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.CorpusMetadata;

public class DetailsMarketContentViewBinder extends DetailsViewBinder {

   public DetailsMarketContentViewBinder() {}

   public void bind(View var1, Document var2) {
      if(var2.getBackend() != 3) {
         var1.setVisibility(8);
      } else {
         super.bind(var1, var2, 2131755140, 2131231113);
         this.populateContent();
         var1.findViewById(2131755137).setVisibility(8);
      }
   }

   protected void populateContent() {
      LinearLayout var1 = (LinearLayout)this.mLayout.findViewById(2131755143);
      var1.removeAllViews();
      View var2 = this.mInflater.inflate(2130968662, var1, (boolean)0);
      TextView var3 = (TextView)var2.findViewById(2131755227);
      TextView var4 = (TextView)var2.findViewById(2131755228);
      var3.setText(2131231101);
      var4.setText(2131231102);
      DetailsMarketContentViewBinder.1 var5 = new DetailsMarketContentViewBinder.1();
      var2.setOnClickListener(var5);
      Context var6 = this.mContext;
      int var7 = this.mDoc.getBackend();
      Drawable var8 = CorpusMetadata.getBucketEntryBackground(var6, var7);
      var2.setBackgroundDrawable(var8);
      var1.addView(var2);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         NavigationManager var2 = DetailsMarketContentViewBinder.this.mNavigationManager;
         String var3 = DetailsMarketContentViewBinder.this.mDoc.getDetailsUrl();
         var2.goToFlagContent(var3);
      }
   }
}
