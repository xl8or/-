package com.htc.android.mail;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountListDialogPicker;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.ComposeActivity;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.MailSearch;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.ll;
import com.htc.android.mail.easclient.ExchangeSvrSetting;
import com.htc.app.HtcListActivity;
import com.htc.widget.Title1;

public class SearchResultActivity extends HtcListActivity implements OnCreateContextMenuListener {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   static final int MENU_ITEM_SEE_CONVERSATION = 1;
   private static final int REQUEST_CODE_ACCOUNT_LIST = 1;
   final String TAG = "SearchResultActivity";
   long mAccountId = 65535L;
   private Context mContext;
   private String mGlobalWhere = null;
   private long mMailboxId = 65535L;
   private Intent mQueryIntent;
   String userQuery;


   public SearchResultActivity() {}

   private void checkParameter(long param1) {
      // $FF: Couldn't be decompiled
   }

   private void createExchangeAccount() {
      if(DEBUG) {
         ll.d("SearchResultActivity", "createExchangeAccount");
      }

      Intent var1 = new Intent();
      Intent var2 = var1.putExtra("intent.eas.mode.wizard", (boolean)1);
      var1.setClass(this, ExchangeSvrSetting.class);
      this.startActivity(var1);
   }

   private void lunchAccountList() {
      if(DEBUG) {
         ll.d("SearchResultActivity", "lunchAccountList");
      }

      Intent var1 = new Intent("android.intent.action.VIEW");
      var1.setClass(this, AccountListDialogPicker.class);
      Intent var3 = var1.putExtra("show_separate_account_only", (boolean)1);
      Intent var4 = var1.putExtra("show_exchange_only", (boolean)1);
      this.startActivityForResult(var1, 1);
   }

   private void readMail(long var1, int var3, boolean var4) {
      Bundle var5 = this.mQueryIntent.getBundleExtra("app_data");
      if(DEBUG) {
         StringBuilder var6 = (new StringBuilder()).append("appData>");
         StringBuilder var8 = var6.append(var3).append(",");
         String var10 = var8.append(var5).toString();
         ll.d("SearchResultActivity", var10);
      }

      StringBuilder var11 = (new StringBuilder()).append("content://mail/messages/");
      Uri var14 = Uri.parse(var11.append(var1).toString());
      if(var5 != null) {
         String var16 = "isDraftMailbox";
         if(!var5.getBoolean(var16)) {
            Intent var17 = new Intent;
            String var19 = "android.intent.action.VIEW";
            var17.<init>(var19, var14);
            String var22 = "position";
            var17.putExtra(var22, var3);
            String var25 = this.mGlobalWhere;
            String var27 = "where";
            var17.putExtra(var27, var25);
            String var31 = "sortRule";
            String var32 = "_internaldate DESC";
            var17.putExtra(var31, var32);
            String var35 = "singleItem";
            var17.putExtra(var35, var4);
            this.startActivity(var17);
         } else {
            ll.d("SearchResultActivity", "composeNew>");
            if(this.mAccountId == 65535L) {
               this.checkParameter(var1);
            }

            Intent var40 = new Intent;
            String var42 = "android.intent.action.MAIN";
            Class var45 = ComposeActivity.class;
            var40.<init>(var42, var14, this, var45);
            String var47 = "cmd";
            String var48 = "editdraft";
            var40.putExtra(var47, var48);
            long var50 = this.mAccountId;
            String var53 = "accountID";
            var40.putExtra(var53, var50);
            String var58 = "SetOrient";
            String var59 = "InMail";
            var40.putExtra(var58, var59);
            this.startActivity(var40);
         }
      } else {
         String var63 = this.mQueryIntent.getStringExtra("intent_extra_data_key");
         boolean var64 = false;
         if(var63 != null) {
            Uri var65 = Uri.parse(var63);
            String var67 = "accountId";
            String var68 = var65.getQueryParameter(var67);
            String var70 = "mailboxId";
            String var71 = var65.getQueryParameter(var70);
            if(var68 != null && var71 != null) {
               label53: {
                  long var72 = Long.parseLong(var68);
                  long var74 = Long.parseLong(var71);
                  AccountPool var76 = AccountPool.getInstance(this);
                  Account var80 = var76.getAccount(this, var72);
                  if(var80 == null) {
                     return;
                  }

                  Mailbox var81 = var80.getMailboxs().getDraftMailbox();
                  Mailbox var82 = var80.getMailboxs().getOutMailbox();
                  if(var81 == null) {
                     return;
                  }

                  if(var82 == null) {
                     return;
                  }

                  long var83 = var81.id;
                  if(var74 != var83) {
                     long var85 = var82.id;
                     if(var74 != var85) {
                        break label53;
                     }
                  }

                  var64 = true;
               }
            }
         }

         if(!var64) {
            Intent var87 = new Intent;
            String var89 = "android.intent.action.VIEW";
            var87.<init>(var89, var14);
            String var92 = "position";
            var87.putExtra(var92, var3);
            String var95 = this.mGlobalWhere;
            String var97 = "where";
            var87.putExtra(var97, var95);
            String var101 = "sortRule";
            String var102 = "_internaldate DESC";
            var87.putExtra(var101, var102);
            String var105 = "singleItem";
            var87.putExtra(var105, var4);
            this.startActivity(var87);
         } else {
            Intent var110 = new Intent;
            String var112 = "android.intent.action.MAIN";
            Class var115 = ComposeActivity.class;
            var110.<init>(var112, var14, this, var115);
            String var117 = "cmd";
            String var118 = "editdraft";
            var110.putExtra(var117, var118);
            this.startActivity(var110);
         }
      }
   }

