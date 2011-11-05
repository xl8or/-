package com.android.settings;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class SyncActivityTooManyDeletes extends Activity implements OnItemClickListener {

   private Account mAccount;
   private String mAuthority;
   private long mNumDeletes;
   private String mProvider;


   public SyncActivityTooManyDeletes() {}

   private void startSyncReallyDelete() {
      Bundle var1 = new Bundle();
      var1.putBoolean("deletions_override", (boolean)1);
      var1.putBoolean("force", (boolean)1);
      var1.putBoolean("expedited", (boolean)1);
      var1.putBoolean("upload", (boolean)1);
      Account var2 = this.mAccount;
      String var3 = this.mAuthority;
      ContentResolver.requestSync(var2, var3, var1);
   }

   private void startSyncUndoDeletes() {
      Bundle var1 = new Bundle();
      var1.putBoolean("discard_deletions", (boolean)1);
      var1.putBoolean("force", (boolean)1);
      var1.putBoolean("expedited", (boolean)1);
      var1.putBoolean("upload", (boolean)1);
      Account var2 = this.mAccount;
      String var3 = this.mAuthority;
      ContentResolver.requestSync(var2, var3, var1);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Bundle var2 = this.getIntent().getExtras();
      if(var2 == null) {
         this.finish();
      } else {
         long var3 = var2.getLong("numDeletes");
         this.mNumDeletes = var3;
         Account var5 = (Account)var2.getParcelable("account");
         this.mAccount = var5;
         String var6 = var2.getString("authority");
         this.mAuthority = var6;
         String var7 = var2.getString("provider");
         this.mProvider = var7;
         CharSequence[] var8 = new CharSequence[3];
         CharSequence var9 = this.getResources().getText(2131034130);
         var8[0] = var9;
         CharSequence var10 = this.getResources().getText(2131034131);
         var8[1] = var10;
         CharSequence var11 = this.getResources().getText(2131034132);
         var8[2] = var11;
         ArrayAdapter var12 = new ArrayAdapter(this, 17367043, 16908308, var8);
         ListView var13 = new ListView(this);
         var13.setAdapter(var12);
         var13.setItemsCanFocus((boolean)1);
         var13.setOnItemClickListener(this);
         TextView var14 = new TextView(this);
         String var15 = this.getResources().getText(2131034129).toString();
         Object[] var16 = new Object[3];
         Long var17 = Long.valueOf(this.mNumDeletes);
         var16[0] = var17;
         String var18 = this.mProvider;
         var16[1] = var18;
         String var19 = this.mAccount.name;
         var16[2] = var19;
         String var20 = String.format(var15, var16);
         var14.setText(var20);
         LinearLayout var21 = new LinearLayout(this);
         var21.setOrientation(1);
         LayoutParams var22 = new LayoutParams(-1, -1, 0.0F);
         var21.addView(var14, var22);
         var21.addView(var13, var22);
         this.setContentView(var21);
      }
   }

   public void onItemClick(AdapterView var1, View var2, int var3, long var4) {
      if(var3 == 0) {
         this.startSyncReallyDelete();
      } else if(var3 == 1) {
         this.startSyncUndoDeletes();
      }

      this.finish();
   }
}
