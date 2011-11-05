package com.htc.android.mail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.HtcSkinUtil;
import android.widget.TextView;
import com.htc.android.mail.DebugActivity;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ProviderListItem;
import com.htc.android.mail.ecNewAccount;
import com.htc.android.mail.ll;
import com.htc.app.HtcListActivity;
import com.htc.widget.HtcListView;
import com.htc.widget.HtcAlertDialog.Builder;

public class ProviderListScreen extends HtcListActivity {

   private static final int CHINA_139_EXIST = 2;
   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final int EXCHANGE_EXIST = 1;
   private static int[] mSecretPhrase = new int[]{33, 52, 31, 37, 48, 33, 32};
   private final int ADD_ACCOUNT = 1;
   final String TAG = "ProviderListScreen";
   private String cmd = null;
   private Cursor mCursor = null;
   private int mSecretPhraseIndex = 0;
   private boolean singleProvider;


   public ProviderListScreen() {}

   protected void onActivityResult(int var1, int var2, Intent var3) {
      String var4 = "onActivityResult>" + var1 + "," + var2 + "," + var3;
      ll.d("ProviderListScreen", var4);
      if(var2 != 200 || this.singleProvider) {
         this.setResult(var2, var3);
         this.finish();
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      if(!this.isFinishing()) {
         int var2 = HtcSkinUtil.getDrawableResIdentifier(this, "common_app_bkg", 34080439);
         if(var2 != 0) {
            Window var3 = this.getWindow();
            Drawable var4 = this.getResources().getDrawable(var2);
            var3.setBackgroundDrawable(var4);
         }
      }
   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.requestWindowFeature(1);
      this.setContentView(2130903082);
      ((HtcListView)this.findViewById(16908298)).setRoundedCornerEnabled((boolean)1, (boolean)1);
      ((TextView)this.findViewById(33685587)).setText(2131361918);
      ((TextView)this.findViewById(33685588)).setText(2131361918);
      Intent var3 = this.getIntent();
      Uri var4 = MailProvider.sProvidersURI;
      Object var6 = null;
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = this.managedQuery(var4, (String[])null, (String)var6, (String[])var7, (String)var8);
      this.mCursor = var9;
      if(this.mCursor != null && this.mCursor.getCount() == 1) {
         this.singleProvider = (boolean)1;
         ll.d("ProviderListScreen", "enter setup screen when only exist one provider>");
         Cursor var10 = this.mCursor;
         if(var10.moveToFirst()) {
            int var11 = var10.getColumnIndexOrThrow("_provider");
            String var12 = var10.getString(var11);
            Intent var13 = new Intent(this, ecNewAccount.class);
            var13.putExtra("provider", var12);
            int var15 = var10.getColumnIndexOrThrow("_domain");
            String var16 = var10.getString(var15);
            var13.putExtra("_domain", var16);
            int var18 = var10.getColumnIndexOrThrow("_id");
            long var19 = var10.getLong(var18);
            var13.putExtra("_providerid", var19);
            int var22 = this.getIntent().getIntExtra("CallingActivity", -1);
            var13.putExtra("CallingActivity", var22);
            this.startActivityForResult(var13, 1);
         } else {
            ll.w("ProviderListScreen", "can move cursor to First>");
         }
      } else {
         this.singleProvider = (boolean)0;
         Cursor var24 = this.mCursor;
         ProviderListScreen.ProviderListAdapter var25 = new ProviderListScreen.ProviderListAdapter(var24, this);
         this.setListAdapter(var25);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         Builder var3 = new Builder(this);
         CharSequence var4 = this.getText(2131361879);
         Builder var5 = var3.setTitle(var4);
         String var6 = this.getString(2131361919);
         var2 = var5.setMessage(var6).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 2:
         Builder var7 = new Builder(this);
         CharSequence var8 = this.getText(2131361879);
         Builder var9 = var7.setTitle(var8);
         String var10 = this.getString(2131362494);
         var2 = var9.setMessage(var10).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      default:
         var2 = super.onCreateDialog(var1);
      }

      return (Dialog)var2;
   }

   protected void onDestroy() {
      super.onDestroy();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      int var3 = var2.getKeyCode();
      int[] var4 = mSecretPhrase;
      int var5 = this.mSecretPhraseIndex;
      int var6 = var4[var5];
      if(var3 == var6) {
         int var7 = this.mSecretPhraseIndex + 1;
         this.mSecretPhraseIndex = var7;
         int var8 = this.mSecretPhraseIndex;
         int var9 = mSecretPhrase.length;
         if(var8 == var9) {
            this.mSecretPhraseIndex = 0;
            Intent var10 = new Intent(this, DebugActivity.class);
            this.startActivity(var10);
         }
      } else {
         this.mSecretPhraseIndex = 0;
      }

      return super.onKeyDown(var1, var2);
   }

   protected void onListItemClick(HtcListView param1, View param2, int param3, long param4) {
      // $FF: Couldn't be decompiled
   }

   public void onResume() {
      super.onResume();
      if(DEBUG) {
         int var1 = Log.d("ProviderListScreen", "[ATS][com.htc.android.mail][press_provider][launch]");
      }
   }

   static class ProviderListAdapter extends CursorAdapter {

      public ProviderListAdapter(Cursor var1, Context var2) {
         super(var2, var1);
      }

      public final void bindView(View var1, Context var2, Cursor var3) {
         ((ProviderListItem)var1).bind(var3);
      }

      public final View newView(Context var1, Cursor var2, ViewGroup var3) {
         return new ProviderListItem(var1);
      }
   }
}
