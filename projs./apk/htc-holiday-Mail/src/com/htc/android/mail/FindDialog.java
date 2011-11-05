package com.htc.android.mail;

import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.htc.android.mail.ReadScreen;
import com.htc.android.mail.ll;

class FindDialog extends Dialog implements TextWatcher {

   String TAG = "FindDialog";
   private EditText mEditText;
   private OnClickListener mFindCancelListener;
   private OnClickListener mFindListener;
   private OnClickListener mFindPreviousListener;
   private TextView mMatches;
   private View mMatchesView;
   private ImageButton mNextButton;
   private View mOk;
   private ImageButton mPrevButton;
   private ReadScreen mReadScreenActivity;
   private WebView mWebView;


   FindDialog(ReadScreen var1) {
      super(var1, 2131492971);
      FindDialog.1 var2 = new FindDialog.1();
      this.mFindListener = var2;
      FindDialog.2 var3 = new FindDialog.2();
      this.mFindCancelListener = var3;
      FindDialog.3 var4 = new FindDialog.3();
      this.mFindPreviousListener = var4;
      this.mReadScreenActivity = var1;
      this.setCanceledOnTouchOutside((boolean)1);
   }

   private void disableButtons() {
      this.mPrevButton.setEnabled((boolean)0);
      this.mNextButton.setEnabled((boolean)0);
      this.mPrevButton.setAlpha(50);
      this.mNextButton.setAlpha(50);
      this.mPrevButton.setFocusable((boolean)0);
      this.mNextButton.setFocusable((boolean)0);
      ll.d(this.TAG, "disableButtons>");
   }

   private void findNext() {
      if(this.mWebView == null) {
         throw new AssertionError("No WebView for FindDialog::findNext");
      } else {
         this.mWebView.findNext((boolean)1);
         this.hideSoftInput();
      }
   }

   private void hideSoftInput() {
      InputMethodManager var1 = (InputMethodManager)this.mReadScreenActivity.getSystemService("input_method");
      IBinder var2 = this.mEditText.getWindowToken();
      var1.hideSoftInputFromWindow(var2, 0);
   }

   private void setMatchesFound(int var1) {
      String var2 = this.TAG;
      String var3 = "setMatchesFound>" + var1;
      ll.d(var2, var3);
      Resources var4 = this.mReadScreenActivity.getResources();
      Object[] var5 = new Object[1];
      Integer var6 = Integer.valueOf(var1);
      var5[0] = var6;
      String var7 = var4.getQuantityString(2131427328, var1, var5);
      this.mMatches.setText(var7);
   }

   public void afterTextChanged(Editable var1) {}

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void dismiss() {
      super.dismiss();
      this.mMatchesView.setVisibility(4);
      this.mWebView.clearMatches();
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      boolean var3;
      if(var1.getAction() == 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      switch(var2) {
      case 23:
      case 66:
         if(this.mEditText.hasFocus()) {
            if(var3) {
               this.findNext();
            }

            var4 = true;
            break;
         }
      default:
         var4 = super.dispatchKeyEvent(var1);
      }

      return var4;
   }

   void onConfigurationChanged(Configuration var1) {
      this.mEditText.getText().clear();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Window var2 = this.getWindow();
      var2.setGravity(87);
      this.setContentView(2130903046);
      var2.setLayout(-1, -1);
      EditText var3 = (EditText)this.findViewById(2131296282);
      this.mEditText = var3;
      ImageButton var4 = (ImageButton)this.findViewById(2131296285);
      this.mNextButton = var4;
      ImageButton var5 = this.mNextButton;
      OnClickListener var6 = this.mFindListener;
      var5.setOnClickListener(var6);
      ImageButton var7 = (ImageButton)this.findViewById(2131296284);
      this.mPrevButton = var7;
      ImageButton var8 = this.mPrevButton;
      OnClickListener var9 = this.mFindPreviousListener;
      var8.setOnClickListener(var9);
      View var10 = this.findViewById(2131296283);
      OnClickListener var11 = this.mFindCancelListener;
      var10.setOnClickListener(var11);
      this.mOk = var10;
      TextView var12 = (TextView)this.findViewById(2131296287);
      this.mMatches = var12;
      View var13 = this.findViewById(2131296286);
      this.mMatchesView = var13;
      this.disableButtons();
      var2.setSoftInputMode(4);
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      if(this.mWebView == null) {
         throw new AssertionError("No WebView for FindDialog::onTextChanged");
      } else {
         Editable var5 = this.mEditText.getText();
         String var6 = this.TAG;
         StringBuilder var7 = (new StringBuilder()).append(" length =");
         int var8 = var5.length();
         String var9 = var7.append(var8).toString();
         ll.d(var6, var9);
         if(var5.length() == 0) {
            this.disableButtons();
            this.mWebView.clearMatches();
            this.mMatchesView.setVisibility(4);
            this.mOk.setVisibility(8);
            ll.d(this.TAG, "length 0");
         } else {
            ll.d(this.TAG, "length > 0");
            this.mOk.setVisibility(0);
            this.mMatchesView.setVisibility(0);
            WebView var10 = this.mWebView;
            String var11 = var5.toString();
            int var12 = var10.findAll(var11);
            this.setMatchesFound(var12);
            if(var12 < 2) {
               this.disableButtons();
               if(var12 == 0) {
                  this.setMatchesFound(0);
               }
            } else {
               this.mPrevButton.setFocusable((boolean)1);
               this.mNextButton.setFocusable((boolean)1);
               this.mPrevButton.setAlpha(255);
               this.mNextButton.setAlpha(255);
               this.mPrevButton.setEnabled((boolean)1);
               this.mNextButton.setEnabled((boolean)1);
               ll.d(this.TAG, "255 >");
            }
         }
      }
   }

   void setWebView(WebView var1) {
      this.mWebView = var1;
   }

   public void show() {
      int var1 = this.mWebView.getHeight();
      super.show();
      boolean var2 = this.mEditText.requestFocus();
      this.mEditText.setText("");
      Editable var3 = this.mEditText.getText();
      int var4 = var3.length();
      var3.setSpan(this, 0, var4, 18);
      this.setMatchesFound(0);
      this.disableButtons();
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         if(FindDialog.this.mWebView == null) {
            throw new AssertionError("No WebView for FindDialog::onClick");
         } else {
            FindDialog.this.mWebView.findNext((boolean)0);
            FindDialog.this.hideSoftInput();
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         if(FindDialog.this.mEditText.getText().length() > 0) {
            FindDialog.this.mEditText.getText().clear();
         } else {
            FindDialog.this.dismiss();
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         FindDialog.this.findNext();
      }
   }
}
