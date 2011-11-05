package com.htc.android.mail;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;

public class AccountListItem extends LinearLayout {

   private String TAG = "AccountListItem";
   Long mID;
   private TextView mName = null;
   private TextView mailDes = null;
   private Context mcontext = null;
   private TextView newMail = null;


   public AccountListItem(Context var1) {
      super(var1);
      this.mcontext = var1;
      View var2 = LayoutInflater.from(var1).inflate(2130903042, this);
   }

   public AccountListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private final void init() {
      TextView var1 = (TextView)this.findViewById(2131296265);
      this.newMail = var1;
      TextView var2 = (TextView)this.findViewById(2131296263);
      this.mailDes = var2;
   }

   public final void bind(Cursor var1) {
      ((LinearLayout)this.findViewById(2131296267)).setBackgroundResource(2130837504);
      int var2 = var1.getColumnIndexOrThrow("_id");
      Long var3 = Long.valueOf(var1.getLong(var2));
      this.mID = var3;
      int var4 = var1.getColumnIndexOrThrow("_desc");
      String var5 = var1.getString(var4);
      int var6 = var1.getColumnIndexOrThrow("_protocol");
      int var7 = var1.getInt(var6);
      int var8 = var1.getColumnIndexOrThrow("_defaultaccount");
      var1.getInt(var8);
      boolean var10 = Mail.isIMAP4(var7);
      int var11 = var1.getColumnIndexOrThrow("_provider");
      var1.getString(var11);
      int var13 = var1.getColumnIndexOrThrow("_defaultfolderId");
      long var14 = var1.getLong(var13);
      int var16 = MailProvider.getUnread(this.mID.longValue(), var7, var14);
      if(this.mailDes == null) {
         this.init();
      }

      if(this.newMail == null) {
         this.init();
      }

      this.setUnread(var16);
      if(this.mailDes != null) {
         if(var5.length() > 0) {
            this.mailDes.setText(var5);
         }
      }
   }

   public void setBottomType() {
      ll.d(this.TAG, "setBottomType>");
      ((LinearLayout)this.findViewById(2131296267)).setBackgroundResource(2130837505);
   }

   public void setLines(int var1) {
      if(this.mailDes == null) {
         this.init();
      }
   }

   public void setName(String var1) {
      if(this.mailDes == null) {
         this.init();
      }

      this.mailDes.setText(var1);
      this.newMail.setVisibility(8);
   }

   public void setUnread(int var1) {
      if(this.newMail == null) {
         TextView var2 = (TextView)this.findViewById(2131296265);
         this.newMail = var2;
      }

      if(var1 == 0) {
         this.newMail.setVisibility(4);
      } else {
         int var3 = MailCommon.getIndicatorFontSize(this.mcontext, var1);
         TextView var4 = this.newMail;
         float var5 = (float)var3;
         var4.setTextSize(var5);
         TextView var6 = this.newMail;
         String var7 = "" + var1;
         var6.setText(var7);
         this.newMail.setVisibility(0);
      }
   }
}
