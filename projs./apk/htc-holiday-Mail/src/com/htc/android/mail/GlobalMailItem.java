package com.htc.android.mail;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.htc.android.mail.Headers;
import com.htc.android.mail.Mailaddress;
import java.util.ArrayList;

public class GlobalMailItem extends RelativeLayout {

   private String TAG = "GlobalMailItem";
   String _subject;
   private long mAccountId;
   private String mCollectionId;
   Context mContext;
   private String mMessageId;
   private String mServerId;
   private TextView tvSubTitle;
   private TextView tvTitle;


   public GlobalMailItem(Context var1) {
      super(var1);
      this.mContext = var1;
      View var2 = LayoutInflater.from(var1).inflate(2130903090, this);
   }

   public GlobalMailItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private StringBuilder getDisplayName(String var1, String var2) {
      if(var1 != null) {
         var1 = var1.trim();
      }

      if(var2 != null) {
         var2 = var2.trim();
      }

      StringBuilder var3 = new StringBuilder();
      ArrayList var4 = Headers.splitMailAddress(var1, (boolean)0);
      StringBuilder var21;
      if(var1 != null && !var1.equalsIgnoreCase("") && var4.size() > 0) {
         int var5 = 0;

         while(true) {
            int var6 = var4.size();
            if(var5 >= var6) {
               if(var2 != null && !var2.equalsIgnoreCase("")) {
                  String var19 = ", " + var2;
                  var3.append(var19);
               }

               var21 = var3;
               break;
            }

            if(!((Mailaddress)var4.get(var5)).mDisplayName.equals("")) {
               if(var5 == 0) {
                  String var7 = ((Mailaddress)var4.get(var5)).mDisplayName;
                  var3.append(var7);
               } else {
                  StringBuilder var9 = (new StringBuilder()).append(", ");
                  String var10 = ((Mailaddress)var4.get(var5)).mDisplayName;
                  String var11 = var9.append(var10).toString();
                  var3.append(var11);
               }
            } else if(!((Mailaddress)var4.get(var5)).mEmail.equals("")) {
               if(var5 == 0) {
                  String var13 = ((Mailaddress)var4.get(var5)).mEmail;
                  var3.append(var13);
               } else {
                  StringBuilder var15 = (new StringBuilder()).append(", ");
                  String var16 = ((Mailaddress)var4.get(var5)).mEmail;
                  String var17 = var15.append(var16).toString();
                  var3.append(var17);
               }
            }

            ++var5;
         }
      } else if(var2 != null && !var2.equals("")) {
         var21 = var3.append(var2);
      } else {
         var21 = null;
      }

      return var21;
   }

   private final void init() {
      TextView var1 = (TextView)this.findViewById(16908308);
      this.tvTitle = var1;
      TextView var2 = (TextView)this.findViewById(16908309);
      this.tvSubTitle = var2;
      this.tvTitle.setVisibility(0);
      this.tvSubTitle.setVisibility(0);
   }

   public final void bind(Cursor var1) {
      if(this.tvTitle == null || this.tvSubTitle == null) {
         this.init();
      }

      int var2 = var1.getColumnIndex("_id");
      String var3 = var1.getString(var2);
      this.mMessageId = var3;
      int var4 = var1.getColumnIndex("_uid");
      String var5 = var1.getString(var4);
      this.mServerId = var5;
      int var6 = var1.getColumnIndex("_collectionId");
      String var7 = var1.getString(var6);
      this.mCollectionId = var7;
      int var8 = var1.getColumnIndex("_account");
      long var9 = var1.getLong(var8);
      this.mAccountId = var9;
      TextView var11 = this.tvTitle;
      int var12 = var1.getColumnIndex("_fromEmail");
      String var13 = var1.getString(var12);
      var11.setText(var13);
      TextView var14 = this.tvSubTitle;
      int var15 = var1.getColumnIndex("_subject");
      String var16 = var1.getString(var15);
      var14.setText(var16);
   }

   public long getAccountId() {
      return this.mAccountId;
   }

   public String getCollectionId() {
      return this.mCollectionId;
   }

   public String getMessageId() {
      return this.mMessageId;
   }

   public String getServerId() {
      return this.mServerId;
   }

   public String getSubTitle() {
      String var1;
      if(this.tvSubTitle != null) {
         var1 = this.tvSubTitle.getText().toString();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getTitle() {
      String var1;
      if(this.tvTitle != null) {
         var1 = this.tvTitle.getText().toString();
      } else {
         var1 = null;
      }

      return var1;
   }
}
