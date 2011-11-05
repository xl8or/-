package com.android.exchange.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import com.android.email.EmailAddressAdapter;
import com.android.email.provider.EmailContent;
import com.android.exchange.provider.ExchangeProvider;
import java.text.SimpleDateFormat;

public class EmailSearchAdapter extends EmailAddressAdapter {

   private static final boolean DEBUG_EMAIL_SEARCH_LOG = true;
   private EmailContent.Account mAccount;
   private String mAccountEmailDomain;
   private Activity mActivity;
   private String mConversationId;
   private String mFoldIdStr;
   private String mFreeText;
   private String mGreaterThanDateStr;
   private String mIdStr;
   private LayoutInflater mInflater;
   private String mLessThanDateStr;
   private String mOptionsDeepTraversalStr;
   private int mSeparatorDisplayCount;
   private int mSeparatorTotalCount;


   public EmailSearchAdapter(Activity var1) {
      super(var1);
      this.mActivity = var1;
   }

   public int search() {
      long var1;
      try {
         var1 = Long.parseLong(this.mFoldIdStr);
      } catch (NumberFormatException var11) {
         throw new IllegalArgumentException("Illegal value in URI");
      }

      ContentResolver var5 = this.mContentResolver;
      Uri var6 = EmailContent.Message.CONTENT_URI;
      String var7 = "mailboxKey=" + var1;
      var5.delete(var6, var7, (String[])null);
      EmailSearchAdapter.1 var9 = new EmailSearchAdapter.1();
      (new Thread(var9)).start();
      return 0;
   }

   public void setAccount(EmailContent.Account var1) {
      this.mAccount = var1;
   }

   public void setConvIdStr(String var1) {
      this.mConversationId = var1;
   }

   public void setFoldIdStr(String var1) {
      this.mFoldIdStr = var1;
   }

   public void setFreeText(String var1) {
      this.mFreeText = var1;
   }

   public void setGreaterThanDateStr(String var1) {
      this.mGreaterThanDateStr = var1;
   }

   public void setIdStr(String var1) {
      this.mIdStr = var1;
   }

   public void setLessThanDateStr(String var1) {
      this.mLessThanDateStr = var1;
   }

   public void setoptionsDeepTraversalStr(String var1) {
      this.mOptionsDeepTraversalStr = var1;
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         new SimpleDateFormat("dd/MM/yyyy");
         Builder var2 = ExchangeProvider.EMAIL_SEARCH_URI.buildUpon();
         String var3 = Long.toString(EmailSearchAdapter.this.mAccount.mId);
         Builder var4 = var2.appendPath(var3);
         String var5 = EmailSearchAdapter.this.mFreeText;
         Builder var6 = var4.appendPath(var5);
         String var7 = EmailSearchAdapter.this.mGreaterThanDateStr;
         Builder var8 = var6.appendPath(var7);
         String var9 = EmailSearchAdapter.this.mLessThanDateStr;
         Builder var10 = var8.appendPath(var9);
         String var11 = EmailSearchAdapter.this.mIdStr;
         Builder var12 = var10.appendPath(var11);
         String var13 = EmailSearchAdapter.this.mFoldIdStr;
         Builder var14 = var12.appendPath(var13);
         String var15 = EmailSearchAdapter.this.mOptionsDeepTraversalStr;
         Builder var16 = var14.appendPath(var15);
         String var17 = EmailSearchAdapter.this.mConversationId;
         Uri var18 = var16.appendPath(var17).build();
         String var19 = "Query: " + var18;
         int var20 = Log.d("Email", var19);
         ContentResolver var21 = EmailSearchAdapter.this.mContentResolver;
         Object var22 = null;
         Object var23 = null;
         Object var24 = null;
         var21.query(var18, (String[])null, (String)var22, (String[])var23, (String)var24);
      }
   }
}