   private void searchServerMail(long var1) {
      String var3 = "search server mail: " + var1;
      ll.d("SearchResultActivity", var3);
      String var4 = this.mQueryIntent.getStringExtra("intent_extra_data_key");
      if(TextUtils.isEmpty(var4)) {
         ll.d("SearchResultActivity", "data key null");
      } else {
         String var5 = Uri.parse(var4).getQueryParameter("searchKey");
         if(TextUtils.isEmpty(var5)) {
            ll.d("SearchResultActivity", "search key null");
         } else {
            Account var6 = MailProvider.getAccount(var1);
            if(var6 == null) {
               String var7 = "account null: " + var1;
               ll.e("SearchResultActivity", var7);
            } else {
               Mailbox var8 = var6.getMailboxs().getDefaultMailbox();
               if(var8 == null) {
                  String var9 = "mailboxs null: " + var1;
                  ll.e("SearchResultActivity", var9);
               } else {
                  long var10 = var6.id;
                  long var12 = var8.id;
                  Uri var14 = Uri.withAppendedPath(MailCommon.getMessagesUri(var10, var12), "default");
                  Intent var15 = new Intent("android.intent.action.MAIN", var14, this, MailSearch.class);
                  Intent var16 = var15.setFlags(67108864);
                  Intent var17 = var15.putExtra("searchSvrMailImmediately", (boolean)1);
                  var15.putExtra("searchKey", var5);
                  this.startActivity(var15);
               }
            }
         }
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(DEBUG) {
         String var4 = "onActivityResult>" + var1 + "," + var2 + "," + var3;
         ll.d("SearchResultActivity", var4);
      }

      switch(var1) {
      case 1:
         if(var2 == -1) {
            if(var3 == null) {
               return;
            } else {
               long var5 = var3.getLongExtra("accountId", 65535L);
               this.mAccountId = var5;
               long var7 = this.mAccountId;
               this.searchServerMail(var7);
            }
         }
      default:
         this.finish();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         ll.d("SearchResultActivity", "SearchResult on Create>");
      }

      boolean var2 = this.requestWindowFeature(1);
      this.setContentView(2130903074);
      View var3 = this.findViewById(33685952);
      if(var3 != null) {
         var3.setVisibility(8);
      }

      Title1 var4 = (Title1)this.findViewById(33685946);
      if(var4 != null) {
         var4.setVisibility(0);
         var4.setTitle1(2131362462);
      }

      this.mContext = this;
      Intent var5 = this.getIntent();
      this.mQueryIntent = var5;
      String var6 = this.mQueryIntent.getAction();
      Uri var7 = this.mQueryIntent.getData();
      Bundle var8 = this.mQueryIntent.getBundleExtra("app_data");
      if(var8 == null) {
         this.mAccountId = 65535L;
      } else {
         long var11 = var8.getLong("accountID", 65535L);
         this.mAccountId = var11;
      }

      if("android.intent.action.mail.search_server_mail".equals(var6)) {
         AccountPool var9 = AccountPool.getInstance(this);
         Context var10 = this.mContext;
         if(var9.getAccountCount(var10, 4) <= 0) {
            this.createExchangeAccount();
            this.finish();
            return;
         }
      }

      if(this.mAccountId == 65535L && MailProvider.getAccountCount((boolean)0) == 0) {
         this.finish();
      } else if("android.intent.action.VIEW".equals(var6)) {
         long var13;
         try {
            var13 = ContentUris.parseId(var7);
         } catch (Exception var23) {
            var23.printStackTrace();
            this.finish();
            return;
         }

         byte var17 = 0;
         if(this.mAccountId == 65535L) {
            var17 = 1;
         }

         this.finish();
         this.readMail(var13, 0, (boolean)var17);
      } else if("android.intent.action.mail.search_server_mail".equals(var6)) {
         AccountPool var18 = AccountPool.getInstance(this);
         Context var19 = this.mContext;
         Account[] var20 = var18.getAccounts(var19, 4);
         if(var20 != null && var20.length != 0) {
            if(var20.length == 1) {
               long var21 = var20[0].id;
               this.searchServerMail(var21);
               this.finish();
            } else {
               this.lunchAccountList();
            }
         } else {
            this.createExchangeAccount();
            this.finish();
         }
      }
   }

   public boolean onSearchRequested() {
      ll.d("SearchResultActivity", "onSearchRequested(), doing nothing...");
      return false;
   }
}
