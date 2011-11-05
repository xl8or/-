package com.facebook.katana.activity.messages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.MyTabHost;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.activity.messages.MailboxThreadsActivity;
import com.facebook.katana.activity.messages.MessageComposeActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.provider.MailboxProvider;

public class MailboxTabHostActivity extends BaseFacebookTabActivity implements MyTabHost.OnTabChangeListener {

   public static final String EXTRA_TAG = "extra_tag";
   public static final String TAG_INBOX = "inbox";
   public static final String TAG_OUTBOX = "outbox";
   public static final String TAG_UPDATES = "updates";
   private MailboxThreadsActivity mCurrentActivity;
   private MailboxTabHostActivity.ProgressListener mProgressListener;


   public MailboxTabHostActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(AppSession.getActiveSession(this, (boolean)1) == null) {
         Intent var2 = this.getIntent();
         LoginActivity.toLogin(this, var2);
         this.finish();
      } else {
         this.setContentView(2130903167);
         this.setPrimaryActionIcon(2130837754);
         MyTabHost var3 = (MyTabHost)this.getTabHost();
         Intent var4 = new Intent(this, MailboxThreadsActivity.class);
         Uri var5 = MailboxProvider.INBOX_THREADS_CONTENT_URI;
         var4.setData(var5);
         Intent var7 = var4.putExtra("within_tab", (boolean)1);
         String var8 = this.getTag();
         var4.putExtra("extra_parent_tag", var8);
         Intent var10 = var4.putExtra("extra_folder", 0);
         RadioButton var11 = this.setupAndGetTabView("inbox", 2131361966);
         MyTabHost.TabSpec var12 = var3.myNewTabSpec("inbox", var11);
         var12.setContent(var4);
         var3.addTab(var12);
         Intent var14 = new Intent(this, MailboxThreadsActivity.class);
         Uri var15 = MailboxProvider.UPDATES_THREADS_CONTENT_URI;
         var14.setData(var15);
         Intent var17 = var14.putExtra("within_tab", (boolean)1);
         String var18 = this.getTag();
         var14.putExtra("extra_parent_tag", var18);
         Intent var20 = var14.putExtra("extra_folder", 4);
         RadioButton var21 = this.setupAndGetTabView("updates", 2131361983);
         MyTabHost.TabSpec var22 = var3.myNewTabSpec("updates", var21);
         var22.setContent(var14);
         var3.addTab(var22);
         Intent var24 = new Intent(this, MailboxThreadsActivity.class);
         Intent var25 = var24.putExtra("within_tab", (boolean)1);
         String var26 = this.getTag();
         var24.putExtra("extra_parent_tag", var26);
         Uri var28 = MailboxProvider.OUTBOX_THREADS_CONTENT_URI;
         var24.setData(var28);
         Intent var30 = var24.putExtra("extra_folder", 1);
         RadioButton var31 = this.setupAndGetTabView("outbox", 2131361975);
         MyTabHost.TabSpec var32 = var3.myNewTabSpec("outbox", var31);
         var32.setContent(var24);
         var3.addTab(var32);
         MailboxTabHostActivity.1 var34 = new MailboxTabHostActivity.1();
         this.mProgressListener = var34;
         MailboxThreadsActivity var35 = (MailboxThreadsActivity)this.getCurrentActivity();
         this.mCurrentActivity = var35;
         MailboxThreadsActivity var36 = this.mCurrentActivity;
         MailboxTabHostActivity.ProgressListener var37 = this.mProgressListener;
         var36.setProgressListener(var37);
         String var38 = this.getIntent().getStringExtra("extra_tag");
         if(var38 != null) {
            var3.setCurrentTabByTag(var38);
            this.onTabChanged(var38);
         } else {
            var3.setCurrentTabByTag("inbox");
            this.onTabChanged("inbox");
         }

         var3.setOnTabChangedListener(this);
         this.setupTabs();
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mCurrentActivity != null) {
         this.mCurrentActivity.setProgressListener((MailboxTabHostActivity.ProgressListener)null);
      }
   }

   public void onTabChanged(String var1) {
      if(this.mCurrentActivity != null) {
         this.mCurrentActivity.setProgressListener((MailboxTabHostActivity.ProgressListener)null);
      }

      MailboxThreadsActivity var2 = (MailboxThreadsActivity)this.getCurrentActivity();
      this.mCurrentActivity = var2;
      MailboxThreadsActivity var3 = this.mCurrentActivity;
      MailboxTabHostActivity.ProgressListener var4 = this.mProgressListener;
      var3.setProgressListener(var4);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      Intent var2 = new Intent(this, MessageComposeActivity.class);
      this.startActivity(var2);
   }

   public interface ProgressListener {

      void onShowProgress(boolean var1);
   }

   class 1 implements MailboxTabHostActivity.ProgressListener {

      1() {}

      public void onShowProgress(boolean var1) {
         View var2 = MailboxTabHostActivity.this.findViewById(2131624177);
         byte var3;
         if(var1) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var2.setVisibility(var3);
      }
   }
}
