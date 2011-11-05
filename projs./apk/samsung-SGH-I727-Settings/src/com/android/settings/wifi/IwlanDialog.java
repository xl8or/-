package com.android.settings.wifi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.settings.wifi.IwlanNetwork;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class IwlanDialog extends AlertDialog implements OnClickListener, TextWatcher, OnItemSelectedListener {

   private static final String ACTION_IWLAN_ACTIVITY_REQUEST_CONNECT_IWLAN_NETWORK = "actoin.IWLAN_ACTIVITY_REQUEST_CONNECT_IWLAN_NETWORK";
   private static final String ACTION_IWLAN_ACTIVITY_REQUEST_DELETE_IWLAN_NETWORK = "actoin.IWLAN_ACTIVITY_REQUEST_DELETE_IWLAN_NETWORK";
   private static final String ACTION_IWLAN_ACTIVITY_REQUEST_DISCONNECT_IWLAN_NETWORK = "actoin.IWLAN_ACTIVITY_REQUEST_DISCONNECT_IWLAN_NETWORK";
   private static final String ACTION_IWLAN_ACTIVITY_REQUEST_SAVE_IWLAN_NETWORK = "actoin.IWLAN_ACTIVITY_REQUEST_SAVE_IWLAN_NETWORK";
   private static final String EXTRA_IWLAN_CONNECT_FROM_DIALOG = "extra.IWLAN_CONNECT_FROM_DIALOG";
   private static final String EXTRA_IWLAN_PDG_ADDRESS = "extra.IWLAN_PDG_ADDRESS";
   private static final String EXTRA_IWLAN_PDG_NAME = "extra.IWLAN_PDG_NAME";
   private static final String FORMAT_IP_ADDRESS_EDIT = "^[0-9.]*$";
   private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
   final boolean edit;
   private Context mContext;
   private boolean mIsValid = 1;
   private final IwlanNetwork mIwlanNetwork;
   private TextView mPdgAddress;
   private TextView mPdgName;
   private View mView;


   public IwlanDialog(Context var1, IwlanNetwork var2, boolean var3) {
      super(var1);
      this.edit = var3;
      this.mIwlanNetwork = var2;
      this.mContext = var1;
   }

   private void addRow(ViewGroup var1, int var2, String var3) {
      View var4 = this.getLayoutInflater().inflate(2130903081, var1, (boolean)0);
      ((TextView)var4.findViewById(2131427398)).setText(var2);
      ((TextView)var4.findViewById(2131427460)).setText(var3);
      var1.addView(var4);
   }

   private boolean isIPv4Address(String var1) {
      boolean var2;
      if(IPV4_PATTERN.matcher(var1).matches()) {
         var2 = true;
      } else {
         String var3 = this.mContext.getString(2131231181);
         this.showAlertDialog(var3);
         var2 = false;
      }

      return var2;
   }

   private boolean isNameValid(String var1) {
      boolean var3;
      if(var1 != null && !var1.equals("")) {
         int var4 = 0;

         while(true) {
            int var5 = var1.length();
            if(var4 >= var5) {
               var3 = true;
               break;
            }

            char var6 = var1.charAt(var4);
            if((var6 < 97 || var6 > 122) && (var6 < 65 || var6 > 90) && (var6 < 48 || var6 > 57) && var6 != 46) {
               String var7 = this.mContext.getString(2131231179);
               this.showAlertDialog(var7);
               var3 = false;
               break;
            }

            ++var4;
         }
      } else {
         String var2 = this.mContext.getString(2131231178);
         this.showAlertDialog(var2);
         var3 = false;
      }

      return var3;
   }

   private void showAlertDialog(String var1) {
      Context var2 = this.mContext;
      Builder var3 = (new Builder(var2)).setMessage(var1);
      String var4 = this.mContext.getString(2131231187);
      IwlanDialog.2 var5 = new IwlanDialog.2();
      AlertDialog var6 = var3.setPositiveButton(var4, var5).show();
   }

   public void afterTextChanged(Editable var1) {}

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void dismiss() {
      if(this.mIwlanNetwork != null || this.mIsValid) {
         super.dismiss();
      }
   }

   public void onBackPressed() {
      this.mIsValid = (boolean)1;
      this.dismiss();
      super.onBackPressed();
   }

   public void onClick(DialogInterface var1, int var2) {
      if(var2 == -1 && this.mIwlanNetwork != null) {
         String var3 = this.mIwlanNetwork.getPdgDomainName();
         Intent var4 = new Intent("actoin.IWLAN_ACTIVITY_REQUEST_DELETE_IWLAN_NETWORK");
         var4.putExtra("extra.IWLAN_PDG_NAME", var3);
         this.mContext.sendBroadcast(var4);
      } else if(var2 == -1) {
         if(this.mIwlanNetwork == null) {
            String var6 = this.mPdgName.getText().toString();
            String var7 = this.mPdgAddress.getText().toString();
            if(this.isNameValid(var6) && this.isIPv4Address(var7)) {
               this.mIsValid = (boolean)1;
               Intent var8 = new Intent("actoin.IWLAN_ACTIVITY_REQUEST_SAVE_IWLAN_NETWORK");
               var8.putExtra("extra.IWLAN_PDG_NAME", var6);
               var8.putExtra("extra.IWLAN_PDG_ADDRESS", var7);
               this.mContext.sendBroadcast(var8);
            } else {
               this.mIsValid = (boolean)0;
            }
         } else if(this.mIwlanNetwork != null && this.mIwlanNetwork.isConnected()) {
            Intent var11 = new Intent("actoin.IWLAN_ACTIVITY_REQUEST_DISCONNECT_IWLAN_NETWORK");
            this.mContext.sendBroadcast(var11);
         } else if(this.mIwlanNetwork != null) {
            if(!this.mIwlanNetwork.isConnected()) {
               String var12 = this.mIwlanNetwork.getPdgDomainName();
               Intent var13 = new Intent("actoin.IWLAN_ACTIVITY_REQUEST_CONNECT_IWLAN_NETWORK");
               var13.putExtra("extra.IWLAN_PDG_NAME", var12);
               Intent var15 = var13.putExtra("extra.IWLAN_CONNECT_FROM_DIALOG", (boolean)1);
               this.mContext.sendBroadcast(var13);
            }
         }
      } else if(var2 == -1) {
         this.mIsValid = (boolean)1;
      }
   }

   protected void onCreate(Bundle var1) {
      View var2 = this.getLayoutInflater().inflate(2130903080, (ViewGroup)null);
      this.mView = var2;
      View var3 = this.mView;
      this.setView(var3);
      this.setInverseBackgroundForced((boolean)1);
      if(this.mIwlanNetwork == null) {
         this.setTitle(2131231167);
         this.mView.findViewById(2131427457).setVisibility(0);
         TextView var4 = (TextView)this.mView.findViewById(2131427458);
         this.mPdgName = var4;
         this.mPdgName.addTextChangedListener(this);
         TextView var5 = (TextView)this.mView.findViewById(2131427459);
         this.mPdgAddress = var5;
         InputFilter[] var6 = new InputFilter[1];
         IwlanDialog.1 var7 = new IwlanDialog.1();
         var6[0] = var7;
         this.mPdgAddress.setFilters(var6);
         this.mPdgAddress.addTextChangedListener(this);
         String var8 = this.mContext.getString(2131231185);
         this.setButton(-1, var8, this);
         String var9 = this.mContext.getString(2131231186);
         this.setButton(-1, var9, this);
      } else {
         String var10 = this.mIwlanNetwork.getPdgDomainName();
         String var11 = this.mIwlanNetwork.getDefaultPdgDomainName();
         if(var10.equals(var11)) {
            String var12 = this.mContext.getString(2131231165);
            this.setTitle(var12);
         } else {
            String var18 = this.mIwlanNetwork.getPdgDomainName();
            this.setTitle(var18);
         }

         ViewGroup var13 = (ViewGroup)this.mView.findViewById(2131427456);
         var13.setVisibility(0);
         String var14 = this.mIwlanNetwork.getPdgDomainName();
         if(var14 != null) {
            this.addRow(var13, 2131231175, var14);
         }

         String var15 = this.mIwlanNetwork.getPdgIpAddress();
         if(var15 != null) {
            this.addRow(var13, 2131231176, var15);
         }

         if(this.mIwlanNetwork.isConnected()) {
            String var16 = this.mContext.getString(2131231183);
            this.setButton(-1, var16, this);
            String var17 = this.mContext.getString(2131231187);
            this.setButton(-1, var17, this);
         } else {
            String var19 = this.mContext.getString(2131231182);
            this.setButton(-1, var19, this);
            String var20 = this.mContext.getString(2131231184);
            this.setButton(-1, var20, this);
         }
      }

      super.onCreate(var1);
   }

   public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {}

   public void onNothingSelected(AdapterView<?> var1) {}

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void onWindowFocusChanged(boolean var1) {
      super.onWindowFocusChanged(var1);
      if(this.mIwlanNetwork != null) {
         String var2 = this.mIwlanNetwork.getPdgDomainName();
         String var3 = this.mIwlanNetwork.getDefaultPdgDomainName();
         if(var2.equals(var3)) {
            if(!this.mIwlanNetwork.isConnected()) {
               this.getButton(-1).setEnabled((boolean)0);
            }
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }

   class 1 implements InputFilter {

      1() {}

      public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
         String var7 = new String();
         StringBuilder var8 = (new StringBuilder()).append(var7);
         CharSequence var9 = var4.subSequence(0, var5);
         String var10 = var8.append(var9).toString();
         StringBuilder var11 = (new StringBuilder()).append(var10);
         CharSequence var12 = var1.subSequence(var2, var3);
         String var13 = var11.append(var12).toString();
         StringBuilder var14 = (new StringBuilder()).append(var13);
         int var15 = var4.length();
         CharSequence var16 = var4.subSequence(var6, var15);
         String var17 = var14.append(var16).toString();

         String var19;
         Pattern var18;
         try {
            var18 = Pattern.compile("^[0-9.]*$");
         } catch (PatternSyntaxException var21) {
            var19 = null;
            return var19;
         }

         if(!var18.matcher(var17).find()) {
            var19 = "";
         } else {
            var19 = null;
         }

         return var19;
      }
   }
}
