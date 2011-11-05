package com.htc.android.mail;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProviderListItem extends LinearLayout {

   private Context mContext;
   private TextView mProvider = null;


   public ProviderListItem(Context var1) {
      super(var1);
      this.mContext = var1;
      View var2 = LayoutInflater.from(var1).inflate(2130903083, this);
   }

   public ProviderListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private final void init() {
      TextView var1 = (TextView)this.findViewById(33685520);
      this.mProvider = var1;
   }

   private final void initImageView() {}

   public final void bind(Cursor var1) {
      int var2 = var1.getColumnIndexOrThrow("_provider");
      String var3 = var1.getString(var2);
      int var4 = var1.getColumnIndexOrThrow("_resid");
      int var5 = var1.getInt(var4);
      int var6 = var1.getColumnIndexOrThrow("_id");
      var1.getLong(var6);
      int var9 = var1.getColumnIndexOrThrow("_description");
      String var10 = var1.getString(var9);
      if(this.mProvider == null) {
         this.init();
      }

      if(var5 == 0) {
         if(var3.equals("Other")) {
            StringBuilder var11 = new StringBuilder();
            String var12 = this.mContext.getString(2131362482);
            var3 = var11.append(var12).append(" (POP3/IMAP)").toString();
         } else if(!var3.equals("Exchange ActiveSync")) {
            if(var3.equals("139")) {
               var3 = this.mContext.getString(2131362492);
            } else if(var10 != null && !"".equals(var10)) {
               var3 = var10;
            }
         }

         this.mProvider.setText(var3);
         this.mProvider.setVisibility(0);
      } else {
         this.mProvider.setVisibility(8);
      }
   }
}
