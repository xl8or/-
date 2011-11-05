package com.htc.android.mail;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.IContentProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import com.htc.preference.HtcCheckBoxPreference;
import com.htc.preference.HtcPreference;
import com.htc.preference.HtcPreferenceActivity;
import com.htc.preference.HtcPreferenceCategory;
import com.htc.preference.HtcPreferenceScreen;
import com.htc.preference.HtcPreference.OnPreferenceChangeListener;

public class FetchTypeSettings2 extends HtcPreferenceActivity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "FetchTypeSettings";
   private final int AMOUNT_BASE = 0;
   private final int DAY_BASE = 1;
   int fetchMailDays;
   int fetchMailNum;
   HtcCheckBoxPreference[] item;
   private long mAccountId = 65535L;
   private HtcCheckBoxPreference mByAmountCheckBox;
   private HtcCheckBoxPreference mByDayCheckBox;
   private int mFetchMailType;
   int newAmount;
   int newDay;
   int newType;
   int oriType;
   HtcPreferenceCategory title;


   public FetchTypeSettings2() {
      HtcCheckBoxPreference[] var1 = new HtcCheckBoxPreference[6];
      this.item = var1;
   }

   private void bind() {
      Uri var1 = this.getIntent().getData();
      long var2 = ContentUris.parseId(var1);
      this.mAccountId = var2;
      if(DEBUG) {
         StringBuilder var4 = (new StringBuilder()).append("mAccountId>");
         long var5 = this.mAccountId;
         String var7 = var4.append(var5).append(",").append(var1).toString();
         ll.d("FetchTypeSettings", var7);
      }

      Account var8 = MailProvider.getAccount(this.mAccountId);
      if(var8 != null) {
         int var9 = var8.fetchMailType;
         this.mFetchMailType = var9;
         int var10 = var8.fetchMailNumIndex;
         this.fetchMailNum = var10;
         int var11 = var8.fetchMailDaysIndex;
         this.fetchMailDays = var11;
         if(DEBUG) {
            StringBuilder var12 = (new StringBuilder()).append("type>");
            int var13 = this.mFetchMailType;
            StringBuilder var14 = var12.append(var13).append(",");
            int var15 = this.fetchMailNum;
            StringBuilder var16 = var14.append(var15).append(",");
            int var17 = this.fetchMailDays;
            String var18 = var16.append(var17).toString();
            ll.d("FetchTypeSettings", var18);
         }

         HtcPreferenceScreen var19 = this.getPreferenceScreen();
         HtcPreferenceCategory var20 = (HtcPreferenceCategory)var19.findPreference("fetch_item");
         this.title = var20;
         HtcCheckBoxPreference var21 = (HtcCheckBoxPreference)var19.findPreference("account_detail_fetch_mail_by_amount_checkbox");
         this.mByAmountCheckBox = var21;
         HtcCheckBoxPreference var22 = (HtcCheckBoxPreference)var19.findPreference("account_detail_fetch_mail_by_day_checkbox");
         this.mByDayCheckBox = var22;
         HtcCheckBoxPreference[] var23 = this.item;
         HtcCheckBoxPreference var24 = (HtcCheckBoxPreference)var19.findPreference("item_0");
         var23[0] = var24;
         HtcCheckBoxPreference[] var25 = this.item;
         HtcCheckBoxPreference var26 = (HtcCheckBoxPreference)var19.findPreference("item_1");
         var25[1] = var26;
         HtcCheckBoxPreference[] var27 = this.item;
         HtcCheckBoxPreference var28 = (HtcCheckBoxPreference)var19.findPreference("item_2");
         var27[2] = var28;
         HtcCheckBoxPreference[] var29 = this.item;
         HtcCheckBoxPreference var30 = (HtcCheckBoxPreference)var19.findPreference("item_3");
         var29[3] = var30;
         HtcCheckBoxPreference[] var31 = this.item;
         HtcCheckBoxPreference var32 = (HtcCheckBoxPreference)var19.findPreference("item_4");
         var31[4] = var32;
         HtcCheckBoxPreference[] var33 = this.item;
         HtcCheckBoxPreference var34 = (HtcCheckBoxPreference)var19.findPreference("item_5");
         var33[5] = var34;
         HtcCheckBoxPreference var35 = this.mByAmountCheckBox;
         FetchTypeSettings2.1 var36 = new FetchTypeSettings2.1();
         var35.setOnPreferenceChangeListener(var36);
         HtcCheckBoxPreference var37 = this.mByDayCheckBox;
         FetchTypeSettings2.2 var38 = new FetchTypeSettings2.2();
         var37.setOnPreferenceChangeListener(var38);
         HtcCheckBoxPreference var39 = this.item[0];
         FetchTypeSettings2.3 var40 = new FetchTypeSettings2.3();
         var39.setOnPreferenceChangeListener(var40);
         HtcCheckBoxPreference var41 = this.item[1];
         FetchTypeSettings2.4 var42 = new FetchTypeSettings2.4();
         var41.setOnPreferenceChangeListener(var42);
         HtcCheckBoxPreference var43 = this.item[2];
         FetchTypeSettings2.5 var44 = new FetchTypeSettings2.5();
         var43.setOnPreferenceChangeListener(var44);
         HtcCheckBoxPreference var45 = this.item[3];
         FetchTypeSettings2.6 var46 = new FetchTypeSettings2.6();
         var45.setOnPreferenceChangeListener(var46);
         HtcCheckBoxPreference var47 = this.item[4];
         FetchTypeSettings2.7 var48 = new FetchTypeSettings2.7();
         var47.setOnPreferenceChangeListener(var48);
         HtcCheckBoxPreference var49 = this.item[5];
         FetchTypeSettings2.8 var50 = new FetchTypeSettings2.8();
         var49.setOnPreferenceChangeListener(var50);
         if((this.mFetchMailType & 1) == 0) {
            if(DEBUG) {
               ll.d("FetchTypeSettings", "create amount>");
            }

            this.mByAmountCheckBox.setChecked((boolean)1);
            this.mByDayCheckBox.setChecked((boolean)0);
            this.oriType = 0;
            this.newType = 0;
            int var51 = this.fetchMailNum;
            this.newAmount = var51;
            int var52 = this.fetchMailDays;
            this.newDay = var52;
            int var53 = this.oriType;
            this.setupItem(var53);
            int var54 = this.newAmount;
            this.checkItem(var54);
         } else {
            if(DEBUG) {
               ll.d("FetchTypeSettings", "create day>");
            }

            this.mByDayCheckBox.setChecked((boolean)1);
            this.mByAmountCheckBox.setChecked((boolean)0);
            this.oriType = 1;
            this.newType = 1;
            int var55 = this.fetchMailDays;
            this.newDay = var55;
            int var56 = this.fetchMailNum;
            this.newAmount = var56;
            int var57 = this.oriType;
            this.setupItem(var57);
            int var58 = this.newDay;
            this.checkItem(var58);
         }
      }
   }

   private final ContentValues gatherValues() {
      ContentValues var1 = new ContentValues();
      Account var2 = MailProvider.getAccount(this.mAccountId);
      this.setResult(-1);
      int var3 = this.newType;
      int var4 = this.oriType;
      int var5;
      if(var3 != var4) {
         var5 = this.newType * 1 + 2;
         this.setResult(2);
      } else {
         var5 = this.newType * 1;
      }

      if(DEBUG) {
         StringBuilder var6 = (new StringBuilder()).append("gatherValues>");
         int var7 = this.newType;
         String var8 = var6.append(var7).append(",").append(var5).toString();
         ll.d("FetchTypeSettings", var8);
      }

      Integer var9 = Integer.valueOf(var5);
      var1.put("_fetchMailType", var9);
      var2.fetchMailType = var5;
      if(this.newType == 0) {
         Integer var10 = Integer.valueOf(this.newAmount);
         var1.put("_fetchMailNum", var10);
         Integer var11 = Integer.valueOf(this.fetchMailDays);
         var1.put("_fetchMailDays", var11);
         int var12 = this.newAmount;
         var2.fetchMailNumIndex = var12;
         int var13 = this.fetchMailDays;
         var2.fetchMailDaysIndex = var13;
         int var14 = this.fetchMailNum;
         int var15 = this.newAmount;
         if(var14 != var15) {
            this.setResult(2);
         }
      } else {
         Integer var16 = Integer.valueOf(this.fetchMailNum);
         var1.put("_fetchMailNum", var16);
         Integer var17 = Integer.valueOf(this.newDay);
         var1.put("_fetchMailDays", var17);
         int var18 = this.fetchMailNum;
         var2.fetchMailNumIndex = var18;
         int var19 = this.newDay;
         var2.fetchMailDaysIndex = var19;
         int var20 = this.fetchMailDays;
         int var21 = this.newDay;
         if(var20 != var21) {
            this.setResult(2);
         }
      }

      return var1;
   }

   private final void updateAccount() {
      ContentValues var1 = this.gatherValues();
      Uri var2 = this.getIntent().getData();
      IContentProvider var3 = MailProvider.instance();

      try {
         var3.update(var2, var1, (String)null, (String[])null);
      } catch (DeadObjectException var7) {
         ;
      } catch (RemoteException var8) {
         ;
      }

      Mail.setServicesEnabled(this);
   }

   void checkItem(int var1) {
      for(int var2 = 0; var2 < 6; ++var2) {
         this.item[var2].setChecked((boolean)0);
      }

      this.item[var1].setChecked((boolean)1);
      if(this.newType == 1) {
         this.newDay = var1;
      } else {
         this.newAmount = var1;
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968586);
      this.bind();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         this.updateAccount();
      }

      return super.onKeyDown(var1, var2);
   }

   protected void onPause() {
      super.onPause();
   }

   protected final void onResume() {
      super.onResume();
   }

   void setupItem(int var1) {
      switch(var1) {
      case 0:
         this.item[0].setTitle(2131362176);
         this.item[1].setTitle(2131362177);
         this.item[2].setTitle(2131362178);
         this.item[3].setTitle(2131362179);
         this.item[4].setTitle(2131362180);
         this.item[5].setTitle(2131362181);
         this.title.setTitle(2131361982);
         return;
      case 1:
         this.item[0].setTitle(2131362182);
         this.item[1].setTitle(2131362183);
         this.item[2].setTitle(2131362184);
         this.item[3].setTitle(2131362185);
         this.item[4].setTitle(2131362186);
         this.item[5].setTitle(2131362187);
         this.title.setTitle(2131361983);
         return;
      default:
      }
   }

   class 2 implements OnPreferenceChangeListener {

      2() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         boolean var3;
         if(FetchTypeSettings2.this.newType == 1) {
            var3 = false;
         } else {
            FetchTypeSettings2.this.newType = 1;
            FetchTypeSettings2.this.mByAmountCheckBox.setChecked((boolean)0);
            FetchTypeSettings2.this.setupItem(1);
            FetchTypeSettings2 var4 = FetchTypeSettings2.this;
            int var5 = FetchTypeSettings2.this.newDay;
            var4.checkItem(var5);
            FetchTypeSettings2.this.mByDayCheckBox.setChecked((boolean)1);
            if(FetchTypeSettings2.DEBUG) {
               StringBuilder var6 = (new StringBuilder()).append("set day>").append(var2).append(",");
               boolean var7 = FetchTypeSettings2.this.mByDayCheckBox.isChecked();
               StringBuilder var8 = var6.append(var7).append(",");
               boolean var9 = FetchTypeSettings2.this.mByAmountCheckBox.isChecked();
               String var10 = var8.append(var9).toString();
               ll.d("FetchTypeSettings", var10);
            }

            var3 = false;
         }

         return var3;
      }
   }

   class 1 implements OnPreferenceChangeListener {

      1() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         boolean var3;
         if(FetchTypeSettings2.this.newType == 0) {
            var3 = false;
         } else {
            FetchTypeSettings2.this.newType = 0;
            FetchTypeSettings2.this.mByDayCheckBox.setChecked((boolean)0);
            FetchTypeSettings2.this.setupItem(0);
            FetchTypeSettings2 var4 = FetchTypeSettings2.this;
            int var5 = FetchTypeSettings2.this.newAmount;
            var4.checkItem(var5);
            FetchTypeSettings2.this.mByAmountCheckBox.setChecked((boolean)1);
            if(FetchTypeSettings2.DEBUG) {
               StringBuilder var6 = (new StringBuilder()).append("set mount>").append(var2).append(",");
               boolean var7 = FetchTypeSettings2.this.mByDayCheckBox.isChecked();
               StringBuilder var8 = var6.append(var7).append(",");
               boolean var9 = FetchTypeSettings2.this.mByAmountCheckBox.isChecked();
               String var10 = var8.append(var9).toString();
               ll.d("FetchTypeSettings", var10);
            }

            var3 = false;
         }

         return var3;
      }
   }

   class 6 implements OnPreferenceChangeListener {

      6() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(3);
         return false;
      }
   }

   class 5 implements OnPreferenceChangeListener {

      5() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(2);
         return false;
      }
   }

   class 4 implements OnPreferenceChangeListener {

      4() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(1);
         return false;
      }
   }

   class 3 implements OnPreferenceChangeListener {

      3() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(0);
         return false;
      }
   }

   class 7 implements OnPreferenceChangeListener {

      7() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(4);
         return false;
      }
   }

   class 8 implements OnPreferenceChangeListener {

      8() {}

      public boolean onPreferenceChange(HtcPreference var1, Object var2) {
         FetchTypeSettings2.this.checkItem(5);
         return false;
      }
   }
}
