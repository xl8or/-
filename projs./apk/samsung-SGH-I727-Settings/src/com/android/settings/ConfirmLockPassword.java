package com.android.settings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.PasswordEntryKeyboardHelper;
import com.android.internal.widget.PasswordEntryKeyboardView;

public class ConfirmLockPassword extends Activity implements OnClickListener, OnEditorActionListener {

   private static final long ERROR_MESSAGE_TIMEOUT = 3000L;
   private Handler mHandler;
   private TextView mHeaderText;
   private PasswordEntryKeyboardHelper mKeyboardHelper;
   private PasswordEntryKeyboardView mKeyboardView;
   private LockPatternUtils mLockPatternUtils;
   private TextView mPasswordEntry;


   public ConfirmLockPassword() {
      Handler var1 = new Handler();
      this.mHandler = var1;
   }

   private void handleNext() {
      String var1 = this.mPasswordEntry.getText().toString();
      if(this.mLockPatternUtils.checkPassword(var1)) {
         this.setResult(-1);
         this.finish();
      } else {
         this.showError(2131231554);
      }
   }

   private void initViews() {
      int var1 = this.mLockPatternUtils.getKeyguardStoredPasswordQuality();
      this.setContentView(2130903065);
      this.getWindow().setFlags(131072, 131072);
      this.findViewById(2131427375).setOnClickListener(this);
      this.findViewById(2131427369).setOnClickListener(this);
      TextView var2 = (TextView)this.findViewById(2131427373);
      this.mPasswordEntry = var2;
      this.mPasswordEntry.setOnEditorActionListener(this);
      this.mPasswordEntry.showCursorController((boolean)0);
      TextView var3 = this.mPasswordEntry;
      ConfirmLockPassword.1 var4 = new ConfirmLockPassword.1();
      var3.setOnLongClickListener(var4);
      PasswordEntryKeyboardView var5 = (PasswordEntryKeyboardView)this.findViewById(2131427374);
      this.mKeyboardView = var5;
      TextView var6 = (TextView)this.findViewById(2131427371);
      this.mHeaderText = var6;
      boolean var7;
      if(262144 != var1 && 327680 != var1) {
         var7 = false;
      } else {
         var7 = true;
      }

      TextView var8 = this.mHeaderText;
      int var9;
      if(var7) {
         var9 = 2131231542;
      } else {
         var9 = 2131231543;
      }

      var8.setText(var9);
      PasswordEntryKeyboardView var10 = this.mKeyboardView;
      TextView var11 = this.mPasswordEntry;
      PasswordEntryKeyboardHelper var12 = new PasswordEntryKeyboardHelper(this, var10, var11);
      this.mKeyboardHelper = var12;
      PasswordEntryKeyboardHelper var13 = this.mKeyboardHelper;
      byte var14;
      if(var7) {
         var14 = 0;
      } else {
         var14 = 1;
      }

      var13.setKeyboardMode(var14);
      PasswordEntryKeyboardHelper var15 = this.mKeyboardHelper;
      int var16;
      if(this.mLockPatternUtils.isTactileFeedbackEnabled()) {
         var16 = 17235991;
      } else {
         var16 = 0;
      }

      var15.setVibratePattern(var16);
      boolean var17 = this.mKeyboardView.requestFocus();
   }

   private void showError(int var1) {
      this.mHeaderText.setText(var1);
      this.mPasswordEntry.setText((CharSequence)null);
      Handler var2 = this.mHandler;
      ConfirmLockPassword.2 var3 = new ConfirmLockPassword.2();
      var2.postDelayed(var3, 3000L);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131427369:
         this.handleNext();
         return;
      case 2131427375:
         this.setResult(0);
         this.finish();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      LockPatternUtils var2 = new LockPatternUtils(this);
      this.mLockPatternUtils = var2;
      this.initViews();
   }

   public boolean onEditorAction(TextView var1, int var2, KeyEvent var3) {
      boolean var4;
      if(var2 == 0) {
         this.handleNext();
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   protected void onPause() {
      super.onPause();
      boolean var1 = this.mKeyboardView.requestFocus();
   }

   protected void onResume() {
      super.onResume();
      boolean var1 = this.mKeyboardView.requestFocus();
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         int var1 = ConfirmLockPassword.this.mLockPatternUtils.getKeyguardStoredPasswordQuality();
         boolean var2;
         if(262144 != var1 && 327680 != var1) {
            var2 = false;
         } else {
            var2 = true;
         }

         TextView var3 = ConfirmLockPassword.this.mHeaderText;
         int var4;
         if(var2) {
            var4 = 2131231542;
         } else {
            var4 = 2131231543;
         }

         var3.setText(var4);
      }
   }

   class 1 implements OnLongClickListener {

      1() {}

      public boolean onLongClick(View var1) {
         return true;
      }
   }
}
