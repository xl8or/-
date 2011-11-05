package com.android.email.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter.ViewBinder;
import com.android.email.activity.MessageList;
import com.android.email.provider.EmailContent;

public class AccountShortcutPicker extends ListActivity implements OnItemClickListener {

   private static final String[] sFromColumns;
   private final int[] sToIds;


   static {
      String[] var0 = new String[]{"displayName", "emailAddress", "_id"};
      sFromColumns = var0;
   }

   public AccountShortcutPicker() {
      int[] var1 = new int[]{2131361973, 2131361931, 2131361974};
      this.sToIds = var1;
   }

   private void setupShortcut(EmailContent.Account var1) {
      long var2 = var1.mId;
      Intent var4 = MessageList.actionHandleAccountUriIntent(this, var2, 0);
      Intent var5 = new Intent();
      var5.putExtra("android.intent.extra.shortcut.INTENT", var4);
      String var7 = var1.getDisplayName();
      var5.putExtra("android.intent.extra.shortcut.NAME", var7);
      ShortcutIconResource var9 = ShortcutIconResource.fromContext(this, 2130837912);
      var5.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", var9);
      this.setResult(-1, var5);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getIntent().getAction();
      if(!"android.intent.action.CREATE_SHORTCUT".equals(var2)) {
         this.finish();
      } else {
         Uri var3 = EmailContent.Account.CONTENT_URI;
         String[] var4 = EmailContent.Account.CONTENT_PROJECTION;
         Object var6 = null;
         Object var7 = null;
         Cursor var8 = this.managedQuery(var3, var4, (String)null, (String[])var6, (String)var7);
         if(var8.getCount() == 0) {
            this.finish();
         } else {
            this.setContentView(2130903067);
            ListView var9 = this.getListView();
            var9.setOnItemClickListener(this);
            var9.setItemsCanFocus((boolean)0);
            View var10 = this.findViewById(2131361971);
            var9.setEmptyView(var10);
            String[] var11 = sFromColumns;
            int[] var12 = this.sToIds;
            AccountShortcutPicker.AccountsAdapter var14 = new AccountShortcutPicker.AccountsAdapter(this, 2130903068, var8, var11, var12);
            var9.setAdapter(var14);
         }
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      Cursor var6 = (Cursor)var1.getItemAtPosition(var3);
      EmailContent.Account var7 = (new EmailContent.Account()).restore(var6);
      this.setupShortcut(var7);
      this.finish();
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class AccountsAdapter extends SimpleCursorAdapter {

      public AccountsAdapter(Context var1, int var2, Cursor var3, String[] var4, int[] var5) {
         super(var1, var2, var3, var4, var5);
         AccountShortcutPicker.AccountsAdapter.MyViewBinder var6 = new AccountShortcutPicker.AccountsAdapter.MyViewBinder((AccountShortcutPicker.1)null);
         this.setViewBinder(var6);
      }

      private static class MyViewBinder implements ViewBinder {

         private MyViewBinder() {}

         // $FF: synthetic method
         MyViewBinder(AccountShortcutPicker.1 var1) {
            this();
         }

         public boolean setViewValue(View var1, Cursor var2, int var3) {
            boolean var5;
            if(var1.getId() == 2131361974) {
               byte var4 = 0;
               if(var4 <= 0) {
                  var1.setVisibility(8);
               } else {
                  TextView var6 = (TextView)var1;
                  String var7 = String.valueOf(var4);
                  var6.setText(var7);
               }

               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         }
      }
   }
}
