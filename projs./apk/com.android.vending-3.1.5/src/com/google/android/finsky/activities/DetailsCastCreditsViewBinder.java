package com.google.android.finsky.activities;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import java.util.Iterator;
import java.util.List;

public class DetailsCastCreditsViewBinder extends DetailsViewBinder {

   private List<DeviceDoc.VideoCredit> mCreditsList;


   public DetailsCastCreditsViewBinder() {}

   private void populateContent() {
      ViewGroup var1 = (ViewGroup)this.mLayout.findViewById(2131755141);
      var1.removeAllViews();
      Iterator var2 = this.mCreditsList.iterator();

      while(var2.hasNext()) {
         DeviceDoc.VideoCredit var3 = (DeviceDoc.VideoCredit)var2.next();
         ViewGroup var4 = (ViewGroup)this.mInflater.inflate(2130968599, var1, (boolean)0);
         TextView var5 = (TextView)var4.findViewById(2131755097);
         String var6 = var3.getCredit().toUpperCase();
         var5.setText(var6);
         TextView var7 = (TextView)var4.findViewById(2131755098);
         List var8 = var3.getNameList();
         String var9 = TextUtils.join(", ", var8);
         var7.setText(var9);
         var1.addView(var4);
      }

   }

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131230977);
      List var3 = var2.getCreditsList();
      this.mCreditsList = var3;
      if(this.mCreditsList != null && this.mCreditsList.size() != 0) {
         this.mLayout.setVisibility(0);
         this.populateContent();
      } else {
         this.mLayout.setVisibility(8);
      }
   }
}
