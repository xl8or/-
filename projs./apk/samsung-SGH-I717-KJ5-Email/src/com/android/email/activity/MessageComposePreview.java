package com.android.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MessageComposePreview extends Activity {

   public static final String EXTRA_PREVIEW_ATTACHMENT = "com.android.email.intent.extra.PREVIEW_ATTACHMENT";
   public static final String EXTRA_PREVIEW_BCC = "com.android.email.intent.extra.PREVIEW_BCC";
   public static final String EXTRA_PREVIEW_CC = "com.android.email.intent.extra.PREVIEW_CC";
   public static final String EXTRA_PREVIEW_CONTENT = "com.android.email.intent.extra.PREVIEW_CONTENT";
   public static final String EXTRA_PREVIEW_SUBJECT = "com.android.email.intent.extra.PREVIEW_SUBJECT";
   public static final String EXTRA_PREVIEW_TO = "com.android.email.intent.extra.PREVIEW_TO";
   private LinearLayout mAttachmentLayout;
   private TextView mContentText;
   private LinearLayout mRecipientBccLayout;
   private TextView mRecipientBccText;
   private LinearLayout mRecipientCcLayout;
   private TextView mRecipientCcText;
   private TextView mRecipientToText;
   private TextView mSubjectText;


   public MessageComposePreview() {}

   private void addAttachment(ArrayList<String> var1) {
      if(var1.size() > 0) {
         int var2 = 0;

         while(true) {
            int var3 = var1.size();
            if(var2 >= var3) {
               return;
            }

            LayoutInflater var4 = this.getLayoutInflater();
            LinearLayout var5 = this.mAttachmentLayout;
            RelativeLayout var6 = (RelativeLayout)var4.inflate(2130903110, var5, (boolean)0);
            TextView var7 = (TextView)var6.findViewById(2131362120);
            CharSequence var8 = (CharSequence)var1.get(var2);
            var7.setText(var8);
            int var9 = var1.size() - 1;
            if(var2 == var9) {
               ((ImageView)var6.findViewById(2131362117)).setVisibility(8);
            }

            this.mAttachmentLayout.addView(var6);
            ++var2;
         }
      }
   }

   private void initViews() {
      TextView var1 = (TextView)this.findViewById(2131362124);
      this.mRecipientToText = var1;
      TextView var2 = (TextView)this.findViewById(2131362126);
      this.mRecipientCcText = var2;
      TextView var3 = (TextView)this.findViewById(2131362128);
      this.mRecipientBccText = var3;
      TextView var4 = (TextView)this.findViewById(2131362130);
      this.mSubjectText = var4;
      TextView var5 = (TextView)this.findViewById(2131362131);
      this.mContentText = var5;
      LinearLayout var6 = (LinearLayout)this.findViewById(2131362125);
      this.mRecipientCcLayout = var6;
      LinearLayout var7 = (LinearLayout)this.findViewById(2131362127);
      this.mRecipientBccLayout = var7;
      LinearLayout var8 = (LinearLayout)this.findViewById(2131362132);
      this.mAttachmentLayout = var8;
      Intent var9 = this.getIntent();
      String var10 = var9.getStringExtra("com.android.email.intent.extra.PREVIEW_TO");
      if(var10 != null) {
         this.mRecipientToText.setText(var10);
      }

      String var11 = var9.getStringExtra("com.android.email.intent.extra.PREVIEW_CC");
      if(!TextUtils.isEmpty(var11)) {
         this.mRecipientCcText.setText(var11);
      }

      String var12 = var9.getStringExtra("com.android.email.intent.extra.PREVIEW_BCC");
      if(!TextUtils.isEmpty(var12)) {
         this.mRecipientBccText.setText(var12);
      }

      String var13 = var9.getStringExtra("com.android.email.intent.extra.PREVIEW_SUBJECT");
      if(var13 != null) {
         this.mSubjectText.setText(var13);
      }

      String var14 = var9.getStringExtra("com.android.email.intent.extra.PREVIEW_CONTENT");
      if(var14 != null) {
         this.mContentText.setText(var14);
      }

      ArrayList var15 = var9.getStringArrayListExtra("com.android.email.intent.extra.PREVIEW_ATTACHMENT");
      if(var15 != null && var15.size() > 0) {
         this.addAttachment(var15);
      } else {
         this.mAttachmentLayout.setVisibility(8);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.requestWindowFeature(1);
      this.setContentView(2130903109);
      this.initViews();
   }
}
